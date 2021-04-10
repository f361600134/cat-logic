package com.cat.server.game.module.mainmission.domain;

import com.cat.orm.core.base.BasePo;

/**
* MainMissionPo
* @author Jeremy
*/
public abstract class MainMissionPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_MISSIONSTR = "missionStr";
	
	protected long playerId;//玩家id
	protected String missionStr;//任务数据str
	
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
		return "MainMission[ playerId= " +getPlayerId()+ "+, playerId= "+ playerId +", missionStr= "+ missionStr+"]";
	}
	
	@Override
	public String[] props() {
		return new String[] {
		"`playerId`",
		"`missionStr`",
		};
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getPlayerId(),
		getMissionStr(),
		};
	}

	@Override
	public String[] indexColumn() {
		return new String[] {
			PROP_PLAYERID,
		};
	}
	
	@Override
	public Object[] indexValues() {
		return new Object[] {
			getPlayerId(),
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
