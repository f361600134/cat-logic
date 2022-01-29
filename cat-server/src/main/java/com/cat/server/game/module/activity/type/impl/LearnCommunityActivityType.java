package com.cat.server.game.module.activity.type.impl;

import java.util.Collection;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.type.AbstractActivityType;
import com.cat.server.game.module.activityoperation.learncommunity.ILearnCommunityService;
import com.cat.server.game.module.activityoperation.learncommunity.domain.LearnCommunity;

/**
 * 研习社活动代理类
 * @author Jeremy
 */
public class LearnCommunityActivityType extends AbstractActivityType{
	
	public LearnCommunityActivityType(Activity activity) {
		super(activity);
	}

	@Override
	public void onPrepare(long now) {
		// TODO Auto-generated method stub
		super.onPrepare(now);
		log.info("=====研习社活动进入[准备]状态=======");
	}

	@Override
	public void onBegin(long now) {
		// TODO Auto-generated method stub
		super.onBegin(now);
		log.info("=====研习社活动进入[开始]状态=======");
	}

	@Override
	public void onSettle(long now) {
		// TODO Auto-generated method stub
		super.onSettle(now);
		log.info("=====研习社活动进入[结算]状态=======");
	}

	@Override
	public void onClose(long now) {
		// TODO Auto-generated method stub
		//发送活动结束的事件???给其他模块去处理???
		super.onClose(now);
		log.info("=====研习社活动进入[关闭]状态=======");
		//TODO 不发送邮件, 直接在当前模块获取所有在线玩家, 处理???
		
	}

//	@Override
//	public LearnCommunityData onInitData(Activity activity) {
//		LearnCommunityData data = null;
//		byte[] bytes = activity.getCustomData();
//		if (bytes == null) {
//			data = JSON.parseObject(activity.getCustomData(), LearnCommunityData.class);
//		}
//		if(data == null) {
//			data = new LearnCommunityData();
//		}
//		return data;
//	}
	
//	/**
//	 * 获取玩家数据
//	 */
//	public LearnCommunityDomain getPlayerData(long playerId) {
//		LearnCommunityDomain domain = this.manager.getDomain(playerId);
//		return domain;
//	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public <T extends IModuleDomain<?, ?>> T getModuleDomain(long playerId) {
//		LearnCommunityDomain domain = this.manager.getDomain(playerId);
//		return (T) domain;
//	}
	
	public void recycleBag() {
		ILearnCommunityService service = SpringContextHolder.getBean(ILearnCommunityService.class);
		Collection<LearnCommunity> cols = service.geAllPlayerData();
		for (LearnCommunity learnCommunity : cols) {
			//TODO
			
		}
		
	}

}
