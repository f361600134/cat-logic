package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* PBPlayerProfileBuilder
* @author Jeremy
*/
public class PBPlayerProfileBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(PBPlayerProfileBuilder.class);
	
	private final PBPlayerProfile.Builder builder = PBPlayerProfile.newBuilder();
	
	public PBPlayerProfileBuilder() {
	}
	
	public static PBPlayerProfileBuilder newInstance() {
		return new PBPlayerProfileBuilder();
	}
	
	public PBPlayerProfile build() {
		return builder.build();
	}
	
	/** 角色id**/
	public void setPlayerId(int value){
		this.builder.setPlayerId(value);
	}
	/** 角色昵称**/
	public void setNickName(String value){
		this.builder.setNickName(value);
	}
	/** 等级**/
	public void setLevel(int value){
		this.builder.setLevel(value);
	}
	/** 战力**/
	public void setPower(int value){
		this.builder.setPower(value);
	}
	/** 家族名称**/
	public void setFamilyName(String value){
		this.builder.setFamilyName(value);
	}
	/** vip等级**/
	public void setVip(int value){
		this.builder.setVip(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
