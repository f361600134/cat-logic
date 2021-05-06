package com.cat.server.game.module.playermail.proto;

import java.util.Collection;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBDefine;
import com.cat.server.game.data.proto.PBPlayer;
import com.cat.server.game.module.playermail.domain.PlayerMail;
import com.google.protobuf.AbstractMessageLite.Builder;

public class AckEmailListResp implements IProtocol {
	
	private PBPlayer.AckEmailList.Builder builder;
	
	public static AckEmailListResp newInstance() {
		return new AckEmailListResp();
	}
	
	public AckEmailListResp() {
		this.builder = PBPlayer.AckEmailList.newBuilder();
	}
	
	public void addEmailInfo(PlayerMail mail){
    	this.builder.addEmails(mail.toProto());
    }
	
	public void addEmailInfos(Collection<PlayerMail> mails){
		for (PlayerMail mail : mails) {
			this.builder.addEmails(mail.toProto());
		}
    }

	@Override
	public short protocol() {
		return PBDefine.PBProtocol.AckEmailList_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}

}
