package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.PBItem.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* PBPlayerInfoBuilder
* @author Jeremy
*/
public class PBPlayerInfoBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(PBPlayerInfoBuilder.class);
	
	private final PBPlayerInfo.Builder builder = PBPlayerInfo.newBuilder();
	
	public PBPlayerInfoBuilder() {
	}
	
	public static PBPlayerInfoBuilder newInstance() {
		return new PBPlayerInfoBuilder();
	}
	
	public PBPlayerInfo build() {
		return builder.build();
	}
	
	/** 账号**/
	public void setAccountName(String value){
		this.builder.setAccountName(value);
	}
	/** 角色id**/
	public void setPlayerId(long value){
		this.builder.setPlayerId(value);
	}
	/** 角色昵称**/
	public void setNickName(String value){
		this.builder.setNickName(value);
	}
	/** 战力**/
	public void setPower(int value){
		this.builder.setPower(value);
	}
	/** 渠道**/
	public void setChannel(int value){
		this.builder.setChannel(value);
	}
	/** 等级**/
	public void setLevel(int value){
		this.builder.setLevel(value);
	}
	/** 经验**/
	public void setExp(int value){
		this.builder.setExp(value);
	}
	/** vip等级**/
	public void setVip(int value){
		this.builder.setVip(value);
	}
	/** VIP经验**/
	public void setVipExp(int value){
		this.builder.setVipExp(value);
	}
	/** 玩家资源信息**/
	public void addProperties(PBPairInfo value){
		this.builder.addProperties(value);
	}
	/** 玩家属性信息**/
	public void addAttrs(PBPairInfo value){
		this.builder.addAttrs(value);
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
