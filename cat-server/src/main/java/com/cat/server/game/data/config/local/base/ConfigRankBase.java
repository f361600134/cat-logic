package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;


/**
 * ph.排行榜.xlsx<br>
 * rank.json<br>
 * 
 * @author auto gen
 *
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
     * 是否跨服
     */
    private int cross;
    

    /**
     * get id
     *
     * @return
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * set id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * get 排序数量
     *
     * @return
     */
    public int getLimit() {
        return this.limit;
    }

    /**
     * set 排序数量
     *
     * @param limit
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * get 展示数量
     *
     * @return
     */
    public int getShowNum() {
        return this.showNum;
    }

    /**
     * set 展示数量
     *
     * @param showNum
     */
    public void setShowNum(int showNum) {
        this.showNum = showNum;
    }

    /**
     * get 是否跨服
     *
     * @return
     */
    public int getCross() {
        return this.cross;
    }

    /**
     * set 是否跨服
     *
     * @param cross
     */
    public void setCross(int cross) {
        this.cross = cross;
    }

}
