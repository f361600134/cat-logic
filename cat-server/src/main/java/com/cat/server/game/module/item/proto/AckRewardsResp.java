package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBItem.AckRewards;
import com.cat.server.game.data.proto.PBItem.PBRewardInfo;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckRewardsResp
* @author Jeremy
*/
public class AckRewardsResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckRewardsResp.class);
	
	private final AckRewards.Builder builder = AckRewards.newBuilder();
	
	public AckRewardsResp() {}
	
	public static AckRewardsResp newInstance() {
		return new AckRewardsResp();
	}
	
	public AckRewards build() {
		return builder.build();
	}
	
	/** 分解后奖励**/
	public void addRewards(PBRewardInfo value){
		this.builder.addRewards(value);
	}
	/** 弹窗类型, 1:弹窗,2:条状飘窗**/
	public void setType(int value){
		this.builder.setType(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckRewards_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}
}
