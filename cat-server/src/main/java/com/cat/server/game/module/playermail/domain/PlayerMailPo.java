package com.cat.server.game.module.playermail.domain;

import com.cat.orm.core.base.BasePo;

/**
* PlayerMailPo
* @author Jeremy
*/
public abstract class PlayerMailPo extends BasePo {

	public static final String PROP_ID = "id";
	public static final String PROP_PLAYERID = "playerId";
	public static final String PROP_TITLE = "title";
	public static final String PROP_CONTENT = "content";
	public static final String PROP_REWARDS = "rewards";
	public static final String PROP_CREATETIME = "createTime";
	public static final String PROP_EXPIRETIME = "expireTime";
	public static final String PROP_STATE = "state";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_ID,
			PROP_PLAYERID,
			PROP_TITLE,
			PROP_CONTENT,
			PROP_REWARDS,
			PROP_CREATETIME,
			PROP_EXPIRETIME,
			PROP_STATE,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_ID,
			PROP_PLAYERID,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			PROP_PLAYERID,
			};
	
	
	/** 邮件ID*/
	protected long id;
	/** 角色ID*/
	protected long playerId;
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
	/** 0=未读取;1=已读*/
	protected byte state;
	
	public PlayerMailPo(){
		this.title = "";
		this.content = "";
		this.rewards = "";
	}
	
	/** 邮件ID **/
	public long getId(){
		return this.id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	/** 角色ID **/
	public long getPlayerId(){
		return this.playerId;
	}
	
	public void setPlayerId(long playerId){
		this.playerId = playerId;
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
	
	/** 0=未读取;1=已读 **/
	public byte getState(){
		return this.state;
	}
	
	public void setState(byte state){
		this.state = state;
	}
	
	
	@Override
	public String toString() {
		return "PlayerMail [id= "+ id +", playerId= "+ playerId +", title= "+ title +", content= "+ content +", rewards= "+ rewards
				 +", createTime= "+ createTime +", expireTime= "+ expireTime +", state= "+ state+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getId(),
		getPlayerId(),
		getTitle(),
		getContent(),
		getRewards(),
		getCreateTime(),
		getExpireTime(),
		getState(),
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
			PROP_PLAYERID,
		};
	}
	
	@Override
	public String cacheId() {
		return getId()+":"+getPlayerId();
	}
	
}
