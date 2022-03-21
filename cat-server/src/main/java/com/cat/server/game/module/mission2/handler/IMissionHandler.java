package com.cat.server.game.module.mission2.handler;

import java.util.Map;

import com.cat.server.core.event.GameEventBus;
import com.cat.server.core.event.IEvent;
import com.cat.server.game.data.config.local.ext.IConfigMission;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.mission.domain.MissionTypeEnum;
import com.cat.server.game.module.mission2.type.Mission;

/**
 * 	任务处理类接口
 * @author Jeremy
 */
public interface IMissionHandler<T extends IConfigMission> {
	
	/**
	 * 获取任务类型
	 * @return MissionTypeEnum  
	 */
	MissionTypeEnum getMissionType();
	
	/**
	 * 根据Id获取配置
	 * @param configId
	 * @return T  
	 * @date 2022年3月19日上午11:42:08
	 */
	T getConfig(int configId);
	
	/**
	 * 获取配置
	 * @return Map<Integer,T>  策划配置相关的map
	 */
	Map<Integer, T> getConfigs();
	
	/**
	 * 检查刷新任务
	 * @param playerId 玩家id
	 * @param now 刷新时间
	 * @param notify  是否通知
	 */
	boolean checkAndRefresh(long playerId, long now, boolean notify);
	
	/**
	 * 刷新任务，如果有可以完成但是没有改变任务状态的任务，刷新
	 * @param playerId 玩家id
	 * @param mission 任务对象
	 * @return true:刷新了，false：没刷新
	 */
	boolean refreshMission(long playerId, Mission mission);
	
    /**
     * 接受任务
     * @param playerId 玩家id
     * @param missionId 任务id
     * @param now 当前时间戳
     * @return
     */
	ResultCodeData<Mission> accept(long playerId, int missionId, long now);
	
    /**
     * 提交任务<br>
     * 移除任务 领取奖励
     * @param player
     * @param questId
     * @return
     */
	ResultCodeData<Map<Integer, Integer>> submit(long playerId, int questId);
	
    /**
     * 放弃任务
     *
     * @param player
     * @param questId
     */
    void abort(long playerId, int questId);
    
    /**
     * 处理事件<br>
     * 尝试刷新任务进度/状态
     *
     * @param owner
     * @param quest
     * @param event
     * @return 若任务数据有变化 返回true
     */
    boolean handleEvent(long playerId, Mission mission, IEvent event);
}
