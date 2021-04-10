package com.cat.server.game.module.item.proto;

import java.util.Collection;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBBag;
import com.cat.server.game.data.proto.PBDefine;

import com.google.protobuf.AbstractMessageLite.Builder;
import com.google.protobuf.Message;

public class AckDeleteBagResp implements IProtocol {

	private PBBag.AckDeleteBagList.Builder builder;

	public static AckDeleteBagResp newInstance() {
		return new AckDeleteBagResp();
	}

	public AckDeleteBagResp() {
		this.builder = PBBag.AckDeleteBagList.newBuilder();
	}
	
	public void addItemId(Long itemId) {
		builder.addItemIds(itemId);
	}

	public void addAllItemId(Collection<Long> itemIds) {
		builder.addAllItemIds(itemIds);
	}

	@Override
	public short protocol() {
		return PBDefine.PBProtocol.AckDeleteBagList_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}

}
