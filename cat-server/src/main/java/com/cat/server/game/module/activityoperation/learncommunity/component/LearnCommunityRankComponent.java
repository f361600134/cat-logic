package com.cat.server.game.module.activityoperation.learncommunity.component;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.module.activity.component.impl.AbstractRankActivityComponent;
import com.cat.server.game.module.activity.type.IActivityPlayerRankData;
import com.cat.server.game.module.activityoperation.learncommunity.ILearnCommunityService;
import com.cat.server.game.module.activityoperation.learncommunity.LearnCommunityActivityType;
import com.cat.server.game.module.activityoperation.learncommunity.domain.LearnCommunity;
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
	public IActivityPlayerRankData getPlayerRankData(long playerId) {
		ILearnCommunityService service = SpringContextHolder.getBean(ILearnCommunityService.class);
		LearnCommunity learnCommunity = service.getLearnCommunity(playerId);
		return learnCommunity;
	}
	
	@Override
	public RankTypeEnum getRankType() {
		return RankTypeEnum.LearnCommunityRank;
	}

}
