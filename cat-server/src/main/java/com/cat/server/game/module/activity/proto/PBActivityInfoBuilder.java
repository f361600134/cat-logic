package com.cat.server.game.module.activity.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
import com.google.protobuf.AbstractMessage;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBActivity.*;

/**
* PBActivityInfoBuilder
* @author Jeremy
*/
public class PBActivityInfoBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(PBActivityInfoBuilder.class);
	
	private final PBActivityInfo.Builder builder = PBActivityInfo.newBuilder();
	
	public PBActivityInfoBuilder() {}
	
	public static PBActivityInfoBuilder newInstance() {
		return new PBActivityInfoBuilder();
	}
	
	public PBActivityInfo build() {
		return builder.build();
	}
	
	/** 活动类型**/
	public void setType(int value){
		this.builder.setType(value);
	}
	/** 活动方案id**/
	public void setPlanId(int value){
		this.builder.setPlanId(value);
	}
	/** 活动状态(0结束1准备2进行3结算)**/
	public void setStatus(int value){
		this.builder.setStatus(value);
	}
	/** 活动阶段(部分活动进行期间有多个阶段)**/
	public void setStage(int value){
		this.builder.setStage(value);
	}
	/** 活动开始时间(秒)**/
	public void setBeginTime(int value){
		this.builder.setBeginTime(value);
	}
	/** 活动结算时间(秒)**/
	public void setSettleTime(int value){
		this.builder.setSettleTime(value);
	}
	/** 活动结束时间(秒)**/
	public void setCloseTime(int value){
		this.builder.setCloseTime(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
	
	@Override
	public AbstractMessage getBuilder() {
		return builder.build();
	}
}
