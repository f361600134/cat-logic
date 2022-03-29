package com.cat.server.game.module.functioncontrol;

import org.springframework.stereotype.Service;

@Service
public class FunctionControlService implements IFunctionControlService{

	/**
	 * 校验功能是否开启
	 */
	@Override
	public boolean checkOpen(long playerId, int moduleId) {
		return true;
	}

	@Override
	public long getLastResetTime(long playerId, int moduleId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLastResetTime(long playerId, int moduleId, long now) {
		// TODO Auto-generated method stub
	}

}
