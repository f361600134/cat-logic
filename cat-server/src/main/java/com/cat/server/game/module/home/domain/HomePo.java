package com.cat.server.game.module.home.domain;

import com.cat.orm.core.base.BasePo;

/**
* HomePo
* @author Jeremy
*/
public abstract class HomePo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_NAME = "name";
	public static final String PROP_EXP = "exp";
	public static final String PROP_LEVEL = "level";
	public static final String PROP_BETSUINSTR = "betsuinStr";
	public static final String PROP_LAYOUTSCHEMESTR = "layoutSchemeStr";
	public static final String PROP_LASTRENAMETIME = "lastRenameTime";
	public static final String PROP_FURNITURESTR = "furnitureStr";
	public static final String PROP_RESOURCESTR = "resourceStr";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_NAME,
			PROP_EXP,
			PROP_LEVEL,
			PROP_BETSUINSTR,
			PROP_LAYOUTSCHEMESTR,
			PROP_LASTRENAMETIME,
			PROP_FURNITURESTR,
			PROP_RESOURCESTR,
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
	/** 家园名称*/
	protected String name;
	/** 家园经验*/
	protected int exp;
	/** 家园等级*/
	protected int level;
	/** 别院数据*/
	protected String betsuinStr;
	/** 家园布局方案*/
	protected String layoutSchemeStr;
	/** 最后改名时间*/
	protected long lastRenameTime;
	/** 布局家具*/
	protected String furnitureStr;
	/** 家具资源*/
	protected String resourceStr;
	
	public HomePo(){
		this.name = "";
		this.betsuinStr = "";
		this.layoutSchemeStr = "";
		this.furnitureStr = "";
		this.resourceStr = "";
	}
	
	/** 玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 家园名称 **/
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	/** 家园经验 **/
	public int getExp(){
		return this.exp;
	}
	
	public void setExp(int exp){
		this.exp = exp;
	}
	
	/** 家园等级 **/
	public int getLevel(){
		return this.level;
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	/** 别院数据 **/
	public String getBetsuinStr(){
		return this.betsuinStr;
	}
	
	public void setBetsuinStr(String betsuinStr){
		this.betsuinStr = betsuinStr;
	}
	
	/** 家园布局方案 **/
	public String getLayoutSchemeStr(){
		return this.layoutSchemeStr;
	}
	
	public void setLayoutSchemeStr(String layoutSchemeStr){
		this.layoutSchemeStr = layoutSchemeStr;
	}
	
	/** 最后改名时间 **/
	public long getLastRenameTime(){
		return this.lastRenameTime;
	}
	
	public void setLastRenameTime(long lastRenameTime){
		this.lastRenameTime = lastRenameTime;
	}
	
	/** 布局家具 **/
	public String getFurnitureStr(){
		return this.furnitureStr;
	}
	
	public void setFurnitureStr(String furnitureStr){
		this.furnitureStr = furnitureStr;
	}
	
	/** 家具资源 **/
	public String getResourceStr(){
		return this.resourceStr;
	}
	
	public void setResourceStr(String resourceStr){
		this.resourceStr = resourceStr;
	}
	
	
	@Override
	public String toString() {
		return "Home [playerId= "+ playerId +", name= "+ name +", exp= "+ exp +", level= "+ level +", betsuinStr= "+ betsuinStr
				 +", layoutSchemeStr= "+ layoutSchemeStr +", lastRenameTime= "+ lastRenameTime +", furnitureStr= "+ furnitureStr +", resourceStr= "+ resourceStr+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getPlayerId(),
		getName(),
		getExp(),
		getLevel(),
		getBetsuinStr(),
		getLayoutSchemeStr(),
		getLastRenameTime(),
		getFurnitureStr(),
		getResourceStr(),
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
