package com.cat.server.game.module.gm.type;

import com.cat.net.network.base.GameSession;

public interface ICommand {
	
	public boolean process(GameSession session, String content);

}
