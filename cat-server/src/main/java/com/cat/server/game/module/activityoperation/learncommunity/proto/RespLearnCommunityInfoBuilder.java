package com.cat.server.game.module.activityoperation.learncommunity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBLearnCommunity.*;

/**
* RespLearnCommunityInfoBuilder
* @author Jeremy
*/
public class RespLearnCommunityInfoBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespLearnCommunityInfoBuilder.class);
	
	private final RespLearnCommunityInfo.Builder builder = RespLearnCommunityInfo.newBuilder();
	
	public RespLearnCommunityInfoBuilder() {}
	
	public static RespLearnCommunityInfoBuilder newInstance() {
		return new RespLearnCommunityInfoBuilder();
	}
	
	public RespLearnCommunityInfo build() {
		return builder.build();
	}
	
	/** 研习等级**/
	public void setLevel(int value){
		this.builder.setLevel(value);
	}
	/** 研习经验,大于0则表示当前经验,小于0表示已满.**/
	public void setExp(int value){
		this.builder.setExp(value);
	}
	/** 是否购买了专属研习**/
	public void setExclusive(boolean value){
		this.builder.setExclusive(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespLearnCommunityInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
