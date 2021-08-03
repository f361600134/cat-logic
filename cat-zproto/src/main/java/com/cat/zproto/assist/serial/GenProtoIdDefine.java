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
public class GenProtoIdDefine implements IGenProtoId{
	
	@Autowired private SettingConfig setting;
	
	@Override
	public int type() {
		return DEFINE;
	}

	@Override
	public Map<String, Integer> genProtoIds(int moduleId, ProtocolObject protoObj) {
		Map<String, Integer> result = new LinkedHashMap<>();
		final int interval = setting.getProto().getPtoroCoefficient();
		int seq = moduleId * interval;
		protoObj.getStructures().forEach((protoName, struct)->{
			int protoId = seq + struct.getInsideId();
			result.put(struct.getName(), protoId);
		});
		return result;
	}
	
	

}
