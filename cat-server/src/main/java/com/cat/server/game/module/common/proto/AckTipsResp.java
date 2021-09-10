package com.cat.server.game.module.common.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBCommon.AckTips;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;

/**
* AckTipsResp
* @author Jeremy
*/
public class AckTipsResp extends AbstractProtocol {

	private static final Logger log = LoggerFactory.getLogger(AckTipsResp.class);
	
	private final AckTips.Builder builder = AckTips.newBuilder();
	
	public AckTipsResp() {}
	
	public static AckTipsResp newInstance() {
		return new AckTipsResp();
	}
	
	public AckTips build() {
		return builder.build();
	}
	
	/** **/
	public void setTipsId(int value){
		this.builder.setTipsId(value);
	}
	/** **/
	public void addParams(int value){
		this.builder.addParams(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.AckTips_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
