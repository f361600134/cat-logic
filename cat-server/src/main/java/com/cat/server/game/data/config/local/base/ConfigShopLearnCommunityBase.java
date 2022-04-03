package com.cat.server.game.data.config.local.base;

import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * sd.商店-研习社.xlsx<br>
 * shop_learn_community.json<br>
 * @author auto gen
 */
public class ConfigShopLearnCommunityBase implements IGameConfig {

    /**
     * 唯一id
     */
    private int id;
    /**
     * 对应商店
     */
    private int shopId;
    /**
     * 对应活动Id
     */
    private int activityId;
    /**
     * 购买所得<br>
     * id_num<br>
     * 资源ID，数量
     */
    private ResourceGroup items;
    /**
     * 现价<br>
     * [id,num]<br>
     * 资源ID，数量
     */
    private ResourceGroup price;
    /**
     * 限购数<br>
     * 不限购为-1
     */
    private int limit;
    /**
     * 重置类型<br>
     * 0.不重置<br>
     * 1.每天重置
     */
    private int resetType;
    /**
     * 一键购买<br>
     * 0.否<br>
     * 1.是
     */
    private int quickBuy;
    /**
     * 权重
     */
    private int weight;

    /** @return 唯一id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id 唯一id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 对应商店*/
    public int getShopId() {
        return this.shopId;
    }

    /** @param shopId 对应商店*/
    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    /** @return 对应活动Id*/
    public int getActivityId() {
        return this.activityId;
    }

    /** @param activityId 对应活动Id*/
    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    /** @return 购买所得*/
    public ResourceGroup getItems() {
        return this.items;
    }

    /** @param items 购买所得*/
    public void setItems(ResourceGroup items) {
        this.items = items;
    }

    /** @return 现价*/
    public ResourceGroup getPrice() {
        return this.price;
    }

    /** @param price 现价*/
    public void setPrice(ResourceGroup price) {
        this.price = price;
    }

    /** @return 限购数*/
    public int getLimit() {
        return this.limit;
    }

    /** @param limit 限购数*/
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /** @return 重置类型*/
    public int getResetType() {
        return this.resetType;
    }

    /** @param resetType 重置类型*/
    public void setResetType(int resetType) {
        this.resetType = resetType;
    }

    /** @return 一键购买*/
    public int getQuickBuy() {
        return this.quickBuy;
    }

    /** @param quickBuy 一键购买*/
    public void setQuickBuy(int quickBuy) {
        this.quickBuy = quickBuy;
    }

    /** @return 权重*/
    public int getWeight() {
        return this.weight;
    }

    /** @param weight 权重*/
    public void setWeight(int weight) {
        this.weight = weight;
    }

}
