package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;


/**
 * zy.资源回收.xlsx<br>
 * recycle.json<br>
 * @author auto gen
 */
public class ConfigRecycleBase implements IGameConfig {

    /**
     * id<br>
     * 资源id
     */
    private int id;
    /**
     * 道具名
     */
    private String name;
    /**
     * 回收策略<br>
     * a:活动结束后回收掉<br>
     * d:xxx天后回收<br>
     * h:xxx小时后回收
     */
    private String recycleStrategy;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 道具名*/
    public String getName() {
        return this.name;
    }

    /** @param name 道具名*/
    public void setName(String name) {
        this.name = name;
    }

    /** @return 回收策略*/
    public String getRecycleStrategy() {
        return this.recycleStrategy;
    }

    /** @param recycleStrategy 回收策略*/
    public void setRecycleStrategy(String recycleStrategy) {
        this.recycleStrategy = recycleStrategy;
    }

}
