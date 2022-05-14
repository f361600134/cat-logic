package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* ReqHomeFurnitureRemoveBuilder
* @author Jeremy
*/
public class ReqHomeFurnitureRemoveBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(ReqHomeFurnitureRemoveBuilder.class);
	
	private final ReqHomeFurnitureRemove.Builder builder = ReqHomeFurnitureRemove.newBuilder();
	
	public ReqHomeFurnitureRemoveBuilder() {}
	
	public static ReqHomeFurnitureRemoveBuilder newInstance() {
		return new ReqHomeFurnitureRemoveBuilder();
	}
	
	public ReqHomeFurnitureRemove build() {
		return builder.build();
	}
	
	/** 布局家具唯一id**/
	public void setUniqueId(long value){
		this.builder.setUniqueId(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqHomeFurnitureRemove_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
