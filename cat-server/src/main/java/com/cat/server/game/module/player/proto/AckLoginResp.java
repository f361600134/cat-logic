package com.cat.server.game.module.player.proto;

import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBLogin;

import com.google.protobuf.AbstractMessageLite.Builder;

public class AckLoginResp implements IProtocol{
	
	private PBLogin.AckLogin.Builder builder;
	
	public AckLoginResp() {
		this.builder = PBLogin.AckLogin.newBuilder();
		this.builder.setTime(System.currentTimeMillis());
	}
	
	public static AckLoginResp create() {
		return new AckLoginResp();
	}
	
	public AckLoginResp setCode(int value) {
		this.builder.setCode(value);
		return this;
	}
	
	public AckLoginResp setStatus(int value) {
		this.builder.setStatus(value);
		return this;
	}
	
	@Override
	public short protocol() {
		return PBProtocol.AckLogin_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}

}
