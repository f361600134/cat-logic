package com.cat.server.game.module.family;

import com.cat.server.game.module.family.assist.FamilyPrivilege;
import com.cat.server.game.module.family.domain.Family;

/**
 * PlayerFamily接口类,外部需要调用,需要在此接口定义方法,由此模块负责人实现
 */
public interface IFamilyService {
	
	
	/**
	 * 根据玩家id获取到家族id
	 * @param playerId
	 * @return
	 */
	public long getPlayerFamilyId(long playerId);
	
	/**
	 * 根据玩家id获取到家族id
	 * @param playerId
	 * @return
	 */
	public Family getFamilyByFamilyId(long familyId);
	
	/**
	 * 根据玩家id获取到家族
	 * @param playerId
	 * @return
	 */
	public Family getFamilyByPlayerId(long playerId);
	
	/**
	 * 根据玩家id获取到家族id
	 * @param playerId
	 * @return
	 */
	public int getPlayerPosition(long playerId);
	
	/**
	 * 是否存在某个权限
	 * @return
	 */
	public boolean hasPrivilege(long playerId, FamilyPrivilege familyPrivilege);
	
}