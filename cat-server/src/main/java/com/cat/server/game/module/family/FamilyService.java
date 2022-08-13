package com.cat.server.game.module.family;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.api.core.task.TokenTaskQueueExecutor;
import com.cat.net.core.executor.DisruptorStrategy;
import com.cat.server.common.ServerConfig;
import com.cat.server.core.lifecycle.ILifecycle;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.family.assist.FamilyPosition;
import com.cat.server.game.module.family.assist.FamilyPrivilege;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.family.domain.FamilyDomain;
import com.cat.server.game.module.function.IFunctionResetService;
import com.cat.server.game.module.group.domain.DefaultApply;
import com.cat.server.game.module.player.IPlayerService;


/**
 * FamilyService
 * 家族相关的公共操作, 都是线程安全的, 丢进公共线程池去处理
 */
@Service
class FamilyService implements IFamilyService, ILifecycle, IFunctionResetService{
	
	private static final Logger logger = LoggerFactory.getLogger(FamilyService.class);
	
	@Autowired
	private ServerConfig serverConfig;

	@Autowired
	private FamilyManager familyManager;
	
	@Autowired
	private IPlayerService playerService;
	
	/**	公共的线程池处理器*/
	@Autowired
	private TokenTaskQueueExecutor defaultExecutor;
	
	/**
	 * 创建家族
	 */
	public ResultCodeData<Family> createFamily(long playerId, String name) throws Exception{
		FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
		Future<ResultCodeData<Family>> future = defaultExecutor.submit(0, ()->{
			if (domain.containsName(name)){
				return ResultCodeData.of(ErrorCode.FAMILY_NAME_EXIST);
			}
			Family family = domain.create(playerId, name);
			//TODO	生成家族日志
			return ResultCodeData.of(ErrorCode.SUCCESS, family);
		});
		return future.get();
	}
	
	/**
	 * 修改家族名
	 */
	public ErrorCode editFamilyName(long playerId, String name) throws Exception{
		Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
			FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
			if (domain == null){
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			final Family family = domain.getGroupByPlayerId(playerId);
			if (family == null) {
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			if (domain.containsName(name)) {
				return ErrorCode.FAMILY_NAME_EXIST;
			}
			domain.rename(family.getId(), name);
			//TODO	生成家族日志
			return ErrorCode.SUCCESS;
		});
		return future.get();
	}
	
	/**
	 * 修改家族号, 只能族长修改,并且只能修改一次
	 * 家族号只能是字母数字,不允许有任何特殊符号
	 */
	public ErrorCode editFamilyTag(long playerId, String tag) throws Exception{
		Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
			FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
			if (domain == null){
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			final Family family = domain.getGroupByPlayerId(playerId);
			if (family == null) {
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			if (domain.containsTag(tag)) {
				return ErrorCode.FAMILY_TAG_EXIST;
			}
			domain.changeTag(family.getId(), tag);
			//TODO	生成家族日志
			return ErrorCode.SUCCESS;
		});
		return future.get();
	}
	
	/**
	 * 查找家族
	 * @param keyword 关键字
	 * @return 家族集合
	 */
	public Collection<Family> searchFamily(String keyword){
		FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
		if (domain == null){
			return Collections.emptyList();
		}
		return domain.searchGroup(keyword);
	}
	
	/**
	 * 申请进入家族
	 * @param playerId 申请玩家id
	 * @return familyId 申请家族id
	 */
	public ErrorCode applyJoinFamily(long playerId, long familyId) throws Exception{
		Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
			FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
			if (domain == null){
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			final Family family = domain.get(familyId);
			if (family == null) {
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			//TODO	判断是否符合进入家族的条件
			// 请求加入申请列表,有权限的人审批后, 把指定玩家加入到家族内, 并通知该玩家.
			DefaultApply familyApply = DefaultApply.create(playerId);
			family.getApplys().put(familyApply.getPlayerId(), familyApply);
			// 申请成功, 发送事件通知长老,族长进行审批, 通过红点系统通知
			family.getMembers().forEach((memberId, member) ->{
				if (this.hasPrivilege(memberId, FamilyPrivilege.APPROVE)) {
					DisruptorStrategy.get(DisruptorStrategy.SINGLE).execute(playerService.getSessionId(memberId), ()->{
						//TODO 发送红点事件,通知家族新申请
						this.checkReddot(playerId);
					});
				}
			});
			return ErrorCode.SUCCESS;
		});
		return future.get();
	}
	
	/**
	 * 审批进入家族的请求
	 * @param playerId 当前审批的玩家id
	 * @param familyId 家族id
	 * @param applyId 申请的玩家id
	 * @param type 0:不同意,1:同意
	 */
	public ErrorCode approveApplys(long playerId, long applyId, int type) throws Exception{
		Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
			//权限判断--可以放在playerFamilyService判断
			boolean bool = this.hasPrivilege(playerId, FamilyPrivilege.APPROVE);
			if (!bool) {
				return ErrorCode.FAMILY_NO_PRIVILEGE;
			}
			FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
			if (domain == null){
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			final Family family = domain.getGroupByPlayerId(playerId);
			if (family == null) {
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			DefaultApply apply = family.getApplys().get(applyId);
			if (apply == null) {
				return ErrorCode.FAMILY_APPLY_NOT_EXIST;
			}
			//判断对方是否进入了新的家族
			if (domain.getGroupIdByPlayerId(applyId) > 0) {
				return ErrorCode.FAMILY_APPLYER_HAS_FAMILY;
			}
			if (type == 0) {
				//to do nothing if type is 0.
			}else if (type == 1) {
				//全部判断完成, 允许其进入家族
				domain.enter(family.newMember(applyId), family.getId());
				//1.1生成家族日志
				//1.2更新家族总贡献
			}
			
			return ErrorCode.SUCCESS;
		});
		return future.get();
	}
	
	/**
	 * 退出家族
	 * @param playerId 退出家族的玩家id
	 * @return 错误码
	 */
	public ErrorCode exitFamily(long playerId) throws Exception{
		Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
			FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
			if (domain == null){
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			final Family family = domain.getGroupByPlayerId(playerId);
			if (family == null) {
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			//	族长判断,族长要先退位才能退出家族
			if (family.getPosition(playerId) == FamilyPosition.PATRIARCH.getValue()) {
				return ErrorCode.FAMILY_FIRE_SELF;
			}
			domain.exit(playerId, family.getId());
			//	生成家族日志
			return ErrorCode.SUCCESS;
		});
		return future.get();
	}
	
	/**
	 * 开除玩家
	 * @param playerId 操作的玩家id
	 * @param firePlayerId  被开除的玩家id
	 * @return 错误码
	 */
	public ErrorCode fire(long playerId, long firePlayerId) throws Exception{
		Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
			FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
			if (domain == null){
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			final Family family = domain.getGroupByPlayerId(playerId);
			if (family == null) {
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			//	被开的玩家是否存在
			if (family.getPosition(firePlayerId) <= 0) {
				return ErrorCode.FAMILY_PLAYER_NOT_EXIST;
			}
			domain.exit(firePlayerId, family.getId());
			//	生成家族日志
			return ErrorCode.SUCCESS;
		});
		return future.get();
	}
	
	/**
	 * 是否存在某个权限
	 * @return true 有该权限, false 无该权限
	 */
	public boolean hasPrivilege(long playerId, long privilege) {
		Family family = getFamilyByPlayerId(playerId);
		if (family == null) {
			return false;
		}
		return family.hasPrivilege(playerId, privilege);
	}
	
	
	/////////////接口方法////////////////////////
	@Override
	public void start() throws Throwable {
		familyManager.init();
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}
	
	@Override
	public int getPlayerPosition(long playerId) {
		Family family = getFamilyByPlayerId(playerId);
		if (family == null) {
			return 0;
		}
		return family.getPosition(playerId);
	}
	
	@Override
	public long getPlayerFamilyId(long playerId){
		FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
		if (domain == null){
			return 0;
		}
		return domain.getGroupIdByPlayerId(playerId);
	}
	
	@Override
	public Family getFamilyByFamilyId(long familyId){
		FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
		if (domain == null){
			return null;
		}
		return domain.get(familyId);
	}
	
	@Override
	public Family getFamilyByPlayerId(long playerId){
		final long familyId = getPlayerFamilyId(playerId);
		if (familyId == 0) {
			return null;
		}
		return getFamilyByFamilyId(familyId);
	}
	
	@Override
	public boolean hasPrivilege(long playerId, FamilyPrivilege familyPrivilege) {
		return hasPrivilege(playerId, familyPrivilege.getPrivilege());
	}

	@Override
	public Collection<Long> getMemberIdsByFamilyId(long familyId) {
		Family family = getFamilyByFamilyId(familyId);
		if(family == null){
			return Collections.emptyList();
		}
		//返回一个复制的成员ids, 并不是一个视图
		return new ArrayList<>(family.getMembers().keySet());
	}

	@Override
	public int getModuleId() {
		return ModuleDefine.FAMILY.getModuleId();
	}

}