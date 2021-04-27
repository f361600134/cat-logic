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
	
	protected long leftKey;//左key，与右key组合成唯一key
	protected long rightKey;//右key，与左key组合成唯一key
	protected String data;//玩家聊天数据
	protected int channel;//频道
	
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
		return new String[] {
		"`leftKey`",
		"`rightKey`",
		"`data`",
		"`channel`",
		};
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
	public String[] indexColumn() {
		return new String[] {
			PROP_LEFTKEY,
			PROP_RIGHTKEY,
		};
	}
	
	@Override
	public Object[] indexValues() {
		return new Object[] {
			getLeftKey(),
			getRightKey(),
		};
	}
	
	@Override
	public String cacheId() {
		return getLeftKey()+":"+getRightKey();
	}
	
	
}
