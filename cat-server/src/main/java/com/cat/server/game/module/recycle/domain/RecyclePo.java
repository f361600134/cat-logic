package com.cat.server.game.module.recycle.domain;

import com.cat.orm.core.base.BasePo;

/**
* RecyclePo
* @author Jeremy
*/
public abstract class RecyclePo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_RESOURCEID = "resourceId";
	public static final String PROP_CONFIGID = "configId";
	public static final String PROP_RECIEVETIME = "recieveTime";
	public static final String PROP_NUMBER = "number";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_RESOURCEID,
			PROP_CONFIGID,
			PROP_RECIEVETIME,
			PROP_NUMBER,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_RESOURCEID,
			PROP_PLAYERID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			PROP_PLAYERID,
			};
	
	
	/** 玩家ID*/
	protected long playerId;
	/** 资源唯一id*/
	protected long resourceId;
	/** 道具配置id*/
	protected int configId;
	/** 获得的时间戳*/
	protected long recieveTime;
	/** 当前数量*/
	protected int number;
	
	public RecyclePo(){
	}
	
	/** 玩家ID **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 资源唯一id **/
	public long getResourceId(){
		return this.resourceId;
	}
	
	public void setResourceId(long resourceId){
		this.resourceId = resourceId;
	}
	
	/** 道具配置id **/
	public int getConfigId(){
		return this.configId;
	}
	
	public void setConfigId(int configId){
		this.configId = configId;
	}
	
	/** 获得的时间戳 **/
	public long getRecieveTime(){
		return this.recieveTime;
	}
	
	public void setRecieveTime(long recieveTime){
		this.recieveTime = recieveTime;
	}
	
	/** 当前数量 **/
	public int getNumber(){
		return this.number;
	}
	
	public void setNumber(int number){
		this.number = number;
	}
	
	
	@Override
	public String toString() {
		return "Recycle [playerId= "+ playerId +", resourceId= "+ resourceId +", configId= "+ configId +", recieveTime= "+ recieveTime +", number= "+ number
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
		getResourceId(),
		getConfigId(),
		getRecieveTime(),
		getNumber(),
		};
	}
	
	@Override
	public Object key() {
		return getResourceId();
	}
	
	@Override
	public String keyColumn() {
		return PROP_RESOURCEID;
	}

	@Override
	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}
	
	@Override
	public Object[] keyAndIndexValues() {
		return new Object[] {
			getResourceId(),
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
		return String.valueOf(getResourceId());
	}
	
}
