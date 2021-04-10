package com.cat.server.game.module.item.proto;

import java.util.Collection;

import com.cat.net.network.base.IProtocol;
import com.cat.server.game.data.proto.PBBag;
import com.cat.server.game.data.proto.PBDefine;
import com.cat.server.game.module.item.domain.IItem;
import com.cat.server.game.module.item.domain.Item;

import com.google.protobuf.AbstractMessageLite.Builder;

public class AckBagListResp implements IProtocol {

	private PBBag.AckBagList.Builder builder;

	public static AckBagListResp newInstance(){
		return new AckBagListResp();
	}
	
    public AckBagListResp() {
        this.builder = PBBag.AckBagList.newBuilder();
    }
    
    public void addItem(Collection<Item> items) {
		for (IItem item : items) {
            PBBag.ItemInfo itemInfo = item.toProto();
			if (itemInfo == null) 
				continue;
			builder.addBags(itemInfo);
		}
    }

	@Override
	public short protocol() {
		 return PBDefine.PBProtocol.AckUpdateBagList_VALUE;
	}

	@Override
	public Builder<?, ?> getBuilder() {
		return builder;
	}

}
