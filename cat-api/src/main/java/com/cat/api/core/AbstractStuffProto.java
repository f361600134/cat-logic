package com.cat.api.core;

import com.cat.net.network.base.AbstractProtocol;
import com.cat.net.util.SerializationUtil;

public abstract class AbstractStuffProto extends AbstractProtocol{
	
	public byte[] toBytes() {
		return SerializationUtil.serialize(this);
	}

}
