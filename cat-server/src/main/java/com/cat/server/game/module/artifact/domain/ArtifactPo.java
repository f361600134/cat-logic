package com.cat.server.game.module.artifact.domain;

import com.cat.orm.core.base.BasePo;

/**
* ArtifactPo
* @author Jeremy
*/
public abstract class ArtifactPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_CONFIGID = "configId";
	public static final String PROP_LEVEL = "level";
	public static final String PROP_EXP = "exp";
	public static final String PROP_REFINELV = "refineLv";
	public static final String PROP_SKILLLV = "skillLv";
	public static final String PROP_STATE = "state";
	public static final String PROP_HOLYSEALLV = "holySealLv";
	public static final String PROP_SKINID = "skinId";
	public static final String PROP_MISSIONSTR = "missionStr";
	public static final String PROP_USEDMATERIALSTR = "usedMaterialStr";
	
	protected long playerId;//玩家ID
	protected int configId;//神器配置id
	protected int level;//神器等级
	protected int exp;//经验
	protected int refineLv;//精炼等级
	protected int skillLv;//技能等级
	protected int state;//状态
	protected int holySealLv;//圣印等级
	protected int skinId;//皮肤id
	protected int missionStr;//已完成任务列表
	protected int usedMaterialStr;//累计使用材料
	
	public ArtifactPo(){
	}
	
	/** 玩家ID **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 神器配置id **/
	public int getConfigId(){
		return this.configId;
	}
	
	public void setConfigId(int configId){
		this.configId = configId;
	}
	
	/** 神器等级 **/
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
	
	/** 精炼等级 **/
	public int getRefineLv(){
		return this.refineLv;
	}
	
	public void setRefineLv(int refineLv){
		this.refineLv = refineLv;
	}
	
	/** 技能等级 **/
	public int getSkillLv(){
		return this.skillLv;
	}
	
	public void setSkillLv(int skillLv){
		this.skillLv = skillLv;
	}
	
	/** 状态 **/
	public int getState(){
		return this.state;
	}
	
	public void setState(int state){
		this.state = state;
	}
	
	/** 圣印等级 **/
	public int getHolySealLv(){
		return this.holySealLv;
	}
	
	public void setHolySealLv(int holySealLv){
		this.holySealLv = holySealLv;
	}
	
	/** 皮肤id **/
	public int getSkinId(){
		return this.skinId;
	}
	
	public void setSkinId(int skinId){
		this.skinId = skinId;
	}
	
	/** 已完成任务列表 **/
	public int getMissionStr(){
		return this.missionStr;
	}
	
	public void setMissionStr(int missionStr){
		this.missionStr = missionStr;
	}
	
	/** 累计使用材料 **/
	public int getUsedMaterialStr(){
		return this.usedMaterialStr;
	}
	
	public void setUsedMaterialStr(int usedMaterialStr){
		this.usedMaterialStr = usedMaterialStr;
	}
	
	
	@Override
	public String toString() {
		return "Artifact [playerId= "+ playerId +", configId= "+ configId +", level= "+ level +", exp= "+ exp +", refineLv= "+ refineLv
				 +", skillLv= "+ skillLv +", state= "+ state +", holySealLv= "+ holySealLv +", skinId= "+ skinId +", missionStr= "+ missionStr
				 +", usedMaterialStr= "+ usedMaterialStr+"]";
	}
	
	@Override
	public String[] props() {
		return new String[] {
		"`playerId`",
		"`configId`",
		"`level`",
		"`exp`",
		"`refineLv`",
		"`skillLv`",
		"`state`",
		"`holySealLv`",
		"`skinId`",
		"`missionStr`",
		"`usedMaterialStr`",
		};
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getPlayerId(),
		getConfigId(),
		getLevel(),
		getExp(),
		getRefineLv(),
		getSkillLv(),
		getState(),
		getHolySealLv(),
		getSkinId(),
		getMissionStr(),
		getUsedMaterialStr(),
		};
	}

	@Override
	public String[] indexColumn() {
		return new String[] {
			PROP_PLAYERID,
			PROP_CONFIGID,
		};
	}
	
	@Override
	public Object[] indexValues() {
		return new Object[] {
			getPlayerId(),
			getConfigId(),
		};
	}
	
	@Override
	public Object key() {
		// 第一主键的数据值, 例如playerId
		//return getId();
		return getPlayerId();
	}

	@Override
	public String cacheId() {
		// 缓存的二级ID, 如果不是一对多关系的return null即可
		//return String.valueOf(getId());
		return String.valueOf(getPlayerId());
	}

	@Override
	public String keyColumn() {
		// 第一主键的数据库列名
		return indexColumn()[0];
	}
	
}
