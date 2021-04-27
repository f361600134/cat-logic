package com.cat.server.game.module.chat.domain;


import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

import com.cat.server.core.server.IModuleDomain;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.type.IChatType;
import com.cat.server.utils.Pair;

/**
 * 聊天域
 * 队伍聊天, 好友聊天不做持久化,
 * 世界聊天, 系统聊天, 家族聊天做少量持久化 
 * @author Jeremy
 */
public class ChatDomain implements IChatType{
	
	/**
	 * 频道id
	 */
	private int id;
	
	private IChatType chatType;
	
	public ChatDomain(int id) {
		this.id = id;
		chatType = ChannelType.channelTypeMap.get(id);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public IChatType getChatType() {
		return chatType;
	}

	public void setChatType(IChatType chatType) {
		this.chatType = chatType;
	}
	
	@Override
	public int getChannel() {
		return chatType.getChannel();
	}

	@Override
	public BigInteger getUniqueId(long senderId, long targetId) {
		return chatType.getUniqueId(senderId, targetId);
	}

	@Override
	public Collection<Long> findReceiverIds(BigInteger uniqueId) {
		return chatType.findReceiverIds(uniqueId);
	}

	@Override
	public Pair<Long, Long> getOriginalIds(BigInteger uniqueId) {
		return chatType.getOriginalIds(uniqueId);
	}

	@Override
	public ErrorCode checkChat(long senderId, BigInteger uniqueId) {
		return chatType.checkChat(senderId, uniqueId);
	}

	@Override
	public ErrorCode sendMsg(ChatDetail chatDetails) {
		return chatType.sendMsg(chatDetails);
	}

//	/**
//	 * channel for chat
//	 */
//	private int channel;
//	
//	/**
//	 * Unique ID, According to different ids by the different domain.
//	 */
//	private long domainId;
//	
//	/**
//	 * chat cache base on FIFO.
//	 */
//	private List<ChatInfo> chatCache;
//	/**
//	 * The newer add this queue.
//	 */
//	private Queue<ChatInfo> chatQueue;
//	
//	/**
//	 * Last broadcast time, it used to record broadcast time.
//	 */
//	private int lastBroadcastTime;
//	
//	/**
//	 * the size of the queue, it used for quantity limit.
//	 */
//	private AtomicInteger queueSize;
//	
//	
//	public ChatDomain(int channel) {
//		this.channel = channel;
//		//ConfigChat config = ConfigChatMgr.getConfig(channel);
//		ConfigChat config = ConfigManager.getInstance().getConfig(ConfigChat.class, channel);
//		this.chatCache = new ConcurrentFixSizeArrayList<ChatInfo>(config.getCacheNum());
//		this.chatQueue = new ConcurrentLinkedQueue<ChatInfo>();
//		this.queueSize = new AtomicInteger(0);
//	}
//	
//	public ChatDomain(int channel, long domainId) {
//		this.channel = channel;
//		this.domainId = domainId;
//		//ConfigChat config = ConfigChatMgr.getConfig(channel);
//		ConfigChat config = ConfigManager.getInstance().getConfig(ConfigChat.class, channel);
//		this.chatCache = new ConcurrentFixSizeArrayList<ChatInfo>(config.getCacheNum());
//		this.chatQueue = new ConcurrentLinkedQueue<ChatInfo>();
//		this.queueSize = new AtomicInteger(0);
//	}
//	
//	/**
//	 * 添加新聊天
//	 * @param chat
//	 * @param isCache 是否缓存, true:缓存, false, 不缓存
//	 */
//	public void addChat(Chat chat, boolean isCache) {
//		ChatInfo chatInfo = chat.toProto();
//		this.chatQueue.add(chatInfo);
//		this.queueSize.incrementAndGet();
//		if (isCache) {
//			this.chatCache.add(chatInfo);
//		}
//	}
//	
//	/**
//	 * return true if the queueSize greater than specified quantity 
//	 * or the current time greater than the last broadcast time.
//	 * @author Jeremy
//	 */
//	public boolean isBroadcast() {
//		//ConfigChat config = ConfigChatMgr.getConfig(channel);
//		ConfigChat config = ConfigManager.getInstance().getConfig(ConfigChat.class, channel);
//		long nextTime = lastBroadcastTime + config.getBroadcastIntervalTime() * 1000;
//		return System.currentTimeMillis() > nextTime || queueSize.get() >  config.getDefaultBroadcastNum();
//	}
//	
//	/**
//	 * 缓存的聊天序列话为消息体
//	 * @return
//	 */
//	public AckChatResp toAllProto(){
//		Iterator<ChatInfo> iter = chatCache.iterator();
//		AckChatResp resp = AckChatResp.newInstance();
//		while (iter.hasNext()) {
//			ChatInfo chat = (ChatInfo) iter.next();
//			resp.addChat(channel, chat);
//		}
//		return resp;
//	}
//	
//	/**
//	 * 新增的聊序列话消息体
//	 * @return
//	 */
//	public AckChatResp toNewerProto(){
//		ChatInfo chat = chatQueue.poll();
//		if (chat == null) {
//			return null;
//		}
//		AckChatResp resp = AckChatResp.newInstance();
//		while(chat != null) {
//			resp.addChat(channel, chat);
//			chat = chatQueue.poll();
//		}
//		return resp;
//	}
//	
//	public long getDomainId() {
//		return domainId;
//	}
	
}
