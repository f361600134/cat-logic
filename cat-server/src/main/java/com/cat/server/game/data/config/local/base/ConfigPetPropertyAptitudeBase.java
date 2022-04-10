package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * cw.宠物属性资质.xlsx<br>
 * pet_property_aptitude.json<br>
 * @author auto gen
 */
public class ConfigPetPropertyAptitudeBase implements IGameConfig {

    /**
     * id
     */
    private int id;
    /**
     * 资质类型
     */
    private int type;
    /**
     */
    private int value;
    /**
     * 权重
     */
    private int weight;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 资质类型*/
    public int getType() {
        return this.type;
    }

    /** @param type 资质类型*/
    public void setType(int type) {
        this.type = type;
    }

    /** @return */
    public int getValue() {
        return this.value;
    }

    /** @param value */
    public void setValue(int value) {
        this.value = value;
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
