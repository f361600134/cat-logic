package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* PBHomeFurnitureBuilder
* @author Jeremy
*/
public class PBHomeFurnitureBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBHomeFurnitureBuilder.class);
	
	private final PBHomeFurniture.Builder builder = PBHomeFurniture.newBuilder();
	
	public PBHomeFurnitureBuilder() {}
	
	public static PBHomeFurnitureBuilder newInstance() {
		return new PBHomeFurnitureBuilder();
	}
	
	public PBHomeFurniture build() {
		return builder.build();
	}
	
	/** 布局家具**/
	public void setUniqueId(long value){
		this.builder.setUniqueId(value);
	}
	/** 家具配置id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
