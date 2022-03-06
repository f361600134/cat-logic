package com.cat.server.game.module.console.type.impl;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigRecycle;
import com.cat.server.game.module.console.annotation.Console;
import com.cat.server.game.module.console.type.IConsole;
import com.cat.server.game.module.recycle.IRecycleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 回收信息
 * @author Jeremy
 */
@Console("recycle")
public class ConsoleRecycle implements IConsole {

	@Autowired
	private IRecycleService recycleService;

	@Override
	public void process(String ...content) {
		try {
			Map<Integer, ConfigRecycle> configMap = ConfigManager.getInstance().getAllConfigs(ConfigRecycle.class);
			configMap.forEach((key, config)->{
				System.out.println(key+":"+config.getStrategy().calculateTimePoint(0));
			});
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

}
