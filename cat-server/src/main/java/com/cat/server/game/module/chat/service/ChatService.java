package com.cat.server.game.module.chat.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigChatModel;
import com.cat.server.game.data.config.local.ConfigConstantPlus;
import com.cat.server.game.data.proto.PBPlayer.ReqChat;
import com.cat.server.game.helper.result.ConfigTipsMgr;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.domain.ChatDetail;
import com.cat.server.game.module.chat.domain.ChatDomain;
import com.cat.server.game.module.chat.domain.ChatRule;
import com.cat.server.game.module.chat.magaer.ChatManager;
import com.cat.server.game.module.player.domain.Player;
import com.cat.server.game.module.player.domain.PlayerContext;
import com.cat.server.game.module.player.proto.AckTipsResp;
import com.cat.server.game.module.player.service.PlayerService;
import com.cat.server.utils.Pair;
import com.cat.server.utils.StringUtilitys;

@Service
public class ChatService {
	
	private static final Logger log = LoggerFactory.getLogger(ChatService.class);
	
	@Autowired private PlayerService playerService;
	
	@Autowired private ChatManager chatManager;
	
	////////////////业务/////////////////////////
	
	/**
	 * 当登陆成功,推送所有聊天信息
	 */
	public void onLogin(long playerId) {
		try {
//			for (ChatEnum chatEnum : ChatEnum.values()) {
//				ChatDomain chatDomain = chatEnum.getDomain(playerId);
//				if (chatDomain == null) {
//					continue;
//				}
//				AckChatResp ack = chatDomain.toAllProto();
//				playerService.sendMessage(playerId, ack);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 当玩家登出, 释放私聊缓存
	 */
	public void onLogout(long playerId) {
		try {
			log.info("onLogin playerId:{}",playerId);
//			System.out.println("onLogin playerId:{}"+playerId);
//			gc(playerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  1秒钟事, 聊天2秒同步广播1次
	 */
	public void onSecond() {
		try {
//			for (ChatEnum chatEnum : ChatEnum.values()) {
//				Collection<ChatDomain> domains = chatEnum.getAllDomain();
//				if (domains == null) {
//					continue;
//				}
//				for (ChatDomain chatDomain : domains) {
//					if (chatDomain == null || !chatDomain.isBroadcast()) {
//						continue;
//					}
//					AckChatResp resp = chatDomain.toNewerProto();
//					if (resp == null) {
//						continue;
//					}
//					Collection<Long> playerIds = chatEnum.findPlayerIds(chatDomain);
//					if(!playerIds.isEmpty()) {
//						playerService.sendMessage(playerIds, resp);
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据聊天模板,发送至聊天
	 * 1. 系统聊天
	 * 2. 功能分享
	 */
	public void onChat(ChannelType channelType, int configId, Object... args) {
		try {
			ConfigChatModel config = ConfigManager.getInstance().getConfig(ConfigChatModel.class, configId);
			if(config==null) {
				log.info("Cannot found config for chat, configId:{}", configId);
				return;
			}
			String text = config.getModelDesc();
			String content = StringUtilitys.formatString(text, Arrays.asList(args));
			
			ChatDomain chatDomain = chatManager.getDomain(channelType.getChannel());
			if (chatDomain == null) {
				return;
			}
			ChatDetail chatDetail = ChatDetail.create(content);
			chatDomain.sendMsg(chatDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 支持自定义系统聊天,譬如GM内容
	 * 1. 系统聊天
	 * 2. 功能分享
	 */
	public void onChat(ChannelType channelType, Long playerId, String content) {
		try {
			if (content.length() > 100) {//长度限制,拒绝广播
				return;
			}
			ChatDomain chatDomain = chatManager.getDomain(channelType.getChannel());
			if (chatDomain == null) {
				return;
			}
			ChatDetail chatDetail = ChatDetail.create(-1, content, playerId);
			chatDomain.sendMsg(chatDetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 聊天业务逻辑入口
	 * 新增聊天添加到聊天域, 不实时更新, 定时推送到前端
	 */
	public ErrorCode chat(ReqChat data, long playerId) {
		int channelId = data.getChatChannel();
		String content = data.getContent();
		long recvId = data.getPlayerId();
		ChatDomain domain = chatManager.getDomain(channelId);
		if (domain == null) {
			return ErrorCode.CHAT_CHANNEL_NOT_EXISTS;
		}
		
		Pair<ErrorCode, String> pair = checkContent(content);
		ErrorCode code = pair.getLeft();
		if (!code.isSuccess()) {//校验文本
			return code;
		}
		//使用过滤后的文本
		content = pair.getRight();
		PlayerContext context = playerService.getPlayerContext(playerId);
		final Player player = context.getPlayer();
		ChatRule rule = chatManager.getChatRule(playerId, channelId);
		long curTime = System.currentTimeMillis();
		if (curTime < rule.getNextSpeakTime() && rule.isAgainst()) { //聊天过快
			//提示玩家剩余x秒后可以聊天, 不允许其聊天
			int lessTime = (int)((rule.getNextSpeakTime() - curTime)/1000);
			AckTipsResp resp = AckTipsResp.newInstance();
			resp.setTipsId(ConfigTipsMgr.Chat_417).addParams(lessTime);
			playerService.sendMessage(playerId, resp);
			return ErrorCode.CHAT_CD;
		}
		//提示聊天过快, 但依旧允许其聊天
		if (curTime < rule.getNextSpeakTime()) { 
			//玩家频繁违法
			rule.onTrigger();
			AckTipsResp resp = AckTipsResp.newInstance().setTipsId(ConfigTipsMgr.Chat_411);
			playerService.sendMessage(playerId, resp);
		}
		
		ChatDetail chatDetail = ChatDetail.create(player.getPlayerId(), content, recvId);
		code = domain.sendMsg(chatDetail);
		rule.onChatSuccess();
		return code;
	}

	/**
	 * 聊天过滤
	 * @return 0:错误码, 1:转换后的文本
	 */
	private Pair<ErrorCode, String> checkContent(final String content) {
		String str = content;
		List<String> tags = new ArrayList<>();
		//对文字进行分解,玩家输入的文字只能包含图片
		int firstindex = str.indexOf("<");
		int lastindex = str.indexOf(">", firstindex);
		while (firstindex > 0 &&  lastindex > 0) {
			
			String tag = str.substring(firstindex , lastindex+1);
			str = str.replaceFirst(tag, "?");
			tags.add(tag);
			
			firstindex = str.indexOf("<");
			lastindex = str.indexOf(">", firstindex);
		}
//		int realLen = str.length() - tags.size();
		ErrorCode code = ErrorCode.SUCCESS;
		if (StringUtils.isBlank(str)) {
			code = ErrorCode.CHAT_MESSAGE_IS_EMPTY;//聊天内容为空
		} 
		if (content.length() > 100) {//总长度大于100超过限制
			code = ErrorCode.CHAT_MESSAGE_TOO_LONG;
		}
//		if (realLen > ConfigConstantPlus.chat) {//文字长度大于100超过限制
//			code = ErrorCode.CHAT_MESSAGE_TOO_LONG;
//		}
		if (tags.size() > ConfigConstantPlus.expression) {//玩家只能发送表情标签, 表情大于5, 视为失败
			code = ErrorCode.CHAT_MESSAGE_TOO_LONG;
		}
		//TODO 文本过滤
		//str = BadWordFilter.doFilter(str);
		//还原文本
		for (String tag : tags) {
			str = str.replaceFirst("\\?", tag);
		}
		
		return Pair.of(code, str);
	}
	
}
