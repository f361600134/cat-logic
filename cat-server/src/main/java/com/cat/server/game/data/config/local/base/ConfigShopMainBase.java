package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.game.module.resource.domain.ResourceMap;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * sd.商店-主界面商店.xlsx<br>
 * shop_main.json<br>
 * @author auto gen
 */
public class ConfigShopMainBase implements IGameConfig {

    /**
     * 唯一id
     */
    private int id;
    /**
     * 对应商店
     */
    private int shopId;
    /**
     * 购买所得<br>
     * [id,num]<br>
     * 资源ID，数量
     */
    private ResourceMap item;
    /**
     * 价格<br>
     * [id,num]<br>
     * 资源ID，数量
     */
    private ResourceMap price;
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

    /** @return 购买所得*/
    public ResourceMap getItem() {
        return this.item;
    }

    /** @param item 购买所得*/
    public void setItem(ResourceMap item) {
        this.item = item;
    }

    /** @return 价格*/
    public ResourceMap getPrice() {
        return this.price;
    }

    /** @param price 价格*/
    public void setPrice(ResourceMap price) {
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
