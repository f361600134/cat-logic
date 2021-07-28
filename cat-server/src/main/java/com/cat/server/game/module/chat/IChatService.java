package com.cat.server.game.module.chat;

import com.cat.server.game.module.chat.assist.ChannelType;

/**
 * 聊天对外接口
 * @author Jeremy
 *
 */
public interface IChatService {
	
	/**
	 * 根据聊天模板,发送至聊天
	 * 1. 系统聊天
	 * 2. 功能分享
	 */
	public void onChat(ChannelType channelType, int configId, Object... args);
	
	/**
	 *  聊天调用, 用于跨服聊天同步聊天内容
	 * @param chatEnum
	 * @param content
	 */
	public void onChat(ChannelType channelType, String content) ;
	
	/**
	 * 聊天调用, 用于跨服聊天同步聊天内容
	 * @param chatEnum
	 * @param playerId
	 * @param content
	 */
	public void onChat(ChannelType channelType, Long playerId, String content);
	
	

}
