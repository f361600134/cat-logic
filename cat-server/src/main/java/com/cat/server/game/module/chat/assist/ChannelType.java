package com.cat.server.game.module.chat.assist;

import java.util.HashMap;
import java.util.Map;

import com.cat.server.game.module.chat.type.IChatType;
import com.cat.server.game.module.chat.type.impl.FamilyChatType;
import com.cat.server.game.module.chat.type.impl.FriendChatType;
import com.cat.server.game.module.chat.type.impl.SystemChatType;
import com.cat.server.game.module.chat.type.impl.TeamChatType;
import com.cat.server.game.module.chat.type.impl.WorldChatType;

/**
 * 聊天枚举类
 * 
 * 
 */
public enum ChannelType {
	/**
	 * 世界聊天
	 */
	WORLD(0) {
		@Override
		public IChatType newInstance() {
			return new WorldChatType();
		}
	},
	/**
	 * 系统消息
	 */
	SYSTEM(1) {
		@Override
		public IChatType newInstance() {
			return new SystemChatType();
		}
	},
	/**
	 * 家族聊天
	 */
	FAMILY(2) {
	@Override
		public IChatType newInstance() {
			return new FamilyChatType();
		}
	},
	/**
	 * 好友私聊
	 */
	FRIEND(3) {
		@Override
		public IChatType newInstance() {
			return new FriendChatType();
		}
	},
	/**
	 * 队伍聊天
	 */
	TEAM(4) {
		@Override
		public IChatType newInstance() {
			return new TeamChatType();
		}
	},

	;

	private int channel;

	private ChannelType(int channel) {
		this.channel = channel;
	}

	public int getChannel() {
		return channel;
	}

	public abstract IChatType newInstance();

	public IChatType getChatType() {
		return channelTypeMap.get(getChannel());
	}

	/**
	 * 根据频道类型获取频道枚举
	 * 
	 * @param type
	 * @return
	 */
	public static ChannelType getChannelType(int channelType) {
		for (ChannelType chatType : values()) {
			if (chatType.getChannel() == channelType) {
				return chatType;
			}
		}
		return null;
	}

	public static Map<Integer, IChatType> channelTypeMap = new HashMap<>();
	static {
		for (ChannelType channelType : ChannelType.values()) {
			channelTypeMap.put(channelType.getChannel(), channelType.newInstance());
		}
	}

}
