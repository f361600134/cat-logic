package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* PBHomeBetsuinBuilder
* @author Jeremy
*/
public class PBHomeBetsuinBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBHomeBetsuinBuilder.class);
	
	private final PBHomeBetsuin.Builder builder = PBHomeBetsuin.newBuilder();
	
	public PBHomeBetsuinBuilder() {}
	
	public static PBHomeBetsuinBuilder newInstance() {
		return new PBHomeBetsuinBuilder();
	}
	
	public PBHomeBetsuin build() {
		return builder.build();
	}
	
	/** 别院id**/
	public void setBetsuinId(int value){
		this.builder.setBetsuinId(value);
	}
	/** 家具位置信息**/
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
