package com.cat.server.game.module.recycle.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto;
import com.cat.server.game.data.proto.PBRecycle.*;
import java.util.Collection;

/**
* PBResourceRecycleBuilder
* @author Jeremy
*/
public class PBResourceRecycleBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(PBResourceRecycleBuilder.class);
	
	private final PBResourceRecycle.Builder builder = PBResourceRecycle.newBuilder();
	
	public PBResourceRecycleBuilder() {}
	
	public static PBResourceRecycleBuilder newInstance() {
		return new PBResourceRecycleBuilder();
	}
	
	public PBResourceRecycle build() {
		return builder.build();
	}
	
	/** 唯一id**/
	public void setUniqueId(long value){
		this.builder.setUniqueId(value);
	}
	/** 回收时间**/
	public void setRecycleTime(long value){
		this.builder.setRecycleTime(value);
	}
	/** 资源id**/
	public void setConfigId(int value){
		this.builder.setConfigId(value);
	}
	
	@Override
	public int protocol() {
		return 0;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
