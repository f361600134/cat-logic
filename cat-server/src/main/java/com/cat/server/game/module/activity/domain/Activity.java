package com.cat.server.game.module.activity.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence;
import com.cat.server.game.data.proto.PBActivity.PBActivityInfo;

/**
 * 活动实体对象
* @author Jeremy
*/
@PO(name = "activity")
public class Activity extends ActivityPo implements IPersistence{
	
//	private transient ActivityStatusManager statusManager = new ActivityStatusManager(this);

	public Activity() {

	}
	
	/**
	 * 带有类型id的构造
	 * @param id
	 */
	public Activity(Integer id) {
		this.id = id;
	}
	
//	/**
//     * 活动是否进行中<br>
//     * {@link ActivityStatus#BEGIN}阶段<br>
//     * @return
//     */
//	public boolean isBegin() {
//    	return status == BEGIN;
//    }
//
//    /**
//     * 活动是否在结算阶段<br>
//     * {@link ActivityStatus#SETTLE}阶段
//     * @return
//     */
//	public boolean isSettle(){
//    	return status == SETTLE;
//    }
//
//    /**
//     * 活动是否开启中<br>
//     * 含{@link ActivityStatus#BEGIN}或{@link ActivityStatus#SETTLE}阶段
//     * @return
//     */
//    boolean isOpen() {
//    	return status == BEGIN || status == SETTLE;
//    }
//
//    /**
//     * 是否在一次活动的时间周期中<br>
//     * 进入准备阶段至活动彻底结束 视为一次活动时间周期<br>
//     * 含{@link ActivityStatus#PREPARE}或
//     * {@link ActivityStatus#BEGIN}或{@link ActivityStatus#SETTLE}阶段
//     * @return
//     */
//    boolean isInCycle(int activityId) {
//    	return status == PREPARE || status == BEGIN || status == SETTLE;
//    }
//    
//    /**
//     * 判断并刷新活动状态<br>
//     * @param now
//     */
//    void checkAndRefreshStatus(int activityId, long now) {
//    	
//    }
//
//    /**
//     * 刷新配置<br>
//     * 检查配置是否发生了变化 修正活动时间<br>
//     * 只在启动服务器 和活动相关配置发生了变化时检查<br>
//     */
//    void refreshConfig(int activityId);
//    
//    ActivityConfig getConfig();
//    
//    /**
//     * 获取当前活动配置id
//     * @return
//     */
//    int getConfigId();
//
//    /**
//     * 获取当前活动配置类型
//     * @return
//     */
//    int getConfigType();

//    /**
//     * 当前活动开始时间
//     * @return
//     */
//    long getBeginTime();
//
//    /**
//     * 当前活动结算时间
//     * @return
//     */
//    long getSettleTime();
//
//    /**
//     * 当前活动结束时间
//     * @return
//     */
//    long getCloseTime();
    
//	 /**
//     * 活动进行准备阶段时执行<br>
//     * 此时活动状态已改为准备阶段<br>
//     * 但还未发送状态更新给前端
//     * 
//     * @param now
//     */
//    public void onPrepare(int activityId, long now);
//	
//	 /**
//     * 活动开始时执行<br>
//     * 此时活动状态已改为进行中<br>
//     * 但还未发送状态更新给前端
//     * @param now
//     */
//	public void onBegin(int activityId, long now);
//    
//    /**
//     * 活动结算时执行<br>
//     * 此时活动状态已改为已结算<br>
//     * 但还未发送状态更新给前端
//     * @param now
//     */
//	public void onSettle(int activityId, long now);
//    
//    /**
//     * 活动结束时执行<br>
//     * 此时活动状态已改为已结束<br>
//     * 但还未发送状态更新给前端
//     * @param now
//     */
//	public void onClose(int activityId, long now);
	
	/**
	 * 数据对象转协议对象
	 * @return
	 */
	public PBActivityInfo toProto() {
		PBActivityInfo.Builder builder = PBActivityInfo.newBuilder();
		builder.setType(getConfigType());
		builder.setPlanId(getPlanId());
		builder.setStatus(getStatus());
		builder.setStage(getStage());
		builder.setBeginTime((int)(getBeginTime()/1000));
		builder.setSettleTime((int)(getSettleTime()/1000));
		builder.setCloseTime((int)(getCloseTime()/1000));
		return builder.build();
	}
	
}
