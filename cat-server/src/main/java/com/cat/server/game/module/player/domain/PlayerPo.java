package com.cat.server.game.module.player.domain;

import com.cat.orm.core.base.BasePo;

/**
* PlayerPo
* @author Jeremy
*/
public abstract class PlayerPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_ACCOUNTNAME = "accountName";
	public static final String PROP_INPUTNAME = "inputName";
	public static final String PROP_NICKNAME = "nickName";
	public static final String PROP_CHANNEL = "channel";
	public static final String PROP_REGTIME = "regTime";
	public static final String PROP_LASTTIME = "lastTime";
	public static final String PROP_CURSERVERID = "curServerId";
	public static final String PROP_INITSERVERID = "initServerId";
	public static final String PROP_LEVEL = "level";
	public static final String PROP_EXP = "exp";
	public static final String PROP_VIP = "vip";
	public static final String PROP_VIPEXP = "vipExp";
	public static final String PROP_STATUS = "status";
	public static final String PROP_PROPERTIESTR = "propertieStr";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_ACCOUNTNAME,
			PROP_INPUTNAME,
			PROP_NICKNAME,
			PROP_CHANNEL,
			PROP_REGTIME,
			PROP_LASTTIME,
			PROP_CURSERVERID,
			PROP_INITSERVERID,
			PROP_LEVEL,
			PROP_EXP,
			PROP_VIP,
			PROP_VIPEXP,
			PROP_STATUS,
			PROP_PROPERTIESTR,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_PLAYERID,
			PROP_ACCOUNTNAME,
			PROP_NICKNAME,
			PROP_INITSERVERID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			PROP_ACCOUNTNAME,
			PROP_NICKNAME,
			PROP_INITSERVERID,
			};
	
	
	/** 角色ID*/
	protected long playerId;
	/** 角色账号,服务器生成的唯一账号,全服唯一*/
	protected String accountName;
	/** 玩家输入账号,记录下来用于排查玩家信息,玩家输入账号*/
	protected String inputName;
	/** 角色昵称,游戏内角色自定义名称,全服唯一*/
	protected String nickName;
	/** 渠道Id*/
	protected int channel;
	/** 注册时间*/
	protected java.util.Date regTime;
	/** 最后登陆时间*/
	protected java.util.Date lastTime;
	/** 当前服务器Id*/
	protected int curServerId;
	/** 玩家创角时的,最原始服务器Id*/
	protected int initServerId;
	/** 等级*/
	protected short level;
	/** 经验*/
	protected int exp;
	/** VIP等级*/
	protected short vip;
	/** VIP经验*/
	protected int vipExp;
	/** 角色状态,0:默认,1:禁言,2:封号,4:删号,8:其他*/
	protected byte status;
	/** 资源数据json格式*/
	protected String propertieStr;
	
	public PlayerPo(){
		this.accountName = "";
		this.inputName = "";
		this.nickName = "";
		this.propertieStr = "";
	}
	
	/** 角色ID **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 角色账号,服务器生成的唯一账号,全服唯一 **/
	public String getAccountName(){
		return this.accountName;
	}
	
	public void setAccountName(String accountName){
		this.accountName = accountName;
	}
	
	/** 玩家输入账号,记录下来用于排查玩家信息,玩家输入账号 **/
	public String getInputName(){
		return this.inputName;
	}
	
	public void setInputName(String inputName){
		this.inputName = inputName;
	}
	
	/** 角色昵称,游戏内角色自定义名称,全服唯一 **/
	public String getNickName(){
		return this.nickName;
	}
	
	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	
	/** 渠道Id **/
	public int getChannel(){
		return this.channel;
	}
	
	public void setChannel(int channel){
		this.channel = channel;
	}
	
	/** 注册时间 **/
	public java.util.Date getRegTime(){
		return this.regTime;
	}
	
	public void setRegTime(java.util.Date regTime){
		this.regTime = regTime;
	}
	
	/** 最后登陆时间 **/
	public java.util.Date getLastTime(){
		return this.lastTime;
	}
	
	public void setLastTime(java.util.Date lastTime){
		this.lastTime = lastTime;
	}
	
	/** 当前服务器Id **/
	public int getCurServerId(){
		return this.curServerId;
	}
	
	public void setCurServerId(int curServerId){
		this.curServerId = curServerId;
	}
	
	/** 玩家创角时的,最原始服务器Id **/
	public int getInitServerId(){
		return this.initServerId;
	}
	
	public void setInitServerId(int initServerId){
		this.initServerId = initServerId;
	}
	
	/** 等级 **/
	public short getLevel(){
		return this.level;
	}
	
	public void setLevel(short level){
		this.level = level;
	}
	
	/** 经验 **/
	public int getExp(){
		return this.exp;
	}
	
	public void setExp(int exp){
		this.exp = exp;
	}
	
	/** VIP等级 **/
	public short getVip(){
		return this.vip;
	}
	
	public void setVip(short vip){
		this.vip = vip;
	}
	
	/** VIP经验 **/
	public int getVipExp(){
		return this.vipExp;
	}
	
	public void setVipExp(int vipExp){
		this.vipExp = vipExp;
	}
	
	/** 角色状态,0:默认,1:禁言,2:封号,4:删号,8:其他 **/
	public byte getStatus(){
		return this.status;
	}
	
	public void setStatus(byte status){
		this.status = status;
	}
	
	/** 资源数据json格式 **/
	public String getPropertieStr(){
		return this.propertieStr;
	}
	
	public void setPropertieStr(String propertieStr){
		this.propertieStr = propertieStr;
	}
	
	
	@Override
	public String toString() {
		return "Player [playerId= "+ playerId +", accountName= "+ accountName +", inputName= "+ inputName +", nickName= "+ nickName +", channel= "+ channel
				 +", regTime= "+ regTime +", lastTime= "+ lastTime +", curServerId= "+ curServerId +", initServerId= "+ initServerId +", level= "+ level
				 +", exp= "+ exp +", vip= "+ vip +", vipExp= "+ vipExp +", status= "+ status +", propertieStr= "+ propertieStr
				+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getPlayerId(),
		getAccountName(),
		getInputName(),
		getNickName(),
		getChannel(),
		getRegTime(),
		getLastTime(),
		getCurServerId(),
		getInitServerId(),
		getLevel(),
		getExp(),
		getVip(),
		getVipExp(),
		getStatus(),
		getPropertieStr(),
		};
	}
	
	@Override
	public Object key() {
		return getPlayerId();
	}
	
	@Override
	public String keyColumn() {
		return PROP_PLAYERID;
	}

	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}
	
	public Object[] keyAndIndexValues() {
		return new Object[] {
			getPlayerId(),
			getAccountName(),
			getNickName(),
			getInitServerId(),
		};
	}
	
	@Override
	public String[] indexColumn() {
		return INDEX_ALL;
	}
	
	@Override
	public String[] indexValues() {
		return new String[] {
			PROP_ACCOUNTNAME,
			PROP_NICKNAME,
			PROP_INITSERVERID,
		};
	}
	
	@Override
	public String cacheId() {
		return String.valueOf(getPlayerId());
	}
	
}
