package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* RespHomeFurnitureCreateBuilder
* @author Jeremy
*/
public class RespHomeFurnitureCreateBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespHomeFurnitureCreateBuilder.class);
	
	private final RespHomeFurnitureCreate.Builder builder = RespHomeFurnitureCreate.newBuilder();
	
	public RespHomeFurnitureCreateBuilder() {}
	
	public static RespHomeFurnitureCreateBuilder newInstance() {
		return new RespHomeFurnitureCreateBuilder();
	}
	
	public RespHomeFurnitureCreate build() {
		return builder.build();
	}
	
	/** 响应创建布局家具资源**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	/** 第一次创建的家具没有位置**/
	public void setFurnitureGeometry(PBHomeFurnitureGeometry value){
		this.builder.setFurnitureGeometry(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespHomeFurnitureCreate_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
