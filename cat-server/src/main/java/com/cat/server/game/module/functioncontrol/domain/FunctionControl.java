package com.cat.server.game.module.functioncontrol.domain;

import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence; 

/**
* @author Jeremy
*/
@PO(name = "function_control")
public class FunctionControl extends FunctionControlPo implements IPersistence{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8141896538656737487L;
	
	public FunctionControl() {

	}
	
	public FunctionControl(long playerId) {
		this.playerId = playerId;
	}

}
