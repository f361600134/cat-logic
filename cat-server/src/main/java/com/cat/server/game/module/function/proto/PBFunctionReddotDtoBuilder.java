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
* PBFunctionReddotDtoBuilder
* @author Jeremy
*/
public class PBFunctionReddotDtoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBFunctionReddotDtoBuilder.class);
	
	private final PBFunctionReddotDto.Builder builder = PBFunctionReddotDto.newBuilder();
	
	public PBFunctionReddotDtoBuilder() {}
	
	public static PBFunctionReddotDtoBuilder newInstance() {
		return new PBFunctionReddotDtoBuilder();
	}
	
	public PBFunctionReddotDto build() {
		return builder.build();
	}
	
	/** 红点条件id**/
	public void setReddotId(int value){
		this.builder.setReddotId(value);
	}
	/** 红点值,大于0表示有红点,也可以表示红点数量**/
	public void setNumber(int value){
		this.builder.setNumber(value);
	}
	/** 扩展数据,用于需要标记的更加具体的红点,如新获得称号,头像框等**/
	public void addExtendValues(int value){
		this.builder.addExtendValues(value);
	}
	
	public void addAllExtendValues(Collection<java.lang.Integer> value){
		this.builder.addAllExtendValues(value);
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
