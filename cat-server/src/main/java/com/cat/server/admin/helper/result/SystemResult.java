package com.cat.server.admin.helper.result;

import com.cat.server.core.result.CodeEnum;

/**
 * 系统集父类
 * @auth Jeremy
 * @date 2019年4月28日下午3:06:32
 */
public class SystemResult extends AbstractResult {
	
	public Object data;

	public SystemResult(CodeEnum eEnum) {
		super(eEnum);
	}
	
	public SystemResult(int code, String tips) {
		super(code, tips);
	}
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * 是否正确
	 * 
	 * @return
	 * @return boolean
	 */
	@Override
	public boolean success() {
		return getCode() == SystemCodeEnum.SUCCESS.getStatus();
	}
	
	
	/**
	 * 构造消息, 系统内的错误码
	 * @return
	 */
	public static SystemResult build(CodeEnum codeEnum) {
		return new SystemResult(codeEnum);
	}
	
	/**
	 * 构造消息, 系统内的错误码
	 * @return
	 */
	public static SystemResult build(CodeEnum codeEnum, Object data) {
		SystemResult result = new SystemResult(codeEnum);
		result.setData(data);
		return result;
	}
	
}
