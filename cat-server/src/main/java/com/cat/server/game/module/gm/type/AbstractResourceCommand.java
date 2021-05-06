package com.cat.server.game.module.gm.type;

import com.cat.net.network.base.GameSession;
import com.cat.server.game.module.gm.annotation.Command;

/**
 * 	资源相关命令
 * @author Jeremy
 *
 */
public abstract class AbstractResourceCommand implements ICommand{
	
	@Override
	public boolean process(GameSession session, String content) {
		Command command = this.getClass().getAnnotation(Command.class);
		if (command == null) {
			return false;
		}
		//	解析出命令以及參數
		String name = command.value();
		String params = content.substring(name.length());
		
		return doProcess(session.getPlayerId(), params);
	}
	
	public abstract boolean doProcess(long playerId, String params);

}
