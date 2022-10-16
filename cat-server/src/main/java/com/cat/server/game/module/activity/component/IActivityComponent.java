package com.cat.server.game.module.activity.component;

/**
 * 活动组件, 用于组合各种活动类型玩法
 * @author Jeremy
 */
public interface IActivityComponent {
	
	/**
	 * 定时执行
	 * @param now  当前时间
	 * @return void  
	 * @date 2022年10月15日下午6:16:40
	 */
	public void tick(long now);
	
	 /**
     * 活动进行准备阶段时执行<br>
     * 此时活动状态已改为准备阶段<br>
     * 但还未发送状态更新给前端
     * 
     * @param now
     */
    public void onPrepare(long now);
	
	 /**
     * 活动开始时执行<br>
     * 此时活动状态已改为进行中<br>
     * 但还未发送状态更新给前端
     * @param now
     */
	public void onBegin(long now);
    
    /**
     * 活动结算时执行<br>
     * 此时活动状态已改为已结算<br>
     * 但还未发送状态更新给前端
     * @param now
     */
	public void onSettle(long now);
    
    /**
     * 活动结束时执行<br>
     * 此时活动状态已改为已结束<br>
     * 但还未发送状态更新给前端
     * @param now
     */
	public void onClose(long now);

//	/**
//	 * 
//	 *   
//	 * @return void  
//	 * @date 2022年10月15日下午6:13:53
//	 */
//	void initConfig();

}
