package com.cat.server.game.module.functioncontrol.domain;

import com.cat.orm.core.base.BasePo;

/**
* FunctionControlPo
* @author Jeremy
*/
public abstract class FunctionControlPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_FUNCTIONID = "functionId";
	public static final String PROP_RESETTIME = "resetTime";
	public static final String PROP_REDDOTSTR = "reddotStr";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_FUNCTIONID,
			PROP_RESETTIME,
			PROP_REDDOTSTR,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_PLAYERID,
			PROP_FUNCTIONID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			};
	
	
	/** 玩家id*/
	protected long playerId;
	/** 功能id*/
	protected int functionId;
	/** 重置时间*/
	protected long resetTime;
	/** 系统红点数据*/
	protected String reddotStr;
	
	public FunctionControlPo(){
		this.reddotStr = "";
	}
	
	/** 玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 功能id **/
	public int getFunctionId(){
		return this.functionId;
	}
	
	public void setFunctionId(int functionId){
		this.functionId = functionId;
	}
	
	/** 重置时间 **/
	public long getResetTime(){
		return this.resetTime;
	}
	
	public void setResetTime(long resetTime){
		this.resetTime = resetTime;
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
		return "FunctionControl [playerId= "+ playerId +", functionId= "+ functionId +", resetTime= "+ resetTime +", reddotStr= "+ reddotStr+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getPlayerId(),
		getFunctionId(),
		getResetTime(),
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
			getFunctionId(),
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
		return getPlayerId()+":"+getFunctionId();
	}
	
}
