package com.cat.zproto.core.result;

/**
 * 系统集父类
 * @auth Jeremy
 * @date 2019年4月28日下午3:06:32
 */
public class SystemResult extends AbstractResult {

	public SystemResult(SystemCodeEnum eEnum) {
		super(eEnum);
	}
	
	public SystemResult(int code, String tips) {
		super(code, tips);
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
	 * 构造消息
	 * @return
	 */
	public static IResult build(SystemCodeEnum codeEnum) {
		return new SystemResult(codeEnum);
	}
	
}
