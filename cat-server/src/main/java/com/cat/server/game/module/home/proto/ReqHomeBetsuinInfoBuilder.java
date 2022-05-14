package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* ReqHomeBetsuinInfoBuilder
* @author Jeremy
*/
public class ReqHomeBetsuinInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqHomeBetsuinInfoBuilder.class);
	
	private final ReqHomeBetsuinInfo.Builder builder = ReqHomeBetsuinInfo.newBuilder();
	
	public ReqHomeBetsuinInfoBuilder() {}
	
	public static ReqHomeBetsuinInfoBuilder newInstance() {
		return new ReqHomeBetsuinInfoBuilder();
	}
	
	public ReqHomeBetsuinInfo build() {
		return builder.build();
	}
	
	/** 别院id**/
	public void setBetsuinId(int value){
		this.builder.setBetsuinId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqHomeBetsuinInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
