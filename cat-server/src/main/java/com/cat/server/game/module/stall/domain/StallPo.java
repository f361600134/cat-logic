package com.cat.server.game.module.stall.domain;

import com.cat.orm.core.base.BasePo;

/**
* StallPo
* @author Jeremy
*/
public abstract class StallPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_DATA = "data";
	public static final String PROP_INITSERVERID = "initServerId";
	public static final String PROP_CURSERVERID = "curServerId";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_DATA,
			PROP_INITSERVERID,
			PROP_CURSERVERID,
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
	/** 玩家出售的商品信息*/
	protected String data;
	/** 初始服务器id*/
	protected int initServerId;
	/** 当前服务器id*/
	protected int curServerId;
	
	public StallPo(){
		this.data = "";
	}
	
	/** 玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 玩家出售的商品信息 **/
	public String getData(){
		return this.data;
	}
	
	public void setData(String data){
		this.data = data;
	}
	
	/** 初始服务器id **/
	public int getInitServerId(){
		return this.initServerId;
	}
	
	public void setInitServerId(int initServerId){
		this.initServerId = initServerId;
	}
	
	/** 当前服务器id **/
	public int getCurServerId(){
		return this.curServerId;
	}
	
	public void setCurServerId(int curServerId){
		this.curServerId = curServerId;
	}
	
	
	@Override
	public String toString() {
		return "Stall [playerId= "+ playerId +", data= "+ data +", initServerId= "+ initServerId +", curServerId= "+ curServerId+"]";
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
		getInitServerId(),
		getCurServerId(),
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
