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
	
	protected long playerId;//玩家id
	protected long joinTime;//加入时间
	protected long leaveTime;//退出时间,强退记录,强制退出后间隔1天才能重新进入家族
	protected long resetTime;//重置时间
	protected int contribute;//个人家族贡献值
	
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
		return new String[] {
		"`playerId`",
		"`joinTime`",
		"`leaveTime`",
		"`resetTime`",
		"`contribute`",
		};
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
	public String[] indexColumn() {
		return new String[] {
		};
	}
	
	@Override
	public Object[] indexValues() {
		return new Object[] {
		};
	}
	
	@Override
	public String cacheId() {
		return getPlayerId()+"";
	}
	
	
}
