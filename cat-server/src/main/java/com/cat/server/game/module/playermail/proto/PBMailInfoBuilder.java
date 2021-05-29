package com.cat.server.game.module.playermail.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;
import com.cat.server.game.data.proto.PBItem.*;
import com.cat.server.game.data.proto.PBMail.*;

/**
* PBMailInfoBuilder
* @author Jeremy
*/
public class PBMailInfoBuilder implements IProtocol {

	private static final Logger log = LoggerFactory.getLogger(PBMailInfoBuilder.class);
	
	private final PBMailInfo.Builder builder = PBMailInfo.newBuilder();
	
	public PBMailInfoBuilder() {
	}
	
	public static PBMailInfoBuilder newInstance() {
		return new PBMailInfoBuilder();
	}
	
	public PBMailInfo build() {
		return builder.build();
	}
	
	/** Mail id**/
	public void setMailId(long value){
		this.builder.setMailId(value);
	}
	/** 标题**/
	public void setTitle(String value){
		this.builder.setTitle(value);
	}
	/** 聊天内容**/
	public void setContent(String value){
		this.builder.setContent(value);
	}
	/** 附件**/
	public void addRewards(PBRewardInfo value){
		this.builder.addRewards(value);
	}
	/** 0=未读取;1=已读取**/
	public void setState(int value){
		this.builder.setState(value);
	}
	/** yyyy-MM-dd**/
	public void setDate(String value){
		this.builder.setDate(value);
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
