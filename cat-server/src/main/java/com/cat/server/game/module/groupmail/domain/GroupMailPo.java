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
	public static final String PROP_OPTMAILID = "optMailId";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_ID,
			PROP_TITLE,
			PROP_CONTENT,
			PROP_REWARDS,
			PROP_CREATETIME,
			PROP_EXPIRETIME,
			PROP_EXTENDSTR,
			PROP_OPTMAILID,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_ID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
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
	/** 运营邮件id,后台发出的邮件,携带后台那边的邮件id*/
	protected long optMailId;
	
	public GroupMailPo(){
		this.title = "";
		this.content = "";
		this.rewards = "";
		this.extendStr = "";
	}
	
	/** 邮件唯一ID,本服唯一邮件id **/
	public long getId(){
		return this.id;
	}
	
	void setId(long id){
		this.id = id;
	}
	
	/** 邮件标题 **/
	public String getTitle(){
		return this.title;
	}
	
	void setTitle(String title){
		this.title = title;
	}
	
	/** 邮件内容 **/
	public String getContent(){
		return this.content;
	}
	
	void setContent(String content){
		this.content = content;
	}
	
	/** 奖励:{configId:num,configId:num} **/
	public String getRewards(){
		return this.rewards;
	}
	
	void setRewards(String rewards){
		this.rewards = rewards;
	}
	
	/** 邮件创建时间 **/
	public long getCreateTime(){
		return this.createTime;
	}
	
	void setCreateTime(long createTime){
		this.createTime = createTime;
	}
	
	/** 邮件过期时间 **/
	public long getExpireTime(){
		return this.expireTime;
	}
	
	void setExpireTime(long expireTime){
		this.expireTime = expireTime;
	}
	
	/** 额外信息,包含已读,已领取玩家id列表 **/
	public String getExtendStr(){
		return this.extendStr;
	}
	
	void setExtendStr(String extendStr){
		this.extendStr = extendStr;
	}
	
	/** 运营邮件id,后台发出的邮件,携带后台那边的邮件id **/
	public long getOptMailId(){
		return this.optMailId;
	}
	
	void setOptMailId(long optMailId){
		this.optMailId = optMailId;
	}
	
	
	@Override
	public String toString() {
		return "GroupMail [id= "+ id +", title= "+ title +", content= "+ content +", rewards= "+ rewards +", createTime= "+ createTime
				 +", expireTime= "+ expireTime +", extendStr= "+ extendStr +", optMailId= "+ optMailId+"]";
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
		getOptMailId(),
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
