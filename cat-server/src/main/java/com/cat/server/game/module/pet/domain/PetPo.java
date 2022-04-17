package com.cat.server.game.module.pet.domain;

import com.cat.orm.core.base.BasePo;

/**
* PetPo
* @author Jeremy
*/
public abstract class PetPo extends BasePo {

	public static final String PROP_CONFIGID = "configId";
	public static final String PROP_UNIQUEID = "uniqueId";
	public static final String PROP_NICKNAME = "nickname";
	public static final String PROP_BIRTHDATE = "birthDate";
	public static final String PROP_HUNGRYSTR = "hungryStr";
	public static final String PROP_DISTRUSTSTR = "distrustStr";
	public static final String PROP_APTITUDESTR = "aptitudeStr";
	public static final String PROP_GENDER = "gender";
	public static final String PROP_LEVEL = "level";
	public static final String PROP_SKILLSTR = "skillStr";
	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_EXP = "exp";
	public static final String PROP_PREFIXID = "prefixId";
	public static final String PROP_REPRODUCTIVE = "reproductive";
	public static final String PROP_ACTIVE = "active";
	public static final String PROP_USEDATTRPOINTSTR = "usedAttrPointStr";
	public static final String PROP_USEDATTRPREFIXPOINTSTR = "usedAttrPrefixPointStr";
	public static final String PROP_SKILLPOOLTYPE = "skillPoolType";
	public static final String PROP_DORMANCY = "dormancy";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_CONFIGID,
			PROP_UNIQUEID,
			PROP_NICKNAME,
			PROP_BIRTHDATE,
			PROP_HUNGRYSTR,
			PROP_DISTRUSTSTR,
			PROP_APTITUDESTR,
			PROP_GENDER,
			PROP_LEVEL,
			PROP_SKILLSTR,
			PROP_PLAYERID,
			PROP_EXP,
			PROP_PREFIXID,
			PROP_REPRODUCTIVE,
			PROP_ACTIVE,
			PROP_USEDATTRPOINTSTR,
			PROP_USEDATTRPREFIXPOINTSTR,
			PROP_SKILLPOOLTYPE,
			PROP_DORMANCY,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_UNIQUEID,
			PROP_PLAYERID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			PROP_PLAYERID,
			};
	
	
	/** 配置id*/
	protected int configId;
	/** 唯一id*/
	protected long uniqueId;
	/** 名字*/
	protected String nickname;
	/** 出生日期时间戳*/
	protected long birthDate;
	/** 饥饿点字符串,包含饥饿点,最后恢复时间戳*/
	protected String hungryStr;
	/** 不信任度,包含饥饿点,最后恢复时间戳*/
	protected String distrustStr;
	/** 属性资质鉴定随机绑定,列表*/
	protected String aptitudeStr;
	/** 性别随机*/
	protected short gender;
	/** 等级,初始1级*/
	protected short level;
	/** 技能列表信息,有几率初始化出技能,列表*/
	protected String skillStr;
	/** 玩家id*/
	protected long playerId;
	/** 经验*/
	protected long exp;
	/** 前缀配置*/
	protected int prefixId;
	/** 生育次数*/
	protected int reproductive;
	/** 是否激活0:false,1:true*/
	protected boolean active;
	/** 已使用的属性点,玩家自定义属性记录*/
	protected String usedAttrPointStr;
	/** 已使用的前缀属性点,实际上根据属性额外记录的*/
	protected String usedAttrPrefixPointStr;
	/** 绑定的技能池类型*/
	protected int skillPoolType;
	/** 是否休眠,信任度为0时进入休眠,需要唤醒*/
	protected boolean dormancy;
	
	public PetPo(){
		this.nickname = "";
		this.hungryStr = "";
		this.distrustStr = "";
		this.aptitudeStr = "";
		this.skillStr = "";
		this.usedAttrPointStr = "";
		this.usedAttrPrefixPointStr = "";
	}
	
	/** 配置id **/
	public int getConfigId(){
		return this.configId;
	}
	
	public void setConfigId(int configId){
		this.configId = configId;
	}
	
	/** 唯一id **/
	public long getUniqueId(){
		return this.uniqueId;
	}
	
	public void setUniqueId(long uniqueId){
		this.uniqueId = uniqueId;
	}
	
	/** 名字 **/
	public String getNickname(){
		return this.nickname;
	}
	
	public void setNickname(String nickname){
		this.nickname = nickname;
	}
	
	/** 出生日期时间戳 **/
	public long getBirthDate(){
		return this.birthDate;
	}
	
	public void setBirthDate(long birthDate){
		this.birthDate = birthDate;
	}
	
	/** 饥饿点字符串,包含饥饿点,最后恢复时间戳 **/
	public String getHungryStr(){
		return this.hungryStr;
	}
	
	public void setHungryStr(String hungryStr){
		this.hungryStr = hungryStr;
	}
	
	/** 不信任度,包含饥饿点,最后恢复时间戳 **/
	public String getDistrustStr(){
		return this.distrustStr;
	}
	
	public void setDistrustStr(String distrustStr){
		this.distrustStr = distrustStr;
	}
	
	/** 属性资质鉴定随机绑定,列表 **/
	public String getAptitudeStr(){
		return this.aptitudeStr;
	}
	
	public void setAptitudeStr(String aptitudeStr){
		this.aptitudeStr = aptitudeStr;
	}
	
	/** 性别随机 **/
	public short getGender(){
		return this.gender;
	}
	
	public void setGender(short gender){
		this.gender = gender;
	}
	
	/** 等级,初始1级 **/
	public short getLevel(){
		return this.level;
	}
	
	public void setLevel(short level){
		this.level = level;
	}
	
	/** 技能列表信息,有几率初始化出技能,列表 **/
	public String getSkillStr(){
		return this.skillStr;
	}
	
	public void setSkillStr(String skillStr){
		this.skillStr = skillStr;
	}
	
	/** 玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 经验 **/
	public long getExp(){
		return this.exp;
	}
	
	public void setExp(long exp){
		this.exp = exp;
	}
	
	/** 前缀配置 **/
	public int getPrefixId(){
		return this.prefixId;
	}
	
	public void setPrefixId(int prefixId){
		this.prefixId = prefixId;
	}
	
	/** 生育次数 **/
	public int getReproductive(){
		return this.reproductive;
	}
	
	public void setReproductive(int reproductive){
		this.reproductive = reproductive;
	}
	
	/** 是否激活0:false,1:true **/
	public boolean getActive(){
		return this.active;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}
	
	/** 已使用的属性点,玩家自定义属性记录 **/
	public String getUsedAttrPointStr(){
		return this.usedAttrPointStr;
	}
	
	public void setUsedAttrPointStr(String usedAttrPointStr){
		this.usedAttrPointStr = usedAttrPointStr;
	}
	
	/** 已使用的前缀属性点,实际上根据属性额外记录的 **/
	public String getUsedAttrPrefixPointStr(){
		return this.usedAttrPrefixPointStr;
	}
	
	public void setUsedAttrPrefixPointStr(String usedAttrPrefixPointStr){
		this.usedAttrPrefixPointStr = usedAttrPrefixPointStr;
	}
	
	/** 绑定的技能池类型 **/
	public int getSkillPoolType(){
		return this.skillPoolType;
	}
	
	public void setSkillPoolType(int skillPoolType){
		this.skillPoolType = skillPoolType;
	}
	
	/** 是否休眠,信任度为0时进入休眠,需要唤醒 **/
	public boolean getDormancy(){
		return this.dormancy;
	}
	
	public void setDormancy(boolean dormancy){
		this.dormancy = dormancy;
	}
	
	
	@Override
	public String toString() {
		return "Pet [configId= "+ configId +", uniqueId= "+ uniqueId +", nickname= "+ nickname +", birthDate= "+ birthDate +", hungryStr= "+ hungryStr
				 +", distrustStr= "+ distrustStr +", aptitudeStr= "+ aptitudeStr +", gender= "+ gender +", level= "+ level +", skillStr= "+ skillStr
				 +", playerId= "+ playerId +", exp= "+ exp +", prefixId= "+ prefixId +", reproductive= "+ reproductive +", active= "+ active
				 +", usedAttrPointStr= "+ usedAttrPointStr +", usedAttrPrefixPointStr= "+ usedAttrPrefixPointStr +", skillPoolType= "+ skillPoolType +", dormancy= "+ dormancy+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getConfigId(),
		getUniqueId(),
		getNickname(),
		getBirthDate(),
		getHungryStr(),
		getDistrustStr(),
		getAptitudeStr(),
		getGender(),
		getLevel(),
		getSkillStr(),
		getPlayerId(),
		getExp(),
		getPrefixId(),
		getReproductive(),
		getActive(),
		getUsedAttrPointStr(),
		getUsedAttrPrefixPointStr(),
		getSkillPoolType(),
		getDormancy(),
		};
	}
	
	@Override
	public Object key() {
		return getUniqueId();
	}
	
	@Override
	public String keyColumn() {
		return PROP_UNIQUEID;
	}

	@Override
	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}
	
	@Override
	public Object[] keyAndIndexValues() {
		return new Object[] {
			getUniqueId(),
			getPlayerId(),
		};
	}
	
	@Override
	public String[] indexColumn() {
		return INDEX_ALL;
	}
	
	@Override
	public String[] indexValues() {
		return new String[] {
			PROP_PLAYERID,
		};
	}
	
	@Override
	public String cacheId() {
		return String.valueOf(getUniqueId());
	}
	
}
