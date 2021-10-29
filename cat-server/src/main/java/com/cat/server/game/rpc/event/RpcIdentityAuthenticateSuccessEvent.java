package com.cat.server.game.rpc.event;

import com.cat.server.core.event.BaseEvent;

/**
 * RPC连接身份验证成功事件
 * @author Jeremy
 */
public class RpcIdentityAuthenticateSuccessEvent extends BaseEvent {

	public static String ID = RpcIdentityAuthenticateSuccessEvent.class.getSimpleName();

	/**
	 * 节点类型
	 */
	private final String nodeType;

	public String getNodeType() {
		return nodeType;
	}

	public RpcIdentityAuthenticateSuccessEvent(String nodeType) {
		this.nodeType = nodeType;
	}

	public static RpcIdentityAuthenticateSuccessEvent create(String nodeType) {
		return new RpcIdentityAuthenticateSuccessEvent(nodeType);
	}

}
