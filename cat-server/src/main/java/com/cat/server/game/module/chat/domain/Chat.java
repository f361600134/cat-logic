package com.cat.server.game.module.chat.domain;

import com.cat.server.game.data.proto.PBPlayer.ChatInfo;
import com.cat.server.game.module.chat.proto.PBChatInfoBuilder;

/**
 * @description 聊天记录对象, 不存储
 * @author Jereme
 * @date 2020/8/21
 */
public class Chat {

	/** 发送玩id*/
	private long sendId;
	/** 内容*/
	private String content;
	/** 渠道*/
	private int channel;
	
	/**
	 * 发送至id
	 * channel为
	 * 私聊,toId为玩家id
	 * 公会,toId为工会id
	 * 世界,跨服,系统toId为0
	 * 同省,toId为省id
	 */
	private long targetId;
	
	/**
	 * 发言时间
	 */
	private long sendTime;

	public Chat() {
	}
	
	public Chat(String content, int channel) {
		this.sendId = -1;
		this.content = content;
		this.channel = channel;
		this.targetId = 0;
	}

	public Chat(long sendId, String content, int channel, long toId) {
		this.sendId = sendId;
		this.content = content;
		this.channel = channel;
		this.targetId = toId;
	}
	
	/**
	 * 创建系统聊天
	 * @param _content 文本
	 * @param _channel 频道
	 * @return Chat
	 */
	public static Chat createSystemChat(String content, int channel) {
		Chat pojo = new Chat(content, channel);
		return pojo;
	}


	/**
	 * 创建普通聊天
	 * @param _sendId 发送者id
	 * @param _content 文本
	 * @param _channel 频道
	 * @param _recvId 接收者id
	 * @return Chat
	 */
	public static Chat create(long sendId, String content, int channel, long recvId) {
		Chat pojo = new Chat(sendId, content, channel, recvId);
		return pojo;
	}

	public long getSendId() {
		return sendId;
	}

	public String getContent() {
		return content;
	}

	public int getChannel() {
		return channel;
	}

	public void setSendId(int sendId) {
		this.sendId = sendId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}
	
	public long getToId() {
		return targetId;
	}
	
	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	public void setSendId(long sendId) {
		this.sendId = sendId;
	}

	/**
	 * 聊天对象转协议对象
	 * @return ChatInfo
	 */
	public ChatInfo toProto() {
		PBChatInfoBuilder builder = PBChatInfoBuilder.newInstance();
		builder.setInfo(this);
		return builder.build();
	}
	
}
