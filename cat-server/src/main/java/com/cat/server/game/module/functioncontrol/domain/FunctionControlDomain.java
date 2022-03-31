package com.cat.server.game.module.functioncontrol.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cat.server.core.server.AbstractModuleMultiDomain;



/**
* FunctionControlDomain
* @author Jeremy
*/
public class FunctionControlDomain extends AbstractModuleMultiDomain<Long, Integer, FunctionControl> {

	private static final Logger log = LoggerFactory.getLogger(FunctionControlDomain.class);
	
	public FunctionControlDomain(){
		
	}
	
	////////////业务代码////////////////////
	
	/**
	 * 获取或创建功能控制信息
	 * @param functionId
	 * @return
	 */
	public FunctionControl getOrCreate(int functionId) {
		FunctionControl functionControl = this.getBean(functionId);
		if (functionControl == null) {
			functionControl = new FunctionControl(this.getId());
			functionControl.setFunctionId(functionId);
			functionControl.save();
			this.putBean(functionId, functionControl);;
		}
		return functionControl;
	}
	
}
