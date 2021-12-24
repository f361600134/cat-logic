package com.cat.server.game.module.groupmail.domain;

import com.cat.orm.core.base.BasePo;

/**
* GroupMailPo
* @author Jeremy
*/
public abstract class GroupMailPo extends BasePo {

	public static final String PROP_ID = "id";
	public static final String PROP_TITLE = "title";
	public static final String PROP_CONTENT = "content";
	public static final String PROP_REWARDS = "rewards";
	public static final String PROP_CREATETIME = "createTime";
	public static final String PROP_EXPIRETIME = "expireTime";
	public static final String PROP_EXTENDSTR = "extendStr";
	public static final String PROP_CURSERVERID = "curServerId";
	public static final String PROP_INITSERVERID = "initServerId";
	public static final String PROP_BACKSTAGEID = "backstageId";
	public static final String PROP_SERVERIDSTR = "serverIdStr";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_ID,
			PROP_TITLE,
			PROP_CONTENT,
			PROP_REWARDS,
			PROP_CREATETIME,
			PROP_EXPIRETIME,
			PROP_EXTENDSTR,
			PROP_CURSERVERID,
			PROP_INITSERVERID,
			PROP_BACKSTAGEID,
			PROP_SERVERIDSTR,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_ID,
			PROP_CURSERVERID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			PROP_CURSERVERID,
			};
	
	
	/** 邮件唯一ID,本服唯一邮件id*/
	protected long id;
	/** 邮件标题*/
	protected String title;
	/** 邮件内容*/
	protected String content;
	/** 奖励:{configId:num,configId:num}*/
	protected String rewards;
	/** 邮件创建时间*/
	protected long createTime;
	/** 邮件过期时间*/
	protected long expireTime;
	/** 额外信息,包含已读,已领取玩家id列表*/
	protected String extendStr;
	/** 当前服务器Id*/
	protected int curServerId;
	/** 原始服务器Id*/
	protected int initServerId;
	/** 后台生成的邮件id，用于合服时数据合并*/
	protected long backstageId;
	/** 邮件发送的服务器组*/
	protected String serverIdStr;
	
	public GroupMailPo(){
		this.title = "";
		this.content = "";
		this.rewards = "";
		this.extendStr = "";
		this.serverIdStr = "";
	}
	
	/** 邮件唯一ID,本服唯一邮件id **/
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	/** 邮件标题 **/
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	/** 邮件内容 **/
	public String getContent(){
		return this.content;
	}
	
	public void setContent(String content){
		this.content = content;
	}
	
	/** 奖励:{configId:num,configId:num} **/
	public String getRewards(){
		return this.rewards;
	}
	
	public void setRewards(String rewards){
		this.rewards = rewards;
	}
	
	/** 邮件创建时间 **/
	public long getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(long createTime){
		this.createTime = createTime;
	}
	
	/** 邮件过期时间 **/
	public long getExpireTime(){
		return this.expireTime;
	}
	
	public void setExpireTime(long expireTime){
		this.expireTime = expireTime;
	}
	
	/** 额外信息,包含已读,已领取玩家id列表 **/
	public String getExtendStr(){
		return this.extendStr;
	}
	
	public void setExtendStr(String extendStr){
		this.extendStr = extendStr;
	}
	
	/** 当前服务器Id **/
	public int getCurServerId(){
		return this.curServerId;
	}
	
	public void setCurServerId(int curServerId){
		this.curServerId = curServerId;
	}
	
	/** 原始服务器Id **/
	public int getInitServerId(){
		return this.initServerId;
	}
	
	public void setInitServerId(int initServerId){
		this.initServerId = initServerId;
	}
	
	/** 后台生成的邮件id，用于合服时数据合并 **/
	public long getBackstageId(){
		return this.backstageId;
	}
	
	public void setBackstageId(long backstageId){
		this.backstageId = backstageId;
	}
	
	/** 邮件发送的服务器组 **/
	public String getServerIdStr(){
		return this.serverIdStr;
	}
	
	public void setServerIdStr(String serverIdStr){
		this.serverIdStr = serverIdStr;
	}
	
	
	@Override
	public String toString() {
		return "GroupMail [id= "+ id +", title= "+ title +", content= "+ content +", rewards= "+ rewards +", createTime= "+ createTime
				 +", expireTime= "+ expireTime +", extendStr= "+ extendStr +", curServerId= "+ curServerId +", initServerId= "+ initServerId +", backstageId= "+ backstageId
				 +", serverIdStr= "+ serverIdStr+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getId(),
		getTitle(),
		getContent(),
		getRewards(),
		getCreateTime(),
		getExpireTime(),
		getExtendStr(),
		getCurServerId(),
		getInitServerId(),
		getBackstageId(),
		getServerIdStr(),
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
			getCurServerId(),
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
		};
	}
	
	@Override
	public String cacheId() {
		return String.valueOf(getId());
	}
	
}
