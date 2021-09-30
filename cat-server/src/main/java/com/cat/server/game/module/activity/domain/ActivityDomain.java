package com.cat.server.game.module.activity.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.common.ServerConfig;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.server.AbstractModuleDomain;
import com.cat.server.game.data.config.local.ConfigActivityScheduleTime;
import com.cat.server.game.module.activity.type.ActivityTypeEnum;
import com.cat.server.game.module.activity.type.IActivityType;

/**
* ActivityDomain, 抽象对活动的操作.
* 具体操作代理给IActivityType去操作
* @author Jeremy
*/
public class ActivityDomain extends AbstractModuleDomain<Integer, Activity> implements IActivityType{

	private static final Logger log = LoggerFactory.getLogger(ActivityDomain.class);
	
	private IActivityType activityType;
	
	/**
	 * Activity自动创建对象,所以创建后保存一下
	 */
	@Override
	public void afterCreate() {
		ServerConfig config = SpringContextHolder.getBean(ServerConfig.class);
		this.bean.setCurServerId(config.getServerId());
		this.bean.save();
	}
	
	@Override
	public void afterInit() {
		super.afterInit();
		int typeId = bean.getId();
		this.activityType = ActivityTypeEnum.valueOf(typeId).newActivityType(bean);
	}

	@Override
	public Activity getActivity() {
		return activityType.getActivity();
	}

	@Override
	public boolean isBegin() {
		return activityType.isBegin();
	}

	@Override
	public boolean isSettle() {
		return activityType.isSettle();
	}

	@Override
	public boolean isOpen() {
		return activityType.isOpen();
	}

	@Override
	public boolean isInCycle() {
		return activityType.isInCycle();
	}

	@Override
	public void checkAndRefreshStatus(long now) {
		activityType.checkAndRefreshStatus(now);
	}

	@Override
	public void refreshConfig() {
		activityType.refreshConfig();
	}

	@Override
	public void onPrepare(long now) {
		activityType.onPrepare(now);
	}

	@Override
	public void onBegin(long now) {
		activityType.onBegin(now);
	}

	@Override
	public void onSettle(long now) {
		activityType.onSettle(now);
	}

	@Override
	public void onClose( long now) {
		activityType.onClose(now);
	}

	@Override
	public void checkAndUseConfig(ConfigActivityScheduleTime activityConfig, long now) {
		activityType.checkAndUseConfig(activityConfig, now);
	}
	
	@Override
	public long getPrepareTime() {
		return activityType.getPrepareTime();
	}

	@Override
	public long getBeginTime() {
		return activityType.getBeginTime();
	}

	@Override
	public long getSettleTime() {
		return activityType.getSettleTime();
	}

	@Override
	public long getCloseTime() {
		return activityType.getCloseTime();
	}

	@Override
	public int getStatus() {
		return activityType.getStatus();
	}

	////////////业务代码////////////////////
	
}
