package com.cat.server.game.module.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigFunction;
import com.cat.server.game.data.proto.PBFunction.ReqFunctionInfo;
import com.cat.server.game.data.proto.PBFunction.ReqFunctionReddotInfo;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.function.domain.Function;
import com.cat.server.game.module.function.domain.FunctionData;
import com.cat.server.game.module.function.domain.FunctionDomain;
import com.cat.server.game.module.function.domain.FunctionReddot;
import com.cat.server.game.module.function.proto.RespFunctionInfoBuilder;
import com.cat.server.game.module.function.proto.RespFunctionReddotInfoBuilder;
import com.cat.server.game.module.function.reddot.IFunctionReddot;
import com.cat.server.game.module.player.IPlayerService;


/**
 * Function控制器
 * @author Jeremy
 */
@Service
public class FunctionService implements IFunctionService  {
	
	private static final Logger log = LoggerFactory.getLogger(FunctionService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private FunctionManager manager;
 
	private Map<Integer, IFunctionReddot> functionReddotMap = new HashMap<>();
	
	@Autowired
	public void initServiceMap(List<IFunctionReddot> functionReddots) {
		for (IFunctionReddot functionReddot : functionReddots) {
			this.functionReddotMap.put(functionReddot.getReddotId(), functionReddot);
		}
	}
	
	/**
	 * 登陆
	 */
	public void onLogin(long playerId) {
		FunctionDomain domain = manager.getOrLoadDomain(playerId);
		if (domain == null) {
			return;
		}
		//检测红点信息
		
		this.responseFunctionInfo(domain);
		this.responseFunctionReddotInfo(domain);
	}
	
	
	/**
	 * 当玩家离线,移除掉道具模块数据
	 * @param playerId
	 */
	public void onLogout(long playerId) {
		manager.remove(playerId);
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
		this.responseFunctionReddotInfo(domain, reddotIds);
	}
	
	/////////////////业务代码//////////////////////////
	
	/**
	 * 更新功能信息<br>
	 * 实际上可以把功能信息, 更新给客户端, 如重置时间<br>
	 * 届时,客户端直接读取此时间用于展示给玩家重置时间, 如果需要的话.
	 */
	public void responseFunctionInfo(FunctionDomain domain) {
		Function bean = domain.getBean();
		Map<Integer, FunctionData> functionDataMap = bean.getFunctionDataMap();
		if (functionDataMap.isEmpty()) {
			return;
		}
		final long playerId = domain.getId();
		RespFunctionInfoBuilder builder = RespFunctionInfoBuilder.newInstance();
		for (FunctionData functionData : functionDataMap.values()) {
			boolean isOpen = this.checkOpen(playerId, functionData.getModuleId());
			builder.addFunctions(functionData.toProto(isOpen));
		}
		playerService.sendMessage(playerId, builder);
	}
	
	/**
	 * 更新功能红点信息<br>
	 */
	public void responseFunctionReddotInfo(FunctionDomain domain) {
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
	public void responseFunctionReddotInfo(FunctionDomain domain, List<Integer> reddotIds) {
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
		//TODO Somthing.
		this.responseFunctionInfo(domain);
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
		//TODO Somthing.
		this.responseFunctionInfo(domain);
		return ErrorCode.SUCCESS;
	}

	/////////////接口方法////////////////////////
	
	/**
	 * 校验功能是否开启<br>
	 */
	@Override
	public boolean checkOpen(long playerId, int functionId) {
		ConfigFunction config = ConfigManager.getInstance().getConfig(ConfigFunction.class, functionId);
		return config.getCondition().accept(playerId);
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
	
}
 
 
