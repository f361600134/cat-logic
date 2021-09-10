package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBItem.AckItemUse;
import com.cat.server.game.data.proto.PBItem.PBRewardInfo;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckItemUseResp
* @author Jeremy
*/
public class AckItemUseResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckItemUseResp.class);
	
	private final AckItemUse.Builder builder = AckItemUse.newBuilder();
	
	public AckItemUseResp() {}
	
	public static AckItemUseResp newInstance() {
		return new AckItemUseResp();
	}
	
	public AckItemUse build() {
		return builder.build();
	}
	
	/** **/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	/** 奖励**/
	public void addRewards(PBRewardInfo value){
		this.builder.addRewards(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckItemUse_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
