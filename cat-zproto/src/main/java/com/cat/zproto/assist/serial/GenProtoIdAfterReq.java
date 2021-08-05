package com.cat.zproto.assist.serial;

import java.util.*;

import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.domain.proto.ProtocolDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.zproto.domain.proto.ProtocolConstant;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;

/**
 * 协议id生成排序方式<br>
 * 1.成对排序, 请求/响应的方式有序生成协议号, <br>
 * 	ReqXXX 1001  AckXXX 1002<br>
 *  ReqYYY 1003  AckYYY 1004<br>
 * 2.先请求, 后响应, 间隔100<br>
 *  ReqXXX 1001 ReqYYY 1002<br>
 *  AckXXX 1003 AckYYY 1004<br>
 */
@Component
public class GenProtoIdAfterReq extends AbstractGenProtoId {
	
	@Override
	public int type() {
		return AFTER_REQ;
	}

	@Override
	public Map<String, Integer> generater(int moduleId, List<ProtocolStructure> reqs, Map<String, ProtocolStructure> resps) {
		Map<String, Integer> result = new LinkedHashMap<>();

		int interval = setting.getProto().getPtoroCoefficient();
		int protoId = moduleId * interval + 100;
		for (ProtocolStructure reqStruct : reqs) {
			String reqProtoName = reqStruct.getName();
			result.put(reqProtoName, protoId+=1);
		}

		protoId = moduleId * interval + 200;
		for (ProtocolStructure respStruct : resps.values()) {
			String respProtoName = respStruct.getName();
			result.put(respProtoName, protoId+=1);
		}
		return result;
	}
}
