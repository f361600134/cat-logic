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
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_CONFIGID,
			PROP_LEVEL,
			PROP_EXP,
			PROP_REFINELV,
			PROP_SKILLLV,
			PROP_STATE,
			PROP_HOLYSEALLV,
			PROP_SKINID,
			PROP_MISSIONSTR,
			PROP_USEDMATERIALSTR,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_PLAYERID,
			PROP_CONFIGID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			};
	
	
	/** 玩家ID*/
	protected long playerId;
	/** 神器配置id*/
	protected int configId;
	/** 神器等级*/
	protected int level;
	/** 经验*/
	protected int exp;
	/** 精炼等级*/
	protected int refineLv;
	/** 技能等级*/
	protected int skillLv;
	/** 状态*/
	protected int state;
	/** 圣印等级*/
	protected int holySealLv;
	/** 皮肤id*/
	protected int skinId;
	/** 已完成任务列表*/
	protected int missionStr;
	/** 累计使用材料*/
	protected int usedMaterialStr;
	
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
		return PROP_ALL;
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
			getConfigId(),
		};
	}
	
	@Override
	public String[] indexColumn() {
		return INDEX_ALL;
	}
	
	@Override
	public String[] indexValues() {
		return new String[] {
		};
	}
	
	@Override
	public String cacheId() {
		return getPlayerId()+":"+getConfigId();
	}
	
}
