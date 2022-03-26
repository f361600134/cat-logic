package com.cat.server.game.data.config.local.base;

import java.util.Map;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * rw.任务-研习社.xlsx<br>
 * mission_learn_community.json<br>
 * @author auto gen
 */
public class ConfigMissionLearnCommunityBase implements IGameConfig {

    /**
     * 唯一id
     */
    private int id;
    /**
     * 任务类型<br>
     * 支持多个任务条件组合
     */
    private int[] completeType;
    /**
     * 任务条件<br>
     * 多条件
     */
    private int[] completeCondition;
    /**
     * 任务完成值<br>
     * 多完成值
     */
    private int[] completeValue;
    /**
     * 完成奖励<br>
     * [[id,num]]<br>
     * id_num,id_num<br>
     * 资源ID，数量
     */
    private Map<Integer, Integer> reward;
    /**
     * 快速完成消耗<br>
     * [[id,num]]<br>
     * id_num,id_num<br>
     * 资源ID，数量
     */
    private Map<Integer, Integer> quickCompleteCost;
    /**
     * 自动提交<br>
     * 0:否<br>
     * 1:是
     */
    private int autoSubmit;
    /**
     * 重置类型<br>
     * 0:不重置<br>
     * 1:每日重置<br>
     * 2:每周重置
     */
    private int resetType;

    /** @return 唯一id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id 唯一id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 任务类型*/
    public int[] getCompleteType() {
        return this.completeType;
    }

    /** @param completeType 任务类型*/
    public void setCompleteType(int[] completeType) {
        this.completeType = completeType;
    }

    /** @return 任务条件*/
    public int[] getCompleteCondition() {
        return this.completeCondition;
    }

    /** @param completeCondition 任务条件*/
    public void setCompleteCondition(int[] completeCondition) {
        this.completeCondition = completeCondition;
    }

    /** @return 任务完成值*/
    public int[] getCompleteValue() {
        return this.completeValue;
    }

    /** @param completeValue 任务完成值*/
    public void setCompleteValue(int[] completeValue) {
        this.completeValue = completeValue;
    }

    /** @return 完成奖励*/
    public Map<Integer, Integer> getReward() {
        return this.reward;
    }

    /** @param reward 完成奖励*/
    public void setReward(Map<Integer, Integer> reward) {
        this.reward = reward;
    }

    /** @return 快速完成消耗*/
    public Map<Integer, Integer> getQuickCompleteCost() {
        return this.quickCompleteCost;
    }

    /** @param quickCompleteCost 快速完成消耗*/
    public void setQuickCompleteCost(Map<Integer, Integer> quickCompleteCost) {
        this.quickCompleteCost = quickCompleteCost;
    }

    /** @return 自动提交*/
    public int getAutoSubmit() {
        return this.autoSubmit;
    }

    /** @param autoSubmit 自动提交*/
    public void setAutoSubmit(int autoSubmit) {
        this.autoSubmit = autoSubmit;
    }

    /** @return 重置类型*/
    public int getResetType() {
        return this.resetType;
    }

    /** @param resetType 重置类型*/
    public void setResetType(int resetType) {
        this.resetType = resetType;
    }

}
