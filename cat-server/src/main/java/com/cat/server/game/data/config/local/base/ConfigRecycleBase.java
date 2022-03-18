package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * zy.资源回收.xlsx<br>
 * recycle.json<br>
 * @author auto gen
 */
public class ConfigRecycleBase implements IGameConfig {

    /**
     * id<br>
     * 需要回收的唯一资源id
     */
    private int id;
    /**
     * 回收策略<br>
     * a:xx活动结束后回收掉<br>
     * d:xx天后回收<br>
     * h:xx小时后回收<br>
     * 举例:1001a, 表示为1001的活动关闭时回收此资源
     */
    private String recycleStrategy;
    /**
     * 是否主动刷新<br>
     * 0:不主动刷新,被动刷新,依赖其他模块状态或事件<br>
     * 1:主动刷新,登录时检测或客户端请求刷新.<br>
     * 当配置主动刷新时,倒计时结束,客户端主动请求刷新最新的资源回收信息
     */
    private boolean refresh;
    /**
     * 转化资源id<br>
     * 转化成的资源id<br>
     * 不填则表示无转化
     */
    private int resId;
    /**
     * 转化比例<br>
     * 百分比,结果向下取整<br>
     * 如1:1.5, 则150
     */
    private int resRate;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 回收策略*/
    public String getRecycleStrategy() {
        return this.recycleStrategy;
    }

    /** @param recycleStrategy 回收策略*/
    public void setRecycleStrategy(String recycleStrategy) {
        this.recycleStrategy = recycleStrategy;
    }

    /** @return 是否主动刷新*/
    public boolean getRefresh() {
        return this.refresh;
    }

    /** @param refresh 是否主动刷新*/
    public void setRefresh(boolean refresh) {
        this.refresh = refresh;
    }

    /** @return 转化资源id*/
    public int getResId() {
        return this.resId;
    }

    /** @param resId 转化资源id*/
    public void setResId(int resId) {
        this.resId = resId;
    }

    /** @return 转化比例*/
    public int getResRate() {
        return this.resRate;
    }

    /** @param resRate 转化比例*/
    public void setResRate(int resRate) {
        this.resRate = resRate;
    }

}
