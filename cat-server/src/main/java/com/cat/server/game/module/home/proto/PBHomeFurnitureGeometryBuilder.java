package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* PBHomeFurnitureGeometryBuilder
* @author Jeremy
*/
public class PBHomeFurnitureGeometryBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBHomeFurnitureGeometryBuilder.class);
	
	private final PBHomeFurnitureGeometry.Builder builder = PBHomeFurnitureGeometry.newBuilder();
	
	public PBHomeFurnitureGeometryBuilder() {}
	
	public static PBHomeFurnitureGeometryBuilder newInstance() {
		return new PBHomeFurnitureGeometryBuilder();
	}
	
	public PBHomeFurnitureGeometry build() {
		return builder.build();
	}
	
	/** 家具对象**/
	public void setFurniture(PBHomeFurniture value){
		this.builder.setFurniture(value);
	}
	/** 矩形方位**/
	public void setRectangle(PBHomeRectangle value){
		this.builder.setRectangle(value);
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
