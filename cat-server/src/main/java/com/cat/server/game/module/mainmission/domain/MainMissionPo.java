package com.cat.server.game.module.mainmission.domain;

import com.cat.orm.core.base.BasePo;

/**
* MainMissionPo
* @author Jeremy
*/
public abstract class MainMissionPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_MISSIONSTR = "missionStr";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_MISSIONSTR,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_PLAYERID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			};
	
	
	/** 玩家id*/
	protected long playerId;
	/** 任务数据str*/
	protected String missionStr;
	
	public MainMissionPo(){
		this.missionStr = "";
	}
	
	/** 玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
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
		return "MainMission [playerId= "+ playerId +", missionStr= "+ missionStr+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getPlayerId(),
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

	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}
	
	public Object[] keyAndIndexValues() {
		return new Object[] {
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
		};
	}
	
	@Override
	public String cacheId() {
		return String.valueOf(getPlayerId());
	}
	
}
