package com.cat.server.game.module.family.domain;

import com.cat.orm.core.base.BasePo;

/**
* PlayerFamilyPo
* @author Jeremy
*/
public abstract class PlayerFamilyPo extends BasePo {

	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_JOINTIME = "joinTime";
	public static final String PROP_LEAVETIME = "leaveTime";
	public static final String PROP_RESETTIME = "resetTime";
	public static final String PROP_CONTRIBUTE = "contribute";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_PLAYERID,
			PROP_JOINTIME,
			PROP_LEAVETIME,
			PROP_RESETTIME,
			PROP_CONTRIBUTE,
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
	/** 加入时间*/
	protected long joinTime;
	/** 退出时间,强退记录,强制退出后间隔1天才能重新进入家族*/
	protected long leaveTime;
	/** 重置时间*/
	protected long resetTime;
	/** 个人家族贡献值*/
	protected int contribute;
	
	public PlayerFamilyPo(){
	}
	
	/** 玩家id **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
	}
	
	/** 加入时间 **/
	public long getJoinTime(){
		return this.joinTime;
	}
	
	public void setJoinTime(long joinTime){
		this.joinTime = joinTime;
	}
	
	/** 退出时间,强退记录,强制退出后间隔1天才能重新进入家族 **/
	public long getLeaveTime(){
		return this.leaveTime;
	}
	
	public void setLeaveTime(long leaveTime){
		this.leaveTime = leaveTime;
	}
	
	/** 重置时间 **/
	public long getResetTime(){
		return this.resetTime;
	}
	
	public void setResetTime(long resetTime){
		this.resetTime = resetTime;
	}
	
	/** 个人家族贡献值 **/
	public int getContribute(){
		return this.contribute;
	}
	
	public void setContribute(int contribute){
		this.contribute = contribute;
	}
	
	
	@Override
	public String toString() {
		return "PlayerFamily [playerId= "+ playerId +", joinTime= "+ joinTime +", leaveTime= "+ leaveTime +", resetTime= "+ resetTime +", contribute= "+ contribute
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
		getJoinTime(),
		getLeaveTime(),
		getResetTime(),
		getContribute(),
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

	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}
	
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
