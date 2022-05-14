package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* RespHomeSchemeLayoutInfoBuilder
* @author Jeremy
*/
public class RespHomeSchemeLayoutInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespHomeSchemeLayoutInfoBuilder.class);
	
	private final RespHomeSchemeLayoutInfo.Builder builder = RespHomeSchemeLayoutInfo.newBuilder();
	
	public RespHomeSchemeLayoutInfoBuilder() {}
	
	public static RespHomeSchemeLayoutInfoBuilder newInstance() {
		return new RespHomeSchemeLayoutInfoBuilder();
	}
	
	public RespHomeSchemeLayoutInfo build() {
		return builder.build();
	}
	
	/** 布局方案列表**/
	public void addLayoutSchemes(PBHomeLayoutScheme value){
		this.builder.addLayoutSchemes(value);
	}
	
	public void addAllLayoutSchemes(Collection<PBHomeLayoutScheme> value){
		this.builder.addAllLayoutSchemes(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespHomeSchemeLayoutInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
