package com.cat.server.game.data.config.local.base;

import java.util.Map;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * sd.商店总表.xlsx<br>
 * shop_control.json<br>
 * @author auto gen
 */
public class ConfigShopControlBase implements IGameConfig {

    /**
     * 唯一id
     */
    private int id;
    /**
     * 刷新策略<br>
     * 1:不可以刷新,商品列表读表<br>
     * 2:可以资源刷新,不限制资源刷新次数(-1)<br>
     * 3:可以资源刷新,限制资源刷新次数(3次)<br>
     * 4:可以资源刷新,限制资源刷新次数(3次),免费刷新次数(3次)<br>
     * 5:可以资源刷新,限制资源刷新次数(3次),免费刷新次数(3次),免费次数不足时开始倒计时<br>
     * 6:不可以资源刷新,不可以免费刷新,倒计时结束后刷新
     */
    private int refreshStrategy;
    /**
     * 刷新消耗<br>
     * 资源ID，数量
     */
    private Map<Integer, Integer> refreshCost;
    /**
     * 商品数量<br>
     * 不限制:-1
     */
    private int commoditiesNum;
    /**
     * 免费刷新次数<br>
     * 没有次数:0<br>
     * 不限制:-1
     */
    private int freeRefreshNum;
    /**
     * 免费刷新次数恢复倒计时(h)<br>
     * 无恢复:0
     */
    private int freeRefreshRecover;
    /**
     * 资源刷新次数<br>
     * 没有次数:0<br>
     * 不限制-1
     */
    private int resRefreshNum;
    /**
     * 免费刷新倒计时(h)<br>
     * 倒计时结束后刷新
     */
    private int freeRefreshCountdown;

    /** @return 唯一id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id 唯一id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 刷新策略*/
    public int getRefreshStrategy() {
        return this.refreshStrategy;
    }

    /** @param refreshStrategy 刷新策略*/
    public void setRefreshStrategy(int refreshStrategy) {
        this.refreshStrategy = refreshStrategy;
    }

    /** @return 刷新消耗*/
    public Map<Integer, Integer> getRefreshCost() {
        return this.refreshCost;
    }

    /** @param refreshCost 刷新消耗*/
    public void setRefreshCost(Map<Integer, Integer> refreshCost) {
        this.refreshCost = refreshCost;
    }

    /** @return 商品数量*/
    public int getCommoditiesNum() {
        return this.commoditiesNum;
    }

    /** @param commoditiesNum 商品数量*/
    public void setCommoditiesNum(int commoditiesNum) {
        this.commoditiesNum = commoditiesNum;
    }

    /** @return 免费刷新次数*/
    public int getFreeRefreshNum() {
        return this.freeRefreshNum;
    }

    /** @param freeRefreshNum 免费刷新次数*/
    public void setFreeRefreshNum(int freeRefreshNum) {
        this.freeRefreshNum = freeRefreshNum;
    }

    /** @return 免费刷新次数恢复倒计时(h)*/
    public int getFreeRefreshRecover() {
        return this.freeRefreshRecover;
    }

    /** @param freeRefreshRecover 免费刷新次数恢复倒计时(h)*/
    public void setFreeRefreshRecover(int freeRefreshRecover) {
        this.freeRefreshRecover = freeRefreshRecover;
    }

    /** @return 资源刷新次数*/
    public int getResRefreshNum() {
        return this.resRefreshNum;
    }

    /** @param resRefreshNum 资源刷新次数*/
    public void setResRefreshNum(int resRefreshNum) {
        this.resRefreshNum = resRefreshNum;
    }

    /** @return 免费刷新倒计时(h)*/
    public int getFreeRefreshCountdown() {
        return this.freeRefreshCountdown;
    }

    /** @param freeRefreshCountdown 免费刷新倒计时(h)*/
    public void setFreeRefreshCountdown(int freeRefreshCountdown) {
        this.freeRefreshCountdown = freeRefreshCountdown;
    }

}
