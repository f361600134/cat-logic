package com.cat.server.game.module.activity.status;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.domain.ActivityDomain;
import com.cat.server.game.module.activity.type.IActivityType;

/**
 * 状态管理器
 * @author Jeremy
 */
public class ActivityStatusManager implements IActivityStatus{

	private static final Logger log = LoggerFactory.getLogger(ActivityDomain.class);
	
	/**
	 * 状态数组
	 */
	private Map<Integer, IActivityStatus> statusMap = new HashMap<>();
	/**
	 * 当前状态
	 */
	private IActivityStatus curStatus;
	/**
	 * 活动对象
	 */
	private IActivityType activityType;
	
	public ActivityStatusManager(IActivityType activityType) {
		this.activityType = activityType;
		//初始化状态
		AbstractStatus closeState = new CloseStatus(activityType);
		AbstractStatus prepareStatus = new PrepareStatus(activityType);
		AbstractStatus beginStatus = new BeginStatus(activityType);
		AbstractStatus settleStatus = new SettleStatus(activityType);
		
		//设置状态首尾相连
		closeState.setNextStatus(prepareStatus);
		prepareStatus.setNextStatus(beginStatus);
		beginStatus.setNextStatus(settleStatus);
		settleStatus.setNextStatus(closeState);
		
		//缓存一份状态信息
		statusMap.put(closeState.getCode(), closeState);
		statusMap.put(prepareStatus.getCode(), prepareStatus);
		statusMap.put(beginStatus.getCode(), beginStatus);
		statusMap.put(settleStatus.getCode(), settleStatus);
		
		//初始化当前状态
		this.curStatus = statusMap.get(activityType.getActivity().getStatus());
	}

	public IActivityStatus getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(IActivityStatus curStatus) {
		this.curStatus = curStatus;
	}
	
	@Override
	public boolean checkNext(long now) {
		return curStatus.checkNext(now);
	}

	@Override
	public void handle(long now) {
		while (checkNext(now)) {
			curStatus = getNextStatus();
			curStatus.handle(now);
			log.info("Update status of activity:{}, curStatus:{}", 
					activityType.getActivity().getConfigId(), getCode());
		}
	}

	@Override
	public int getCode() {
		return curStatus.getCode();
	}

	@Override
	public IActivityStatus getNextStatus() {
		return curStatus.getNextStatus();
	}

	public Activity getActivity() {
		return activityType.getActivity();
	}
}
