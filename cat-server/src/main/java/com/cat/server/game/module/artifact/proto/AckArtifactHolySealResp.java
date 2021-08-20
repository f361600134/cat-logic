package com.cat.server.game.module.artifact.proto;

import com.cat.net.network.base.IProtocol;
import com.google.protobuf.AbstractMessageLite.Builder;

/**
* AckArtifactHolySealResp
* @author Jeremy
*/
public class AckArtifactHolySealResp implements IProtocol {

//	private static final Logger log = LoggerFactory.getLogger(AckArtifactHolySealResp.class);
//	
//	private PBArtifact.AckArtifactHolySeal.Builder builder;
//	
//	public AckArtifactHolySealResp() {
//		this.builder = PBArtifact.AckArtifactHolySeal.newBuilder();
//	}
//	
//	public static AckArtifactHolySealResp newInstance() {
//		return new AckArtifactHolySealResp();
//	}
//	
//	public PBArtifact.AckArtifactHolySeal build() {
//		return builder.build();
//	}
//	
//	/** 错误码**/
//	public void setCode(int value){
//		this.builder.setCode(value);
//	}
//	
	@Override
	public int protocol() {
//		return PBDefine.PBProtocol.AckArtifactHolySeal_VALUE;
		return 0;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return null;
	}
}
