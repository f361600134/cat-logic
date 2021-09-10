package com.cat.server.game.module.item.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBItem.AckItemDelete;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckItemDeleteResp
* @author Jeremy
*/
public class AckItemDeleteResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckItemDeleteResp.class);
	
	private final AckItemDelete.Builder builder = AckItemDelete.newBuilder();
	
	public AckItemDeleteResp() {}
	
	public static AckItemDeleteResp newInstance() {
		return new AckItemDeleteResp();
	}
	
	public AckItemDelete build() {
		return builder.build();
	}
	
	/** 奖励**/
	public void addIds(long value){
		this.builder.addIds(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckItemDelete_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
