package com.cat.server.game.module.family.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.family.assist.FamilyConstant;

/**
* @author Jeremy
*/
@PO(name = "player_family")
public class PlayerFamily extends PlayerFamilyPo implements IPersistence{

	public PlayerFamily() {

	}
	
	public PlayerFamily(long playerId) {
		this.playerId = playerId;
	}
	
	@Override
	public Object key() {
		return getPlayerId();
	}
	
	public String keyColumn() {
		return PROP_PLAYERID;
	}
	
	/**
	 * 获取下一次进入家族的时间
	 * @return
	 */
	public long getNextJoinTime() {
		return getLeaveTime() + FamilyConstant.JOIN_TIME_LIMIT;
	}
	
}
