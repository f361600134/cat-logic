package com.cat.robot.module.login.proto;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.google.protobuf.AbstractMessage;
import com.cat.server.game.data.proto.PBPlayer;

public class ReqPlayerLoginBuilder extends AbstractProtocol{
	
	private PBPlayer.ReqPlayerLogin.Builder builder;
	
	public ReqPlayerLoginBuilder() {
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
	
	public static ReqPlayerLoginBuilder create(String userName) {
		ReqPlayerLoginBuilder req = new ReqPlayerLoginBuilder();
		req.setUserName(userName);
		req.setServerId(1);
		return req;
	}
	
	@Override
	public AbstractMessage getBuilder() {
		return this.builder.build();
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
