package com.cat.server.game.module.mission2.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;
import java.util.Collection;

/**
* ReqMissionInfoBuilder
* @author Jeremy
*/
public class ReqMissionInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqMissionInfoBuilder.class);
	
	private final ReqMissionInfo.Builder builder = ReqMissionInfo.newBuilder();
	
	public ReqMissionInfoBuilder() {}
	
	public static ReqMissionInfoBuilder newInstance() {
		return new ReqMissionInfoBuilder();
	}
	
	public ReqMissionInfo build() {
		return builder.build();
	}
	
	/** 任务类型id**/
	public void addMissionId(int value){
		this.builder.addMissionId(value);
	}
	
	public void addAllMissionId(Collection<java.lang.Integer> value){
		this.builder.addAllMissionId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqMissionInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
