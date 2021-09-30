package com.cat.server.game.module.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.server.common.ServerConfig;
import com.cat.server.core.server.AbstractModuleManager;
import com.cat.server.game.module.activity.domain.ActivityDomain;
import com.cat.server.game.module.family.domain.Family;

/**
 * @author Jeremy
 */
@Component
class ActivityManager extends AbstractModuleManager<Integer, ActivityDomain> {

	@Autowired
	private ServerConfig serverConfig;

	@Override
	public ActivityDomain getFromDb(Integer id) {
		try {
			ActivityDomain domain = clazz.newInstance();
			List list = process.selectByIndex(domain.getBasePoClazz(), new String[] { Family.PROP_CURSERVERID },
					new Object[] { id });
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

	@Override
	public void init() {
		// 初始化, 加载一次, 初始化所有家族数据
		getFromDb(serverConfig.getServerId());
	}

}
