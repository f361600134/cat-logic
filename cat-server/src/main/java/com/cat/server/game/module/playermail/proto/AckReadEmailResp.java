package com.cat.server.game.module.playermail.proto;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBDefine;
import com.cat.server.game.data.proto.PBPlayer;
import com.google.protobuf.AbstractMessageLite.Builder;

public class AckReadEmailResp implements IProtocol {
	
	private PBPlayer.AckReadEmail.Builder builder;
	
	public AckReadEmailResp() {
		this.builder = PBPlayer.AckReadEmail.newBuilder();
	}

	public static AckReadEmailResp newInstance() {
		return new AckReadEmailResp();
	}
	
	public void setId(long value) {
		this.builder.setId(value);
	}
	
	public void setCode(int value) {
		this.builder.setCode(value);
	}
	
	@Override
	public short protocol() {
		return PBDefine.PBProtocol.AckReadEmail_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
	

}
