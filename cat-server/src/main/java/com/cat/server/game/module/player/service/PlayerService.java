package com.cat.server.game.module.player.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.cat.net.core.executor.DisruptorStrategy;
import com.cat.net.network.base.GameSession;
import com.cat.net.network.base.IProtocol;
import com.cat.orm.core.db.process.DataProcessorAsyn;
import com.cat.server.common.ServerConfig;
import com.cat.server.common.ServerConstant;
import com.cat.server.core.event.GameEventBus;
import com.cat.server.game.data.proto.PBLogin;
import com.cat.server.game.data.proto.PBLogin.AckLogin;
import com.cat.server.game.data.proto.PBLogin.ReqLogin;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.domain.Player;
import com.cat.server.game.module.player.domain.PlayerContext;
import com.cat.server.game.module.player.event.PlayerLoadEndEvent;
import com.cat.server.game.module.player.event.PlayerLoadEvent;
import com.cat.server.game.module.player.event.PlayerLoginEndEvent;
import com.cat.server.game.module.player.event.PlayerLoginEvent;
import com.cat.server.game.module.player.proto.AckLoginResp;
import com.cat.server.game.module.player.resp.ResAuthResult;
import com.cat.server.game.module.resource.IResourceService;
import com.cat.server.utils.HttpClientUtil;

@Component
public class PlayerService implements IPlayerService, IResourceService {

	private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

	@Autowired
	private ServerConfig config;

	@Autowired
	private DataProcessorAsyn process;
	
	@Autowired
	private GameEventBus eventBus;
	
	/**
	 * 玩家登录信息缓存
	 */
	private Map<String, PlayerContext> accountMap = new ConcurrentHashMap<>();
	/**
	 * key: 玩家id value: 玩家上下文
	 */
	private Map<Long, PlayerContext> playerMap = new ConcurrentHashMap<>();
	
	/**
	 * 添加一个新的玩家上下文对象
	 * 
	 * @param context
	 * @return void
	 * @date 2020年9月8日下午6:42:11
	 */
	public void addContext(PlayerContext context) {
		this.playerMap.put(context.getPlayerId(), context);
	}

	/**
	 * 通过玩家账号获取一个玩家对象
	 * 
	 * @param username
	 * @return
	 * @return PlayerContext
	 * @date 2020年9月7日下午5:37:16
	 */
	public PlayerContext getOrCreatePlayer(String username, int initServerId) {
		String key = getCacheKey(username, initServerId);
		PlayerContext context = accountMap.get(key);
		if (context == null) {
			context = new PlayerContext();
			accountMap.put(key, context);
		}
		return context;
	}

	private String getCacheKey(String username, int initServerId) {
		String key = username.concat("-").concat(String.valueOf(initServerId));
		return key;
	}

	/**
	 * 查询一个玩家
	 * 
	 * @date 2020年7月17日
	 * @param playerId
	 * @return
	 */
	private Player loadPlayer(String username, int initServerId) {
		String[] props = new String[] { Player.PROP_ACCOUNTNAME, Player.PROP_INITSERVERID };
		Object[] objs = new Object[] { username, initServerId };
		List<Player> players = process.selectByIndex(Player.class, props, objs);
		return players.isEmpty() ? null : players.get(0);
	}

	////////////////////// 以下是业务//////////////////////////

	/**
	 * 登陆
	 * @param session
	 * @param req
	 * @return
	 */
	public ErrorCode login(GameSession session, ReqLogin req, AckLoginResp ack) {
		final String username = req.getUserName();
		final int initServerId = req.getServerId();
		// Http调用, 去账号服请求验证
//		ErrorCode errorCode = checkLogin(req);
//		if (!errorCode.isSuccess()) {
//			return errorCode;
//		}
		PlayerContext context = getOrCreatePlayer(username, initServerId);
		//	是否加载过角色,若不为true表示未加载过角色, 加载新角色
		if (!context.isLoaded()) {
			Player player = this.loadPlayer(username, initServerId);
			if (player == null) {
				//	查询的玩家为null
				ack.setStatus(1);
				return ErrorCode.ACCOUNT_NOT_FOUNT;
			}
			context.setPlayer(player);
			//	加载其它模块数据
//			eventBus.post(PlayerLoadEvent.create(context.getPlayerId()));
//			eventBus.post(PlayerLoadEndEvent.create(context.getPlayerId()));
		}
		final long playerId = context.getPlayerId();
		//	是否登录过, 若为true表示登录过, 旧角色挤掉
		if (context.isOnline()) {
			kickPlayer(playerId);
		}
		session.setPlayerId(playerId);
		context.setSession(session);
		addContext(context);
		//	登录事件
//		eventBus.post(PlayerLoginEvent.create(playerId));
		eventBus.post(PlayerLoginEndEvent.create(context.getPlayerId()));
		return ErrorCode.SUCCESS;
	}

	/**
	 * 账号服进行玩家数据校验
	 * 
	 * @param data
	 * @return
	 */
	private ErrorCode checkLogin(PBLogin.ReqLogin data) {

		String userName = data.getUserName();
		int channel = data.getChannel();
		String sessionKey = data.getSessionKey();
		int serverId = data.getServerId();
		// 登录服务器id
		int loginServerId = data.getLoginSid();

		String url = config.getLoginUrl() + ServerConstant.authUrl;
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("userName", userName));
		params.add(new BasicNameValuePair("channel", String.valueOf(channel)));
		params.add(new BasicNameValuePair("sessionKey", sessionKey));
		params.add(new BasicNameValuePair("serverId", String.valueOf(serverId)));
		params.add(new BasicNameValuePair("loginServerId", String.valueOf(loginServerId)));

		try {
			String ret = HttpClientUtil.doGet(url, params);
			ResAuthResult result = JSON.parseObject(ret, ResAuthResult.class);
			if (result.getCode() != 0) {
				logger.info("doLogin fail, result = {}", result);
				return ErrorCode.ACCOUNT_ILLEGAL;
			}
		} catch (Exception e) {
			logger.error("doLogin error, e:", e);
			return ErrorCode.ACCOUNT_ERROR;
		}
		return ErrorCode.SUCCESS;
	}
	
	/**
	 * 踢玩家下线
	 * @param playerId
	 */
	public void kickPlayer(long playerId) {
		PlayerContext context = getPlayerContext(playerId);
		if (context == null || context.isOnline()) {
			return;
		}
		final int sessionId = context.getSession().getId();
		DisruptorStrategy.get(DisruptorStrategy.SINGLE).execute(sessionId, ()->{
			context.forceLogout();
		});
	}
	
///////////////////////////以下是接口//////////////////////////////////

	/**
	 * 通过玩家id获取一个玩家上下文
	 * 
	 * @date 2020年7月17日
	 * @param playerId
	 * @return
	 */
	@Override
	public PlayerContext getPlayerContext(Long playerId) {
		return playerMap.get(playerId);
	}

	/**
	 * 获取所有在线玩家id
	 * 
	 * @return void
	 * @date 2020年8月24日下午2:57:19
	 */
	@Override
	public Collection<Long> getPlayerIds() {
		return playerMap.keySet();
	}

	/**
	 * 获取所有在线玩家id
	 * 
	 * @return void
	 * @date 2020年8月24日下午2:57:19
	 */
	@Override
	public Collection<PlayerContext> getPlayerContexts() {
		return playerMap.values();
	}

	/**
	 * 发送消息
	 * 
	 * @return void
	 * @date 2020年8月24日下午2:57:19
	 */
	public void sendMessage(long playerId, IProtocol protocol) {
		final PlayerContext context = getPlayerContext(playerId);
		if (context != null) {
			context.send(protocol);
		}
	}

	@Override
	public void sendMessage(Collection<Long> playerIds, IProtocol protocol) {
		playerIds.forEach(playerId -> sendMessage(playerId, protocol));
	}

	/**
	 * 玩家下线, 玩家丢进线程池中,执行踢下线操作
	 */
	@Override
	public void kickPlayer(Collection<Long> playerIds) {
		playerIds.forEach((playerId) -> kickPlayer(playerId));
	}

	@Override
	public int resType() {
		return ResourceType.Property.getType();
	}

	@Override
	public boolean checkAdd(long playerId, Integer configId, Integer value) {
		return true;// 属性默认不限制
	}

	@Override
	public boolean checkEnough(long playerId, Integer configId, Integer value) {
		final PlayerContext playerContext = getPlayerContext(playerId);
		// return PropertiesType.getType(configId).check(playerContext.getPlayer(),
		// value);
		return playerContext.getPlayer().getProperties(configId) >= value;
	}

	@Override
	public void reward(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		final PlayerContext playerContext = getPlayerContext(playerId);
		final Player player = playerContext.getPlayer();
		player.addProperties(configId, value);
		// PropertiesType.getType(configId).add(player, value);
		process.update(player);
	}

	@Override
	public void cost(long playerId, Integer configId, Integer value, NatureEnum nEnum) {
		final PlayerContext playerContext = getPlayerContext(playerId);
		final Player player = playerContext.getPlayer();
		// PropertiesType.getType(configId).reduce(player, value);
		player.reduceProperties(configId, value);
		process.update(player);
	}

	@Override
	public void cost(long playerId, Long uniqueId, NatureEnum nEnum) {
		throw new UnsupportedOperationException("玩家属性不支持该操作");
	}

}
