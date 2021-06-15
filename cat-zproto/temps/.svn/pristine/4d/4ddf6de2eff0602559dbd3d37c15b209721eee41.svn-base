package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* ReqPlayerRandNameBuilder
* @author Jeremy
*/
public class ReqPlayerRandNameBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(ReqPlayerRandNameBuilder.class);
	
	private final ReqPlayerRandName.Builder builder = ReqPlayerRandName.newBuilder();
	
	public ReqPlayerRandNameBuilder() {}
	
	public static ReqPlayerRandNameBuilder newInstance() {
		return new ReqPlayerRandNameBuilder();
	}
	
	public ReqPlayerRandName build() {
		return builder.build();
	}
	
	
	@Override
	public int protocol() {
		return PBProtocol.ReqPlayerRandName_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
