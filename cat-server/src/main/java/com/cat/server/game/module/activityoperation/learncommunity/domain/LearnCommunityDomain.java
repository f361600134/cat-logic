package com.cat.server.game.module.activityoperation.learncommunity.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cat.server.core.server.AbstractModuleDomain;

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
	 *   
	 * @return void  
	 * @date 2021年9月30日上午8:45:37
	 */
	public boolean checkAndReset() {
		return false;
	}
}

