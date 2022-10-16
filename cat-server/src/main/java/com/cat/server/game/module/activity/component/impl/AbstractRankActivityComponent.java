package com.cat.server.game.module.activity.component.impl;

import java.util.Optional;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.data.config.local.ConfigActivityRank;
import com.cat.server.game.helper.log.NatureEnum;
import com.cat.server.game.helper.result.ErrorCode;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.activity.component.IActivityComponent;
import com.cat.server.game.module.activity.type.IActivityType;
import com.cat.server.game.module.rank.IRankService;
import com.cat.server.game.module.rank.domain.RankTypeEnum;
import com.cat.server.game.module.resource.IResourceGroupService;
import com.cat.server.game.module.resource.domain.ResourceGroup;

/**
 * 抽象的活动排行榜组件
 * @auth Jeremy
 * @date 2022年10月16日下午3:42:13
 */
public abstract class AbstractRankActivityComponent implements IActivityComponent{

	private IResourceGroupService resourceGroupService;
	private IRankService rankService;
	private IActivityType activityType;
	
	/**
	 * 构造抽象的活动排行组件
	 */
	public AbstractRankActivityComponent(IActivityType activityType) {
		this.resourceGroupService = SpringContextHolder.getBean(IResourceGroupService.class);
		this.rankService = SpringContextHolder.getBean(IRankService.class);
		this.activityType = activityType;
	}
	
	@Override
	public void tick(long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPrepare(long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBegin(long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSettle(long now) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose(long now) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 领取排行榜奖励
	 * @param playerId  
	 * @return void  
	 * @date 2022年10月16日下午3:38:55
	 */
	public ResultCodeData<ResourceGroup> receiveRankReward(long playerId) {
		if (!this.isRankReward(playerId)) {
			//已领奖不能重复领取
			return ResultCodeData.of(ErrorCode.ACTIVITY_REWARD_ALREAD_RECEIVE);
		}
		ResourceGroup rewardGroup = this.getRankRewards(playerId);
		if (rewardGroup.empty()) {
			return ResultCodeData.of(ErrorCode.ACTIVITY_NOT_IN_RANK);
		}
		resourceGroupService.reward(playerId, rewardGroup, NatureEnum.GM);
		this.setRankReward(true);
		//TODO 通知客户端更新当前活动的最新信息
		
		return ResultCodeData.of(rewardGroup);
	}
	
	/**
	 * 根据玩家的活动排名获取对应的排名奖励
	 * @return void  
	 * @date 2022年10月16日下午3:45:28
	 */
	public ResourceGroup getRankRewards(long playerId) {
		//1.获取排行榜排名
		int rank = rankService.getRank(this.getRankType(), playerId);
		final int activityTypeId = activityType.getConfigId();
		final int activityPlanId = activityType.getActivity().getPlanId();
		//2.根据排名筛选出对应的奖励
		Optional<ConfigActivityRank> optional = ConfigManager.getInstance().getAllConfigs(ConfigActivityRank.class)
				.values().stream()
				.filter(c->c.getActivityType()==activityTypeId && c.getPlanId() == activityPlanId)
				.filter(c->rank>=c.getHighRank() && rank<=c.getLowRank())
				.findFirst();
		if (!optional.isPresent()) {
			return ResourceGroup.emptyGroup;
		}
		ResourceGroup reward = optional.get().getReward();
		return reward;
	}
	
	/**
	 * 是否可以领取排行榜奖励
	 * @return  
	 * @return boolean  
	 * @date 2022年10月16日下午3:43:11
	 */
	public abstract boolean isRankReward(long playerId);
	
	/**
	 * 设置排行榜奖励已领取状态
	 *   
	 * @return void  
	 * @date 2022年10月16日下午3:44:36
	 */
	public abstract void setRankReward(boolean bool);
	
	/**
	 * 获取排行榜枚举
	 * @return  
	 * @return RankTypeEnum  
	 * @date 2022年10月16日下午3:49:28
	 */
	public abstract RankTypeEnum getRankType();
	
}
