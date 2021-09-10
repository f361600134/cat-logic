package com.cat.server.game.module.player.proto;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBLogin;
import com.google.protobuf.AbstractMessageLite.Builder;

public class ReqLogin extends AbstractProtocol{
	
	private PBLogin.ReqLogin.Builder builder;
	
	public ReqLogin() {
		this.builder = PBLogin.ReqLogin.newBuilder();
	}
	
	public void setUserName(String value) {
		this.builder.setUserName(value);
	}
	
	public void setSessionKey(String value) {
		this.builder.setSessionKey(value);
	}
	
	public void setChannel(int value) {
		this.builder.setChannel(value);
	}
	
	public void setServerId(int value) {
		this.builder.setServerId(value);
	}
	
	public void setLoginSid(int value) {
		this.builder.setLoginSid(value);
	}
	
	public static ReqLogin create(String userName) {
		ReqLogin req = new ReqLogin();
		req.setUserName(userName);
		req.setServerId(1);
		return req;
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqPlayerLogin_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}

}
