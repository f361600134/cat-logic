package com.cat.server.game.module.family;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.cat.server.game.module.group.DefaultMember;
import com.cat.server.game.module.rank.domain.Rank;


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
	
	/**	公共的线程池处理器*/
	@Autowired
	private TokenTaskQueueExecutor defaultExecutor;
	
	/**
	 * key: 家族id
	 * value: 家族
	 */
	private Map<Long, Family> familyMap = new ConcurrentHashMap<>();
	/**
	 * key:familyName
	 * value: familyId
	 */
	private Map<String, Long> familyNameMap = new ConcurrentHashMap<>();
	/**
	 * 加入此Map,可以精确查询
	 * key:familyTag
	 * value: familyId
	 */
	private Map<String, Long> familyTagMap = new ConcurrentHashMap<>();
	/**
	 * key: 玩家id
	 * value: 家族id
	 */
	private Map<Long, Long> familyPlayerIdMap = new ConcurrentHashMap<>();
	
	/**
	 * 从数据库获取
	 * @return void  
	 * @date 2021年5月10日下午10:12:41
	 */
	private List<Family> loadAllFamilys() {
		final int serverId = serverConfig.getServerId();
		String[] cols = new String[] {Rank.PROP_CURSERVERID};
		List<Family> list = dataProcess.selectByIndex(Family.class, cols, new Object[] {serverId});
		return list;
	}
	
	/**
	 * 创建家族
	 */
	public Family createFamily(long playerId, String name) throws Exception{
		Future<Family> future = defaultExecutor.submit(0, ()->{
			//			实例化一个新的家族
			long id = this.generator.nextId();
			Family family = Family.create(id, name);
			
			//	设置创建家族的成员为族长
			DefaultMember familymember = new DefaultMember();
			familymember.setPlayerId(playerId);
			familymember.setPosition(FamilyPosition.PATRIARCH.getValue());
			family.getMembers().put(playerId, familymember);
			
			//	丢进缓存
			this.familyMap.put(id, family);
			this.familyNameMap.put(name, id);
			this.familyPlayerIdMap.put(playerId, id);
			
			//	生成家族日志
			return family;
		});
		return future.get();
	}
	
	/**
	 * 修改家族名, 
	 */
	public ErrorCode editFamilyName(long playerId,String name) throws Exception{
		Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
			final Family family = getFamilyByPlayerId(playerId);
			if (family == null) {
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			//	重名判断
			if (this.familyNameMap.containsKey(name)) {
				return ErrorCode.FAMILY_NAME_EXIST;
			}
			//	获取到家族信息
			family.setName(name);
			//	丢进缓存
			this.familyNameMap.put(name, family.getId());
			//	生成家族日志
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
			final Family family = getFamilyByPlayerId(playerId);
			if (family == null) {
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			if (this.familyTagMap.containsKey(tag)) {
				return ErrorCode.FAMILY_NAME_EXIST;
			}
			//	设置新的家族号
			family.setTag(tag);
			//	丢进缓存
			this.familyTagMap.put(tag, family.getId());
			//	生成家族日志
			return ErrorCode.SUCCESS;
		});
		return future.get();
	}
	
	/**
	 * 查找家族
	 * @param keyword 关键字
	 * @return
	 */
	public Collection<Long> searchFamily(String keyword){
		List<Long> result = new ArrayList<>();
		//	先精确查找
		Long familyId = familyTagMap.get(keyword);
		if (familyId != null) {
			result.add(familyId);
		}
		//	再模糊查找
		for (String familyName : familyNameMap.keySet()) {
			//	只要包含关键字,就返回
			if (familyName.contains(keyword)) {
				result.add(familyNameMap.get(familyName));
			}
		}
		return result;
	}
	
	/**
	 * 申请进入家族
	 * @param keyword 关键字
	 * @return
	 */
	public ErrorCode applyJoinFamily(long playerId, long familyId) throws Exception{
		Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
			final Family family = getFamilyByFamilyId(familyId);
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
	 * @param keyword 关键字
	 * @return
	 */
	public ErrorCode exitFamily(long playerId) throws Exception{
		Future<ErrorCode> future = defaultExecutor.submit(0, ()->{
			final Family family = getFamilyByPlayerId(playerId);
			if (family == null) {
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			//	族长判断,族长要先退位才能退出家族
			if (family.getPosition(playerId) == FamilyPosition.PATRIARCH.getValue()) {
				return ErrorCode.FAMILY_FIRE_SELF;
			}
			family.getMembers().remove(playerId);
			familyPlayerIdMap.remove(playerId);
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
			final Family family = getFamilyByPlayerId(playerId);
			if (family == null) {
				return ErrorCode.FAMILY_NO_FAMILY;
			}
			//	被开的玩家是否存在
			if (family.getPosition(firePlayerId) <= 0) {
				return ErrorCode.FAMILY_PLAYER_NOT_EXIST;
			}
			family.getMembers().remove(firePlayerId);
			familyPlayerIdMap.remove(firePlayerId);
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
		List<Family> loadAllFamilys = loadAllFamilys();
		loadAllFamilys.forEach((family)->{
			this.familyMap.put(family.getId(), family);
			this.familyNameMap.put(family.getName(), family.getId());
			this.familyTagMap.put(family.getTag(), family.getId());
			family.getMembers().keySet().forEach((playerId)->{
				this.familyPlayerIdMap.put(playerId, family.getId());
			});
		});
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
	public long getPlayerFamilyId(long playerId){
		return familyPlayerIdMap.getOrDefault(playerId, 0L);
	}
	
	/**
	 * 根据玩家id获取到家族id
	 * @param playerId
	 * @return
	 */
	public Family getFamilyByFamilyId(long familyId){
		return familyMap.get(familyId);
	}
	
	/**
	 * 根据玩家id获取到家族
	 * @param playerId
	 * @return
	 */
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