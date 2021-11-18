package com.cat.server.game.module.recycle.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBRecycle.*;
import java.util.Collection;

/**
* RespResourceRecycleInfoBuilder
* @author Jeremy
*/
public class RespResourceRecycleInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespResourceRecycleInfoBuilder.class);
	
	private final RespResourceRecycleInfo.Builder builder = RespResourceRecycleInfo.newBuilder();
	
	public RespResourceRecycleInfoBuilder() {}
	
	public static RespResourceRecycleInfoBuilder newInstance() {
		return new RespResourceRecycleInfoBuilder();
	}
	
	public RespResourceRecycleInfo build() {
		return builder.build();
	}
	
	/** 资源回收对象列表**/
	public void addResourceCycleInfo(PBResourceRecycle value){
		this.builder.addResourceCycleInfo(value);
	}
	
	public void addAllResourceCycleInfo(Collection<PBResourceRecycle> value){
		this.builder.addAllResourceCycleInfo(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespResourceRecycleInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
