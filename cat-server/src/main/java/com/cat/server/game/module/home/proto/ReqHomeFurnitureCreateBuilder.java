package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* ReqHomeFurnitureCreateBuilder
* @author Jeremy
*/
public class ReqHomeFurnitureCreateBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqHomeFurnitureCreateBuilder.class);
	
	private final ReqHomeFurnitureCreate.Builder builder = ReqHomeFurnitureCreate.newBuilder();
	
	public ReqHomeFurnitureCreateBuilder() {}
	
	public static ReqHomeFurnitureCreateBuilder newInstance() {
		return new ReqHomeFurnitureCreateBuilder();
	}
	
	public ReqHomeFurnitureCreate build() {
		return builder.build();
	}
	
	/** 布局家具资源id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqHomeFurnitureCreate_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
