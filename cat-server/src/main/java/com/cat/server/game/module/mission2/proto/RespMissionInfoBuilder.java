package com.cat.server.game.module.mission2.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBMission.*;
import java.util.Collection;

/**
* RespMissionInfoBuilder
* @author Jeremy
*/
public class RespMissionInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespMissionInfoBuilder.class);
	
	private final RespMissionInfo.Builder builder = RespMissionInfo.newBuilder();
	
	public RespMissionInfoBuilder() {}
	
	public static RespMissionInfoBuilder newInstance() {
		return new RespMissionInfoBuilder();
	}
	
	public RespMissionInfo build() {
		return builder.build();
	}
	
	/** 任务目标类型**/
	public void setType(int value){
		this.builder.setType(value);
	}
	/** 任务列表数据**/
	public void addMissionInfos(PBMissionInfo value){
		this.builder.addMissionInfos(value);
	}
	
	public void addAllMissionInfos(Collection<PBMissionInfo> value){
		this.builder.addAllMissionInfos(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespMissionInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
