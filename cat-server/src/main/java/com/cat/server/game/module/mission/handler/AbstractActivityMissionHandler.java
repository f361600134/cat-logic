package com.cat.server.game.module.mission.handler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.event.IEvent;
import com.cat.server.game.data.config.local.ext.IConfigActivityMission;
import com.cat.server.game.module.activity.IActivityService;
import com.cat.server.game.module.activity.type.IActivityType;
import com.cat.server.game.module.mission.domain.Quest;

/**
 * 抽象活动任务处理类<br>
 * 活动任务数据挂载到活动数据上<br>
 * 专用于处理带有任务的活动系统
 * @auth Jeremy
 * @date 2022年3月26日下午6:06:56
 */
public abstract class AbstractActivityMissionHandler<T extends IConfigActivityMission, A extends IActivityType> extends AbstractMissionHandler<T>{

	@Autowired protected IActivityService activityService;
	
	protected Class<A> activityClazz;
	
	@SuppressWarnings("unchecked")
    public AbstractActivityMissionHandler() {
    	Type superClass = getClass().getGenericSuperclass();
    	this.missionConfigClazz = (Class<T>) (((ParameterizedType) superClass).getActualTypeArguments()[0]);
		this.activityClazz = (Class<A>) (((ParameterizedType) superClass).getActualTypeArguments()[1]);
    }
	
	/**
	 * 重写任务监听处理,活动任务只有再活动开始阶段,才可以处理,其他阶段均不能处理,如有特殊需求,则重写改方法
	 * @param playerId 玩家id
	 * @param quest 任务对象
	 * @param event 事件
	 */
	@Override
	public boolean handleEvent(long playerId, Quest quest, IEvent event) {
		A activityType = this.getActivityType();
		if (!activityType.isBegin()) {
			return false;
		}
		return super.handleEvent(playerId, quest, event);
	}
	
	/**
     * 获取活动
     * @return  
     * @return IActivityType  
     * @date 2022年3月13日上午11:27:41
     */
    public A getActivityType() {
    	return activityService.getActivityType(activityClazz);
    }
    
    @Override
    public Map<Integer, T> getConfigs() {
    	A activity = this.getActivityType();
    	if (activity == null) {
			return Collections.emptyMap();
		}
    	return ConfigManager.getInstance().getAllConfigs(missionConfigClazz)
    	 .values().stream().filter(c->c.getActivityId() == activity.getConfigId())
    	 .collect(Collectors.toMap(IConfigActivityMission::getId, Function.identity()));
    }
}
