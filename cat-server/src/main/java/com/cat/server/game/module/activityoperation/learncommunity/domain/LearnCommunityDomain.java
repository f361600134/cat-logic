package com.cat.server.game.module.activityoperation.learncommunity.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.event.IEvent;
import com.cat.server.core.server.AbstractModuleDomain;
import com.cat.server.game.data.config.local.ConfigMissionLearnCommunity;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.utils.TimeUtil;

/**
* LearnCommunityDomain
* @author Jeremy
*/
public class LearnCommunityDomain extends AbstractModuleDomain<Long, LearnCommunity> {

	private static final Logger log = LoggerFactory.getLogger(LearnCommunityDomain.class);
	
	public LearnCommunityDomain(){
	}
	
	////////////业务代码////////////////////
	
	/**
	 * 检测每日重置
	 * @return void  
	 * @date 2021年9月30日上午8:45:37
	 */
	public boolean checkAndReset() {
		long now = TimeUtil.now();
		if (!TimeUtil.isSameDay(now, bean.getLastResetTime())) {
			return false;
		}
		bean.setLastResetTime(now);
		bean.setTodayExp(0);
		bean.getDailyActiveMap().clear();
		bean.update();
		return true;
	}
	
	/**
	 * 清除玩家的研习社信息
	 */
	public void clear() {
		bean.setTodayExp(0);
		bean.setExp(0);
		bean.setActivityId(0);
		bean.setExclusive(false);
		bean.setLevel(0);
		
		bean.setLastResetTime(0);
		bean.getDailyActiveMap().clear();
		bean.getQuestTypeData().clear();
		bean.getRewardDataMap().clear();
		bean.update();
	}
	
	/////////接口类实现///////////


}

