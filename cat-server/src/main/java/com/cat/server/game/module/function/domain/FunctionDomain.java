package com.cat.server.game.module.function.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
	
	/**
	 * 获取开启的功能列表<br>
	 * 这里返回的是玩家已解锁的所有功能列表, 不能受到配置屏蔽的影响
	 * @return
	 */
	public Collection<Integer> getFunctionIds(){
		List<Integer> ret = new ArrayList<>();
		Map<Integer, FunctionData> functionDataMap = this.getBean().getFunctionDataMap();
		for (FunctionData functionData : functionDataMap.values()) {
			if (functionData.isOpen()) {
				ret.add(functionData.getModuleId());
			}
		}
		return ret;
	}
	
	/**
	 * domain层的功能校验<br>
	 * 1. 校验配置
	 * 2. 校验使玩家是否开启
	 * @return
	 */
	public boolean checkFunctionOpen(int functionId){
		//再判断是否开启
		FunctionData functionData = this.getBean().getFunctionData(functionId);
		return functionData.isOpen();
	}
	
}

