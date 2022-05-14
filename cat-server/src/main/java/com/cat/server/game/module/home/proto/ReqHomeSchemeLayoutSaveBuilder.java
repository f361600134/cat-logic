package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* ReqHomeSchemeLayoutSaveBuilder
* @author Jeremy
*/
public class ReqHomeSchemeLayoutSaveBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqHomeSchemeLayoutSaveBuilder.class);
	
	private final ReqHomeSchemeLayoutSave.Builder builder = ReqHomeSchemeLayoutSave.newBuilder();
	
	public ReqHomeSchemeLayoutSaveBuilder() {}
	
	public static ReqHomeSchemeLayoutSaveBuilder newInstance() {
		return new ReqHomeSchemeLayoutSaveBuilder();
	}
	
	public ReqHomeSchemeLayoutSave build() {
		return builder.build();
	}
	
	/** 地图id**/
	public void setMapId(int value){
		this.builder.setMapId(value);
	}
	/** 方案id**/
	public void setPlanId(int value){
		this.builder.setPlanId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqHomeSchemeLayoutSave_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
