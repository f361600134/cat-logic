package com.cat.server.game.module.mission2.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;
import java.util.Collection;

/**
* RespMissionDelBuilder
* @author Jeremy
*/
public class RespMissionDelBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespMissionDelBuilder.class);
	
	private final RespMissionDel.Builder builder = RespMissionDel.newBuilder();
	
	public RespMissionDelBuilder() {}
	
	public static RespMissionDelBuilder newInstance() {
		return new RespMissionDelBuilder();
	}
	
	public RespMissionDel build() {
		return builder.build();
	}
	
	/** 要删除的任务id**/
	public void addId(int value){
		this.builder.addId(value);
	}
	
	public void addAllId(Collection<java.lang.Integer> value){
		this.builder.addAllId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespMissionDel_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
