package com.cat.battle.service.module.other;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBLogin;
import com.cat.server.game.data.proto.PBLogin.ReqLogin;
import com.google.protobuf.InvalidProtocolBufferException;

public class ReqLoginBuilder extends AbstractProtocol{

	private ReqLogin.Builder builder;

	public static ReqLoginBuilder newInstance(){
		return new ReqLoginBuilder();
	}
	
    public ReqLoginBuilder() {
        this.builder = PBLogin.ReqLogin.newBuilder();
    }

    public void setUserName(String value){
        this.builder.setUserName(value);
    }

    public void setServerId(int value){
        this.builder.setServerId(value);
    }
    
    public String getUserName(){
        return this.builder.getUserName();
    }
    
    public void build(byte[] bytes){
        try {
			this.builder.mergeFrom(bytes);
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
    }

    @Override
    public int protocol() {
        return PBProtocol.AckPlayerLogin_VALUE;
    }

    @Override
	public byte[] toBytes() {
		return builder.build().toByteArray();
	}
	
}
