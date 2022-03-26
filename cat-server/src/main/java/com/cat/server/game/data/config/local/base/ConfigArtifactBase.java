package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * sq.神器.xlsx<br>
 * artifact.json<br>
 * @author auto gen
 */
public class ConfigArtifactBase implements IGameConfig {

    /**
     * 唯一id
     */
    private int id;
    /**
     * 下一个神器
     */
    private int next;
    /**
     * 神器等级上限<br>
     * 多条件
     */
    private int maxLevel;
    /**
     * 神器技能等级上限<br>
     * 多完成值
     */
    private int maxSkillLevel;
    /**
     * 神器精炼等级上限<br>
     * [[id,num]]<br>
     * id_num,id_num<br>
     * 资源ID，数量
     */
    private int maxRefineLevel;

    /** @return 唯一id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id 唯一id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 下一个神器*/
    public int getNext() {
        return this.next;
    }

    /** @param next 下一个神器*/
    public void setNext(int next) {
        this.next = next;
    }

    /** @return 神器等级上限*/
    public int getMaxLevel() {
        return this.maxLevel;
    }

    /** @param maxLevel 神器等级上限*/
    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    /** @return 神器技能等级上限*/
    public int getMaxSkillLevel() {
        return this.maxSkillLevel;
    }

    /** @param maxSkillLevel 神器技能等级上限*/
    public void setMaxSkillLevel(int maxSkillLevel) {
        this.maxSkillLevel = maxSkillLevel;
    }

    /** @return 神器精炼等级上限*/
    public int getMaxRefineLevel() {
        return this.maxRefineLevel;
    }

    /** @param maxRefineLevel 神器精炼等级上限*/
    public void setMaxRefineLevel(int maxRefineLevel) {
        this.maxRefineLevel = maxRefineLevel;
    }

}
