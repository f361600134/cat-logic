package com.cat.server.game.module.shadow.domain;

public class OtherPlayer {
	
	/** 玩家id*/
	private long playerId;
	/** 等级*/
	private short level;
	/** VIP等级*/
	private short vip;
	/** 角色昵称,游戏内角色自定义名称,全服唯一*/
	private String nickName;
	/** 战力*/
	private short power;
	
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public short getLevel() {
		return level;
	}
	public void setLevel(short level) {
		this.level = level;
	}
	public short getVip() {
		return vip;
	}
	public void setVip(short vip) {
		this.vip = vip;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public short getPower() {
		return power;
	}
	public void setPower(short power) {
		this.power = power;
	}
}
