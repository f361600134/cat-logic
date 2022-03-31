package com.cat.server.game.module.functioncontrol.domain;

public class FunctionInfo {
	
	/**
	 * 功能id
	 */
	private int functionId;
	
	/**
	 * 重置时间
	 */
	private long resetTime;
	
	public FunctionInfo() {}

	int getFunctionId() {
		return functionId;
	}

	void setFunctionId(int functionId) {
		this.functionId = functionId;
	}

	long getResetTime() {
		return resetTime;
	}

	void setResetTime(long resetTime) {
		this.resetTime = resetTime;
	}
	
	public static FunctionInfo create(int functionId) {
		FunctionInfo functionControl = new FunctionInfo();
		functionControl.setFunctionId(functionId);
		return functionControl;
	}
}
