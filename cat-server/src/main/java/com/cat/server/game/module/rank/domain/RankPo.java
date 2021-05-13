package com.cat.server.game.module.rank.domain;

import com.cat.orm.core.base.BasePo;

/**
* RankPo
* @author Jeremy
*/
public abstract class RankPo extends BasePo {

	public static final String PROP_CURSERVERID = "curServerId";
	public static final String PROP_RANKTYPE = "rankType";
	public static final String PROP_UNIQUEID = "uniqueId";
	public static final String PROP_FIRSTVALUE = "firstValue";
	public static final String PROP_SECONDVALUE = "secondValue";
	public static final String PROP_THIRDVALUE = "thirdValue";
	public static final String PROP_CREATETIME = "createTime";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_CURSERVERID,
			PROP_RANKTYPE,
			PROP_UNIQUEID,
			PROP_FIRSTVALUE,
			PROP_SECONDVALUE,
			PROP_THIRDVALUE,
			PROP_CREATETIME,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_CURSERVERID,
			PROP_RANKTYPE,
			PROP_UNIQUEID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			PROP_CURSERVERID,
			PROP_RANKTYPE,
			PROP_UNIQUEID,
			};
	
	
	/** 当前服务器Id*/
	protected int curServerId;
	/** 排行榜类型*/
	protected int rankType;
	/** 对象id*/
	protected long uniqueId;
	/** 第一比较值,最主要的比较值*/
	protected long firstValue;
	/** 第二比较值,第一比较值相同的情况下, 比较此值*/
	protected long secondValue;
	/** 第三比较值,第二比较值相同的情况下, 比较此值*/
	protected long thirdValue;
	/** 入榜时间*/
	protected long createTime;
	
	public RankPo(){
	}
	
	/** 当前服务器Id **/
	public int getCurServerId(){
		return this.curServerId;
	}
	
	public void setCurServerId(int curServerId){
		this.curServerId = curServerId;
	}
	
	/** 排行榜类型 **/
	public int getRankType(){
		return this.rankType;
	}
	
	public void setRankType(int rankType){
		this.rankType = rankType;
	}
	
	/** 对象id **/
	public long getUniqueId(){
		return this.uniqueId;
	}
	
	public void setUniqueId(long uniqueId){
		this.uniqueId = uniqueId;
	}
	
	/** 第一比较值,最主要的比较值 **/
	public long getFirstValue(){
		return this.firstValue;
	}
	
	public void setFirstValue(long firstValue){
		this.firstValue = firstValue;
	}
	
	/** 第二比较值,第一比较值相同的情况下, 比较此值 **/
	public long getSecondValue(){
		return this.secondValue;
	}
	
	public void setSecondValue(long secondValue){
		this.secondValue = secondValue;
	}
	
	/** 第三比较值,第二比较值相同的情况下, 比较此值 **/
	public long getThirdValue(){
		return this.thirdValue;
	}
	
	public void setThirdValue(long thirdValue){
		this.thirdValue = thirdValue;
	}
	
	/** 入榜时间 **/
	public long getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(long createTime){
		this.createTime = createTime;
	}
	
	
	@Override
	public String toString() {
		return "Rank [curServerId= "+ curServerId +", rankType= "+ rankType +", uniqueId= "+ uniqueId +", firstValue= "+ firstValue +", secondValue= "+ secondValue
				 +", thirdValue= "+ thirdValue +", createTime= "+ createTime+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getCurServerId(),
		getRankType(),
		getUniqueId(),
		getFirstValue(),
		getSecondValue(),
		getThirdValue(),
		getCreateTime(),
		};
	}
	
	@Override
	public Object key() {
		//主键为空,返回null
		return null;
	}
	
	@Override
	public String keyColumn() {
		//主键为空,返回null
		return null;
	}

	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}
	
	public Object[] keyAndIndexValues() {
		return new Object[] {
			getCurServerId(),
			getRankType(),
			getUniqueId(),
		};
	}
	
	@Override
	public String[] indexColumn() {
		return INDEX_ALL;
	}
	
	@Override
	public String[] indexValues() {
		return new String[] {
			PROP_CURSERVERID,
			PROP_RANKTYPE,
			PROP_UNIQUEID,
		};
	}
	
	@Override
	public String cacheId() {
		//主键为空,返回索引组合
		return getCurServerId()+":"+getRankType()+":"+getUniqueId();
	}
	
}
