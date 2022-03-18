package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * ph.排行榜.xlsx<br>
 * rank.json<br>
 * @author auto gen
 */
public class ConfigRankBase implements IGameConfig {

    /**
     * id
     */
    private int id;
    /**
     * 排序数量
     */
    private int limit;
    /**
     * 展示数量
     */
    private int showNum;
    /**
     * 是否跨服<br>
     * 0:不是<br>
     * 1:是
     */
    private int cross;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 排序数量*/
    public int getLimit() {
        return this.limit;
    }

    /** @param limit 排序数量*/
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /** @return 展示数量*/
    public int getShowNum() {
        return this.showNum;
    }

    /** @param showNum 展示数量*/
    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

    /** @return 是否跨服
0:不是
1:是*/
    public int getCross() {
        return this.cross;
    }

    /** @param cross 是否跨服
0:不是
1:是*/
    public void setCross(int cross) {
        this.cross = cross;
    }

}
