package com.cat.server.game.module.activity;

import java.util.List;

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.common.ServerConfig;
import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.activity.domain.Activity;
import com.cat.server.game.module.activity.domain.ActivityDomain;
import com.cat.server.game.module.family.domain.Family;

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
			List list = process.selectByIndex(domain.getBasePoClazz(), new String[] { Family.PROP_CURSERVERID, Family.PROP_ID },
					new Object[] { serverConfig.getServerId(), id });
			if (list.isEmpty()) {
				// 无数据创建
				domain.initData(id);
			} else {
				// 有数据初始化
				domain.initData(id, list);
			}
			domain.afterInit();
			return domain;
		} catch (Exception e) {
			logger.error("getFromDb error", e);
		}
		return null;
	}

	/**
	 *  初始化, 加载一次, 初始化所有活动数据
	 */
	@Override
	public void init() {
		List<Activity> list = process.selectByIndex(Activity.class, new String[] { Family.PROP_CURSERVERID }, new Object[] { serverConfig.getServerId()});
		ActivityDomain domain = null;
		for (Activity activity : list) {
			domain = new ActivityDomain();
			domains.put(activity.getPlanId(), domain);
			domain.initData(activity.getPlanId(), Lists.newArrayList(activity));
		}
	}

	
}
