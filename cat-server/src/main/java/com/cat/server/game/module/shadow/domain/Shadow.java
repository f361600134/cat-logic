package com.cat.server.game.module.shadow.domain;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.data.proto.PBPlayer.PBPlayerProfile;
import com.cat.server.utils.TimeUtil; 

/**
* @author Jeremy
*/
@PO(name = "shadow")
public class Shadow extends ShadowPo implements IPersistence{
	
	/**
	 * 玩家信息
	 */
	@Column(PROP_DATA)
	private OtherPlayer other;
	
	public Shadow() {
		this.updateTime = TimeUtil.now();
		this.other = new OtherPlayer();
	}
	
	public Shadow(long playerId) {
		this.playerId = playerId;
		this.updateTime = TimeUtil.now();
		this.other = new OtherPlayer();
	}
	
	public OtherPlayer getOther() {
		return other;
	}

	public void setOther(OtherPlayer other) {
		this.other = other;
	}
	
	/**
	 * 创建一个影子对象
	 * @param playerId
	 * @return
	 */
	public static Shadow create(long playerId) {
		Shadow shadow = new Shadow(playerId);
		shadow.getOther().setLevel((short)1);
		shadow.getOther().setNickName("Jeremy");
		shadow.getOther().setVip((short)1);
		shadow.getOther().setPlayerId(playerId);
		shadow.save();
		return shadow;
	}
	
	/**
	 * 影子对象转协议对象
	 * @return
	 */
	public PBPlayerProfile toProto() {
		return other.toProto();
	}
	
}
