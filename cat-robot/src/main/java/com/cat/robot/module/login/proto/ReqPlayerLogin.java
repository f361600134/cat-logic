package com.cat.robot.module.login.proto;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBPlayer;

public class ReqPlayerLogin extends AbstractProtocol{
	
	private PBPlayer.ReqPlayerLogin.Builder builder;
	
	public ReqPlayerLogin() {
		this.builder = PBPlayer.ReqPlayerLogin.newBuilder();
	}
	
	public void setUserName(String value) {
		this.builder.setAccountName(value);
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
	
	public static ReqPlayerLogin create(String userName) {
		ReqPlayerLogin req = new ReqPlayerLogin();
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
