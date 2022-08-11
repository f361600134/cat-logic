package com.cat.server.game.module.function.domain;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.core.config.ConfigManager;
import com.cat.server.core.server.AbstractModuleDomain;
import com.cat.server.game.data.config.local.ConfigFunction;
import com.cat.server.game.helper.condition.ICondition;

/**
* FunctionDomain
* @author Jeremy
*/
public class FunctionDomain extends AbstractModuleDomain<Long, Function> {

	private static final Logger log = LoggerFactory.getLogger(FunctionDomain.class);
	
	
	public FunctionDomain(){
	}
	
	////////////业务代码////////////////////
	
	@Override
	public void afterCreate() {
		//初始化需要开启的功能
		Map<Integer, ConfigFunction> configs = ConfigManager.getInstance().getConfigs(ConfigFunction.class, (c)->{
			/* 配置控制的功能, 单独单独判断,不能影响功能初始化*/
//			if (c.getShield() == 1) {
//                return false;
//          }
			ICondition unlockCondition = c.getCondition();
            return unlockCondition == null;
		});
		Function function = this.getBean();
		for (ConfigFunction config : configs.values()) {
			FunctionData functionData = function.getFunctionData(config.getId());
			functionData.setOpen(true);
		}
		function.update();
	}
	
}

