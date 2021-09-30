package com.cat.server.game.module.activity.domain;

import com.cat.orm.core.base.BasePo;

/**
* ActivityPo
* @author Jeremy
*/
public abstract class ActivityPo extends BasePo {

	public static final String PROP_ID = "id";
	public static final String PROP_STATUS = "status";
	public static final String PROP_STAGE = "stage";
	public static final String PROP_PAUSE = "pause";
	public static final String PROP_CONFIGID = "configId";
	public static final String PROP_CONFIGTYPE = "configType";
	public static final String PROP_PLANID = "planId";
	public static final String PROP_BEGINTIME = "beginTime";
	public static final String PROP_SETTLETIME = "settleTime";
	public static final String PROP_CLOSETIME = "closeTime";
	public static final String PROP_CUSTOMDATA = "customData";
	public static final String PROP_BAGDATA = "bagData";
	public static final String PROP_OPENEDNUM = "openedNum";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_ID,
			PROP_STATUS,
			PROP_STAGE,
			PROP_PAUSE,
			PROP_CONFIGID,
			PROP_CONFIGTYPE,
			PROP_PLANID,
			PROP_BEGINTIME,
			PROP_SETTLETIME,
			PROP_CLOSETIME,
			PROP_CUSTOMDATA,
			PROP_BAGDATA,
			PROP_OPENEDNUM,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_ID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			};
	
	
	/** 活动类型id*/
	protected int id;
	/** 活动状态*/
	protected int status;
	/** 活动阶段*/
	protected int stage;
	/** 是否暂停中*/
	protected boolean pause;
	/** 配置id*/
	protected int configId;
	/** 配置类型*/
	protected int configType;
	/** 活动方案id*/
	protected int planId;
	/** 活动开始时间*/
	protected long beginTime;
	/** 活动结算时间*/
	protected long settleTime;
	/** 活动结束时间*/
	protected long closeTime;
	/** 活动的自定义数据*/
	protected byte[] customData;
	/** 活动背包缓存的数据*/
	protected byte[] bagData;
	/** 开启的次数*/
	protected int openedNum;
	
	public ActivityPo(){
	}
	
	/** 活动类型id **/
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	/** 活动状态 **/
	public int getStatus(){
		return this.status;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	/** 活动阶段 **/
	public int getStage(){
		return this.stage;
	}
	
	public void setStage(int stage){
		this.stage = stage;
	}
	
	/** 是否暂停中 **/
	public boolean getPause(){
		return this.pause;
	}
	
	public void setPause(boolean pause){
		this.pause = pause;
	}
	
	/** 配置id **/
	public int getConfigId(){
		return this.configId;
	}
	
	public void setConfigId(int configId){
		this.configId = configId;
	}
	
	/** 配置类型 **/
	public int getConfigType(){
		return this.configType;
	}
	
	public void setConfigType(int configType){
		this.configType = configType;
	}
	
	/** 活动方案id **/
	public int getPlanId(){
		return this.planId;
	}
	
	public void setPlanId(int planId){
		this.planId = planId;
	}
	
	/** 活动开始时间 **/
	public long getBeginTime(){
		return this.beginTime;
	}
	
	public void setBeginTime(long beginTime){
		this.beginTime = beginTime;
	}
	
	/** 活动结算时间 **/
	public long getSettleTime(){
		return this.settleTime;
	}
	
	public void setSettleTime(long settleTime){
		this.settleTime = settleTime;
	}
	
	/** 活动结束时间 **/
	public long getCloseTime(){
		return this.closeTime;
	}
	
	public void setCloseTime(long closeTime){
		this.closeTime = closeTime;
	}
	
	/** 活动的自定义数据 **/
	public byte[] getCustomData(){
		return this.customData;
	}
	
	public void setCustomData(byte[] customData){
		this.customData = customData;
	}
	
	/** 活动背包缓存的数据 **/
	public byte[] getBagData(){
		return this.bagData;
	}
	
	public void setBagData(byte[] bagData){
		this.bagData = bagData;
	}
	
	/** 开启的次数 **/
	public int getOpenedNum(){
		return this.openedNum;
	}
	
	public void setOpenedNum(int openedNum){
		this.openedNum = openedNum;
	}
	
	
	@Override
	public String toString() {
		return "Activity [id= "+ id +", status= "+ status +", stage= "+ stage +", pause= "+ pause +", configId= "+ configId
				 +", configType= "+ configType +", planId= "+ planId +", beginTime= "+ beginTime +", settleTime= "+ settleTime +", closeTime= "+ closeTime
				 +", customData= "+ customData +", bagData= "+ bagData +", openedNum= "+ openedNum+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getId(),
		getStatus(),
		getStage(),
		getPause(),
		getConfigId(),
		getConfigType(),
		getPlanId(),
		getBeginTime(),
		getSettleTime(),
		getCloseTime(),
		getCustomData(),
		getBagData(),
		getOpenedNum(),
		};
	}
	
	@Override
	public Object key() {
		return getId();
	}
	
	@Override
	public String keyColumn() {
		return PROP_ID;
	}

	@Override
	public String[] keyAndIndexColumn() {
		return KEY_AND_INDEX_COLUMN;
	}
	
	@Override
	public Object[] keyAndIndexValues() {
		return new Object[] {
			getId(),
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
		return String.valueOf(getId());
	}
	
}
