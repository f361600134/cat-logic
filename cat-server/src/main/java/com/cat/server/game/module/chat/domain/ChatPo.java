package com.cat.server.game.module.chat.domain;

import com.cat.orm.core.base.BasePo;

/**
* ChatPo
* @author Jeremy
*/
public abstract class ChatPo extends BasePo {

	public static final String PROP_LEFTKEY = "leftKey";
	public static final String PROP_RIGHTKEY = "rightKey";
	public static final String PROP_DATA = "data";
	public static final String PROP_CHANNEL = "channel";
	
	/** 所有列字段数组*/
	public static final String[] PROP_ALL = new String[] {
			PROP_LEFTKEY,
			PROP_RIGHTKEY,
			PROP_DATA,
			PROP_CHANNEL,
			};
			
	/** 所有主键索引字段数组*/
	public static final String[] KEY_AND_INDEX_COLUMN = new String[] {
			PROP_LEFTKEY,
			PROP_RIGHTKEY,
			};
		
	/** 所有索引字段数组*/
	public static final String[] INDEX_ALL = new String[] {
			PROP_LEFTKEY,
			PROP_RIGHTKEY,
			};
	
	
	/** 左key，与右key组合成唯一key*/
	protected long leftKey;
	/** 右key，与左key组合成唯一key*/
	protected long rightKey;
	/** 玩家聊天数据*/
	protected String data;
	/** 频道*/
	protected int channel;
	
	public ChatPo(){
		this.data = "";
	}
	
	/** 左key，与右key组合成唯一key **/
	public long getLeftKey(){
		return this.leftKey;
	}
	
	public void setLeftKey(long leftKey){
		this.leftKey = leftKey;
	}
	
	/** 右key，与左key组合成唯一key **/
	public long getRightKey(){
		return this.rightKey;
	}
	
	public void setRightKey(long rightKey){
		this.rightKey = rightKey;
	}
	
	/** 玩家聊天数据 **/
	public String getData(){
		return this.data;
	}
	
	public void setData(String data){
		this.data = data;
	}
	
	/** 频道 **/
	public int getChannel(){
		return this.channel;
	}
	
	public void setChannel(int channel){
		this.channel = channel;
	}
	
	
	@Override
	public String toString() {
		return "Chat [leftKey= "+ leftKey +", rightKey= "+ rightKey +", data= "+ data +", channel= "+ channel+"]";
	}
	
	@Override
	public String[] props() {
		return PROP_ALL;
	}

	@Override
	public Object[] propValues() {
		return new Object[] { 
		getLeftKey(),
		getRightKey(),
		getData(),
		getChannel(),
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
			getLeftKey(),
			getRightKey(),
		};
	}
	
	@Override
	public String[] indexColumn() {
		return INDEX_ALL;
	}
	
	@Override
	public String[] indexValues() {
		return new String[] {
			PROP_LEFTKEY,
			PROP_RIGHTKEY,
		};
	}
	
	@Override
	public String cacheId() {
		//主键为空,返回索引组合
		return getLeftKey()+":"+getRightKey();
	}
	
}
