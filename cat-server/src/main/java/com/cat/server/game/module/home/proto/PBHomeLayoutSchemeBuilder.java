package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* PBHomeLayoutSchemeBuilder
* @author Jeremy
*/
public class PBHomeLayoutSchemeBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBHomeLayoutSchemeBuilder.class);
	
	private final PBHomeLayoutScheme.Builder builder = PBHomeLayoutScheme.newBuilder();
	
	public PBHomeLayoutSchemeBuilder() {}
	
	public static PBHomeLayoutSchemeBuilder newInstance() {
		return new PBHomeLayoutSchemeBuilder();
	}
	
	public PBHomeLayoutScheme build() {
		return builder.build();
	}
	
	/** 方案id**/
	public void setPlanId(int value){
		this.builder.setPlanId(value);
	}
	/** 布局家具方位信息**/
	public void addFurnitureGeometries(PBHomeFurnitureGeometry value){
		this.builder.addFurnitureGeometries(value);
	}
	
	public void addAllFurnitureGeometries(Collection<PBHomeFurnitureGeometry> value){
		this.builder.addAllFurnitureGeometries(value);
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
