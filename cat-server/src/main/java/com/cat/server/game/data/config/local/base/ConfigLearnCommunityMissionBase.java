package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.game.module.resource.domain.ResourceMap;


/**
 * yxs.研习社任务.xlsx<br>
 * learn_community_mission.json<br>
 * 
 * @author auto gen
 *
 */
public class ConfigLearnCommunityMissionBase implements IGameConfig {

    /**
     * 任务id
     */
    private int id;
    
    /**
     * 任务名
     */
    private String name_0;
    
    /**
     * 目标类型<br>
     * 任务实际要干的内容，参考
     */
    private int goalType;
    
    /**
     * 目标条件<br>
     * 部分目标类型<br>
     * 需要填写额外参数<br>
     * 如关卡id,卡牌id等
     */
    private int goalCondition;
    
    /**
     * 目标值<br>
     * 该目标完成所需要达到的值
     */
    private int goalValue;
    
    /**
     * 任务奖励<br>
     * 占位用,战令任务不允许增加奖励,只增加战令经验.
     */
    private ResourceMap reward;
    
    /**
     * 任务类别<br>
     * 1.表示每日任务<br>
     * 2.表示每周任务
     */
    private int category;
    
    /**
     * 特殊任务<br>
     * 0普通<br>
     * 1特殊
     */
    private int special;
    
    /**
     * 增加经验
     */
    private int exp;
    

    /**
     * get 任务id
     *
     * @return
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * set 任务id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get 任务名
     *
     * @return
     */
    public String getName_0() {
        return this.name_0;
    }

    /**
     * set 任务名
     *
     * @param name_0
     */
    public void setName_0(String name_0) {
        this.name_0 = name_0;
    }

    /**
     * get 目标类型
     *
     * @return
     */
    public int getGoalType() {
        return this.goalType;
    }

    /**
     * set 目标类型
     *
     * @param goalType
     */
    public void setGoalType(int goalType) {
        this.goalType = goalType;
    }

    /**
     * get 目标条件
     *
     * @return
     */
    public int getGoalCondition() {
        return this.goalCondition;
    }

    /**
     * set 目标条件
     *
     * @param goalCondition
     */
    public void setGoalCondition(int goalCondition) {
        this.goalCondition = goalCondition;
    }

    /**
     * get 目标值
     *
     * @return
     */
    public int getGoalValue() {
        return this.goalValue;
    }

    /**
     * set 目标值
     *
     * @param goalValue
     */
    public void setGoalValue(int goalValue) {
        this.goalValue = goalValue;
    }

    /**
     * get 任务奖励
     *
     * @return
     */
    public ResourceMap getReward() {
        return this.reward;
    }

    /**
     * set 任务奖励
     *
     * @param reward
     */
    public void setReward(ResourceMap reward) {
        this.reward = reward;
    }

    /**
     * get 任务类别
     *
     * @return
     */
    public int getCategory() {
        return this.category;
    }

    /**
     * set 任务类别
     *
     * @param category
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * get 特殊任务
     *
     * @return
     */
    public int getSpecial() {
        return this.special;
    }

    /**
     * set 特殊任务
     *
     * @param special
     */
    public void setSpecial(int special) {
        this.special = special;
    }

    /**
     * get 增加经验
     *
     * @return
     */
    public int getExp() {
        return this.exp;
    }

    /**
     * set 增加经验
     *
     * @param exp
     */
    public void setExp(int exp) {
        this.exp = exp;
    }

}
