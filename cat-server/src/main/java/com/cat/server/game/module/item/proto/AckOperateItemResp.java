package com.cat.server.game.module.item.proto;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBBag;
import com.cat.server.game.data.proto.PBDefine;

import com.google.protobuf.AbstractMessageLite.Builder;

public class AckOperateItemResp implements IProtocol {
	
	private PBBag.AckOperateItem.Builder builder;
	
	public AckOperateItemResp() {
		this.builder = PBBag.AckOperateItem.newBuilder();
	}
	
	public static AckOperateItemResp newInstance() {
		return new AckOperateItemResp();
	}
	
	public AckOperateItemResp setCode(int value) {
		this.builder.setCode(value);
		return this;
	}

	@Override
	public short protocol() {
		return PBDefine.PBProtocol.AckOperateItem_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}

}
