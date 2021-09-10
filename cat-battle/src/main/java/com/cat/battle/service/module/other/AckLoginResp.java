package com.cat.battle.service.module.other;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.server.game.data.proto.PBDefine.PBProtocol;
import com.cat.server.game.data.proto.PBLogin;
import com.cat.server.game.data.proto.PBLogin.AckLogin;
import com.google.protobuf.AbstractMessageLite.Builder;

public class AckLoginResp extends AbstractProtocol{

	private AckLogin.Builder builder;

	public static AckLoginResp newInstance(){
		return new AckLoginResp();
	}
	
    public AckLoginResp() {
        this.builder = PBLogin.AckLogin.newBuilder();
    }

    public void setCode(int code){
        this.builder.setCode(code);
    }

    public void setStatus(int status){
        this.builder.setStatus(status);
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
