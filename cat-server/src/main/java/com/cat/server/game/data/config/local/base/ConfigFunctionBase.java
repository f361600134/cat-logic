package com.cat.server.game.data.config.local.base;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.config.annotation.ConfigPath;


/**
 * gn.功能控制.xlsx<br>
 * function.json<br>
 * @author auto gen
 */
public class ConfigFunctionBase implements IGameConfig {

    /**
     * id<br>
     * 功能唯一id<br>
     * 策划填,通知程序<br>
     * 或程序填,通知策划<br>
     * 功能id:3位<br>
     * 子功能id:5位
     */
    private int id;
    /**
     * 是否屏蔽功能<br>
     * 0:不屏蔽<br>
     * 1:屏蔽
     */
    private int shield;
    /**
     * 解锁条件<br>
     * DSL<br>
     * 支持与,或判断,最多2个条件<br>
     * 子功能解锁条件必须后于父功能<br>
     * 空条件:无解锁条件<br>
     * 1:等级解锁<br>
     * 2:关卡解锁<br>
     * 1:20|2:10010<br>
     * 1:20&2:10010
     */
    private String unlock;
    /**
     * 重置类型<br>
     * DSL(与)<br>
     * 每日重置:h_0<br>
     * 每日多时间点重置:h_0,8,16<br>
     * 每周单日期重置:dw_1(程序设置每周第一天)<br>
     * 每周指定日期重置:dw_1,3,5,7<br>
     * 每周指定日期及时间点重置:dw_1,3,5,7|h_0,8,16
     */
    private String reset;
    /**
     * 红点条件<br>
     * 引用红点条件表,用于计算红点<br>
     * 优先指向子功能,如没有子功能,指向父功能
     */
    private int[] reddots;

    /** @return id*/
    @Override
    public int getId() {
        return this.id;
    }

    /** @param id id*/
    public void setId(int id) {
        this.id = id;
    }

    /** @return 是否屏蔽功能*/
    public int getShield() {
        return this.shield;
    }

    /** @param shield 是否屏蔽功能*/
    public void setShield(int shield) {
        this.shield = shield;
    }

    /** @return 解锁条件*/
    public String getUnlock() {
        return this.unlock;
    }

    /** @param unlock 解锁条件*/
    public void setUnlock(String unlock) {
        this.unlock = unlock;
    }

    /** @return 重置类型*/
    public String getReset() {
        return this.reset;
    }

    /** @param reset 重置类型*/
    public void setReset(String reset) {
        this.reset = reset;
    }

    /** @return 红点条件*/
    public int[] getReddots() {
        return this.reddots;
    }

    /** @param reddots 红点条件*/
    public void setReddots(int[] reddots) {
        this.reddots = reddots;
    }

}
