package com.cat.server.game.module.activity.status;

import java.util.HashMap;
import java.util.Map;

import com.cat.server.game.module.activity.domain.IActivityDomain;

/**
 * 状态管理器
 * @author Jeremy
 */
public class ActivityStatusManager implements IActivityStatus{

	/**
	 * 状态数组
	 */
	private Map<Integer, IActivityStatus> statusMap = new HashMap<>();
	/**
	 * 当前状态
	 */
	private IActivityStatus curStatus;
	/**
	 * 活动domain
	 */
	private IActivityDomain activityDomain;
	
	public ActivityStatusManager(IActivityDomain activityDomain) {
		this.activityDomain = activityDomain;
		//初始化状态
		IActivityStatus closeState = new CloseStatus(activityDomain);
		IActivityStatus prepareStatus = new PrepareStatus(activityDomain);
		IActivityStatus beginStatus = new BeginStatus(activityDomain);
		IActivityStatus settleStatus = new SettleStatus(activityDomain);
		statusMap.put(closeState.getCode(), closeState);
		statusMap.put(prepareStatus.getCode(), prepareStatus);
		statusMap.put(beginStatus.getCode(), beginStatus);
		statusMap.put(settleStatus.getCode(), settleStatus);
		//初始化当前状态
		this.curStatus = statusMap.get(activityDomain.getActivity().getStatus());
	}

	public Map<Integer, IActivityStatus> getStatusMap() {
		return statusMap;
	}

	public IActivityStatus getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(IActivityStatus curStatus) {
		this.curStatus = curStatus;
	}

	@Override
	public boolean handle(long now) {
		boolean bool = curStatus.handle(now);
		if (bool) {//如果当前状态执行成功,则切换状态
			curStatus = statusMap.get(activityDomain.getActivity().getStatus());
		}
		return bool;
	}

	@Override
	public int getCode() {
		return curStatus.getCode();
	}

}
