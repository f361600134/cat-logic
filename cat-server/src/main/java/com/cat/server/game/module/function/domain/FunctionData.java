package com.cat.server.game.module.function.domain;

import com.cat.server.game.data.proto.PBFunction.PBFunctionDto;
import com.cat.server.game.module.function.proto.PBFunctionDtoBuilder;

/**
 * 模块功能数据
 * @author Jeremy Feng
 */
public class FunctionData {
	
	/**
	 * 模块id
	 */
	private int moduleId;
	
	/**
	 * 模块重置时间
	 */
	private long resetTime;
	
	public FunctionData() {
	}
	
	public FunctionData(int moduleId, long resetTime) {
		super();
		this.moduleId = moduleId;
		this.resetTime = resetTime;
	}

	public int getModuleId() {
		return moduleId;
	}

	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}

	public long getResetTime() {
		return resetTime;
	}

	public void setResetTime(long resetTime) {
		this.resetTime = resetTime;
	}
	
	/**
	 *  构建一个模块相关的数据
	 * @param moduleId
	 * @param resetTime
	 * @return
	 */
	public static FunctionData create(int moduleId) {
		return new FunctionData(moduleId, 0L);
	}
	
	/**
	 * 功能数据转协议对象
	 * @param open 是否
	 * @return
	 */
	public PBFunctionDto toProto(boolean isOpen) {
		PBFunctionDtoBuilder builder = PBFunctionDtoBuilder.newInstance();
		builder.setFunctionId(moduleId);
		builder.setOpen(isOpen);
		return builder.build();
	}

	
}
