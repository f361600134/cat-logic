package com.cat.server.game.module.player.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.game.data.proto.PBDefine.*;
import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBPlayer.*;

/**
* ReqPlayerCreateRoleBuilder
* @author Jeremy
*/
public class ReqPlayerCreateRoleBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(ReqPlayerCreateRoleBuilder.class);
	
	private final ReqPlayerCreateRole.Builder builder = ReqPlayerCreateRole.newBuilder();
	
	public ReqPlayerCreateRoleBuilder() {}
	
	public static ReqPlayerCreateRoleBuilder newInstance() {
		return new ReqPlayerCreateRoleBuilder();
	}
	
	public ReqPlayerCreateRole build() {
		return builder.build();
	}
	
	/** 角色类型(策划表里的ID,不是男女)**/
	public void setRoleType(int value){
		this.builder.setRoleType(value);
	}
	/** 昵称**/
	public void setNickName(String value){
		this.builder.setNickName(value);
	}
	/** 玩家输入账号**/
	public void setInputName(String value){
		this.builder.setInputName(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.ReqPlayerCreateRole_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
