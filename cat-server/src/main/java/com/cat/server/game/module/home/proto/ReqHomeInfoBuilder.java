package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* ReqHomeInfoBuilder
* @author Jeremy
*/
public class ReqHomeInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqHomeInfoBuilder.class);
	
	private final ReqHomeInfo.Builder builder = ReqHomeInfo.newBuilder();
	
	public ReqHomeInfoBuilder() {}
	
	public static ReqHomeInfoBuilder newInstance() {
		return new ReqHomeInfoBuilder();
	}
	
	public ReqHomeInfo build() {
		return builder.build();
	}
	
	/** 地图id**/
	public void setMapId(int value){
		this.builder.setMapId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqHomeInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
