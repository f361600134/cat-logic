package com.cat.server.game.module.activity.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleDomain;

/**
* ActivityDomain, 抽象对活动的操作
* @author Jeremy
*/
public class ActivityDomain extends AbstractModuleDomain<Integer, Activity> implements IActivityDomain{

	private static final Logger log = LoggerFactory.getLogger(ActivityDomain.class);

	@Override
	public Activity getActivity() {
		return bean;
	}

//	@Override
//	public ActivityType getType() {
//		return ActivityType.valueOf(getId());
//	}
//
//	@Override
//	public int getPlanId() {
//		return bean.getPlanId();
//	}
//
//	@Override
//	public int getStatus() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public boolean isBegin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSettle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInCycle() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void checkAndRefreshStatus(long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshConfig() {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public int getConfigId() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getConfigType() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//	@Override
//	public long getBeginTime() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public long getSettleTime() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public long getCloseTime() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public void onPrepare(long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBegin(long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSettle(long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose(long now) {
		// TODO Auto-generated method stub
		
	}
	
	////////////业务代码////////////////////
	
	
	
	
}
