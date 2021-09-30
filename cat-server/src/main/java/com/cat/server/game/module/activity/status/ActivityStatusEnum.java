package com.cat.server.game.module.activity.status;

/**
 * 活动状态<br>
 * open=begin+settle<br>
 * cycle=prepare+begin+settle
 * @author hdh
 * @deprecated 状态码{@link IActivityStatus}代替
 */
public enum ActivityStatusEnum {
    /**
     * 关闭状态<br>
     * 初始状态 活动开启前或已彻底结束<br>
     */
    CLOSE(0),
    /**
     * 准备阶段<br>
     * 活动开始前 依然视为活动未开始<br>
     * 部分活动可能没这阶段<br>
     * 也可能会被跳过<br>
     * 只可以从{@link ActivityStatus#END}状态进入
     */
    PREPARE(1),
    /**
     * 开始<br>
     * 活动进行中<br>
     * 只可以从{@link ActivityStatus#PREPARE}/{@link ActivityStatus#END}状态进入
     */
    BEGIN(2),
    /**
     * 结算<br>
     * 活动部分功能关闭<br>
     * 只可以从{@link ActivityStatus#BEGIN}状态进入
     */
    SETTLE(3),
    /**
     * 强行结束<br>
     * 活动提前结束 到达原活动时间时 切换为{@link ActivityStatus#END}状态 <br>
     * 只是给后台控制<br>
     * 只可以从{@link ActivityStatus#BEGIN}/{@link ActivityStatus#SETTLE}状态进入
     */
    FORCE_END(5),
    /**
     * 暂停 活动状态停止改变 且视为未开启/已彻底结束 该状态不直接使用<br>
     * 只是展示用
     */
    PAUSE(10),;

    private final int value;

    private ActivityStatusEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ActivityStatusEnum valueOf(int status) {
        for (ActivityStatusEnum tmp : values()) {
            if (tmp.getValue() == status) {
                return tmp;
            }
        }
        return CLOSE;
    }

}
