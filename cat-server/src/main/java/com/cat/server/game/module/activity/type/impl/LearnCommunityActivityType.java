package com.cat.server.game.module.activity.type.impl;

import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.type.AbstractActivityType;

/**
 * 仅用于测试
 * @author Jeremy
 */
public class LearnCommunityActivityType extends AbstractActivityType{

	public LearnCommunityActivityType(Activity activity) {
		super(activity);
	}

	@Override
	public void onPrepare(long now) {
		// TODO Auto-generated method stub
		log.info("=====研习社活动进入[准备]状态=======");
	}

	@Override
	public void onBegin(long now) {
		// TODO Auto-generated method stub
		log.info("=====研习社活动进入[开始]状态=======");
	}

	@Override
	public void onSettle(long now) {
		// TODO Auto-generated method stub
		log.info("=====研习社活动进入[结算]状态=======");
	}

	@Override
	public void onClose(long now) {
		// TODO Auto-generated method stub
		//发送活动结束的事件???给其他模块去处理???
		log.info("=====研习社活动进入[关闭]状态=======");
	}

	
}
