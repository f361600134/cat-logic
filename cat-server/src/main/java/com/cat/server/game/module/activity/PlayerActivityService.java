package com.cat.server.game.module.activity;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.data.proto.PBActivity.ReqActivityInfo;
import com.cat.server.game.data.proto.PBActivity.ReqActivityRankReward;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.activity.component.impl.AbstractRankActivityComponent;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.proto.RespActivityInfoBuilder;
import com.cat.server.game.module.activity.proto.RespActivityInfoUpdateBuilder;
import com.cat.server.game.module.activity.proto.RespActivityRankRewardBuilder;
import com.cat.server.game.module.activity.type.IActivityType;
import com.cat.server.game.module.function.IFunctionService;
import com.cat.server.game.module.player.IPlayerService;
import com.cat.server.game.module.resource.domain.ResourceGroup;

@Service
public class PlayerActivityService {
	
	private static final Logger log = LoggerFactory.getLogger(PlayerActivityService.class);
	
	@Autowired private ActivityService activityService;
	@Autowired private IPlayerService playerService;
	@Autowired private IFunctionService functionService;
	
	/**
	 * 更新信息给指定玩家
	 */
	public void responseActivityInfo(long playerId) {
		RespActivityInfoBuilder resp = RespActivityInfoBuilder.newInstance();
		Collection<? extends IActivityType> activityTypes = activityService.getAllActivityTypes();
		try {
			for (IActivityType activityType : activityTypes) {
				resp.addActivitys(activityType.toProto());
			}
			playerService.sendMessage(playerId, resp);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("responseActivityInfo error, e:", e);
		}
	}
	
	/**
	 * 更新指定的活动信息给所有玩家
	 * @param typeId 类型id
	 */
	public void responseActivityUpdateInfo(int typeId) {
		RespActivityInfoUpdateBuilder resp = RespActivityInfoUpdateBuilder.newInstance();
		IActivityType activityType = activityService.getActivityType(typeId);
		Activity activity = activityType.getActivity();
		if (activity == null) {
			return;
		}
		resp.setActivity(activity.toProto());
		playerService.sendMessageToAll(resp);
	}
	
	/**
	 * 领取排行奖励
	 * @param playerId 玩家id
	 * @param typeId 活动类型id
	 * @return void  
	 * @date 2022年10月16日下午3:27:50
	 */
	public ErrorCode reqActivityRankReward(long playerId, ReqActivityRankReward req, RespActivityRankRewardBuilder ack) {
		final int activityTypeId = req.getActivityType();
		IActivityType activityType = activityService.getActivityType(activityTypeId);
		//没有这个活动或者活动不处于结算状态返回错误码
		if (activityType == null || !activityType.isSettle()) {
			//返回错误码
            return ErrorCode.ACTIVITY_NOT_EXIST;
        }
		AbstractRankActivityComponent component = activityType.getComponent(AbstractRankActivityComponent.class);
		if (component == null) {
			//非排行榜活动,不能领奖
			return ErrorCode.ACTIVITY_NOT_RANK_ACTIVITY;
		}
		ResultCodeData<ResourceGroup> result = component.receiveRankReward(playerId);
		if (!result.isSuccess()) {
			return result.getErrorCode();
		}
		ack.addAllRewards(result.getData().toCollProto());
		//通知玩家操作的对应活动的模块信息推送给前端
		functionService.responseModuleInfo(playerId, activityType.getModuleType().getModuleId());
		return result.getErrorCode();
	}
	
	/////////////业务逻辑//////////////////
	
	/**
	* 查询活动状态信息
	* @param playerId 玩家id
	* @param req 请求参数对象
	*/
	public void reqActivityInfo(long playerId, ReqActivityInfo req){
		this.responseActivityInfo(playerId);
	}
	

}
