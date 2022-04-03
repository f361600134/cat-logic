package com.cat.server.game.data.config.local.base;

import com.cat.server.game.module.resource.domain.ResourceGroup;
import java.util.Map;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * test.测试.xlsx<br>
 * Test.json<br>
 * @author auto gen
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
    private ResourceGroup reward;
    /**
     * 奖励
     */
    private ResourceGroup cost;
    /**
     * 奖励
     */
    private Map<Integer, Integer> reward2;
    /**
     * 时间点
     */
    private String timePoint;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return int数组*/
    public int[] getIntArr() {
        return this.intArr;
    }

    /** @param intArr int数组*/
    public void setIntArr(int[] intArr) {
        this.intArr = intArr;
    }

    /** @return 奖励*/
    public ResourceGroup getReward() {
        return this.reward;
    }

    /** @param reward 奖励*/
    public void setReward(ResourceGroup reward) {
        this.reward = reward;
    }

    /** @return 奖励*/
    public ResourceGroup getCost() {
        return this.cost;
    }

    /** @param cost 奖励*/
    public void setCost(ResourceGroup cost) {
        this.cost = cost;
    }

    /** @return 奖励*/
    public Map<Integer, Integer> getReward2() {
        return this.reward2;
    }

    /** @param reward2 奖励*/
    public void setReward2(Map<Integer, Integer> reward2) {
        this.reward2 = reward2;
    }

    /** @return 时间点*/
    public String getTimePoint() {
        return this.timePoint;
    }

    /** @param timePoint 时间点*/
    public void setTimePoint(String timePoint) {
        this.timePoint = timePoint;
    }

}
