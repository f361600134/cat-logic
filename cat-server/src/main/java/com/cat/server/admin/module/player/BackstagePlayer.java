package com.cat.server.admin.module.player;

/**
 * 后台玩家数据Dto
 * @author Jeremy
 */
public class BackstagePlayer {
	
	/** 角色ID*/
	private long playerId;
	/** 角色账号,服务器生成的唯一账号,全服唯一*/
	private String accountName;
	/** 玩家输入账号,记录下来用于排查玩家信息,玩家输入账号*/
	private String inputName;
	/** 角色昵称,游戏内角色自定义名称,全服唯一*/
	private String nickName;
	/** 渠道Id*/
	private int channel;
	/** 注册时间*/
	private long regTime;
	/** 最后登陆时间*/
	private long lastTime;
	/** 当前服务器Id*/
	private int curServerId;
	/** 玩家创角时的,最原始服务器Id*/
	private int initServerId;
	/** 等级*/
	private short level;
	/** 经验*/
	private int exp;
	/** VIP等级*/
	private short vip;
	/** VIP经验*/
	private int vipExp;
	/** 角色状态,0:默认,1:禁言,2:封号,4:删号,8:其他*/
	private byte status;
	/** 资源数据json格式*/
	private String propertieStr;
	
	
	public long getPlayerId() {
		return playerId;
	}
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getInputName() {
		return inputName;
	}
	public void setInputName(String inputName) {
		this.inputName = inputName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public long getRegTime() {
		return regTime;
	}
	public void setRegTime(long regTime) {
		this.regTime = regTime;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	public int getCurServerId() {
		return curServerId;
	}
	public void setCurServerId(int curServerId) {
		this.curServerId = curServerId;
	}
	public int getInitServerId() {
		return initServerId;
	}
	public void setInitServerId(int initServerId) {
		this.initServerId = initServerId;
	}
	public short getLevel() {
		return level;
	}
	public void setLevel(short level) {
		this.level = level;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public short getVip() {
		return vip;
	}
	public void setVip(short vip) {
		this.vip = vip;
	}
	public int getVipExp() {
		return vipExp;
	}
	public void setVipExp(int vipExp) {
		this.vipExp = vipExp;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public String getPropertieStr() {
		return propertieStr;
	}
	public void setPropertieStr(String propertieStr) {
		this.propertieStr = propertieStr;
	}
}
