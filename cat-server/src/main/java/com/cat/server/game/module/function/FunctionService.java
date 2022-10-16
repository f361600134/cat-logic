package com.cat.server.game.module.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.server.IModuleManager;
import com.cat.server.game.data.config.local.ConfigFunction;
import com.cat.server.game.data.config.remote.ConfigFunctionSwitch;
import com.cat.server.game.data.proto.PBFunction.ReqFunctionInfo;
import com.cat.server.game.data.proto.PBFunction.ReqFunctionReddotInfo;
import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.helper.condition.ICondition;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.function.domain.Function;
import com.cat.server.game.module.function.domain.FunctionData;
import com.cat.server.game.module.function.domain.FunctionDomain;
import com.cat.server.game.module.function.domain.FunctionReddot;
import com.cat.server.game.module.function.proto.RespFunctionInfoBuilder;
import com.cat.server.game.module.function.proto.RespFunctionReddotInfoBuilder;
import com.cat.server.game.module.function.proto.RespFunctionSwitchNoticeBuilder;
import com.cat.server.game.module.function.reddot.IFunctionReddot;
import com.cat.server.game.module.player.IPlayerService;

/**
 * Function控制器
 * @author Jeremy
 */
@Service
class FunctionService implements IFunctionService  {
	
	private static final Logger log = LoggerFactory.getLogger(FunctionService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private FunctionManager manager;
	
	private Map<Integer, IFunctionReddot> functionReddotMap = new HashMap<>();
	private Map<Integer, IPlayerModuleService> moduleServiceMap = new HashMap<>();
	
	@Autowired
	public void initServiceMap(List<IFunctionReddot> functionReddots) {
		for (IFunctionReddot functionReddot : functionReddots) {
			this.functionReddotMap.put(functionReddot.getReddotId(), functionReddot);
		}
	}
	
	@Autowired
	public void initModuleServiceMap(List<IPlayerModuleService> playerModules) {
		for (IPlayerModuleService moduleService : playerModules) {
			this.moduleServiceMap.put(moduleService.getModuleId(), moduleService);
			log.info("FunctionService initModuleServiceMap, service:{}",moduleService.getClass());
		}
		log.info("FunctionService==============> moduleSize:{}", moduleServiceMap.size());
	}
	
	@Override
	public void responseModuleInfo(long playerId) {
		FunctionDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			return;
		}
		//登录重新计算一遍红点信息
		this.checkAllReddot(playerId);
		//响应功能解锁信息
		this.responseFunctionInfo(domain);
		//响应红点信息
		this.responseFunctionReddotInfo(domain);
	}
	
	/**
	 * 检测红点
	 * @param playerId
	 * @param reddotTypeEnum
	 */
	public void onCheckReddot(long playerId, int moduleId) {
		FunctionDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			return;
		}
		ConfigFunction config = ConfigManager.getInstance().getConfig(ConfigFunction.class, moduleId);
		if (config == null) {
			return;
		}
		int[] reddots = config.getReddots();
		if (ArrayUtils.isEmpty(reddots)) {
			return;
		}
		Function function = domain.getBean();
		List<Integer> reddotIds = new ArrayList<>();
		for (int reddotId : reddots) {
			IFunctionReddot functionReddot = functionReddotMap.get(reddotId);
			List<Integer> newReddot = functionReddot.checkReddot(playerId);
			boolean bool = function.replaceReddot(reddotId, newReddot);
			if (bool) {
				reddotIds.add(reddotId);
			}
		}
		if (!CollectionUtils.isEmpty(reddotIds)) {
			function.save();
			this.responseFunctionReddotInfo(domain, reddotIds);
		}
	}
	
	
	/**
	 * 检测功能开启<br>
	 * 当发生指定事件,如升级,解锁副本时, 检测功能控制表, 是否有新的功能解锁.
	 * 此检测忽略配置, 控制台屏蔽
	 * @param playerId
	 */
	public void onCheckFunctionOpen(long playerId) {
		FunctionDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			return;
		}
		List<Integer> functionIds = new ArrayList<>();
		Function function = domain.getBean();
		Map<Integer, ConfigFunction> configs = ConfigManager.getInstance().getAllConfigs(ConfigFunction.class);
		for (ConfigFunction config : configs.values()) {
			final int functionId = config.getId();
			FunctionData functionData = function.getFunctionData(functionId);
			//功能已开启, 忽略
			if (functionData.isOpen()) {
				continue;
			}
			final ICondition condition = config.getCondition();
			if (condition == null || condition.accept(playerId)) {
				//条件为null, 或不为null,可以解锁
				functionData.setOpen(true);
				functionIds.add(functionId);
			}
		}
		if (!CollectionUtils.isEmpty(functionIds)) {
			function.save();
			//通知客户端新功能开启
			this.responseFunctionInfo(domain, functionIds);
		}
	}
	
	/**
	 * 每日重置事件
	 * @param playerId 玩家id
	 */
	public void onPlayerDailyReset(long playerId, long time){
		FunctionDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			return;
		}
		boolean bool = this.checkAndReset(playerId, time);
		if (bool) {
			this.checkAllReddot(playerId);
			this.responseFunctionReddotInfo(domain);
		}
	}
	
	/**
	 * 当远程文件刷新,通知所有在綫玩家
	 */
	public void onRemoteConfigRefresh(Class<? extends IGameConfig> configClazz) {
		if (configClazz.isInstance(ConfigFunctionSwitch.class)) {
			return;
		}
		Map<Integer, RespFunctionSwitchNoticeBuilder> noticeMap = new HashMap<>();
		ConfigFunctionSwitch configFunctionSwith = ConfigManager.getInstance().getUniqueConfig(ConfigFunctionSwitch.class);
		for (Long playerId: playerService.getOnlinePlayerIds()) {
			int channel = playerService.getChannel(playerId);
			RespFunctionSwitchNoticeBuilder builder = noticeMap.computeIfAbsent(channel, k-> {
				RespFunctionSwitchNoticeBuilder ret = RespFunctionSwitchNoticeBuilder.newInstance();
				ret.addAllForceCloseIds(configFunctionSwith.getCloseFunctionIds(channel));
				return ret;
			});
			playerService.sendMessage(playerId, builder);
		}
	}
	
	/**
	 * 检测所有功能的红点
	 * @param playerId
	 * @param reddotTypeEnum
	 */
	protected void checkAllReddot(long playerId) {
		Map<Integer, ConfigFunction> configs = ConfigManager.getInstance().getAllConfigs(ConfigFunction.class);
		if (MapUtils.isEmpty(configs)) {
			return;
		}
		FunctionDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			return;
		}
		Function function = domain.getBean();
		for (ConfigFunction config : configs.values()) {
			//判断是否开启了此功能
			if (!this.checkOpen(playerId, config.getId())) {
				continue;
			}
			//配置的红点条件如果为空, 表示不需要计算红点
			int[] reddots = config.getReddots();
			if (ArrayUtils.isEmpty(reddots)) {
				continue;
			}
			//开始检测红点
			for (int reddotId : reddots) {
				IFunctionReddot functionReddot = functionReddotMap.get(reddotId);
				List<Integer> newReddot = functionReddot.checkReddot(playerId);
				function.replaceReddot(reddotId, newReddot);
			}
		}
		function.update();
	}
	
	/////////////////业务代码//////////////////////////
	
	/**
	 * 更新功能信息<br>
	 * 实际上可以把功能信息, 更新给客户端, 如重置时间<br>
	 * 届时,客户端直接读取此时间用于展示给玩家重置时间, 如果需要的话.
	 */
	protected void responseFunctionInfo(FunctionDomain domain) {
		Function bean = domain.getBean();
		Map<Integer, FunctionData> functionDataMap = bean.getFunctionDataMap();
		if (functionDataMap.isEmpty()) {
			return;
		}
		
		final long playerId = domain.getId();
		final int channel = playerService.getChannel(playerId);
		
		//判断后台强制关闭了功能
		ConfigFunctionSwitch configFunctionSwith = ConfigManager.getInstance().getUniqueConfig(ConfigFunctionSwitch.class);
		Collection<Integer> forceCloseIds = configFunctionSwith == null ? Collections.emptyList() 
				: configFunctionSwith.getCloseFunctionIds(channel);
		
		RespFunctionInfoBuilder builder = RespFunctionInfoBuilder.newInstance();
		builder.addAllFunctionIds(domain.getFunctionIds());
		builder.addAllForceCloseIds(forceCloseIds);
		
		playerService.sendMessage(playerId, builder);
	}
	
	/**
	 * 更新指定功能信息<br>
	 * @param functionIds 玩家已开启的功能id列表
	 */
	protected void responseFunctionInfo(FunctionDomain domain, List<Integer> functionIds) {
		RespFunctionInfoBuilder builder = RespFunctionInfoBuilder.newInstance();
		builder.addAllFunctionIds(functionIds);
		playerService.sendMessage(domain.getId(), builder);
	}
	
	/**
	 * 更新功能红点信息<br>
	 */
	protected void responseFunctionReddotInfo(FunctionDomain domain) {
		RespFunctionReddotInfoBuilder builder = RespFunctionReddotInfoBuilder.newInstance();
		Function bean = domain.getBean();
		for (FunctionReddot reddot : bean.getReddotMap().values()) {
			builder.addFunctionReddots(reddot.toProto());
		}
		playerService.sendMessage(domain.getId(), builder);
	}
	
	/**
	 * 更新指定功能红点信息<br>
	 * @param reddotIds 红点id列表
	 */
	protected void responseFunctionReddotInfo(FunctionDomain domain, List<Integer> reddotIds) {
		RespFunctionReddotInfoBuilder builder = RespFunctionReddotInfoBuilder.newInstance();
		Function bean = domain.getBean();
		reddotIds.forEach(reddotId->{
			FunctionReddot reddot = bean.getReddotMap().get(reddotId);
			if (reddot == null) {
				return;
			}
			builder.addFunctionReddots(reddot.toProto());
		});
		playerService.sendMessage(domain.getId(), builder);
	}
	
	/**
	* 请求功能红点信息
	* @param long playerId
	* @param ReqFunctionReddotInfo req
	* @param RespFunctionReddotInfoResp ack
	*/
	public ErrorCode reqFunctionReddotInfo(long playerId, ReqFunctionReddotInfo req, RespFunctionReddotInfoBuilder ack){
		FunctionDomain domain = manager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.DOMAIN_IS_NULL;
		}
		this.responseFunctionReddotInfo(domain);
		return ErrorCode.SUCCESS;
	}
	
	
	/**
	* 请求功能控制信息
	* @param long playerId
	* @param ReqFunctionInfo req
	* @param RespFunctionInfoResp ack
	*/
	public ErrorCode reqFunctionInfo(long playerId, ReqFunctionInfo req, RespFunctionInfoBuilder ack){
		FunctionDomain domain = manager.getDomain(playerId);
		if (domain == null) {
			return ErrorCode.DOMAIN_IS_NULL;
		}
		this.responseFunctionInfo(domain);
		return ErrorCode.SUCCESS;
	}

	/////////////接口方法////////////////////////
	
	/**
	 * 校验功能是否开启<br>
	 */
	@Override
	public boolean checkOpen(long playerId, int functionId) {
		//判断后台强制关闭了功能
		ConfigFunctionSwitch configFunctionSwith = ConfigManager.getInstance().getUniqueConfig(ConfigFunctionSwitch.class);
		if (configFunctionSwith.isClose(playerService.getChannel(playerId), functionId)) {
			return false;
		}
		ConfigFunction configFunction = ConfigManager.getInstance().getConfig(ConfigFunction.class, functionId);
		//判断配置屏蔽了功能
		if (configFunction.getShield() == 1) {
			return false;
		}
		//判断玩家有没有解锁此功能
		FunctionDomain domain = manager.getDomain(playerId);
		if (domain == null) {
			return false;
		}
		return domain.checkFunctionOpen(functionId);
	}

	@Override
	public long getLastResetTime(long playerId, int functionId) {
		FunctionDomain domain = this.manager.getDomain(playerId);
		if (domain == null) {
			return 0L;
		}
		Function function = domain.getBean();
		FunctionData functionData = function.getFunctionData(functionId);
		return functionData.getResetTime();
	}

	@Override
	public void setLastResetTime(long playerId, int functionId, long now) {
		FunctionDomain domain = this.manager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		Function function = domain.getBean();
		FunctionData functionData = function.getFunctionData(functionId);
		functionData.setResetTime(now);
		function.update();
	}

	@Override
	public IModuleManager<Long, ?> getModuleManager() {
		return manager;
	}

	@Override
	public int getModuleId() {
		return ModuleDefine.FUNCTION.getModuleId();
	}

	@Override
	public void responseModuleInfo(long playerId, int functionId) {
		IPlayerModuleService playerModuleService = moduleServiceMap.get(functionId);
		playerModuleService.responseModuleInfo(playerId);
	}
	
}
 
 
