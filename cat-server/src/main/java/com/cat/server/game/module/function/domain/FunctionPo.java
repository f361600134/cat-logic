package com.cat.server.game.module.function.domain;

import com.cat.orm.core.base.BasePo;

/**
* FunctionPo
* @author Jeremy
*/
public abstract class FunctionPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_FUNCTIONSTR = "functionStr";
	public static final String PROP_REDDOTSTR = "reddotStr";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_FUNCTIONSTR,
			PROP_REDDOTSTR,
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
	/** 功能数据*/
	protected String functionStr;
	/** 系统红点数据*/
	protected String reddotStr;
	
	public FunctionPo(){
		this.functionStr = "";
		this.reddotStr = "";
	}
	
	/** 玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 功能数据 **/
	public String getFunctionStr(){
		return this.functionStr;
	}
	
	public void setFunctionStr(String functionStr){
		this.functionStr = functionStr;
	}
	
	/** 系统红点数据 **/
	public String getReddotStr(){
		return this.reddotStr;
	}
	
	public void setReddotStr(String reddotStr){
		this.reddotStr = reddotStr;
	}
	
	
	@Override
	public String toString() {
		return "Function [playerId= "+ playerId +", functionStr= "+ functionStr +", reddotStr= "+ reddotStr+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getPlayerId(),
		getFunctionStr(),
		getReddotStr(),
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
