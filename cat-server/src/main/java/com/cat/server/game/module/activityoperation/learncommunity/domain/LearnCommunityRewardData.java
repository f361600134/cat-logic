package com.cat.server.game.module.activityoperation.learncommunity.domain;

/**
 * @author Jeremy
 * @date 2020-12-15 16:31:28
 */
public class LearnCommunityRewardData {
    /**
     * 配置id
     */
    private int configId;
    /**
     * 普通奖励是否已领取
     */
    private boolean simpleRewarded;
    /**
     * 专属奖励是否已领取
     */
    private boolean exclusiveRewarded;

    public LearnCommunityRewardData() {
    }

    public LearnCommunityRewardData(int configId) {
        this.configId = configId;
    }

    /** 配置id **/
    public int getConfigId() {
        return this.configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public boolean isSimpleRewarded() {
        return simpleRewarded;
    }

    public void setSimpleRewarded(boolean simpleRewarded) {
        this.simpleRewarded = simpleRewarded;
    }

    public boolean isExclusiveRewarded() {
        return exclusiveRewarded;
    }

    public void setExclusiveRewarded(boolean exclusiveRewarded) {
        this.exclusiveRewarded = exclusiveRewarded;
    }

    /**
     * 普通领奖
     */
    public void onSimpleReward() {
        this.setSimpleRewarded(true);
    }

    /**
     * 专属领奖
     */
    public void onExclusiveReward() {
        this.setExclusiveRewarded(true);
    }

}
