package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBItem.PBRewardInfo;
import com.cat.server.game.data.proto.PBItem.RespRewards;

/**
* RespRewardsBuilder
* @author Jeremy
*/
public class RespRewardsBuilder extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(RespRewardsBuilder.class);
	
	private final RespRewards.Builder builder = RespRewards.newBuilder();
	
	public RespRewardsBuilder() {}
	
	public static RespRewardsBuilder newInstance() {
		return new RespRewardsBuilder();
	}
	
	public RespRewards build() {
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
		return PBProtocol.RespRewards_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
