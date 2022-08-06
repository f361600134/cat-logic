package com.cat.server.game.module.functioncontrol.domain;

import java.util.HashMap;
import java.util.Map;

import com.cat.orm.core.annotation.Column;
import com.cat.orm.core.annotation.PO;
import com.cat.server.core.server.IPersistence; 

/**
 * 功能开启模块
* @author Jeremy Feng.
*/
@PO(name = "function_control")
public class FunctionControl extends FunctionControlPo implements IPersistence{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8141896538656737487L;
	
	@Column(PROP_REDDOTSTR)
	private Map<Integer, Integer> reddotMap = new HashMap<>();
	
	public FunctionControl() {

	}
	
	public FunctionControl(long playerId) {
		this.playerId = playerId;
	}
	
	/**
	 * 获取指定条件的红点
	 * @param conditionId 条件id
	 * @return 红点数量, 大于0表示有红点
	 */
	public int getReddot(int conditionId) {
		return reddotMap.getOrDefault(conditionId, 0);
	}

}
