package com.cat.zproto.assist.serial;

import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class GenProtoIdDefine extends AbstractGenProtoId {
	
	@Override
	public int type() {
		return DEFINE;
	}

//	@Override
//	public Map<String, Integer> genProtoIds(int moduleId, ProtocolObject protoObj) {
//
//		List<ProtocolStructure> reqs = new ArrayList<>();
//		Map<String, ProtocolStructure> resps = new LinkedHashMap<>();
//		Map<String, Integer> result = new LinkedHashMap<>();
//		String reqPrefix = setting.getProto().getReqPrefix();
//		String respPrefix = setting.getProto().getRespPrefix();
//		protoObj.getStructures().forEach((protoName, struct)->{
//			if (protoName.startsWith(reqPrefix)) {
//				reqs.add(struct);
//			}
//			else if (protoName.startsWith(respPrefix)) {
//				resps.put(protoName, struct);
//			}
//		});
//
//		final int interval = setting.getProto().getPtoroCoefficient();
//		int seq = moduleId * interval + 100;
//		//筛选请求响应协议
//		for (ProtocolStructure reqStruct : reqs) {
//			String reqProtoName = reqStruct.getName();
//			int protoId = seq + reqStruct.getInsideId();
//			if (result.values().contains((Integer.valueOf(protoId)))){
//				continue;
//			}
//			result.put(reqProtoName, protoId);
//		}
//		seq = moduleId * interval + 200;
//		for (ProtocolStructure respStruct : resps.values()) {
//			String respProtoName = respStruct.getName();
//			int protoId = seq + respStruct.getInsideId();
//			if (result.values().contains((Integer.valueOf(protoId)))){
//				continue;
//			}
//			result.put(respProtoName, protoId);
//		}
//		return result;
//	}

	@Override
	public Map<String, Integer> generater(int moduleId, List<ProtocolStructure> reqs, Map<String, ProtocolStructure> resps) {
		Map<String, Integer> result = new LinkedHashMap<>();
		final int interval = setting.getProto().getPtoroCoefficient();
		int seq = moduleId * interval + 100;
		//筛选请求响应协议
		for (ProtocolStructure reqStruct : reqs) {
			String reqProtoName = reqStruct.getName();
			int protoId = seq + reqStruct.getInsideId();
			if (result.values().contains((Integer.valueOf(protoId)))){
				continue;
			}
			result.put(reqProtoName, protoId);
		}
		seq = moduleId * interval + 200;
		for (ProtocolStructure respStruct : resps.values()) {
			String respProtoName = respStruct.getName();
			int protoId = seq + respStruct.getInsideId();
			if (result.values().contains((Integer.valueOf(protoId)))){
				continue;
			}
			result.put(respProtoName, protoId);
		}
		return result;
	}
	

}
