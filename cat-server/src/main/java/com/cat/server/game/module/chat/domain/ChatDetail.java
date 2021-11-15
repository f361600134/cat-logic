package com.cat.server.game.module.chat.domain;

import com.cat.server.utils.TimeUtil;

/**
 * 聊天通用记录, 用于所有频道. 这个对象仅仅是每条聊天的实体信息.
 * 
 * 
 */
public class ChatDetail {
	/**
	 * 发送玩id
	 */
	private long senderId;
	/**
	 * 聊天内容
	 */
	private String content;
	/**
	 * 发送至id channel为 私聊,targetId为玩家id
	 */
	private long targetId;
	/**
	 * 发言时间
	 */
	private long sendTime;
	
	public ChatDetail() {
		this.content = "";
		this.sendTime = TimeUtil.now();
	}

	private ChatDetail(long senderId, String content, long targetId) {
		this.senderId = senderId;
		this.content = content;
		this.targetId = targetId;
		this.sendTime = TimeUtil.now();
	}

	public long getSenderId() {
		return senderId;
	}

	public void setSenderId(long senderId) {
		this.senderId = senderId;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getSendTime() {
		return sendTime;
	}

	public void setSendTime(long sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 创建一条玩家聊天实体信息, 携带发送者id,用于玩家发送的聊天
	 * 
	 * @param senderId   发送者id
	 * @param content  发送内容
	 * @param targetId 目标id,不同频道目标id不一致
	 * @return 聊天实体
	 */
	public static ChatDetail create(long senderId, String content, long targetId) {
		return new ChatDetail(senderId, content, targetId);
	}

	/**
	 * 创建一条聊天实体信息, 不携带发送者,
	 * @param content 发送内容
	 * @return 聊天实体
	 */
	public static ChatDetail create(String content) {
		return new ChatDetail(-1, content, -1);
	}
}
