package com.cat.zproto.assist.serial;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.domain.proto.ProtocolDomain;
import com.cat.zproto.domain.proto.ProtocolObject;

/**
 * 协议id生成排序方式<br>
 * 1.成对排序, 请求/响应的方式有序生成协议号, <br>
 * 	ReqXXX 1001  AckXXX 1002<br>
 *  ReqYYY 1003  AckYYY 1004<br>
 * 2.先请求, 后响应, 间隔100<br>
 *  ReqXXX 1001 ReqYYY 1002<br>
 *  AckXXX 1003 AckYYY 1004<br>
 * 3. 自定义协议号，配置了此项, 页面会开启协议号输入框
 *
 * @author Jeremy
 */
public interface IGenProtoId {
	
	/**
	 * 成对生成
	 */
	int PAIRS = 1;
	
	/**
	 * 响应在请求后生成
	 */
	int AFTER_REQ = 2;

	/**
	 * 自定义协议号， 由开发人员输入
	 */
	int DEFINE = 3;

	/**
	 * 类型, 1:成对生成, 2:先请求,后响应
	 * @return
	 */
	int type();
	
	/**
	 * key: 协议名,  value: 协议号
	 * @param moduleId 模块id 包含协议对象
	 * @param protoObj 协议对象信息
	 * @return
	 */
	Map<String, Integer> genProtoIds(int moduleId, ProtocolObject protoObj);

	/**
	 * key: 协议名,  value: 协议号
	 * @param protocolDomain 模块domain
	 * @param moduleEntitys 协议对象列表
	 * @return
	 */
	default Map<String, Integer> genAllProtoIds(ProtocolDomain protocolDomain, Collection<ModuleEntity> moduleEntitys){
		Map<String, Integer> tempMap = new HashMap<>();
		for (ModuleEntity module : moduleEntitys) {
			ProtocolObject protoObject = protocolDomain.getProtoObject(module.getName());
			tempMap.putAll(this.genProtoIds(module.getId(), protoObject));
		}
		protocolDomain.replaceAllProtoId(tempMap);
		protocolDomain.saveAll();
		return protocolDomain.getProtoIdMap();
	}

}
