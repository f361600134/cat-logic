package com.cat.server.game.module.activityoperation.learncommunity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBLearnCommunity.*;

/**
* ReqLearnCommunityInfoBuilder
* @author Jeremy
*/
public class ReqLearnCommunityInfoBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(ReqLearnCommunityInfoBuilder.class);
	
	private final ReqLearnCommunityInfo.Builder builder = ReqLearnCommunityInfo.newBuilder();
	
	public ReqLearnCommunityInfoBuilder() {}
	
	public static ReqLearnCommunityInfoBuilder newInstance() {
		return new ReqLearnCommunityInfoBuilder();
	}
	
	public ReqLearnCommunityInfo build() {
		return builder.build();
	}
	
	
	@Override
	public int protocol() {
		return PBProtocol.ReqLearnCommunityInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
