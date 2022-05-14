package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* PBHomeRectangleBuilder
* @author Jeremy
*/
public class PBHomeRectangleBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBHomeRectangleBuilder.class);
	
	private final PBHomeRectangle.Builder builder = PBHomeRectangle.newBuilder();
	
	public PBHomeRectangleBuilder() {}
	
	public static PBHomeRectangleBuilder newInstance() {
		return new PBHomeRectangleBuilder();
	}
	
	public PBHomeRectangle build() {
		return builder.build();
	}
	
	/** 相对于坐标原点 x 坐标**/
	public void setX(int value){
		this.builder.setX(value);
	}
	/** 相对于坐标原点 y 坐标**/
	public void setY(int value){
		this.builder.setY(value);
	}
	/** 是否方向反转(相对于配置方向)**/
	public void setReverse(boolean value){
		this.builder.setReverse(value);
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
