package com.cat.server.game.module.activity.type;

import com.cat.server.core.annotation.NotUse;
import com.cat.server.game.module.activity.domain.Activity;

@NotUse
@Deprecated
public abstract class AbstractActivityTypePlus<T extends IActivityData<? extends IActivityPlayerData>> extends AbstractActivityType{

	private T activityData;
	
	public AbstractActivityTypePlus(Activity activity) {
		super(activity);
	}

}
