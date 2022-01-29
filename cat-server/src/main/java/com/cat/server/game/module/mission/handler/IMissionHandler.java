package com.cat.server.game.module.mission.handler;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.data.proto.PBMission;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mission.type.IMission;

import java.util.Collection;
import java.util.List;

/**
 * 	任务处理类接口
 * @author Jeremy
 *
 */
public interface IMissionHandler {
	
	/**
	 * 获取到玩家id
	 * @return 玩家id
	 */
	public long getPlayerId();
	
	/**
	 * 	当处理任务
	 * @param event 事件id
	 * @return 错误码
	 */
	public ErrorCode onProcess(IEvent event);
	
	/**
	 * 	当领取任务奖励
	 * @param configId 任务id
	 * @param nenum 消耗原由
	 * @return 错误码
	 */
	public ErrorCode onReward(int configId, NatureEnum nenum);
	
	/**
	 * 	任务序列化
	 * @return 任务消息对象列表
	 */
	public Collection<PBMission.PBMissionInfo> toProto();
	
	/**
	 * 获取更新的任务信息
	 * @return 任务对象列表
	 */
	public List<IMission> getUpdateList();
	
}
