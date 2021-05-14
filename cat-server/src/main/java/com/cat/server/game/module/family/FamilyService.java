package com.cat.server.game.module.family;

import com.cat.orm.core.db.process.IDataProcess;
import com.cat.server.common.ServerConfig;
import com.cat.server.core.lifecycle.Lifecycle;
import com.cat.server.core.task.TokenTaskQueueExecutor;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.uuid.SnowflakeGenerator;
import com.cat.server.game.module.family.assist.FamilyPosition;
import com.cat.server.game.module.family.assist.FamilyPrivilege;
import com.cat.server.game.module.family.domain.Family;
import com.cat.server.game.module.family.domain.FamilyApply;
import com.cat.server.game.module.family.domain.FamilyDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Future;


/**
 * FamilyService
 * 家族相关的公共操作, 都是线程安全的, 丢进公共线程池去处理
 */
@Service
class FamilyService implements IFamilyService, Lifecycle{
	
	private static final Logger logger = LoggerFactory.getLogger(FamilyService.class);
	
	@Autowired
	private SnowflakeGenerator generator;
	
	@Autowired
	private IDataProcess dataProcess;
	
	@Autowired
	private ServerConfig serverConfig;

	@Autowired
	private FamilyManager familyManager;
	
	/**	公共的线程池处理器*/
	@Autowired
	private TokenTaskQueueExecutor defaultExecutor;
	
	/**
	 * 创建家族
	 */
	public ErrorCode createFamily(long playerId, String name) throws Exception{
		FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
		Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
			if (domain.containsName(name)){
				return ErrorCode.FAMILY_NAME_EXIST;
			}
			domain.create(playerId, name);
			//	生成家族日志
			return ErrorCode.SUCCESS;
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
	 * @return
	 */
	public Collection<Family> searchFamily(String keyword){
		FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
		if (domain == null){
			return Collections.EMPTY_LIST;
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
			//	请求加入申请列表
			FamilyApply familyApply = FamilyApply.create(playerId);
			family.getApplys().put(familyApply.getPlayerId(), familyApply);
			return ErrorCode.SUCCESS;
		});
		return future.get();
	}
	
	/**
	 * 退出家族
	 * @param playerId 退出家族的玩家id
	 * @return
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
	 * @param playerId
	 * @param firePlayerId
	 * @return
	 * @throws Exception
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
	public int getPlayerPosition(long playerId) {
		Family family = getFamilyByPlayerId(playerId);
		if (family == null) {
			return 0;
		}
		return family.getPosition(playerId);
	}
	
	/**
	 * 根据玩家id获取到家族id
	 * @param playerId
	 * @return
	 */
	@Override
	public long getPlayerFamilyId(long playerId){
		FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
		if (domain == null){
			return 0;
		}
		return domain.getGroupIdByPlayerId(playerId);
	}
	
	/**
	 * 根据玩家id获取到家族id
	 * @param familyId
	 * @return
	 */
	@Override
	public Family getFamilyByFamilyId(long familyId){
		FamilyDomain domain = familyManager.getDomain(serverConfig.getServerId());
		if (domain == null){
			return null;
		}
		return domain.get(familyId);
	}
	
	/**
	 * 根据玩家id获取到家族
	 * @param playerId
	 * @return
	 */
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
	
}