package com.cat.server.game.module.playermail.proto;

import java.util.Collection;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBDefine;
import com.cat.server.game.data.proto.PBPlayer;
import com.google.protobuf.AbstractMessageLite.Builder;

public class AckDeleteEmailResp implements IProtocol {
	
	private PBPlayer.AckDeleteEmail.Builder builder;
	
	public AckDeleteEmailResp() {
		this.builder = PBPlayer.AckDeleteEmail.newBuilder();
	}

	public static AckDeleteEmailResp newInstance() {
		return new AckDeleteEmailResp();
	}
	
	public void setCode(int code) {
		this.builder.setCode(code);
	}
	
	public void addMailId(long mailId) {
		this.builder.addDeletes(mailId);
	}
	
	public void addMailId(Collection<Long> mailIds) {
		this.builder.addAllDeletes(mailIds);
	}
	
	@Override
	public short protocol() {
		return PBDefine.PBProtocol.AckDeleteEmail_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
	

}
