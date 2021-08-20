package com.cat.server.game.module.gm;

import com.cat.net.network.base.ISession;

public interface ICommandService {
	
	/**
	 * 是否是命令
	 * @param command
	 * @return
	 */
	public boolean isCommand(String command);
	
	/**
	 * 处理命令
	 * @param command
	 * @return
	 */
	public boolean process(ISession session, String command);

}
