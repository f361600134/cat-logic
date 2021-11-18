package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;


/**
 * dj.道具.xlsx<br>
 * item.json<br>
 * @author auto gen
 */
public class ConfigItemBase implements IGameConfig {

    /**
     * id<br>
     * 道具类型*100000+id自增
     */
    private int id;
    /**
     * 叠加数量<br>
     * 1不可叠加,大于0表示一个格子道具最大数量
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
     * 道具效果<br>
     * 程序定义
     */
    private int effect;
    /**
     * 道具效果参数<br>
     * 程序定义
     */
    private String effectParam;

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

    /** @return 道具效果*/
    public int getEffect() {
        return this.effect;
    }

    /** @param effect 道具效果*/
    public void setEffect(int effect) {
        this.effect = effect;
    }

    /** @return 道具效果参数*/
    public String getEffectParam() {
        return this.effectParam;
    }

    /** @param effectParam 道具效果参数*/
    public void setEffectParam(String effectParam) {
        this.effectParam = effectParam;
    }

}
