package com.cat.api.module.battle.request;

import com.cat.api.ProtocolId;
import com.cat.api.core.AbstractStuffProto;

/**
 * 身份认证请求参数
 * 
 * @author Jeremy
 */
public class ReqIdentityAuthenticate extends AbstractStuffProto {

	/**
	 * 节点id
	 */
	private int nodeId;
	/**
	 * 请求连接的节点类型
	 */
	private String nodeType;
	/**
	 * 秘钥
	 */
	private String secretKey;

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	public int protocol() {
		return ProtocolId.ReqIdentityAuthenticate;
	}
	
	public ReqIdentityAuthenticate() {
	}
	
//	public ReqIdentityAuthenticate(int id, String nodeType) {
//		this.nodeId = id;
//		this.nodeType = nodeType;
//		this.secretKey = "123";
//	}
	
	public ReqIdentityAuthenticate(int id, String nodeType, String secretKey) {
		this.nodeId = id;
		this.nodeType = nodeType;
		this.secretKey = secretKey;
	}
	
	public static ReqIdentityAuthenticate create(int id, String nodeType, String secretKey){
		return new ReqIdentityAuthenticate(id, nodeType, secretKey);
	}

}
