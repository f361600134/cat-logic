package com.cat.zproto.assist.serial;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.zproto.domain.proto.ProtocolConstant;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;

@Component
public class GenProtoIdParis extends AbstractGenProtoId {
	
	@Override
	public int type() {
		return PAIRS;
	}

//	@Override
//	public Map<String, Integer> genProtoIds(int moduleId, ProtocolObject protoObj) {
//		Map<String, Integer> result = new LinkedHashMap<>();
//		final String reqPrefix = setting.getProto().getReqPrefix();
//		final String respPrefix = setting.getProto().getRespPrefix();
//
//		List<ProtocolStructure> reqs = new ArrayList<>();
//		Map<String, ProtocolStructure> resps = new LinkedHashMap<>();
//		//筛选请求响应协议
//		protoObj.getStructures().forEach((protoName, struct)->{
//			if (protoName.startsWith(reqPrefix)) {
//				reqs.add(struct);
//			}
//			else if (protoName.startsWith(respPrefix)) {
//				resps.put(protoName, struct);
//			}
//		});
//		int protoId = moduleId * setting.getProto().getPtoroCoefficient();
//		for (ProtocolStructure reqStruct : reqs) {
//			String reqProtoName = reqStruct.getName();
//			result.put(reqProtoName, protoId+=1);
//			//返回的协议名
//			String ackProtoName = reqProtoName.replace(reqPrefix, respPrefix);
//			ProtocolStructure respStruct = resps.remove(ackProtoName);
//			if (respStruct != null) {
//				result.put(ackProtoName, protoId+=1);
//			}
//		}
//		//剩余的响应消息,生成协议号
//		for (ProtocolStructure respStruct : resps.values()) {
//			String respProtoName = respStruct.getName();
//			result.put(respProtoName, protoId+=1);
//		}
//		return result;
//	}

	@Override
	public Map<String, Integer> generater(int moduleId, List<ProtocolStructure> reqs, Map<String, ProtocolStructure> resps) {
		Map<String, Integer> result = new LinkedHashMap<>();
		final String reqPrefix = setting.getProto().getReqPrefix();
		final String respPrefix = setting.getProto().getRespPrefix();
		int protoId = moduleId * setting.getProto().getPtoroCoefficient();
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
