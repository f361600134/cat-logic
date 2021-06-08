package com.cat.zproto.assist.serial;

import java.util.Map;

import com.cat.zproto.domain.proto.ProtocolObject;

/**
 * 生成协议id接口
 * @author Administrator
 *
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
	
	
	int INTERVAL = 1000;

	/**
	 * 类型, 1:成对生成, 2:先请求,后响应
	 * @return
	 */
	int type();
	
	/**
	 * key: 协议名,  value: 协议号
	 * @param protoStructs本模块的协议集合, 包含协议对象
	 * @return
	 */
	Map<String, Integer> genProtoIds(int moduleId, ProtocolObject protoObj);
	
}
