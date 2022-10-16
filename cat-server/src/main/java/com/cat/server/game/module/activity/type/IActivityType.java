package com.cat.server.game.module.activity.type;

import java.util.Collection;
import com.cat.server.game.data.config.local.interfaces.IConfigActivitySchedule;
import com.cat.server.game.data.proto.PBActivity.PBActivityInfo;
import com.cat.server.game.helper.ModuleDefine;
import com.cat.server.game.module.activity.component.IActivityComponent;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.status.IActivityStatus;

/**
 * 活动容器接口/活动代理接口
 * @author Jeremy
 */
public interface IActivityType {
	
	/**
	 * 获取活动数据
	 * @return
	 */
	Activity getActivity();
	
	/**
	 * 定时调用
	 * @param now  
	 * @return void  
	 * @date 2022年10月16日上午9:36:25
	 */
	void tick(long now);
	
	/**
	 * 检测活动是否可以开启
	 * @return  
	 * @return booolean  true:可以正常开启
	 * @date 2022年10月15日下午6:14:43
	 */
	boolean checkBegin(IConfigActivitySchedule activityConfig);
	
    /**
     * 活动是否进行中<br>
     * {@link IActivityStatus#BEGIN}阶段<br>
     * @return
     */
    boolean isBegin();

    /**
     * 活动是否在结算阶段<br>
     * {@link IActivityStatus#SETTLE}阶段
     * @return
     */
    boolean isSettle();

    /**
     * 活动是否开启中<br>
     * 含{@link IActivityStatus#BEGIN}或{@link IActivityStatus#SETTLE}阶段
     * @return
     */
    boolean isOpen();

    /**
     * 是否在一次活动的时间周期中<br>
     * 进入准备阶段至活动彻底结束 视为一次活动时间周期<br>
     * 含{@link IActivityStatus#PREPARE}或
     * {@link IActivityStatus#BEGIN}或{@link IActivityStatus#SETTLE}阶段
     * @return
     */
    boolean isInCycle();
    
    /**
     * 判断并使用活动配置<br>
     * 若使用了新配置 触发状态刷新
     * 
     * @param activityConfig
     * @param now
     */
    void checkAndUseConfig(IConfigActivitySchedule activityConfig, long now);
    
    /**
     * 判断并刷新活动状态<br>
     * @param now
     */
    void checkAndRefreshStatus(long now);

    /**
     * 刷新配置<br>
     * 检查配置是否发生了变化 修正活动时间<br>
     * 只在启动服务器 和活动相关配置发生了变化时检查<br>
     */
    void refreshConfig();
    
    
//    ActivityConfig getConfig();
//    
    /**
     * 获取当前活动配置id, 同activityId
     * @return
     */
    int getConfigId();
//
//    /**
//     * 获取当前活动配置类型
//     * @return
//     */
//    int getConfigType();
    /**
     * 当前活动开始时间
     * @return
     */
    long getPrepareTime();

    /**
     * 当前活动开始时间
     * @return
     */
    long getBeginTime();

    /**
     * 当前活动结算时间
     * @return
     */
    long getSettleTime();

    /**
     * 当前活动结束时间
     * @return
     */
    long getCloseTime();
    
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
	
	 /**
     * 活动状态<br>
     * @return
     */
    int getStatus();
    
    /**
     * 活动对象序列化协议对象
     * @return
     */
    PBActivityInfo toProto();
    
    /**
	 * 根据组件类型获取组件
	 * @date 20221016 
	 * @return 返回强转后的组件对象
	 */
	public <C extends IActivityComponent> C getComponent(Class<C> clazz);
	
	/**
	 * 注册活动组件
	 * @date 20221016
	 * @return 返回组件列表对象
	 */
	public Collection<IActivityComponent> getComponents();
	
	/**
	 * 所属模块
	 * @return  
	 * @return ModuleDefine  
	 * @date 2022年10月16日下午10:55:41
	 */
	public abstract ModuleDefine getModuleType();
	
}
