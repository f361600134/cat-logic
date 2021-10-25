package com.cat.server.game.module.activity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.common.ServerConfig;
import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.core.task.Task;
import com.cat.server.core.task.impl.CommonTaskExecutor;
import com.cat.server.game.data.config.local.ConfigActivityScheduleTime;
import com.cat.server.game.module.activity.define.ActivityConstant;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.domain.ActivityDomain;
import com.cat.server.game.module.activity.type.ActivityTypeEnum;
import com.cat.server.utils.TimeUtil;
import com.google.common.collect.Lists;

/**
 * 活动初始化, 默认根据typeId进行初始化, 如果获取不到domain 从数据库获取 对于活动来说, 当启动服务器的时候,
 * 根据serverId获取所有活动, 然后加入活动缓存, 一次IO获取所有活动,这样才比较合理<br>
 * @author Jeremy
 */
@Component
class ActivityManager extends AbstractModuleManager<Integer, ActivityDomain> {

	@Autowired
	private ServerConfig serverConfig;

	@Autowired
	private CommonTaskExecutor commonExecutor;

//	/**
//	 * 获取数据, 获取不到从数据库获取
//	 */
//	@Override
//    public synchronized ActivityDomain getDomain(Integer id) {
//		ActivityDomain domain = domains.get(id);
//		if (domain == null) {
//			domain = getFromDb(id);
//			domains.put(id, domain);
//		}
//		return domain;
//	}

	/**
	 * @param id 活动typeId
	 */
	@Override
	public ActivityDomain getFromDb(Integer id) {
		try {
			ActivityDomain domain = clazz.newInstance();
			List list = process.selectByIndex(domain.getBasePoClazz(),
					new String[] { Activity.PROP_CURSERVERID, Activity.PROP_ID },
					new Object[] { serverConfig.getServerId(), id });
			if (list.isEmpty()) {
				// 无数据创建
				domain.initData(id);
			} else {
				// 有数据初始化
				domain.initData(id, list);
			}
			return domain;
		} catch (Exception e) {
			logger.error("getFromDb error", e);
		}
		return null;
	}

	/**
	 * 初始化, 加载一次, 初始化当前所有活动<br>
	 * 这里跟{@link #getFromDb(Integer)}唯一的区别就是, 这里在init的时候, 进行一次IO获取到所有活动, 然后在内存中进行初始化
	 */
	@Override
	public void init() {
		// 初始化活动
		List<Activity> activitys = process.selectByIndex(Activity.class, new String[] { Activity.PROP_CURSERVERID },
				new Object[] { serverConfig.getServerId() });
		// 根据定义的活动枚举去初始化
		Collection<Integer> allTypes = ActivityTypeEnum.allTypes();
		for (Integer typeId : allTypes) {
			Activity activity = findActivity(typeId, activitys);
			ActivityDomain domain = new ActivityDomain();
			if (activity != null) {
				// 如果不为null, 表示加载的旧活动
				domain.initData(activity.getId(), Lists.newArrayList(activity));
			} else {
				// 如果为null, 表示初始化的新活动
				domain.initData(typeId);
			}
			domains.put(domain.getId(), domain);
		}
		// 开始定时任务
		commonExecutor.scheduleTaskStartAtNextMinute(new ActivityTickTask(), ActivityConstant.TICK_INTERVAL);
	}

	private Activity findActivity(int typeId, List<Activity> activitys) {
		Optional<Activity> opt = activitys.stream().filter((a) -> a.getId() == typeId).findFirst();
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	/**
	 * 活动定时任务
	 * 1. 所有活动状态检测
	 * 2. 所有活动配置检测
	 * 3. 所有活动数据存储
	 * @author Administrator
	 *
	 */
	private class ActivityTickTask implements Task {

		private int counter;

		@Override
		public void execute() throws Exception {
			long now = TimeUtil.now();

			long openDateTime = TimeUtil.getTimestamp(serverConfig.getOpenDate());
			if (now < openDateTime) {
				// 开服之前 所有活动都不会启动
				return;
			}
			// 所有活动状态检测
			Collection<ActivityDomain> activityTypes = getAllDomain();
			for (ActivityDomain activityDomain : activityTypes) {
				activityDomain.checkAndRefreshStatus(now);
			}
			// 本地活动
			Map<Integer, ConfigActivityScheduleTime> scheduleTimeConfigMap = ConfigManager.getInstance()
					.getAllConfigs(ConfigActivityScheduleTime.class);
			for (ConfigActivityScheduleTime config : scheduleTimeConfigMap.values()) {
				int type = config.getType();
				ActivityDomain activityDomain = getDomain(type);
				if (activityDomain == null) {
					logger.warn("activity[{}] tick fail.activity is null.schedule config id[{}]", type, config.getId());
					continue;
				}
				activityDomain.checkAndUseConfig(config, now);
			}
			// 自动保存
			if (counter >= ActivityConstant.AUTO_SAVE_TICK_COUNT) {
				for (ActivityDomain activityDomain : activityTypes) {
					activityDomain.getActivity().update();
				}
				counter = 0;
			}
	    	counter++;
		}

	}

}
