package com.cat.server.game.module.playermail.proto;

import java.util.Collection;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBCommon.PBPairInfo;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBMail.RespMailReward;

/**
* RespMailRewardBuilder
* @author Jeremy
*/
public class RespMailRewardBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespMailRewardBuilder.class);
	
	private final RespMailReward.Builder builder = RespMailReward.newBuilder();
	
	public RespMailRewardBuilder() {}
	
	public static RespMailRewardBuilder newInstance() {
		return new RespMailRewardBuilder();
	}
	
	public RespMailReward build() {
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
	
	public void addAllRewards(Collection<PBPairInfo> value){
		this.builder.addAllRewards(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespPlayerMailReward_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
