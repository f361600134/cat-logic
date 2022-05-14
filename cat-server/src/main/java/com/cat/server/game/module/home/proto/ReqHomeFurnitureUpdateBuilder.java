package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* ReqHomeFurnitureUpdateBuilder
* @author Jeremy
*/
public class ReqHomeFurnitureUpdateBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqHomeFurnitureUpdateBuilder.class);
	
	private final ReqHomeFurnitureUpdate.Builder builder = ReqHomeFurnitureUpdate.newBuilder();
	
	public ReqHomeFurnitureUpdateBuilder() {}
	
	public static ReqHomeFurnitureUpdateBuilder newInstance() {
		return new ReqHomeFurnitureUpdateBuilder();
	}
	
	public ReqHomeFurnitureUpdate build() {
		return builder.build();
	}
	
	/** 家园家具最新的位置信息**/
	public void setFurnitureGeometry(PBHomeFurnitureGeometry value){
		this.builder.setFurnitureGeometry(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqHomeFurnitureUpdate_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
