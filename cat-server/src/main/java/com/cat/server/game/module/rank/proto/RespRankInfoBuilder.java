package com.cat.server.game.module.rank.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBRank.*;

/**
* RespRankInfoBuilder
* @author Jeremy
*/
public class RespRankInfoBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespRankInfoBuilder.class);
	
	private final RespRankInfo.Builder builder = RespRankInfo.newBuilder();
	
	public RespRankInfoBuilder() {}
	
	public static RespRankInfoBuilder newInstance() {
		return new RespRankInfoBuilder();
	}
	
	public RespRankInfo build() {
		return builder.build();
	}
	
	/** 排行榜类型**/
	public void setRankType(int value){
		this.builder.setRankType(value);
	}
	/** 排行榜列表**/
	public void addRankDtos(PBRankDto value){
		this.builder.addRankDtos(value);
	}
	/** 自己的排行信息**/
	public void setSelfRankDto(PBRankDto value){
		this.builder.setSelfRankDto(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespRankInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
