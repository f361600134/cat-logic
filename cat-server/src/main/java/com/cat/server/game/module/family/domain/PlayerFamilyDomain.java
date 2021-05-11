package com.cat.server.game.module.family.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.server.AbstractModuleDomain;
import com.cat.server.utils.TimeUtil;


/**
* PlayerFamilyDomain
* @author Jeremy
*/

public class PlayerFamilyDomain extends AbstractModuleDomain<Long, PlayerFamily> {

	private static final Logger log = LoggerFactory.getLogger(PlayerFamilyDomain.class);
	
	public PlayerFamilyDomain(){
		
	}

	
	////////////业务代码////////////////////
	
	/**
	 * 是否满足时间限制
	 * @return
	 */
	public boolean isMeetTime(){
		return TimeUtil.now() >= bean.getNextJoinTime();
	}
	
	
}

