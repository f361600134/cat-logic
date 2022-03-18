package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * rw.任务类型.xlsx<br>
 * mission_type.json<br>
 * @author auto gen
 */
public class ConfigMissionTypeBase implements IGameConfig {

    /**
     * 唯一id
     */
    private int id;
    /**
     * 完成类型
     */
    private int completeType;

    /** @return 唯一id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id 唯一id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 完成类型*/
    public int getCompleteType() {
        return this.completeType;
    }

    /** @param completeType 完成类型*/
    public void setCompleteType(int completeType) {
        this.completeType = completeType;
    }

}
