package com.cat.server.game.module.family;

import com.cat.server.game.module.family.assist.FamilyPrivilege;
import com.cat.server.game.module.family.domain.Family;

import java.util.Collection;

/**
 * PlayerFamily接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 * @author Jeremy
 */
public interface IFamilyService {

	/**
	 * 根据玩家id获取到家族id
	 * @param playerId 玩家id
	 * @return 家族id
	 */
	public long getPlayerFamilyId(long playerId);
	
	/**
	 * 根据家族id获取到家族
	 * @param familyId 家族id
	 * @return 家族对象
	 */
	public Family getFamilyByFamilyId(long familyId);
	
	/**
	 * 根据玩家id获取到家族
	 * @param playerId 玩家id
	 * @return 家族对象
	 */
	public Family getFamilyByPlayerId(long playerId);
	
	/**
	 * 根据玩家id获取到家族的职位
	 * @param playerId 玩家id
	 * @return 职位
	 */
	public int getPlayerPosition(long playerId);
	
	/**
	 * 是否存在某个权限
	 * @param playerId 玩家id
	 * @param familyPrivilege 权限枚举
	 * @return true: 有
	 */
	public boolean hasPrivilege(long playerId, FamilyPrivilege familyPrivilege);

	/**
	 * 根据家族id获取到家族所有成员id
	 * @param familyId 家族id
	 * @return 家族所有成员id
	 */
	public Collection<Long> getMemberIdsByFamilyId(long familyId);
	
}