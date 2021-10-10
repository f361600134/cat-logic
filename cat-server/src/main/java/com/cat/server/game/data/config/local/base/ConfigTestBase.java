package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.game.module.resource.domain.ResourceMap;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * test.测试.xlsx<br>
 * Test.json<br>
 * 
 * @author auto gen
 *
 */
public class ConfigTestBase implements IGameConfig {

    /**
     * id
     */
    private int id;
    
    /**
     * int数组
     */
    private int[] intArr;
    
    /**
     * 奖励
     */
    private ResourceMap reward;
    
    /**
     * 奖励
     */
    private ResourceMap cost;
    
    /**
     * 时间点
     */
    private String timePoint;
    

    /**
     * get id
     *
     * @return
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * set id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get int数组
     *
     * @return
     */
    public int[] getIntArr() {
        return this.intArr;
    }

    /**
     * set int数组
     *
     * @param intArr
     */
    public void setIntArr(int[] intArr) {
        this.intArr = intArr;
    }

    /**
     * get 奖励
     *
     * @return
     */
    public ResourceMap getReward() {
        return this.reward;
    }

    /**
     * set 奖励
     *
     * @param reward
     */
    public void setReward(ResourceMap reward) {
        this.reward = reward;
    }

    /**
     * get 奖励
     *
     * @return
     */
    public ResourceMap getCost() {
        return this.cost;
    }

    /**
     * set 奖励
     *
     * @param cost
     */
    public void setCost(ResourceMap cost) {
        this.cost = cost;
    }

    /**
     * get 时间点
     *
     * @return
     */
    public String getTimePoint() {
        return this.timePoint;
    }

    /**
     * set 时间点
     *
     * @param timePoint
     */
    public void setTimePoint(String timePoint) {
        this.timePoint = timePoint;
    }

}
