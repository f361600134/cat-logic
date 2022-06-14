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
     * 初始属性
     */
    private Map<Integer, Integer> attribute;
    /**
     * 加工隐藏属性<br>
     * [1级加工隐藏,2级隐藏,3级隐藏]
     */
    private String starHiddenAttrs;
    /**
     * 打孔隐藏属性<br>
     * [1级打孔属性,2级隐藏,3级隐藏]
     */
    private String holeHiddenAttrs;
    /**
     * 隐藏技能
     */
    private int[] hiddenSkill;
    /**
     * 类别<br>
     * 对应槽位<br>
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

    /** @return 初始属性*/
    public Map<Integer, Integer> getAttribute() {
        return this.attribute;
    }

    /** @param attribute 初始属性*/
    public void setAttribute(Map<Integer, Integer> attribute) {
        this.attribute = attribute;
    }

    /** @return 加工隐藏属性*/
    public String getStarHiddenAttrs() {
        return this.starHiddenAttrs;
    }

    /** @param starHiddenAttrs 加工隐藏属性*/
    public void setStarHiddenAttrs(String starHiddenAttrs) {
        this.starHiddenAttrs = starHiddenAttrs;
    }

    /** @return 打孔隐藏属性*/
    public String getHoleHiddenAttrs() {
        return this.holeHiddenAttrs;
    }

    /** @param holeHiddenAttrs 打孔隐藏属性*/
    public void setHoleHiddenAttrs(String holeHiddenAttrs) {
        this.holeHiddenAttrs = holeHiddenAttrs;
    }

    /** @return 隐藏技能*/
    public int[] getHiddenSkill() {
        return this.hiddenSkill;
    }

    /** @param hiddenSkill 隐藏技能*/
    public void setHiddenSkill(int[] hiddenSkill) {
        this.hiddenSkill = hiddenSkill;
    }

    /** @return 类别*/
    public int getCategory() {
        return this.category;
    }

    /** @param category 类别*/
    public void setCategory(int category) {
        this.category = category;
    }

}
