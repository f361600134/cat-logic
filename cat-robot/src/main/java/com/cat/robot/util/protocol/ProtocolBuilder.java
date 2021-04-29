/**
 * 
 */
package com.cat.robot.util.protocol;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.robot.util.protocol.parser.ProtocolField;
import com.cat.robot.util.protocol.parser.ProtocolParser;
import com.cat.robot.util.protocol.parser.ProtocolStructure;
import com.google.protobuf.GeneratedMessageLite;

/**
 * @auth Jeremy
 * @date 2019年4月22日下午5:25:38
 */
public class ProtocolBuilder {

	public static final Logger logger = LoggerFactory.getLogger(ProtocolBuilder.class.getName());

	/**
	 * 脚本中的协议创建
	 * @param protocolId
	 * @param inputValues
	 * @return
	 * @return Object
	 * @date 2019年4月22日下午6:28:41
	 */
	public static Object build(String protocolStr, String[] inputValues) {
		Class<? extends GeneratedMessageLite> selectClz = ProtocolManager.getProtocol(protocolStr);
		return doBuild(selectClz, protocolStr, inputValues);
	}
	
	
	/**
	 * @param clazz
	 * @param fieldValus
	 * @return
	 * @return Object
	 * @date 2019年4月22日下午6:28:38
	 */
	private static Object doBuild(Class<?> clazz, String protocolStr, String[] fieldValus) {
		try {
			Method newMethod = clazz.getDeclaredMethod("newBuilder", new Class[0]);
			Object builder = newMethod.invoke(clazz, new Object[0]);
			Method buildMethod = builder.getClass().getDeclaredMethod("build", new Class[0]);
			
			ProtocolStructure struct = ProtocolParser.protoMap.get(protocolStr);
			if (struct == null) {
				return null;
			}
			
			Method setter = null;
			Method getter = null;
			int index = 0;
			for (ProtocolField field : struct.getFields()) {
				String methodName = "get" + upperFirst(field.getName());
				getter = builder.getClass().getDeclaredMethod(methodName);
				if (getter == null) {
					logger.info("check...{}", methodName);
					continue;
				}
				methodName = "set" + upperFirst(field.getName());
				Class<?> setReturnType = getter.getReturnType();
				setter = builder.getClass().getDeclaredMethod(methodName, setReturnType);
				if (setter != null) {
					setter.invoke(builder, ConvertUtils.convert(fieldValus[index++], setReturnType));
				}
			}
			return buildMethod.invoke(builder, null);
		} catch (Exception e) {
			logger.error("错误:{}", e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		try {
			ProtocolManager.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String [] value = new String[] {"1"};
		Object obj = build("ReqStageOver", value);

		System.out.println(obj);
	}
	
	public static boolean filter(String name) {
		if (name.equals("defaultInstanceForType")) {
			return false;
		}
		if (name.equals("initialized")) {
			return false;
		}
		if (name.equals("parserForType")) {
			return false;
		}
		if (name.equals("serializedSize")) {
			return false;
		}
		if (name.equals("unknownFields")) {
			return false;
		}
		if (name.contains("Bytes")) {
			return false;
		}
		if (name.contains("OrBuilderList")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 判断是否为基础类型以及基础类型的封装类型
	 *
	 * @see java.lang.Boolean#TYPE
	 * @see java.lang.Character#TYPE
	 * @see java.lang.Byte#TYPE
	 * @see java.lang.Short#TYPE
	 * @see java.lang.Integer#TYPE
	 * @see java.lang.Long#TYPE
	 * @see java.lang.Float#TYPE
	 * @see java.lang.Double#TYPE
	 */
	public static boolean isPrimitive(Class<?> clz) {
		if ((clz == Boolean.TYPE || clz == Boolean.class)//
				|| (clz == Character.TYPE || clz == Character.class)//
				|| (clz == Integer.TYPE || clz == Integer.class)//
				|| (clz == Long.TYPE || clz == Long.class)//
				|| (clz == Byte.TYPE || clz == Byte.class)//
				|| (clz == Double.TYPE || clz == Double.class)//
				|| (clz == Short.TYPE || clz == Short.class) //
				|| (clz == Float.TYPE || clz == Float.class)//
				|| clz == String.class) {
			return true;
		}
		return false;
	}
	
	private static String upperFirst(String value) {
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}
}
