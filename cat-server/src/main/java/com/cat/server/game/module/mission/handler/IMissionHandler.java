package com.cat.server.game.module.mission.handler;

import java.util.Collection;
import java.util.List;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.data.proto.PBBag;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.module.mission.type.IMission;

/**
 * 	任务处理类接口
 * @author Jeremy
 *
 */
public interface IMissionHandler {
	
	/**
	 * 获取到玩家id
	 * @return
	 */
	public long getPlayerId();
	
	/**
	 * 	当处理任务
	 * @param event
	 * @param value
	 * @return
	 */
	public ErrorCode onProcess(IEvent event);
	
	/**
	 * 	当领取任务奖励
	 * @param event
	 * @param value
	 * @return
	 */
	public ErrorCode onReward(int configId, NatureEnum nenum);
	
	/**
	 * 	任务序列化
	 * @return
	 */
	public Collection<PBBag.PBMissionInfo> toProto();
	
	/**
	 * 获取更新的任务信息
	 * @return
	 */
	public List<IMission> getUpdateList();
	
}
