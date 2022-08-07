package com.cat.server.game.module.functioncontrol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.lifecycle.Priority;
import com.cat.server.game.data.config.local.ConfigFunction;
import com.cat.server.game.module.functioncontrol.define.ReddotConditionEnum;
import com.cat.server.game.module.functioncontrol.domain.FunctionControl;
import com.cat.server.game.module.functioncontrol.domain.FunctionControlDomain;
import com.cat.server.game.module.functioncontrol.reddot.IFunctionReddot;

@Service
class FunctionControlService implements IFunctionControlService{
	
	@Autowired private FunctionControlManager manager;

	private Map<Integer, IFunctionReddot> functionReddotMap = new HashMap<>();
	
	@Autowired
	public void initServiceMap(List<IFunctionReddot> functionReddots) {
		for (IFunctionReddot functionReddot : functionReddots) {
			this.functionReddotMap.put(functionReddot.getCondition(), functionReddot);
		}
	}
	
	/**
	 * 根据条件检测红点
	 * @param playerId
	 * @param reddotCondition
	 * @return  
	 * @return int  
	 * @date 2022年8月7日下午8:24:43
	 */
	public int checkReddot(long playerId, ReddotConditionEnum reddotCondition) {
		FunctionControlDomain domain = this.manager.getDomain(playerId);
		if (domain == null) {
			return 0;
		}
		IFunctionReddot functionReddot = functionReddotMap.get(reddotCondition.getConditionId());
		if (functionReddot == null) {
			return 0;
		}
		int newReddot = functionReddot.checkReddot(playerId);
		//这里其实要获得功能id
		FunctionControl functionControl = domain.getBean(0);
		int oldReddot = functionControl.getReddot(reddotCondition.getConditionId());
		if (oldReddot != newReddot) {
			functionControl.replaceReddot(reddotCondition.getConditionId(), newReddot);
		}
		return newReddot;
	}
	
	/**
	 * 校验功能是否开启<br>
	 */
	@Override
	public boolean checkOpen(long playerId, int functionId) {
		ConfigFunction config = ConfigManager.getInstance().getConfig(ConfigFunction.class, functionId);
		return config.getCondition().accept(playerId);
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
