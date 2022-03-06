package com.cat.server.game.module.console.type.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.module.console.annotation.Console;
import com.cat.server.game.module.console.type.IConsole;

/**
 * 退出应用程序
 * @author Jeremy
 */
@Console("exit")
public class ConsoleExit implements IConsole{
	
	private static final Logger log = LoggerFactory.getLogger(ConsoleExit.class);

	@Override
	public void process(String ...content) {
		log.info("====> 收到控制台停服命令, 优雅停服...");
		System.exit(0);
	}
	
}
