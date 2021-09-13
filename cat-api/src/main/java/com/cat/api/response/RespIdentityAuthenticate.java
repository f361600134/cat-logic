package com.cat.api.response;

import com.cat.api.ProtocolId;
import com.cat.api.core.AbstractStuffProto;

/**
 * 身份认证请求参数
 * @author Jeremy
 */
public class RespIdentityAuthenticate extends AbstractStuffProto {
	
	/**
	 * 错误码, 0:成功, 其他:失败
	 */
	private int code;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	public RespIdentityAuthenticate() {}

	public int protocol() {
		return ProtocolId.RespIdentityAuthenticate;
	}
	
}
