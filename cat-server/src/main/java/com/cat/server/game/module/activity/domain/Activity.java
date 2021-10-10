package com.cat.server.game.module.activity.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;

/**
 * 活动实体对象
* @author Jeremy
*/
@PO(name = "activity")
public class Activity extends ActivityPo implements IPersistence{

	public Activity() {

	}
	
}
