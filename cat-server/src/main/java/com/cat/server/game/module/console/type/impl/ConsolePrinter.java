package com.cat.server.game.module.console.type.impl;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.game.data.config.local.ConfigTest;
import com.cat.server.game.module.console.annotation.Console;
import com.cat.server.game.module.console.type.IConsole;

/**
 * 控制台命令,更新指定排行榜
 * @author Jeremy
 */
@Console("print")
public class ConsolePrinter implements IConsole {

	/**
	 * print,101
	 * print,102
	 * print,103
	 * print,104
	 * print,109
	 */
	@Override
	public void process(String ...content) {
		int value = Integer.parseInt(content[1]);
//		ConfigFunction config = ConfigManager.getInstance().getConfig(ConfigFunction.class, value);
//		System.out.println(config.getResetTimePoint().getResetTimePoint(TimeUtil.now()));
//		System.out.println(config.getReset());
//		System.out.println(config.getUnlock());
		ConfigTest config = ConfigManager.getInstance().getConfig(ConfigTest.class, value);
		System.out.println(config.getId()+", "+ config.getReward());
		config.getReward().addCount(1, 2);
		System.out.println(config.getId()+", "+ config.getReward());
	}
	
}
