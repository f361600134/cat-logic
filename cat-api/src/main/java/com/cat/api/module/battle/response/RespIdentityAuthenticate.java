package com.cat.api.module.battle.response;

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

	/**
	 * 连接的服务节点类型
	 */
	private String nodeType;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public RespIdentityAuthenticate() {}

	public int protocol() {
		return ProtocolId.RespIdentityAuthenticate;
	}
	
}
