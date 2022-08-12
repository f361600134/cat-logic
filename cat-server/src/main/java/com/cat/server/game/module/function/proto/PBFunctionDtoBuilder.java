package com.cat.server.game.module.function.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.AbstractMessage;
import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBFunction.*;
import java.util.Collection;

/**
* PBFunctionDtoBuilder
* @author Jeremy
*/
public class PBFunctionDtoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBFunctionDtoBuilder.class);
	
	private final PBFunctionDto.Builder builder = PBFunctionDto.newBuilder();
	
	public PBFunctionDtoBuilder() {}
	
	public static PBFunctionDtoBuilder newInstance() {
		return new PBFunctionDtoBuilder();
	}
	
	public PBFunctionDto build() {
		return builder.build();
	}
	
	/** 已开启的功能模块id**/
	public void addFunctionIds(int value){
		this.builder.addFunctionIds(value);
	}
	
	public void addAllFunctionIds(Collection<java.lang.Integer> value){
		this.builder.addAllFunctionIds(value);
	}
	/** 强行关闭的功能id**/
	public void addForceCloseIds(int value){
		this.builder.addForceCloseIds(value);
	}
	
	public void addAllForceCloseIds(Collection<java.lang.Integer> value){
		this.builder.addAllForceCloseIds(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}
	
	@Override
	public AbstractMessage getBuilder() {
		return builder.build();
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
