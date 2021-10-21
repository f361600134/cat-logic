package com.cat.server.game.module.shadow.domain;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.data.proto.PBPlayer.PBPlayerProfile;
import com.cat.server.game.module.player.domain.Player;
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
	 * 数据替换
	 */
	public void replacement(Player player) {
		other.setNickName(player.getNickName());
		other.setPlayerId(player.getPlayerId());
		other.setVip(player.getVip());
		//this.setPower(player.getpo());
		other.setLevel(player.getLevel());
	}

	/**
	 * 影子对象转协议对象
	 * @return
	 */
	public PBPlayerProfile toProto() {
		PBPlayerProfile.Builder builder = PBPlayerProfile.newBuilder();
		builder.setPlayerId(other.getPlayerId());
		builder.setNickName(other.getNickName());
		builder.setLevel(other.getLevel());
		builder.setVip(other.getVip());
		builder.setPower(other.getPower());
		return builder.build();
	}
	
}
