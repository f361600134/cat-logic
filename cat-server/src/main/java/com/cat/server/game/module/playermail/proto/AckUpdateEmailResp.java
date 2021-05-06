package com.cat.server.game.module.playermail.proto;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBDefine;
import com.cat.server.game.data.proto.PBPlayer;
import com.cat.server.game.module.playermail.domain.PlayerMail;
import com.google.protobuf.AbstractMessageLite.Builder;

public class AckUpdateEmailResp implements IProtocol {
	
	private PBPlayer.AckUpdateEmail.Builder builder;
	
	public AckUpdateEmailResp() {
		this.builder = PBPlayer.AckUpdateEmail.newBuilder();
	}

	public static AckUpdateEmailResp newInstance() {
		return new AckUpdateEmailResp();
	}
	
	public void addMails(PlayerMail mail) {
		this.builder.addEmails(mail.toProto());
	}
	
	@Override
	public short protocol() {
		return PBDefine.PBProtocol.AckUpdateEmail_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
	

}
