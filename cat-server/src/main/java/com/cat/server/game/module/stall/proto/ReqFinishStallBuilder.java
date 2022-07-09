package com.cat.server.game.module.stall.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessage;
import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBStall.*;
import java.util.Collection;

/**
* ReqFinishStallBuilder
* @author Jeremy
*/
public class ReqFinishStallBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqFinishStallBuilder.class);
	
	private final ReqFinishStall.Builder builder = ReqFinishStall.newBuilder();
	
	public ReqFinishStallBuilder() {}
	
	public static ReqFinishStallBuilder newInstance() {
		return new ReqFinishStallBuilder();
	}
	
	public ReqFinishStall build() {
		return builder.build();
	}
	
	
	@Override
	public int protocol() {
		return PBProtocol.ReqFinishStall_VALUE;
	}
	
	@Override
	public AbstractMessage getBuilder() {
		return builder.build();
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
