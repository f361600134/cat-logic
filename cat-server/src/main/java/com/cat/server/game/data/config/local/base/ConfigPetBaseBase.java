package com.cat.server.game.data.config.local.base;

import java.util.Map;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * cw.宠物基础.xlsx<br>
 * pet_base.json<br>
 * @author auto gen
 */
public class ConfigPetBaseBase implements IGameConfig {

    /**
     * id
     */
    private int id;
    /**
     * 名字
     */
    private String name;
    /**
     * 生育级别<br>
     * 每个数值表示一个可生育的等级
     */
    private int[] bear;
    /**
     * 性别<br>
     * 可以随机出来的性别<br>
     * 0:中性<br>
     * 1:雄性<br>
     * 2:雌性
     */
    private int[] gender;
    /**
     * 可以培养上限<br>
     * 可食用PP果数量
     */
    private int trainLimit;
    /**
     * 信任度上限<br>
     * 信任度降低至0时,宠物冬眠,需要唤醒
     */
    private int trustLimit;
    /**
     * 等级上限
     */
    private int levelLimit;
    /**
     * 基础属性
     */
    private Map<Integer, Integer> attribute;
    /**
     * 基础属性<br>
     * 服务器从技能池记录一个随机池id
     */
    private int[] skillPoollType;
    /**
     * 领悟技能命中概率
     */
    private int skillRateHit;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 名字*/
    public String getName() {
        return this.name;
    }

    /** @param name 名字*/
    public void setName(String name) {
        this.name = name;
    }

    /** @return 生育级别*/
    public int[] getBear() {
        return this.bear;
    }

    /** @param bear 生育级别*/
    public void setBear(int[] bear) {
        this.bear = bear;
    }

    /** @return 性别*/
    public int[] getGender() {
        return this.gender;
    }

    /** @param gender 性别*/
    public void setGender(int[] gender) {
        this.gender = gender;
    }

    /** @return 可以培养上限*/
    public int getTrainLimit() {
        return this.trainLimit;
    }

    /** @param trainLimit 可以培养上限*/
    public void setTrainLimit(int trainLimit) {
        this.trainLimit = trainLimit;
    }

    /** @return 信任度上限*/
    public int getTrustLimit() {
        return this.trustLimit;
    }

    /** @param trustLimit 信任度上限*/
    public void setTrustLimit(int trustLimit) {
        this.trustLimit = trustLimit;
    }

    /** @return 等级上限*/
    public int getLevelLimit() {
        return this.levelLimit;
    }

    /** @param levelLimit 等级上限*/
    public void setLevelLimit(int levelLimit) {
        this.levelLimit = levelLimit;
    }

    /** @return 基础属性*/
    public Map<Integer, Integer> getAttribute() {
        return this.attribute;
    }

    /** @param attribute 基础属性*/
    public void setAttribute(Map<Integer, Integer> attribute) {
        this.attribute = attribute;
    }

    /** @return 基础属性*/
    public int[] getSkillPoollType() {
        return this.skillPoollType;
    }

    /** @param skillPoollType 基础属性*/
    public void setSkillPoollType(int[] skillPoollType) {
        this.skillPoollType = skillPoollType;
    }

    /** @return 领悟技能命中概率*/
    public int getSkillRateHit() {
        return this.skillRateHit;
    }

    /** @param skillRateHit 领悟技能命中概率*/
    public void setSkillRateHit(int skillRateHit) {
        this.skillRateHit = skillRateHit;
    }

}
