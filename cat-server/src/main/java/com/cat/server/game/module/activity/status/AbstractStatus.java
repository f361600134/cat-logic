package com.cat.server.game.module.activity.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.module.activity.PlayerActivityService;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.type.IActivityType;

/**
 * 活动状态抽象类, 维护活动对象代理, 以及当前活动下一个状态<br>
 * 状态类, 不允许状态类修改<br>
 * @author Jeremy
 */
public abstract class AbstractStatus implements IActivityStatus{
	
	protected Logger LOGGER = LoggerFactory.getLogger(getClass());
	/**
	 * 活动对象
	 */
	protected IActivityType activityType;

	/**
	 * 当前状态的下一个状态
	 */
	protected IActivityStatus nextStatus;
	
	public AbstractStatus(IActivityType activityType) {
		this.activityType = activityType;
	}
	
	@Override
	public IActivityStatus getNextStatus() {
		return nextStatus;
	}

	public void setNextStatus(IActivityStatus nextStatus) {
		this.nextStatus = nextStatus;
	}
	
	@Override
	public void handle(long now) {
        //最后处理相关状态下的行为
        doHandle(now);
	}
	
	public abstract void doHandle(long now);
	
}
