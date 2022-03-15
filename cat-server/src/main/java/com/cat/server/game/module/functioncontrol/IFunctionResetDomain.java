package com.cat.server.game.module.functioncontrol;

public interface IFunctionResetDomain <I extends Number>{
	
	/**
	 * 检查重置
	 * @param id
	 * @param now
	 */
	public boolean checkAndReset(I id, long now);

}
