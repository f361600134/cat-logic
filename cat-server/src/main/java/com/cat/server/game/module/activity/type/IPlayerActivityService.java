package com.cat.server.game.module.activity.type;

import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.helper.result.ResultCodeData;
import com.cat.server.game.module.activity.IActivityService;

/**
 * 玩家个人活动类service接口, 用于处理个人活动模块的数据, 封装一些公共判断
 * @author Jeremy
 * @param T 活动对象
 */
public interface IPlayerActivityService<T extends IActivityType> {
	
	/**
	 * 获取活动service
	 * @return  
	 * @return IActivityService  
	 * @date 2022年10月16日上午11:46:27
	 */
	default public IActivityService getService() {
		return SpringContextHolder.getBean(IActivityService.class);
	}
	
	/**
	 * 活动类型
	 * @return int 活动类型
	 * @date 2021年9月30日上午8:03:46
	 */
	public ActivityTypeEnum activityType();
	
//	/**
//	 * 活动类型
//	 * @return int 活动类型
//	 * @date 2021年9月30日上午8:03:46
//	 */
//	public Class<? extends IActivityType> activityClazz();
	
	/**
	 * 默认方法, 根据活动的类型, 获取活动实现类
	 * @return
	 */
	public Class<T> activityClazz();
	
	/**
	 * 
	 * @return  
	 * @return ResultCodeData<T>  
	 * @date 2022年10月16日上午11:42:20
	 */
	default public ResultCodeData<T> getUseableActivity(){
		return this.getService().getUsableActivityType(this.activityType(), this.activityClazz());
	}
	
	/**
	 * 
	 * @return  
	 * @return ResultCodeData<T>  
	 * @date 2022年10月16日上午11:42:20
	 */
	default public ResultCodeData<T> getUseableActivity(int activityTypeId){
		return this.getService().getUsableActivityType(activityTypeId, this.activityClazz());
	}
	
//	/**
//	 * 是否处于活动中
//	 * @return boolean true:是
//	 * @date 2021年9月30日上午8:05:07
//	 */
//	default public ErrorCode isInCycle() {
//		IActivityType activityType = getActivityType();
//		if (activityType == null) {
//			return ErrorCode.ACTIVITY_NOT_EXIST;
//		}
//		else if (!activityType.isInCycle()) {
//			return ErrorCode.ACTIVITY_NOT_IN_ACTIVITY_TIME;
//		}
//		return ErrorCode.SUCCESS;
//	}
	
}
