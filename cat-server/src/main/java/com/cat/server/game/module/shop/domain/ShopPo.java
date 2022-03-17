package com.cat.server.game.module.shop.domain;

import com.cat.orm.core.base.BasePo;

/**
* ShopPo
* @author Jeremy
*/
public abstract class ShopPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_SHOPID = "shopId";
	public static final String PROP_FREEREFRESHNUM = "freeRefreshNum";
	public static final String PROP_FREEREFRESHTIME = "freeRefreshTime";
	public static final String PROP_RESREFRESHNUM = "resRefreshNum";
	public static final String PROP_BUYEDSTR = "buyedStr";
	public static final String PROP_COMMODITIESSTR = "commoditiesStr";
	public static final String PROP_RESETTIME = "resetTime";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_SHOPID,
			PROP_FREEREFRESHNUM,
			PROP_FREEREFRESHTIME,
			PROP_RESREFRESHNUM,
			PROP_BUYEDSTR,
			PROP_COMMODITIESSTR,
			PROP_RESETTIME,
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
	/** 商店免费刷新次数*/
	protected short freeRefreshNum;
	/** 商店免费刷新时间*/
	protected long freeRefreshTime;
	/** 商店资源刷新次数*/
	protected short resRefreshNum;
	/** 已购买列表*/
	protected String buyedStr;
	/** 当前商品列表,随机商品列表时使用*/
	protected String commoditiesStr;
	/** 最后重置时间,用于每日重置*/
	protected long resetTime;
	
	public ShopPo(){
		this.buyedStr = "";
		this.commoditiesStr = "";
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
	
	/** 商店免费刷新次数 **/
	public short getFreeRefreshNum(){
		return this.freeRefreshNum;
	}
	
	public void setFreeRefreshNum(short freeRefreshNum){
		this.freeRefreshNum = freeRefreshNum;
	}
	
	/** 商店免费刷新时间 **/
	public long getFreeRefreshTime(){
		return this.freeRefreshTime;
	}
	
	public void setFreeRefreshTime(long freeRefreshTime){
		this.freeRefreshTime = freeRefreshTime;
	}
	
	/** 商店资源刷新次数 **/
	public short getResRefreshNum(){
		return this.resRefreshNum;
	}
	
	public void setResRefreshNum(short resRefreshNum){
		this.resRefreshNum = resRefreshNum;
	}
	
	/** 已购买列表 **/
	public String getBuyedStr(){
		return this.buyedStr;
	}
	
	public void setBuyedStr(String buyedStr){
		this.buyedStr = buyedStr;
	}
	
	/** 当前商品列表,随机商品列表时使用 **/
	public String getCommoditiesStr(){
		return this.commoditiesStr;
	}
	
	public void setCommoditiesStr(String commoditiesStr){
		this.commoditiesStr = commoditiesStr;
	}
	
	/** 最后重置时间,用于每日重置 **/
	public long getResetTime(){
		return this.resetTime;
	}
	
	public void setResetTime(long resetTime){
		this.resetTime = resetTime;
	}
	
	
	@Override
	public String toString() {
		return "Shop [playerId= "+ playerId +", shopId= "+ shopId +", freeRefreshNum= "+ freeRefreshNum +", freeRefreshTime= "+ freeRefreshTime +", resRefreshNum= "+ resRefreshNum
				 +", buyedStr= "+ buyedStr +", commoditiesStr= "+ commoditiesStr +", resetTime= "+ resetTime+"]";
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
		getFreeRefreshNum(),
		getFreeRefreshTime(),
		getResRefreshNum(),
		getBuyedStr(),
		getCommoditiesStr(),
		getResetTime(),
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
