package com.cat.server.game.module.playermail.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBMail.AckMailList;
import com.cat.server.game.data.proto.PBMail.PBMailInfo;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckMailListResp
* @author Jeremy
*/
public class AckMailListResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckMailListResp.class);
	
	private final AckMailList.Builder builder = AckMailList.newBuilder();
	
	public AckMailListResp() {}
	
	public static AckMailListResp newInstance() {
		return new AckMailListResp();
	}
	
	public AckMailList build() {
		return builder.build();
	}
	
	/** **/
	public void addMails(PBMailInfo value){
		this.builder.addMails(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckMailList_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
