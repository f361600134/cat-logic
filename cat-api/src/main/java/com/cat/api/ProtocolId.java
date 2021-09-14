package com.cat.api;

/**
 * 协议号定义, 用于rpc通讯
 * @author Administrator
 */
public class ProtocolId {
	
//	ReqIdentityAuthenticate(10001),
//	RespIdentityAuthenticate(10002),
//	
//	;
//	private int protoId;
//	
//	private ProtocolId(int protoId){
//		this.protoId = protoId;
//	}
//	
//	public int getProtoId() {
//		return protoId;
//	}
//	public void setProtoId(int protoId) {
//		this.protoId = protoId;
//	}
	
	public static final int ReqIdentityAuthenticate  = 10001;
	public static final int RespIdentityAuthenticate  = 10002;
	public static final int ReqKickUpPlayer = 10003;
	public static final int RespKickUpPlayer = 10004;

}
