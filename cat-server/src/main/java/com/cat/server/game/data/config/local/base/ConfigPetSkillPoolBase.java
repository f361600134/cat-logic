package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * cw.宠物技能领悟概率.xlsx<br>
 * pet_skill_pool.json<br>
 * @author auto gen
 */
public class ConfigPetSkillPoolBase implements IGameConfig {

    /**
     * id
     */
    private int id;
    /**
     * id
     */
    private int skillId;
    /**
     * 类型<br>
     * 1:初级类型池(无坐,生活,战斗技能平衡)<br>
     * 2:平衡系(各种技能概率相同,不分优劣)<br>
     * 3:物理系类型池(物理系技能概率偏高,防御系,恢复系,解封系持中,法系技能偏低)<br>
     * 4:法系类型池(法系技能概率偏高,防御系,恢复系,解封系持中,物理系偏低)<br>
     * 5:防御恢复系(防御系,恢复系,解封系偏高,物理系,法系技能偏低)
     */
    private int type;
    /**
     * 类型<br>
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

    /** @return id*/
    public int getSkillId() {
        return this.skillId;
    }

    /** @param skillId id*/
    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    /** @return 类型*/
    public int getType() {
        return this.type;
    }

    /** @param type 类型*/
    public void setType(int type) {
        this.type = type;
    }

    /** @return 类型*/
    public int getWeight() {
        return this.weight;
    }

    /** @param weight 类型*/
    public void setWeight(int weight) {
        this.weight = weight;
    }

}
