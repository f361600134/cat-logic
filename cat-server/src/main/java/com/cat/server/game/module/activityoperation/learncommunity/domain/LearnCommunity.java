package com.cat.server.game.module.activityoperation.learncommunity.domain;

import java.util.HashMap;
import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.module.activity.type.IActivityPlayerData;
import com.cat.server.game.module.mission.domain.QuestTypeData;

/**
* @author Jeremy
*/
@PO(name = "learn_community")
public class LearnCommunity extends LearnCommunityPo implements IPersistence, IActivityPlayerData{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 任务数据
	 */
	@Column(PROP_QUESTTYPEDATASTR)
	private QuestTypeData questData = new QuestTypeData();
	
	/**
     * 活跃记录, 记录已领取的日活跃奖励
     */
	@Column(PROP_DAILYACTIVEMAPSTR)
    private Map<Integer, LearnCommunityActiveData> dailyActiveMap = new HashMap<>();
	
    /**
     * 奖励记录, 仅记录已领奖的配置<br>
     * 等级奖励
     */
	@Column(PROP_DAILYACTIVEMAPSTR)
    private Map<Integer, LearnCommunityRewardData> rewardDataMap = new HashMap<>();
	

	public LearnCommunity() {
	}
	
	public LearnCommunity(long playerId) {
		this.playerId = playerId;
	}

	public QuestTypeData getQuestTypeData() {
		return questData;
	}

	public Map<Integer, LearnCommunityActiveData> getDailyActiveMap() {
		return dailyActiveMap;
	}

	public Map<Integer, LearnCommunityRewardData> getRewardDataMap() {
		return rewardDataMap;
	}
	
}
