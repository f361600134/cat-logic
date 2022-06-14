package com.cat.server.game.data.config.local.base;

import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * zb.装备升星.xlsx<br>
 * equip_star.json<br>
 * @author auto gen
 */
public class ConfigEquipStarBase implements IGameConfig {

    /**
     * id<br>
     * 加工等级
     */
    private int id;
    /**
     * 下一星级
     */
    private int nextStar;
    /**
     * 加工消耗
     */
    private ResourceGroup consume;
    /**
     * 加工成功几率<br>
     * 万分比
     */
    private int rate;
    /**
     * 隐藏属性几率<br>
     * 万分比
     */
    private int hiddenAttrRate;
    /**
     * 失败回退星级<br>
     * 如果加工失败,回退到指定等级
     */
    private int fallbackStar;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 下一星级*/
    public int getNextStar() {
        return this.nextStar;
    }

    /** @param nextStar 下一星级*/
    public void setNextStar(int nextStar) {
        this.nextStar = nextStar;
    }

    /** @return 加工消耗*/
    public ResourceGroup getConsume() {
        return this.consume;
    }

    /** @param consume 加工消耗*/
    public void setConsume(ResourceGroup consume) {
        this.consume = consume;
    }

    /** @return 加工成功几率*/
    public int getRate() {
        return this.rate;
    }

    /** @param rate 加工成功几率*/
    public void setRate(int rate) {
        this.rate = rate;
    }

    /** @return 隐藏属性几率*/
    public int getHiddenAttrRate() {
        return this.hiddenAttrRate;
    }

    /** @param hiddenAttrRate 隐藏属性几率*/
    public void setHiddenAttrRate(int hiddenAttrRate) {
        this.hiddenAttrRate = hiddenAttrRate;
    }

    /** @return 失败回退星级*/
    public int getFallbackStar() {
        return this.fallbackStar;
    }

    /** @param fallbackStar 失败回退星级*/
    public void setFallbackStar(int fallbackStar) {
        this.fallbackStar = fallbackStar;
    }

}
