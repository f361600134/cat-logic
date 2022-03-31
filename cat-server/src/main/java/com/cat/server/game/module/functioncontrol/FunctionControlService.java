package com.cat.server.game.module.functioncontrol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.data.config.local.ConfigFunction;
import com.cat.server.game.module.functioncontrol.domain.FunctionControl;
import com.cat.server.game.module.functioncontrol.domain.FunctionControlDomain;

@Service
public class FunctionControlService implements IFunctionControlService{
	
	@Autowired private FunctionControlManager manager;

	/**
	 * 校验功能是否开启<br>
	 * 
	 */
	@Override
	public boolean checkOpen(long playerId, int moduleId) {
		ConfigFunction config = ConfigManager.getInstance().getConfig(ConfigFunction.class, moduleId);
		
		return true;
	}

	@Override
	public long getLastResetTime(long playerId, int functionId) {
		FunctionControlDomain domain = this.manager.getDomain(playerId);
		if (domain == null) {
			return 0L;
		}
		FunctionControl functionControl = domain.getOrCreate(functionId);
		return functionControl.getResetTime();
	}

	@Override
	public void setLastResetTime(long playerId, int functionId, long now) {
		FunctionControlDomain domain = this.manager.getDomain(playerId);
		if (domain == null) {
			return;
		}
		FunctionControl functionControl = domain.getOrCreate(functionId);
		functionControl.setResetTime(now);
		functionControl.update();
	}
	
	@Override
	public int priority() {
		return Priority.LOGIC.getPriority();
	}

}
