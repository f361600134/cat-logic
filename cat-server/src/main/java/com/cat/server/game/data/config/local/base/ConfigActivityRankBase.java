package com.cat.server.game.data.config.local.base;

import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * hd.活动排行.xlsx<br>
 * activity_rank.json<br>
 * @author auto gen
 */
public class ConfigActivityRankBase implements IGameConfig {

    /**
     * id
     */
    private int id;
    /**
     * 活动类型ID
     */
    private int activityType;
    /**
     * 计划id
     */
    private int planId;
    /**
     * 最高排名
     */
    private int highRank;
    /**
     * 最低排名<br>
     * 不限制人数填-1，表示参赛但没获得其他名次的玩家
     */
    private int lowRank;
    /**
     * 奖励
     */
    private ResourceGroup reward;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 活动类型ID*/
    public int getActivityType() {
        return this.activityType;
    }

    /** @param activityType 活动类型ID*/
    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    /** @return 计划id*/
    public int getPlanId() {
        return this.planId;
    }

    /** @param planId 计划id*/
    public void setPlanId(int planId) {
        this.planId = planId;
    }

    /** @return 最高排名*/
    public int getHighRank() {
        return this.highRank;
    }

    /** @param highRank 最高排名*/
    public void setHighRank(int highRank) {
        this.highRank = highRank;
    }

    /** @return 最低排名*/
    public int getLowRank() {
        return this.lowRank;
    }

    /** @param lowRank 最低排名*/
    public void setLowRank(int lowRank) {
        this.lowRank = lowRank;
    }

    /** @return 奖励*/
    public ResourceGroup getReward() {
        return this.reward;
    }

    /** @param reward 奖励*/
    public void setReward(ResourceGroup reward) {
        this.reward = reward;
    }

}
