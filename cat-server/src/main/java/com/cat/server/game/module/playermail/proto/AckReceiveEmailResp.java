package com.cat.server.game.module.playermail.proto;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBDefine;
import com.cat.server.game.data.proto.PBPlayer;
import com.google.protobuf.AbstractMessageLite.Builder;

public class AckReceiveEmailResp implements IProtocol {
	
	private PBPlayer.AckReceiveEmail.Builder builder;
	
	public AckReceiveEmailResp() {
		this.builder = PBPlayer.AckReceiveEmail.newBuilder();
	}

	public static AckReceiveEmailResp newInstance() {
		return new AckReceiveEmailResp();
	}
	
	public void setCode(int value) {
		this.builder.setCode(value);
	}
	
	@Override
	public short protocol() {
		return PBDefine.PBProtocol.AckReceiveEmail_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
	

}
