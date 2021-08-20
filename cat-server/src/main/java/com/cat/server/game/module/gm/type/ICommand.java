package com.cat.server.game.module.gm.type;

import com.cat.net.network.base.ISession;

public interface ICommand {
	
	public boolean process(ISession session, String content);

}
