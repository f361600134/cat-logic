package com.cat.server.game.module.mission.domain;

import com.cat.orm.core.base.BasePo;

/**
* MissionPo
* @author Jeremy
*/
public abstract class MissionPo extends BasePo {

	protected static final String PROP_PLAYERID = "playerId";
	protected static final String PROP_MISSIONTYPE = "missionType";
	protected static final String PROP_MISSIONSTR = "missionStr";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_MISSIONTYPE,
			PROP_MISSIONSTR,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_PLAYERID,
			PROP_MISSIONTYPE,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			PROP_PLAYERID,
			};
	
	
	/** 角色ID*/
	protected long playerId;
	/** 任务类型,1:主线,2:支线,3:...*/
	protected int missionType;
	/** 任务数据str*/
	protected String missionStr;
	
	public MissionPo(){
		this.missionStr = "";
	}
	
	/** 角色ID **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 任务类型,1:主线,2:支线,3:... **/
	public int getMissionType(){
		return this.missionType;
	}
	
	public void setMissionType(int missionType){
		this.missionType = missionType;
	}
	
	/** 任务数据str **/
	public String getMissionStr(){
		return this.missionStr;
	}
	
	public void setMissionStr(String missionStr){
		this.missionStr = missionStr;
	}
	
	
	@Override
	public String toString() {
		return "Mission [playerId= "+ playerId +", missionType= "+ missionType +", missionStr= "+ missionStr+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getPlayerId(),
		getMissionType(),
		getMissionStr(),
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

	@Override
	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}
	
	@Override
	public Object[] keyAndIndexValues() {
		return new Object[] {
			getPlayerId(),
			getMissionType(),
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
		return getPlayerId()+":"+getMissionType();
	}
	
}
