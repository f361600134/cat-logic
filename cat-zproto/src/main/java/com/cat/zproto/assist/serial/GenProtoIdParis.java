package com.cat.zproto.assist.serial;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cat.zproto.domain.proto.ProtocolConstant;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;

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
public class GenProtoIdParis implements IGenProtoId{
	
	@Override
	public int type() {
		return PAIRS;
	}

	@Override
	public Map<String, Integer> genProtoIds(int moduleId, ProtocolObject protoObj) {
		List<ProtocolStructure> reqs = new ArrayList<>();
		Map<String, ProtocolStructure> resps = new LinkedHashMap<>();
		Map<String, Integer> result = new LinkedHashMap<>();
		
		String reqPrefix = ProtocolConstant.REQ_PREFIX;
		String respPrefix = ProtocolConstant.RESP_PREFIX; 
		
		//筛选请求响应协议
		protoObj.getStructures().forEach((protoName, struct)->{
			if (protoName.startsWith(reqPrefix)) {
				reqs.add(struct);
			}
			else if (protoName.startsWith(respPrefix)) {
				resps.put(protoName, struct);
			}
		});
		int protoId = moduleId * INTERVAL;
		for (ProtocolStructure reqStruct : reqs) {
			String reqProtoName = reqStruct.getName();
			result.put(reqProtoName, protoId+=1);
			//返回的协议名
			String ackProtoName = reqProtoName.replace(reqPrefix, respPrefix);
			ProtocolStructure respStruct = resps.remove(ackProtoName);
			if (respStruct != null) {
				result.put(ackProtoName, protoId+=1);
			}
		}
		//剩余的响应消息,生成协议号
		for (ProtocolStructure respStruct : resps.values()) {
			String respProtoName = respStruct.getName();
			result.put(respProtoName, protoId+=1);
		}
		return result;
	}
	
	

}
