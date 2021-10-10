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
