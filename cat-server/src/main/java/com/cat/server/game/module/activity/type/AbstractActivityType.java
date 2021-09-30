package com.cat.server.game.module.activity.type;

import static com.cat.server.game.module.activity.status.IActivityStatus.*;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.game.data.config.local.ConfigActivityScheduleTime;
import com.cat.server.game.module.activity.PlayerActivityController;
import com.cat.server.game.module.activity.define.ActivityConstant;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.status.ActivityStatusManager;
import com.cat.server.game.module.activity.time.point.ITimePoint;
import com.cat.server.utils.TimeUtil;

/**
 * 活动抽象类类型, 不同活动不同阶段的操作不同, 所以
 * @author Jeremy
 */
public abstract class AbstractActivityType implements IActivityType{
	
	protected final Logger log = LoggerFactory.getLogger(AbstractActivityType.class);
	
	protected final Activity activity;
	
	private final ActivityStatusManager statusManager;

	public AbstractActivityType(Activity activity) {
		this.activity = activity;
		this.statusManager =  new ActivityStatusManager(this);
	}
	
	@Override
	public Activity getActivity() {
		return activity;
	}

	@Override
	public boolean isBegin() {
        return getActivity().getStatus() == BEGIN;
	}

	@Override
	public boolean isSettle() {
		return getActivity().getStatus() == SETTLE;
	}

	@Override
	public boolean isOpen() {
		int status = getActivity().getStatus();
		return status == BEGIN || status == SETTLE;
	}

	@Override
	public boolean isInCycle() {
		int status = getActivity().getStatus();
		return status == PREPARE || status == BEGIN || status == SETTLE;
	}

	@Override
	public void checkAndRefreshStatus(long now) {
		statusManager.handle(now);
	}
	
	@Override
    public void checkAndUseConfig(ConfigActivityScheduleTime activityConfig, long now) {
        if (!checkUseConfig(activityConfig, now)) {
            return;
        }
        useConfig(activityConfig, now);
        checkAndRefreshStatus(now);
    }

	@Override
	public void refreshConfig() {
		int configId = getActivity().getConfigId();
		if (configId <= 0) {
			return;
		}
		//TODO 刷新配置,数值覆盖到activity
		ConfigManager configManager = SpringContextHolder.getBean(ConfigManager.class);
		ConfigActivityScheduleTime config = configManager.getConfig(ConfigActivityScheduleTime.class, configId);
		useConfig(config, configId);
	}
	
	/**
     * 判断是否使用该配置
     * 
     * @param activityConfig
     * @param now
     * @return
     */
    protected boolean checkUseConfig(ConfigActivityScheduleTime activityConfig, long now) {
//        if (isPause()) {
//            // 暂停状态 状态不会发生变化
//            return false;
//        }
//        if (!isFunctionOpen()) {
//            // 功能被关闭 视为暂停
//            return false;
//        }
        if (isUsingAnyConfig()) {
            // 已经选用了其中一个配置
            return false;
        }
        if (getStatus() != CLOSE) {
            // 只在活动结束状态才尝试使用新的活动配置
            return false;
        }
        ITimePoint startTime = activityConfig.getStartTimePoint();
        long startTimestamp = startTime.getLastTime(now);
        long settleTime = startTimestamp;
        int beginDuration = activityConfig.getBeginDuration();
        if (beginDuration > 0) {
            // 正常配置 在准备阶段或开始阶段期间
            int prepareDuration = activityConfig.getPrepareDuration();
            settleTime = startTimestamp + TimeUnit.SECONDS.toMillis(prepareDuration + beginDuration);
        } else if (beginDuration == ActivityConstant.ACTIVITY_DURATION_INTERVAL_1_MINUTE) {
            // 特殊配置
            int settleDuration = activityConfig.getSettleDuration();
            settleTime = startTime.getNextTime(now) - TimeUtil.MINIUTE_MILLISECONDS - TimeUnit.SECONDS.toMillis(settleDuration);
        }
        if (now >= startTimestamp && now < settleTime) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否正在使用着任意一个配置
     * 
     * @return
     */
    protected boolean isUsingAnyConfig() {
        return activity.getConfigType() != ActivityConstant.CONFIG_TYPE_NONE && activity.getConfigId() > 0;
    }
	
	/**
     * 根据配置 刷新持久化数据<br>
     * 配置中的时间 方案等信息 存库<br>
     * 避免后台直接改配置或者删配置导致活动直接结束<br>
     * 启动时/配置更新时会执行该方法
     * @param config
     * @param now
     */
    protected void useConfig(ConfigActivityScheduleTime config, long now) {
        this.activity.setConfigId(config.getId());
        int planId = config.getPlanId();
        if (planId >= 0) {
            // 活动方案>=0 为配置直接指定使用某个方案
            // 若为-1 则为自定义使用某个活动方案 需要在prepare或begin时自行设置
            this.activity.setPlanId(planId);
        }
        // 活动相关时间
        ITimePoint startTime = config.getStartTimePoint();
        int prepareDuration = config.getPrepareDuration();
        int beginDuration = config.getBeginDuration();
        int settleDuration = config.getSettleDuration();
        long startTimestamp = startTime.getLastTime(now);
        long beginTime = startTimestamp + TimeUnit.SECONDS.toMillis(prepareDuration);
        long settleTime = beginTime + TimeUnit.SECONDS.toMillis(beginDuration);
        long closeTime = settleTime + TimeUnit.SECONDS.toMillis(settleDuration);
        if (beginDuration == ActivityConstant.ACTIVITY_DURATION_INTERVAL_1_MINUTE) {
        	closeTime = startTime.getNextTime(now) - TimeUtil.MINIUTE_MILLISECONDS;
            settleTime = closeTime - TimeUnit.SECONDS.toMillis(settleDuration);
        }
        this.activity.setPrepareTime(startTimestamp);
        this.activity.setBeginTime(beginTime);
        this.activity.setSettleTime(settleTime);
        this.activity.setCloseTime(closeTime);
        this.activity.update();
    }
    
    @Override
    public void onClose(long now) {
        //先设置状态为关闭,再通知,最后清空活动数据
        activity.setConfigType(0);
		activity.setConfigId(0);
		activity.setPlanId(0);
		activity.setBeginTime(0);
		activity.setSettleTime(0);
		activity.setCloseTime(0);
		activity.update();
    }
//    
//    @Override
//   	public void onPrepare(long now) {
//   		// TODO Auto-generated method stub
//   		
//   	}
//    
//    @Override
//    public void onBegin(long now) {
//    	// TODO Auto-generated method stub
//    	
//    }
//    
//    @Override
//    public void onSettle(long now) {
//    	// TODO Auto-generated method stub
//    	
//    }
    
    @Override
	public long getPrepareTime() {
		return this.activity.getPrepareTime();
	}
    
	@Override
	public long getBeginTime() {
		return this.activity.getBeginTime();
	}

	@Override
	public long getSettleTime() {
		return this.activity.getSettleTime();
	}

	@Override
	public long getCloseTime() {
		return this.activity.getCloseTime();
	}
	
	@Override
	public int getStatus() {
		return this.activity.getStatus();
	}
	
}
