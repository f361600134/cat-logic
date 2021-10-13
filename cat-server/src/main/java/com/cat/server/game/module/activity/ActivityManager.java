package com.cat.server.game.module.activity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.common.ServerConfig;
import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.domain.ActivityDomain;
import com.cat.server.game.module.activity.type.ActivityTypeEnum;
import com.google.common.collect.Lists;

/**
 * 活动初始化, 默认根据planId进行初始化, 如果获取不到domain 从数据库获取
 * 对于活动来说, 当启动服务器的时候, 根据serverId获取所有活动, 然后加入活动缓存, 一次IO获取所有活动,这样才比较合理<br>
 * @param Integer 活动planId
 * @param ActivityDomain 单个活动domain
 * @author Jeremy
 */
@Component
class ActivityManager extends AbstractModuleManager<Integer, ActivityDomain> {

	@Autowired
	private ServerConfig serverConfig;

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
	 * @param id,活动planId
	 */
	@Override
	public ActivityDomain getFromDb(Integer id) {
		try {
			ActivityDomain domain = clazz.newInstance();
			List list = process.selectByIndex(domain.getBasePoClazz(), new String[] { Activity.PROP_CURSERVERID, Activity.PROP_ID },
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
	 *  初始化, 加载一次, 初始化当前所有活动<br>
	 *  这里跟{@link #getFromDb(Integer)}唯一的区别就是, 这里在init的时候, 进行一次IO获取到所有活动, 然后在内存中进行初始化
	 */
	@Override
	public void init() {
		//初始化活动
		List<Activity> activitys = process.selectByIndex(Activity.class, new String[] { Activity.PROP_CURSERVERID }, new Object[] { serverConfig.getServerId()});
		//根据定义的活动枚举去初始化
		Collection<Integer> allTypes = ActivityTypeEnum.allTypes();
		for (Integer typeId : allTypes) {
			Activity activity = findActivity(typeId, activitys);
			ActivityDomain domain = new ActivityDomain();
			if (activity != null) {
				//如果不为null, 表示加载的旧活动
				domain.initData(activity.getId(), Lists.newArrayList(activity));
			}else {
				//如果为null, 表示初始化的新活动
				domain.initData(typeId);
			}
			domains.put(domain.getId(), domain);
		}
	}
	
	private Activity findActivity(int typeId, List<Activity> activitys) {
		Optional<Activity> opt = activitys.stream().filter((a)->a.getId() == typeId).findFirst();
		if (opt.isPresent()) {
			return opt.get();
		} 
		return null;
	}
	
}
