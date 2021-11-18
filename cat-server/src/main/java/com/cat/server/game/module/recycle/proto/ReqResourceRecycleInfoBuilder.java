package com.cat.server.game.module.recycle.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBRecycle.*;
import java.util.Collection;

/**
* ReqResourceRecycleInfoBuilder
* @author Jeremy
*/
public class ReqResourceRecycleInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqResourceRecycleInfoBuilder.class);
	
	private final ReqResourceRecycleInfo.Builder builder = ReqResourceRecycleInfo.newBuilder();
	
	public ReqResourceRecycleInfoBuilder() {}
	
	public static ReqResourceRecycleInfoBuilder newInstance() {
		return new ReqResourceRecycleInfoBuilder();
	}
	
	public ReqResourceRecycleInfo build() {
		return builder.build();
	}
	
	
	@Override
	public int protocol() {
		return PBProtocol.ReqResourceRecycleInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
