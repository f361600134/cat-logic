package com.cat.server.game.module.shop.domain;

import com.cat.orm.core.base.BasePo;

/**
* ShopPo
* @author Jeremy
*/
public abstract class ShopPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_SHOPID = "shopId";
	public static final String PROP_REFRESHTIME = "refreshTime";
	public static final String PROP_REFRESHNUM = "refreshNum";
	public static final String PROP_BUYEDSTR = "buyedStr";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_SHOPID,
			PROP_REFRESHTIME,
			PROP_REFRESHNUM,
			PROP_BUYEDSTR,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_PLAYERID,
			PROP_SHOPID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			};
	
	
	/** 玩家id*/
	protected long playerId;
	/** 商店类型id*/
	protected int shopId;
	/** 商店刷新时间*/
	protected long refreshTime;
	/** 商店刷新次数*/
	protected short refreshNum;
	/** 已购买列表*/
	protected String buyedStr;
	
	public ShopPo(){
		this.buyedStr = "";
	}
	
	/** 玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 商店类型id **/
	public int getShopId(){
		return this.shopId;
	}
	
	public void setShopId(int shopId){
		this.shopId = shopId;
	}
	
	/** 商店刷新时间 **/
	public long getRefreshTime(){
		return this.refreshTime;
	}
	
	public void setRefreshTime(long refreshTime){
		this.refreshTime = refreshTime;
	}
	
	/** 商店刷新次数 **/
	public short getRefreshNum(){
		return this.refreshNum;
	}
	
	public void setRefreshNum(short refreshNum){
		this.refreshNum = refreshNum;
	}
	
	/** 已购买列表 **/
	public String getBuyedStr(){
		return this.buyedStr;
	}
	
	public void setBuyedStr(String buyedStr){
		this.buyedStr = buyedStr;
	}
	
	
	@Override
	public String toString() {
		return "Shop [playerId= "+ playerId +", shopId= "+ shopId +", refreshTime= "+ refreshTime +", refreshNum= "+ refreshNum +", buyedStr= "+ buyedStr
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
		getShopId(),
		getRefreshTime(),
		getRefreshNum(),
		getBuyedStr(),
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
			getShopId(),
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
		return getPlayerId()+":"+getShopId();
	}
	
}
