package com.cat.server.game.module.activityoperation.learncommunity.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;

/**
* @author Jeremy
*/
@PO(name = "learn_community")
public class LearnCommunity extends LearnCommunityPo implements IPersistence{

	public LearnCommunity() {

	}
	
	public LearnCommunity(long playerId) {
		this.playerId = playerId;
	}

}
