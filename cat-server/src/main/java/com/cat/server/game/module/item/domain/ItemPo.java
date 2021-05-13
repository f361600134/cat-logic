package com.cat.server.game.module.item.domain;

import com.cat.orm.core.base.BasePo;

/**
* ItemPo
* @author Jeremy
*/
public abstract class ItemPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_ITEMID = "itemId";
	public static final String PROP_CONFIGID = "configId";
	public static final String PROP_COUNT = "count";
	public static final String PROP_RECIEVETIME = "recieveTime";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_ITEMID,
			PROP_CONFIGID,
			PROP_COUNT,
			PROP_RECIEVETIME,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_ITEMID,
			PROP_PLAYERID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			PROP_PLAYERID,
			};
	
	
	/** 玩家ID*/
	protected long playerId;
	/** 道具唯一id*/
	protected long itemId;
	/** 道具配置id*/
	protected int configId;
	/** 物品数量*/
	protected int count;
	/** 获得时间*/
	protected int recieveTime;
	
	public ItemPo(){
	}
	
	/** 玩家ID **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 道具唯一id **/
	public long getItemId(){
		return this.itemId;
	}
	
	public void setItemId(long itemId){
		this.itemId = itemId;
	}
	
	/** 道具配置id **/
	public int getConfigId(){
		return this.configId;
	}
	
	public void setConfigId(int configId){
		this.configId = configId;
	}
	
	/** 物品数量 **/
	public int getCount(){
		return this.count;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	
	/** 获得时间 **/
	public int getRecieveTime(){
		return this.recieveTime;
	}
	
	public void setRecieveTime(int recieveTime){
		this.recieveTime = recieveTime;
	}
	
	
	@Override
	public String toString() {
		return "Item [playerId= "+ playerId +", itemId= "+ itemId +", configId= "+ configId +", count= "+ count +", recieveTime= "+ recieveTime
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
		getItemId(),
		getConfigId(),
		getCount(),
		getRecieveTime(),
		};
	}
	
	@Override
	public Object key() {
		return getItemId();
	}
	
	@Override
	public String keyColumn() {
		return PROP_ITEMID;
	}

	@Override
	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}

	@Override
	public Object[] keyAndIndexValues() {
		return new Object[] {
			getItemId(),
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
		return String.valueOf(getItemId());
	}
	
}
