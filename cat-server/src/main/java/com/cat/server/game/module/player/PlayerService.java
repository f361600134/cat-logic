package com.cat.server.game.module.player;

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
import com.cat.net.network.base.IProtocol;
import com.cat.net.network.base.ISession;
import com.cat.orm.core.db.process.IDataProcess;
import com.cat.server.common.ServerConfig;
import com.cat.server.core.event.GameEventBus;
import com.cat.server.game.data.proto.PBPlayer.ReqPlayerCreateRole;
import com.cat.server.game.data.proto.PBPlayer.ReqPlayerHeart;
import com.cat.server.game.data.proto.PBPlayer.ReqPlayerLogin;
import com.cat.server.game.data.proto.PBPlayer.ReqPlayerRandName;
import com.cat.server.game.data.proto.PBPlayer.ReqPlayerReLogin;
import com.cat.server.game.helper.AccountApiEnum;
import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.player.domain.Player;
import com.cat.server.game.module.player.domain.PlayerContext;
import com.cat.server.game.module.player.event.PlayerLoginEndEvent;
import com.cat.server.game.module.player.proto.RespPlayerCreateRoleBuilder;
import com.cat.server.game.module.player.proto.RespPlayerHeartBuilder;
import com.cat.server.game.module.player.proto.RespPlayerLoginBuilder;
import com.cat.server.game.module.player.proto.RespPlayerRandNameBuilder;
import com.cat.server.game.module.player.proto.RespPlayerReLoginBuilder;
import com.cat.server.game.module.player.resp.ResAuthResult;
import com.cat.server.game.module.resource.IResourceService;
import com.cat.server.utils.HttpClientUtil;

/**
 * 玩家service
 * @author Jeremy
 */
@Component
class PlayerService implements IPlayerService, IResourceService {

	private static final Logger logger = LoggerFactory.getLogger(PlayerService.class);

	@Autowired
	private ServerConfig config;

	@Autowired
	private IDataProcess process;
	
	@Autowired
	private GameEventBus eventBus;
	
	/**
	 * 缓存玩家session
	 */
//	private final BiMap<Long, ISession> sessionMap = Maps.synchronizedBiMap(HashBiMap.create());
	/**
	 * 玩家登录信息缓存<P>
	 * key:玩家账号+初始服务器id<P>
	 * value:玩家上下文对象<P>
	 */
	private final Map<String, PlayerContext> accountMap = new ConcurrentHashMap<>();
	/**
	 * 在线玩家缓存
	 * key: 玩家id<P>
	 * value: 玩家上下文<P>
	 */
	private final Map<Long, PlayerContext> playerMap = new ConcurrentHashMap<>();
	
	/**
	 * 添加一个新的玩家上下文对象
	 * @param context 玩家上下文对象
	 * @date 2020年9月8日下午6:42:11
	 */
	public void addContext(PlayerContext context) {
		this.playerMap.put(context.getPlayerId(), context);
	}

	/**
	 * 移除一个玩家上下文对象
	 * 玩家离线后, 移除上下文对象, 一般来说玩家断线不会立刻释放资源(有可能因为网络波动造成的断线)，立刻断线的玩家加入定时线程池
	 * 指定时间后调用此方法从缓存中移除， 如果在指定时间内上线的话，从定时线程池内移除掉此玩家
	 */
	public void removeContext(PlayerContext context){
		this.playerMap.remove(context.getPlayerId());
		String key = getCacheKey(context.getAccountName(), context.getInitServerId());
		this.accountMap.remove(key);
	}

	/**
	 * 通过玩家账号获取一个玩家对象
	 * @param accountName 玩家唯一账号, 由账号服生成
	 * @return PlayerContext 玩家上下文对象
	 * @date 2020年9月7日下午5:37:16
	 */
	public PlayerContext getOrCreatePlayer(String accountName, int initServerId) {
		String key = getCacheKey(accountName, initServerId);
		PlayerContext context = accountMap.get(key);
		if (context == null) {
			context = new PlayerContext();
			accountMap.put(key, context);
		}
		return context;
	}

	/**
	 * 通过账号, 初始服务器id生成唯一的缓存主键
	 * @param accountName 账号名
	 * @param initServerId 初始服务器id
	 * @return 缓存唯一主键
	 */
	private String getCacheKey(final String accountName, final int initServerId) {
		return accountName.concat("-").concat(String.valueOf(initServerId));
	}

	/**
	 * 根据账号, 初始服务器id 查询一个玩家
	 * @date 2020年7月17日
	 * @param accountName 账号
	 * @param initServerId 初始服务器id
	 * @return 玩家对象
	 */
	private Player loadPlayer(String accountName, int initServerId) {
		String[] props = new String[] { Player.PROP_ACCOUNTNAME, Player.PROP_INITSERVERID };
		Object[] objs = new Object[] { accountName, initServerId };
		List<Player> players = process.selectByIndex(Player.class, props, objs);
		return players.isEmpty() ? null : players.get(0);
	}

	////////////////////// 以下是业务//////////////////////////

	/**
	 * 登陆
	 * @param session 游戏session
	 * @param req 登录请求消息体
	 * @return 错误码
	 */
	public ErrorCode reqPlayerLogin(ISession session, ReqPlayerLogin req, RespPlayerLoginBuilder ack) {
		final String accountName = req.getAccountName();
		final int initServerId = req.getServerId();
		// Http调用, 去账号服请求验证
//		ErrorCode errorCode = checkLogin(req);
//		if (!errorCode.isSuccess()) {
//			return errorCode;
//		}
		PlayerContext context = getOrCreatePlayer(accountName, initServerId);
		//	是否加载过角色,若不为true表示未加载过角色, 加载新角色
		if (!context.isLoaded()) {
			Player player = this.loadPlayer(accountName, initServerId);
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
		session.setUserData(playerId);
		context.setSession(session);
		addContext(context);
		//	登录事件
//		eventBus.post(PlayerLoginEvent.create(playerId));
		eventBus.post(PlayerLoginEndEvent.create(context.getPlayerId()));
		return ErrorCode.SUCCESS;
	}

	/**
	 * 账号服进行玩家数据校验
	 * @param data 请求协议
	 * @return 错误码
	 */
	private ErrorCode checkLogin(ReqPlayerLogin data) {
		String account = data.getAccountName();
		int channel = data.getChannel();
		String sessionKey = data.getSessionKey();
		int serverId = data.getServerId();
		// 登录服务器id
		int loginServerId = data.getLoginSid();

		String url = AccountApiEnum.getApi(AccountApiEnum.LOGIN);
		List<NameValuePair> params = new ArrayList<>();
		params.add(new BasicNameValuePair("account", account));
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
	* 请求获取随机名
	* @param long playerId
	* @param ReqPlayerRandName req
	* @param Resp ack
	*/
	public ErrorCode reqPlayerRandName(long playerId, ReqPlayerRandName req, RespPlayerRandNameBuilder ack){
		try {
//			PlayerDomain domain = getDomain(playerId);
//			if (domain == null) {
//				return ErrorCode.DOMAIN_IS_NULL;
//			}
//			//TODO Somthing.
//			this.responsePlayerInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("reqPlayerRandName error, playerId:{}", playerId);
			logger.error("reqPlayerRandName error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求断线重连
	* @param long playerId
	* @param ReqPlayerReLogin req
	* @param RespPlayerReLoginBuilder ack
	*/
	public ErrorCode reqPlayerReLogin(long playerId, ReqPlayerReLogin req, RespPlayerReLoginBuilder ack){
		try {
//			PlayerDomain domain = getDomain(playerId);
//			if (domain == null) {
//				return ErrorCode.DOMAIN_IS_NULL;
//			}
//			//TODO Somthing.
//			this.responsePlayerInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("reqPlayerReLogin error, playerId:{}", playerId);
			logger.error("reqPlayerReLogin error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求心跳
	* @param long playerId
	* @param ReqPlayerHeart req
	* @param Resp ack
	*/
	public ErrorCode reqPlayerHeart(long playerId, ReqPlayerHeart req, RespPlayerHeartBuilder ack){
		try {
//			PlayerDomain domain = getDomain(playerId);
//			if (domain == null) {
//				return ErrorCode.DOMAIN_IS_NULL;
//			}
//			//TODO Somthing.
//			this.responsePlayerInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			logger.info("reqPlayerHeart error, playerId:{}", playerId);
			logger.error("reqPlayerHeart error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求创建角色
	* @param long playerId
	* @param ReqPlayerCreateRole req
	* @param Resp ack
	*/
	public ErrorCode reqPlayerCreateRole(long playerId, ReqPlayerCreateRole req, RespPlayerCreateRoleBuilder ack){
		try {
//			PlayerDomain domain = getDomain(playerId);
//			if (domain == null) {
//				return ErrorCode.DOMAIN_IS_NULL;
//			}
//			//TODO Somthing.
//			this.responsePlayerInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			logger.info("reqPlayerCreateRole error, playerId:{}", playerId);
			logger.error("reqPlayerCreateRole error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	
//	/**
//	* 请求连接游戏服
//	* @param long playerId
//	* @param ReqPlayerLogin req
//	* @param Resp ack
//	*/
//	public ErrorCode reqPlayerLogin(ISession session, ReqPlayerLogin req, AckPlayerLoginResp ack){
//		try {
////			PlayerDomain domain = getDomain(playerId);
////			if (domain == null) {
////				return ErrorCode.DOMAIN_IS_NULL;
////			}
////			//TODO Somthing.
////			this.responsePlayerInfo(domain);
//			return ErrorCode.SUCCESS;
//		} catch (Exception e) {
//			logger.info("reqPlayerLogin error, playerId:{}", playerId);
//			logger.error("reqPlayerLogin error, e:", e);
//			return ErrorCode.UNKNOWN_ERROR;
//		}
//	}
	
	/**
	 * 踢玩家下线
	 * @param playerId 玩家id
	 */
	public void kickPlayer(long playerId) {
		PlayerContext context = getPlayerContext(playerId);
		if (context == null || context.isOnline()) {
			return;
		}
		final int sessionId = context.getSession().getSessionId();
		DisruptorStrategy.get(DisruptorStrategy.SINGLE).execute(sessionId, context::forceLogout);
	}
	
///////////////////////////以下是接口//////////////////////////////////

	/**
	 * 通过玩家id获取一个玩家上下文
	 * 
	 * @date 2020年7月17日
	 * @param playerId 玩家id
	 * @return 玩家上下文
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
	public Collection<Long> getOnlinePlayerIds() {
		return playerMap.keySet();
	}
	
	@Override
	public int getSessionId(long playerId) {
		PlayerContext context = getPlayerContext(playerId);
		if (context == null) {
			return 0;
		}
		return context.getSession().getSessionId();
	}

	/**
	 * 获取所有在线玩家id
	 * @return void
	 * @date 2020年8月24日下午2:57:19
	 */
	@Override
	public Collection<PlayerContext> getPlayerContexts() {
		return playerMap.values();
	}

	/**
	 * 发送消息
	 * @date 2020年8月24日下午2:57:19
	 */
	@Override
	public void sendMessage(long playerId, IProtocol protocol) {
		final PlayerContext context = getPlayerContext(playerId);
		if (context != null) {
			context.send(protocol);
		}
	}

	/**
	 * 发送消息
	 * @param playerIds 玩家列表
	 * @param protocol 协议体
	 */
	@Override
	public void sendMessage(Collection<Long> playerIds, IProtocol protocol) {
		playerIds.forEach(playerId -> sendMessage(playerId, protocol));
	}
	
	/**
	 * 发送消息至所有玩家
	 * @param protocol 协议体
	 */
	@Override
	public void sendMessageToAll(IProtocol protocol) {
		playerMap.forEach((playerId, context)->sendMessage(playerId, protocol));
	}
	
//	@Override
//	public Long getPlayerId(ISession session) {
//		return sessionMap.inverse().get(session);
//	}
//
//	@Override
//	public PlayerContext getPlayerContext(ISession session) {
//		Long playerId = getPlayerId(session);
//		if (playerId == null) {
//			return null;
//		}
//		return getPlayerContext(playerId);
//	}

	/**
	 * 玩家下线, 玩家丢进线程池中,执行踢下线操作
	 *
	 */
	@Override
	public void kickPlayer(Collection<Long> playerIds) {
		playerIds.forEach(this::kickPlayer);
	}

	@Override
	public int resType() {
		return ResourceType.Property.getType();
	}

	@Override
	public boolean checkAdd(long playerId, Integer configId, Integer value) {
		// 属性默认不限制
		return true;
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
