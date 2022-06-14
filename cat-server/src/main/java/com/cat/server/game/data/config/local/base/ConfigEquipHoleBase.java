package com.cat.server.game.data.config.local.base;

import com.cat.server.game.module.resource.domain.ResourceGroup;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * zb.装备打孔.xlsx<br>
 * equip_hole.json<br>
 * @author auto gen
 */
public class ConfigEquipHoleBase implements IGameConfig {

    /**
     * id<br>
     * 加工等级
     */
    private int id;
    /**
     * 装备类型<br>
     * 1:武器<br>
     * 2:衣服<br>
     * 3:帽子<br>
     * 4:项链<br>
     * 5:鞋子<br>
     * 6:面具<br>
     * 7:戒指<br>
     * 8:背包
     */
    private int category;
    /**
     * 孔数<br>
     * 不同孔数消耗道具数量不一致
     */
    private int hole;
    /**
     * 打孔消耗
     */
    private ResourceGroup consume;
    /**
     * 打孔成功几率<br>
     * 万分比
     */
    private int rate;
    /**
     * 隐藏属性几率<br>
     * 万分比
     */
    private int hiddenAttrRate;
    /**
     * 失败销毁几率<br>
     * 万分比<br>
     * rate大于等于80%时失败不被销毁
     */
    private int destoryRate;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 装备类型*/
    public int getCategory() {
        return this.category;
    }

    /** @param category 装备类型*/
    public void setCategory(int category) {
        this.category = category;
    }

    /** @return 孔数*/
    public int getHole() {
        return this.hole;
    }

    /** @param hole 孔数*/
    public void setHole(int hole) {
        this.hole = hole;
    }

    /** @return 打孔消耗*/
    public ResourceGroup getConsume() {
        return this.consume;
    }

    /** @param consume 打孔消耗*/
    public void setConsume(ResourceGroup consume) {
        this.consume = consume;
    }

    /** @return 打孔成功几率*/
    public int getRate() {
        return this.rate;
    }

    /** @param rate 打孔成功几率*/
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

    /** @return 失败销毁几率*/
    public int getDestoryRate() {
        return this.destoryRate;
    }

    /** @param destoryRate 失败销毁几率*/
    public void setDestoryRate(int destoryRate) {
        this.destoryRate = destoryRate;
    }

}
