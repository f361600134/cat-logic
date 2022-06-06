package com.cat.server.game.data.config.local.base;

import java.util.Map;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * zb.装备.xlsx<br>
 * equip.json<br>
 * @author auto gen
 */
public class ConfigEquipBase implements IGameConfig {

    /**
     * id<br>
     * 道具类型*100000+id自增
     */
    private int id;
    /**
     * 叠加数量<br>
     * 1不可叠加,大于1表示一个格子道具最大数量
     */
    private int stack;
    /**
     * 品质<br>
     * 0:普通<br>
     * 1:蓝<br>
     * 2:靛<br>
     * 3:粉<br>
     * 4:绿<br>
     * 5:黄<br>
     * 6:红<br>
     * 7:紫
     */
    private int quality;
    /**
     * 持有者类型<br>
     * 1:英雄<br>
     * 2:宠物
     */
    private int holderType;
    /**
     * 初始属性<br>
     * 增加的基础属性
     */
    private Map<Integer, Integer> attribute;
    /**
     * 隐藏技能<br>
     * 隐藏属性
     */
    private int[] hiddenAttrs;
    /**
     * 隐藏技能<br>
     * 隐藏技能
     */
    private int[] hiddenSkill;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 叠加数量*/
    public int getStack() {
        return this.stack;
    }

    /** @param stack 叠加数量*/
    public void setStack(int stack) {
        this.stack = stack;
    }

    /** @return 品质*/
    public int getQuality() {
        return this.quality;
    }

    /** @param quality 品质*/
    public void setQuality(int quality) {
        this.quality = quality;
    }

    /** @return 持有者类型*/
    public int getHolderType() {
        return this.holderType;
    }

    /** @param holderType 持有者类型*/
    public void setHolderType(int holderType) {
        this.holderType = holderType;
    }

    /** @return 初始属性*/
    public Map<Integer, Integer> getAttribute() {
        return this.attribute;
    }

    /** @param attribute 初始属性*/
    public void setAttribute(Map<Integer, Integer> attribute) {
        this.attribute = attribute;
    }

    /** @return 隐藏技能*/
    public int[] getHiddenAttrs() {
        return this.hiddenAttrs;
    }

    /** @param hiddenAttrs 隐藏技能*/
    public void setHiddenAttrs(int[] hiddenAttrs) {
        this.hiddenAttrs = hiddenAttrs;
    }

    /** @return 隐藏技能*/
    public int[] getHiddenSkill() {
        return this.hiddenSkill;
    }

    /** @param hiddenSkill 隐藏技能*/
    public void setHiddenSkill(int[] hiddenSkill) {
        this.hiddenSkill = hiddenSkill;
    }

}
