package com.cat.server.game.module.mail.proto;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMail.PBMailInfo;
import com.cat.server.game.data.proto.PBMail.RespMailList;

/**
* RespMailListBuilder
* @author Jeremy
*/
public class RespMailListBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespMailListBuilder.class);
	
	private final RespMailList.Builder builder = RespMailList.newBuilder();
	
	public RespMailListBuilder() {}
	
	public static RespMailListBuilder newInstance() {
		return new RespMailListBuilder();
	}
	
	public RespMailList build() {
		return builder.build();
	}
	
	/** **/
	public void addMails(PBMailInfo value){
		this.builder.addMails(value);
	}
	
	public void addAllMails(Collection<PBMailInfo> value){
		this.builder.addAllMails(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespMailList_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
