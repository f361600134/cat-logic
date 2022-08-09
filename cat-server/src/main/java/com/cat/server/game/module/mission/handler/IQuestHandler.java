package com.cat.server.game.module.mission.handler;

import java.util.Map;

import com.cat.server.core.event.IEvent;
import com.cat.server.game.data.config.local.interfaces.IConfigMission;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.function.IFunctionResetService;
import com.cat.server.game.module.mission.define.MissionTypeEnum;
import com.cat.server.game.module.mission.domain.Quest;
import com.cat.server.game.module.mission.proto.RespMissionInfoBuilder;
import com.cat.server.game.module.resource.domain.ResourceGroup;

/**
 * 	任务处理类接口
 * @author Jeremy
 */
public interface IQuestHandler<T extends IConfigMission> extends IFunctionResetService{
	
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
	 * 刷新任务，如果有可以完成但是没有改变任务状态的任务，刷新
	 * @param playerId 玩家id
	 * @param quest 任务对象
	 * @return true:刷新了，false：没刷新
	 */
	boolean refreshQuest(long playerId, Quest quest);
	
	/**
	 * 检查重置任务, 用于每日重置, 每周重置
	 * @param playerId 玩家id
	 * @param now 刷新时间
	 * @param notify  是否通知
	 */
	void checkAndReset(long playerId, long now, boolean notify);
	
    /**
     * 接受任务
     * @param playerId 玩家id
     * @param configId 任务配置id
     * @param now 当前时间戳
     * @return
     */
	ResultCodeData<Quest> accept(long playerId, int configId, long now);
	
    /**
     * 提交任务<br>
     * 移除任务 领取奖励
     * @param playerId 玩家id
     * @param configId 任务id
     * @return
     */
	ResultCodeData<ResourceGroup> submit(long playerId, int configId);
	
    /**
     * 放弃任务
     * @param playerId 玩家id
     * @param configId 任务id 
     */
    void abort(long playerId, int configId);
    
    /**
     * 清除已完成任务, 清除掉已完成的, 指定的任务记录
     * @param playerId 玩家id
     * @param configId 任务id 
     */
    void clear(long playerId, int configId);
    
    /**
     * 处理事件<br>
     * 尝试刷新任务进度/状态
     * @param playerId 玩家id
     * @param quest 任务对象
     * @param event 事件
     * @return 若任务数据有变化 返回true
     */
    boolean handleEvent(long playerId, Quest quest, IEvent event);
    
    /**
     * 任务类型数据转协议对象
     * @param playerId 玩家id
     * @return RespMissionInfoBuilder  
     * @date 2022年3月27日下午11:09:04
     */
    RespMissionInfoBuilder toProto(long playerId);
}