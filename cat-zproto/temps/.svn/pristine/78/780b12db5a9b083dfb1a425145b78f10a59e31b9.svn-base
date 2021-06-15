package com.cat.server.game.module.player.service;

import java.util.List;
import java.util.Map;
import java.util.Collection;

import com.cat.server.game.helper.ResourceType;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.module.player.service.IPlayerService;
import com.cat.server.game.module.player.domain.Player;
import com.cat.server.game.module.player.domain.PlayerDomain;
import com.cat.server.game.module.player.manager.PlayerManager;
import com.cat.server.game.module.player.service.IPlayerService;
import com.cat.server.game.module.resource.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cat.server.game.helper.result.ErrorCode;


/**
 * Player控制器
 * @author Jeremy
 */
@Service
public class PlayerService implements IPlayerService {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerService.class);
	
	@Autowired private IPlayerService playerService;
	
	@Autowired private PlayerManager playerManager;
	
	
	/////////////业务逻辑//////////////////
	
	/**
	* 请求创建角色
	* @param long playerId
	* @param ReqPlayerCreateRole req
	* @param AckPlayerCreateRoleResp ack
	*/
	public ErrorCode reqPlayerCreateRole(long playerId, ReqPlayerCreateRole req, AckPlayerCreateRoleResp ack){
		try {
			PlayerDomain domain = getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.responsePlayerInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("reqPlayerCreateRole error, playerId:{}", playerId);
			log.error("reqPlayerCreateRole error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求断线重连
	* @param long playerId
	* @param ReqPlayerReLogin req
	* @param AckPlayerReLoginResp ack
	*/
	public ErrorCode reqPlayerReLogin(long playerId, ReqPlayerReLogin req, AckPlayerReLoginResp ack){
		try {
			PlayerDomain domain = getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.responsePlayerInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("reqPlayerReLogin error, playerId:{}", playerId);
			log.error("reqPlayerReLogin error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求获取随机名
	* @param long playerId
	* @param ReqPlayerRandName req
	* @param AckPlayerRandNameResp ack
	*/
	public ErrorCode reqPlayerRandName(long playerId, ReqPlayerRandName req, AckPlayerRandNameResp ack){
		try {
			PlayerDomain domain = getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.responsePlayerInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("reqPlayerRandName error, playerId:{}", playerId);
			log.error("reqPlayerRandName error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求连接游戏服
	* @param long playerId
	* @param ReqPlayerLogin req
	* @param AckPlayerLoginResp ack
	*/
	public ErrorCode reqPlayerLogin(long playerId, ReqPlayerLogin req, AckPlayerLoginResp ack){
		try {
			PlayerDomain domain = getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.responsePlayerInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("reqPlayerLogin error, playerId:{}", playerId);
			log.error("reqPlayerLogin error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	/**
	* 请求心跳
	* @param long playerId
	* @param ReqPlayerHeart req
	* @param AckPlayerHeartResp ack
	*/
	public ErrorCode reqPlayerHeart(long playerId, ReqPlayerHeart req, AckPlayerHeartResp ack){
		try {
			PlayerDomain domain = getDomain(playerId);
			if (domain == null) {
				return ErrorCode.DOMAIN_IS_NULL;
			}
			//TODO Somthing.
			this.responsePlayerInfo(domain);
			return ErrorCode.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("reqPlayerHeart error, playerId:{}", playerId);
			log.error("reqPlayerHeart error, e:", e);
			return ErrorCode.UNKNOWN_ERROR;
		}
	}
	
	/////////////接口方法////////////////////////
	
}
 
 
