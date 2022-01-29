package com.cat.server.game.module.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.cat.server.game.module.chat.proto.RespChatBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigConstantPlus;
import com.cat.server.game.data.config.local.base.ConfigChatModelBase;
import com.cat.server.game.data.proto.PBChat.ReqChat;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.chat.assist.ChannelType;
import com.cat.server.game.module.chat.domain.ChatDetail;
import com.cat.server.game.module.chat.domain.ChatDomain;
import com.cat.server.game.module.chat.domain.ChatRule;
import com.cat.server.game.module.common.proto.RespTipsBuilder;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.player.domain.Player;
import com.cat.server.game.module.player.domain.PlayerContext;
import com.cat.server.utils.Pair;
import com.cat.server.utils.StringUtilitys;
import com.cat.server.utils.TimeUtil;

@Service
class ChatService {
	
	private static final Logger log = LoggerFactory.getLogger(ChatService.class);
	
	@Autowired private IPlayerService playerService;
	@Autowired private ChatManager chatManager;
	
	////////////////业务/////////////////////////
	/**
	 * 当登陆成功,推送所有频道的聊天信息
	 */
	public void onLogin(long playerId) {
		try {
			for (ChannelType chatEnum : ChannelType.values()) {
				ChatDomain chatDomain = chatManager.getOrLoadDomain(chatEnum.getChannel());
				if (chatDomain == null) {
					continue;
				}
				Collection<ChatDetail> details = chatDomain.getAllChatRecord(playerId, chatDomain.getChannel());
				RespChatBuilder builder = RespChatBuilder.newInstance();
				builder.setChannel(chatDomain.getChannel());
				details.forEach(detail->{
					builder.addChats(chatDomain.getChatType().buildChatDto(detail));
				});
				playerService.sendMessage(playerId, builder);
			}
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
			ConfigChatModelBase config = ConfigManager.getInstance().getConfig(ConfigChatModelBase.class, configId);
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
			//长度限制,拒绝广播
			if (content.length() > 100) {
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
		int channelId = data.getChannel();
		String content = data.getContent();
		long recvId = data.getRecvId();
		ChatDomain domain = chatManager.getDomain(channelId);
		if (domain == null) {
			return ErrorCode.CHAT_CHANNEL_NOT_EXISTS;
		}
		
		Pair<ErrorCode, String> pair = checkContent(content);
		ErrorCode code = pair.getLeft();
		//校验文本
		if (!code.isSuccess()) {
			return code;
		}
		//使用过滤后的文本
		content = pair.getRight();
		PlayerContext context = playerService.getPlayerContext(playerId);
		final Player player = context.getPlayer();
		ChatRule rule = chatManager.getChatRule(playerId, channelId);
		long curTime = TimeUtil.now();
		if (rule.isProhibited(curTime)) {
			//提示玩家剩余x秒后可以聊天, 不转发聊天内容
			int lessTime = (int)((rule.getNextSpeakTime() - curTime)/1000);
			playerService.sendMessage(playerId, ErrorCode.CHAT_TIME_LIMIT.toProto(lessTime));
			return ErrorCode.CHAT_CD;
		}
		else if (rule.isAgainst(curTime)) {
			//提示聊天过快, 但依旧允许其聊天
			playerService.sendMessage(playerId, ErrorCode.CHAT_CD.toProto());
		}
		ChatDetail chatDetail = ChatDetail.create(player.getPlayerId(), content, recvId);
		code = domain.sendMsg(chatDetail);
		rule.onChatSuccess(curTime);
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
			//聊天内容为空
			code = ErrorCode.CHAT_MESSAGE_IS_EMPTY;
		}
		//总长度大于100超过限制
		if (content.length() > 100) {
			code = ErrorCode.CHAT_MESSAGE_TOO_LONG;
		}
		//文字长度大于100超过限制
//		if (realLen > configconstantplus.chat) {
//			code = ErrorCode.CHAT_MESSAGE_TOO_LONG;
//		}
		//玩家只能发送表情标签, 表情大于5, 视为失败
		if (tags.size() > ConfigConstantPlus.expression) {
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
