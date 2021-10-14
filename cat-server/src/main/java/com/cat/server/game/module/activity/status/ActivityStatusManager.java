package com.cat.server.game.module.activity.status;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import com.cat.server.game.module.activity.type.IActivityType;

/**
 * 状态管理器
 * @author Jeremy
 */
public class ActivityStatusManager implements IActivityStatus{

	/**
	 * 状态数组
	 */
	private TreeSet<IActivityStatus> statusSet = new TreeSet<>();
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
		IActivityStatus closeState = new CloseStatus(activityType);
		IActivityStatus prepareStatus = new PrepareStatus(activityType);
		IActivityStatus beginStatus = new BeginStatus(activityType);
		IActivityStatus settleStatus = new SettleStatus(activityType);
		statusMap.put(closeState.getCode(), closeState);
		statusMap.put(prepareStatus.getCode(), prepareStatus);
		statusMap.put(beginStatus.getCode(), beginStatus);
		statusMap.put(settleStatus.getCode(), settleStatus);
		//初始化当前状态
		this.curStatus = statusMap.get(activityType.getActivity().getStatus());
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
			curStatus = statusMap.get(activityType.getActivity().getStatus());
		}
		return bool;
	}

	@Override
	public int getCode() {
		return curStatus.getCode();
	}

}
