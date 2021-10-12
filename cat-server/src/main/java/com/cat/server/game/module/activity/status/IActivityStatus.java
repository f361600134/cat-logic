package com.cat.server.game.module.activity.status;

/**
 * 活动状态接口
 * @author Jeremy
 */
public interface IActivityStatus {
	 /**
     * 关闭状态<br>
     * 初始状态 活动开启前或已彻底结束<br>
     */
    int CLOSE = 0;
    /**
     * 准备阶段<br>
     * 活动开始前 依然视为活动未开始<br>
     * 部分活动可能没这阶段<br>
     * 也可能会被跳过<br>
     * 只可以从<b>CLOSE</b>状态进入
     */
    int PREPARE = 1;
    /**
     * 开始<br>
     * 活动进行中<br>
     * 只可以从<b>PREPARE</b>/<b>CLOSE</b>状态进入
     */
    int BEGIN = 2;
    /**
     * 结算<br>
     * 活动部分功能关闭<br>
     * 只可以从<b>BEGIN</b>状态进入
     */
    int SETTLE = 3;
    /**
     * 强行结束<br>
     * 活动提前结束 到达原活动时间时 切换为{@link ActivityStatus#END}状态 <br>
     * 只是给后台控制<br>
     * 只可以从<b>BEGIN</b>/<b>SETTLE</b>状态进入
     */
    int FORCE_END=5;
    /**
     * 暂停 活动状态停止改变 且视为未开启/已彻底结束 该状态不直接使用<br>
     * 只是展示用
     */
	int PAUSE = 10;
	
	/**
	 * 状态下的处理, 是否能进入下一个行为, 以及进入下一个状态
	 * @return  
	 * @return boolean  
	 * @date 2021年10月11日下午2:19:37
	 */
	public boolean handle(long now);
	
	/**
	 * 状态对象唯一的标识,状态码, 非活动当前状态
	 * @return  
	 * @return int  
	 * @date 2021年10月11日下午2:19:37
	 */
	public int getCode();

}
