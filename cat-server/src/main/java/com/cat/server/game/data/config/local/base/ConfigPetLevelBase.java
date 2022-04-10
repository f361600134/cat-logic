package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * cw.宠物等级.xlsx<br>
 * pet_level.json<br>
 * @author auto gen
 */
public class ConfigPetLevelBase implements IGameConfig {

    /**
     * id
     */
    private int id;
    /**
     * 需要经验<br>
     * 达到此经验升到下一级
     */
    private int exp;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 需要经验*/
    public int getExp() {
        return this.exp;
    }

    /** @param exp 需要经验*/
    public void setExp(int exp) {
        this.exp = exp;
    }

}
