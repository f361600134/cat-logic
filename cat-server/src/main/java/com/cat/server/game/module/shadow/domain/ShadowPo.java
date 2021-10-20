package com.cat.server.game.module.shadow.domain;

import com.cat.orm.core.base.BasePo;

/**
* ShadowPo
* @author Jeremy
*/
public abstract class ShadowPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_DATA = "data";
	public static final String PROP_UPDATETIME = "updateTime";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_DATA,
			PROP_UPDATETIME,
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
	/** 玩家数据*/
	protected String data;
	/** 更新时间,用于过滤数据*/
	protected long updateTime;
	
	public ShadowPo(){
		this.data = "";
	}
	
	/** 玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 玩家数据 **/
	public String getData(){
		return this.data;
	}
	
	public void setData(String data){
		this.data = data;
	}
	
	/** 更新时间,用于过滤数据 **/
	public long getUpdateTime(){
		return this.updateTime;
	}
	
	public void setUpdateTime(long updateTime){
		this.updateTime = updateTime;
	}
	
	
	@Override
	public String toString() {
		return "Shadow [playerId= "+ playerId +", data= "+ data +", updateTime= "+ updateTime+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getPlayerId(),
		getData(),
		getUpdateTime(),
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
