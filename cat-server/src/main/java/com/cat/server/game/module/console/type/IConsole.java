package com.cat.server.game.module.console.type;

public interface IConsole {
	
	/**
	 * 处理命令
	 * @param content
	 * @return
	 */
	public void process(String ...content);

}
