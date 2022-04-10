package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * cw.宠物技能.xlsx<br>
 * pet_skill.json<br>
 * @author auto gen
 */
public class ConfigPetSkillBase implements IGameConfig {

    /**
     * id
     */
    private int id;
    /**
     * 类型<br>
     * 1:生活技能<br>
     * 2:战斗技能
     */
    private int type;
    /**
     * 初始等级
     */
    private int startLv;
    /**
     * 等级限制
     */
    private int levelLimit;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 类型*/
    public int getType() {
        return this.type;
    }

    /** @param type 类型*/
    public void setType(int type) {
        this.type = type;
    }

    /** @return 初始等级*/
    public int getStartLv() {
        return this.startLv;
    }

    /** @param startLv 初始等级*/
    public void setStartLv(int startLv) {
        this.startLv = startLv;
    }

    /** @return 等级限制*/
    public int getLevelLimit() {
        return this.levelLimit;
    }

    /** @param levelLimit 等级限制*/
    public void setLevelLimit(int levelLimit) {
        this.levelLimit = levelLimit;
    }

}
