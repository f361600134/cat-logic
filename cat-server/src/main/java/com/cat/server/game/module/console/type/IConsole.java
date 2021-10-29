package com.cat.server.game.module.console.type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.module.console.type.impl.ConsoleIdentify;

public interface IConsole {
	
	/**
	 * 处理命令
	 * @param content
	 * @return
	 */
	public void process(String content);

}
