package com.cat.server.game.module.playermail.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.orm.core.base.BasePo;
import com.cat.orm.core.base.IBasePo;

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
	
	protected int id;//邮件ID
	protected long playerId;//角色ID
	protected String title;//邮件标题
	protected String content;//邮件内容
	protected String rewards;//奖励:{configId:num,configId:num}
	protected long createTime;//邮件创建时间戳
	protected long expireTime;//邮件过期时间戳
	protected boolean state;//0=未读取;1=已读
	
	public PlayerMailPo(){
		this.title = "";
		this.content = "";
		this.rewards = "";
	}
	
	/** 邮件ID **/
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
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
	
	/** 邮件创建时间戳 **/
	public long getCreateTime(){
		return this.createTime;
	}
	
	public void setCreateTime(long createTime){
		this.createTime = createTime;
	}
	
	/** 邮件过期时间戳 **/
	public long getExpireTime(){
		return this.expireTime;
	}
	
	public void setExpireTime(long expireTime){
		this.expireTime = expireTime;
	}
	
	/** 0=未读取;1=已读 **/
	public boolean getState(){
		return this.state;
	}
	
	public void setState(boolean state){
		this.state = state;
	}
	
	
	@Override
	public String toString() {
		return "PlayerMail [id= "+ id +", playerId= "+ playerId +", title= "+ title +", content= "+ content +", rewards= "+ rewards
				 +", createTime= "+ createTime +", expireTime= "+ expireTime +", state= "+ state+"]";
	}
	
	@Override
	public String[] props() {
		return new String[] {
		"`id`",
		"`playerId`",
		"`title`",
		"`content`",
		"`rewards`",
		"`createTime`",
		"`expireTime`",
		"`state`",
		};
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
	public String[] indexColumn() {
		return new String[] {
			PROP_PLAYERID,
		};
	}
	
	@Override
	public Object[] indexValues() {
		return new Object[] {
				getPlayerId(),
		};
	}
	
		@Override
	public String cacheId() {
		return getPlayerId()+":"+getId();
	}
	
	
}
