package com.cat.server.game.module.home.proto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.*;
//import com.cat.server.game.data.proto.*;
import com.cat.server.game.data.proto.PBHome.*;
import java.util.Collection;

/**
* RespHomeBetsuinInfoBuilder
* @author Jeremy
*/
public class RespHomeBetsuinInfoBuilder extends AbstractProtocol {

	//private static final Logger log = LoggerFactory.getLogger(RespHomeBetsuinInfoBuilder.class);
	
	private final RespHomeBetsuinInfo.Builder builder = RespHomeBetsuinInfo.newBuilder();
	
	public RespHomeBetsuinInfoBuilder() {}
	
	public static RespHomeBetsuinInfoBuilder newInstance() {
		return new RespHomeBetsuinInfoBuilder();
	}
	
	public RespHomeBetsuinInfo build() {
		return builder.build();
	}
	
	/** 别院信息**/
	public void setBetsuin(PBHomeBetsuin value){
		this.builder.setBetsuin(value);
	}
	/** 错误码**/
	public void setErrorCode(int value){
		this.builder.setErrorCode(value);
	}
	
	@Override
	public int protocol() {
		return PBProtocol.RespHomeBetsuinInfo_VALUE;
	}

	@Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
}
