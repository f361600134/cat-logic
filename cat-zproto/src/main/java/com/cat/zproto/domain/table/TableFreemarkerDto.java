package com.cat.zproto.domain.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;

/**
 * 协议
 * @auth Jeremy
 * @date 2021年6月6日下午8:38:12
 */
public class TableFreemarkerDto {
	
	private final TableEntity entity;
	
	private final ProtocolObject protocolObj;
	
	private final List<ProtocolStructure> protoReqStructList = new ArrayList<>();
	
	private final Map<String, ProtocolStructure> protoAckStructMap = new HashMap<>();
	
	public TableFreemarkerDto(TableEntity entity, ProtocolObject protocolObj) {
		this.entity = entity;
		this.protocolObj = protocolObj;
	}
	
	public TableEntity getEntity() {
		return entity;
	}

	public ProtocolObject getProtocolObj() {
		return protocolObj;
	}

	public List<ProtocolStructure> getProtoReqStructList() {
		return protoReqStructList;
	}

	public Map<String, ProtocolStructure> getProtoAckStructMap() {
		return protoAckStructMap;
	}
	
}
