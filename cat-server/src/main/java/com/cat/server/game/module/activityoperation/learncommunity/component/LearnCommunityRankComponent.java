package com.cat.server.game.module.activityoperation.learncommunity.component;

import com.cat.server.game.module.activity.component.impl.AbstractRankActivityComponent;
import com.cat.server.game.module.activityoperation.learncommunity.LearnCommunityActivityType;
import com.cat.server.game.module.rank.domain.RankTypeEnum;

/**
 * 研习社活动排行榜组件, 实际上要操作研习社玩家数据哦...这就很尴尬了
 * @auth Jeremy
 * @date 2022年10月16日下午11:04:26
 */
public class LearnCommunityRankComponent extends AbstractRankActivityComponent{

	public LearnCommunityRankComponent(LearnCommunityActivityType activityType) {
		super(activityType);
	}

	@Override
	public boolean isRankReward(long playerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRankReward(boolean bool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public RankTypeEnum getRankType() {
		// TODO Auto-generated method stub
		return null;
	}

}
