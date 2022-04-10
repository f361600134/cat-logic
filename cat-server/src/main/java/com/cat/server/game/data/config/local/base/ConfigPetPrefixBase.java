package com.cat.server.game.data.config.local.base;

import java.util.Map;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * cw.宠物前缀.xlsx<br>
 * pet_prefix.json<br>
 * @author auto gen
 */
public class ConfigPetPrefixBase implements IGameConfig {

    /**
     * id
     */
    private int id;
    /**
     * 出现概率<br>
     * 万分比
     */
    private int weight;
    /**
     * 属性点<br>
     * 每升1级增加的属性点
     */
    private int attributePoint;
    /**
     * 增加的属性<br>
     * 每升1级额外加的属性,可为负
     */
    private Map<Integer, Integer> addedAttribute;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 出现概率*/
    public int getWeight() {
        return this.weight;
    }

    /** @param weight 出现概率*/
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /** @return 属性点*/
    public int getAttributePoint() {
        return this.attributePoint;
    }

    /** @param attributePoint 属性点*/
    public void setAttributePoint(int attributePoint) {
        this.attributePoint = attributePoint;
    }

    /** @return 增加的属性*/
    public Map<Integer, Integer> getAddedAttribute() {
        return this.addedAttribute;
    }

    /** @param addedAttribute 增加的属性*/
    public void setAddedAttribute(Map<Integer, Integer> addedAttribute) {
        this.addedAttribute = addedAttribute;
    }

}
