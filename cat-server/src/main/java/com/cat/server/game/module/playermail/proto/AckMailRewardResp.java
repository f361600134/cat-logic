package com.cat.server.game.module.playermail.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBItem.PBPairInfo;
import com.cat.server.game.data.proto.PBMail.AckMailReward;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckMailRewardResp
* @author Jeremy
*/
public class AckMailRewardResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckMailRewardResp.class);
	
	private final AckMailReward.Builder builder = AckMailReward.newBuilder();
	
	public AckMailRewardResp() {}
	
	public static AckMailRewardResp newInstance() {
		return new AckMailRewardResp();
	}
	
	public AckMailReward build() {
		return builder.build();
	}
	
	/** 错误码,非0表示弹提示**/
	public void setCode(int value){
		this.builder.setCode(value);
	}
	/** 奖励**/
	public void addRewards(PBPairInfo value){
		this.builder.addRewards(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckMailReward_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
