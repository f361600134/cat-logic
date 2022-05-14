package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* ReqHomeSchemeLayoutInfoBuilder
* @author Jeremy
*/
public class ReqHomeSchemeLayoutInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqHomeSchemeLayoutInfoBuilder.class);
	
	private final ReqHomeSchemeLayoutInfo.Builder builder = ReqHomeSchemeLayoutInfo.newBuilder();
	
	public ReqHomeSchemeLayoutInfoBuilder() {}
	
	public static ReqHomeSchemeLayoutInfoBuilder newInstance() {
		return new ReqHomeSchemeLayoutInfoBuilder();
	}
	
	public ReqHomeSchemeLayoutInfo build() {
		return builder.build();
	}
	
	
	@Override
	public int protocol() {
		return PBProtocol.ReqHomeSchemeLayoutInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
