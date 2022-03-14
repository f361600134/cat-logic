package com.cat.server.game.module.functioncontrol;

import org.springframework.stereotype.Service;

@Service
public class FunctionControlService implements IFunctionControlService{

	@Override
	public boolean checkOpen(long playerId, int moduleId) {
		return true;
	}

}
