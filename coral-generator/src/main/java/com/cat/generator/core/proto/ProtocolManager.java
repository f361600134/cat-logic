/**
 * 
 */
package com.cat.generator.core.proto;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.generator.util.ClassPathScanner;
import com.google.common.collect.HashBiMap;
import com.google.protobuf.GeneratedMessageLite;

/**
 * 解析proto
 * 每个请求对应固定的消息协议号
 * 1. 首先找到协议号, PBDefine.java, 协议号->方法名
 * 2. 解析其他文件
 * @auth Jeremy
 * @date 2019年4月22日下午3:16:50
 */
public class ProtocolManager {
	
	public static final Logger logger = LoggerFactory.getLogger(ProtocolManager.class.getName());
	
	private static HashBiMap<Integer, String> protoIdMap = HashBiMap.create();
	private static Map<Integer, Class<? extends GeneratedMessageLite>> protoMap = new HashMap<>();
	
	public static void init() throws Exception {
		ClassPathScanner scan = new ClassPathScanner(false, true, null);
		Set<Class<?>> classes = scan.getPackageAllClasses("com.lszj.game.data.proto", true);
		
		//初始化协议号
        //Class<?> clz = PBProtocol.class;
        Class<?> clz = null;
		Object[] objects = clz.getEnumConstants();
		Method getCode = clz.getMethod("getNumber");
		for (Object obj : objects){
			protoIdMap.put((int)(getCode.invoke(obj)), obj.toString());
        }
		
		for (Class<?> clazz : classes) {
			String clazzName = clazz.getSimpleName();
			if (clazzName.contains("PBDefine")) {
				continue;
			}
			if(clazzName.contains("OrBuilder")) {
				continue;
			}
			if(clazzName.contains("Req") || clazzName.contains("Ack")) {
				@SuppressWarnings("unchecked")
				Class<? extends GeneratedMessageLite> realClazz = (Class<? extends GeneratedMessageLite>) clazz;
				protoMap.put(protoIdMap.inverse().get(clazzName), realClazz);
			}
		}
		logger.info("protoIdMap:{}", protoIdMap);
		logger.info("protoMap:{}", protoMap);
	}
	
	public static Map<Integer, Class<? extends GeneratedMessageLite>> getRequestProtocols() {
		return protoMap;
	}
	
	public static Map<Integer, Class<? extends GeneratedMessageLite>> getResponeProtocols() {
		return protoMap;
	}
	
	public static void main(String[] args) {
//		com.google.protobuf.Internal.EnumLiteMap<PBProtocol> map = PBDefine.PBProtocol.internalGetValueMap();
//		System.out.println(map.findValueByNumber(100).name());
		
//		copyFile();
		
		//test init
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
//		int compilationResult = javac.run(null, null, null, "-g", "-verbose", "F:\\workspace\\ClientSimulator\\src\\main\\java\\com\\proto\\BagMsg.java");
//		System.out.println(compilationResult);
		
//		ParameterizedType pt = (ParameterizedType) GenericTest.class.getField(   
//                "list").getGenericType();   
//        System.out.println(pt.getActualTypeArguments().length);   
//        System.out.println(pt.getActualTypeArguments()[0]); 
	}
	
	public static Class<? extends GeneratedMessageLite> getProtocol(int protocol) {
		return protoMap.get(protocol);
	}
	
	public static Class<? extends GeneratedMessageLite> getProtocol(String protocolStr){
		try {
			int protocol = protoIdMap.inverse().get(protocolStr);
			return getProtocol(protocol);
		} catch (Exception e) {
			logger.error("getProtocol error, protocolStr:{}, e:{}", protocolStr, e);
		}
		return null;
	}
	
	public static int getProtocolId(String protocolStr){
		int protocol = protoIdMap.inverse().get(protocolStr);
		return protocol;
	}
}
