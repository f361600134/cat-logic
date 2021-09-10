package com.cat.server.game.module.player.proto;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBPlayer.PBPlayerProfile;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* PBPlayerProfileBuilder
* @author Jeremy
*/
public class PBPlayerProfileBuilder extends AbstractProtocol {

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
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
