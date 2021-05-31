package com.cat.server.game.data.config.local;

import com.cat.server.game.module.resource.domain.RewardMap;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * test.测试.xlsx<br>
 * Test.json<br>
 * 
 * @author auto gen
 *
 */
@ConfigPath("Test.json")
public class ConfigTest implements IGameConfig {

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
    private RewardMap reward;
    
    /**
     * 奖励
     */
    private RewardMap cost;
    

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
    public RewardMap getReward() {
        return this.reward;
    }

    /**
     * set 奖励
     *
     * @param reward
     */
    public void setReward(RewardMap reward) {
        this.reward = reward;
    }

    /**
     * get 奖励
     *
     * @return
     */
    public RewardMap getCost() {
        return this.cost;
    }

    /**
     * set 奖励
     *
     * @param cost
     */
    public void setCost(RewardMap cost) {
        this.cost = cost;
    }

}
