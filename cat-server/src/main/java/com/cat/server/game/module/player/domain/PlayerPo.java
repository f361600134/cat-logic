package com.cat.server.game.module.player.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.orm.core.base.BasePo;
import com.cat.server.core.server.IPersistence;

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
	
	protected long playerId;//角色ID
	protected String accountName;//角色账号,服务器生成的唯一账号,全服唯一
	protected String inputName;//玩家输入账号,记录下来用于排查玩家信息,玩家输入账号
	protected String nickName;//角色昵称,游戏内角色自定义名称,全服唯一
	protected int channel;//渠道Id
	protected java.util.Date regTime;//注册时间
	protected java.util.Date lastTime;//最后登陆时间
	protected int curServerId;//当前服务器Id
	protected int initServerId;//玩家创角时的,最原始服务器Id
	protected int level;//等级
	protected int exp;//经验
	protected int vip;//VIP等级
	protected int vipExp;//VIP经验
	protected int status;//角色状态,0:默认,1:禁言,2:封号,4:删号,8:其他
	protected String propertieStr;//资源数据json格式
	
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
	public int getLevel(){
		return this.level;
	}
	
	public void setLevel(int level){
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
	public int getVip(){
		return this.vip;
	}
	
	public void setVip(int vip){
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
	public int getStatus(){
		return this.status;
	}
	
	public void setStatus(int status){
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
		return new String[] {
		"`playerId`",
		"`accountName`",
		"`inputName`",
		"`nickName`",
		"`channel`",
		"`regTime`",
		"`lastTime`",
		"`curServerId`",
		"`initServerId`",
		"`level`",
		"`exp`",
		"`vip`",
		"`vipExp`",
		"`status`",
		"`propertieStr`",
		};
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
	public String[] indexColumn() {
		return new String[] {
			PROP_ACCOUNTNAME,
			PROP_NICKNAME,
			PROP_INITSERVERID,
		};
	}
	
	@Override
	public Object[] indexValues() {
		return new Object[] {
			getAccountName(),
			getNickName(),
			getInitServerId(),
		};
	}
	
	@Override
	public Object key() {
		return getPlayerId();
	}

	@Override
	public String cacheId() {
		return String.valueOf(getPlayerId());
	}

	@Override
	public String keyColumn() {
		return PROP_PLAYERID;
	}
	
}
