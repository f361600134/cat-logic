package com.cat.server.game.module.activity;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.game.data.proto.PBActivity.ReqActivityInfo;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.proto.RespActivityInfoBuilder;
import com.cat.server.game.module.player.IPlayerService;

@Service
public class PlayerActivityService {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerActivityService.class);
	
	@Autowired private ActivityService activityService;
	@Autowired private IPlayerService playerService;
	
	/**
	 * 更新信息
	 */
	public void responseActivityInfo(long playerId) {
		RespActivityInfoBuilder resp = RespActivityInfoBuilder.newInstance();
		Collection<Activity> beans = activityService.getAllActivitys();
		try {
			for (Activity activity : beans) {
				resp.addActivitys(activity.toProto());
			}
			playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("responseActivityInfo error, e:", e);
		}
	}
	
	
	/////////////业务逻辑//////////////////
	
	/**
	* 查询活动状态信息
	* @param long playerId
	* @param ReqActivityInfo req
	*/
	public void reqActivityInfo(long playerId, ReqActivityInfo req){
		try {
			this.responseActivityInfo(playerId);
		} catch (Exception e) {
			e.printStackTrace();
			log.info("reqActivityInfo error, playerId:{}", playerId);
			log.error("reqActivityInfo error, e:", e);
		}
	}
	

}
