package com.cat.zproto.enums;

import java.util.EnumMap;
import java.util.Map;

/**
 * proto类型枚举类
 * @author Jeremy
 */
public enum ProtoTypeEnum {
	
	DOUBLE("double", "double", "double"),
	FLOAT("float", "float", "float"),
	/**
	 * 使用可变长编码方式。编码负数时不够高效——如果你的字段可能含有负数，那么请使用sint32。
	 */
	INT32("int32", "int", "int32"),
	/**
	 * 使用可变长编码方式。编码负数时不够高效——如果你的字段可能含有负数，那么请使用sint64。
	 */
	INT64("int64", "long", "int64"),
	/**
	 * 总是4个字节。如果数值总是比总是比228大的话，这个类型会比uint32高效。
	 */
	UNIT32("unit32", "int", "unit32"),
	/**
	 * 总是8个字节。如果数值总是比总是比256大的话，这个类型会比uint64高效。
	 */
	UNIT64("unit64", "long", "unit64"),
	/**
	 * 使用可变长编码方式。有符号的整型值。编码时比通常的int32高效。
	 */
	SINT32("sint32", "int", "sint32"),
	/**
	 * 使用可变长编码方式。有符号的整型值。编码时比通常的int64高效。
	 */
	SINT64("sint64", "long", "sint64"),
	FIXED32("fixed32", "int", "unit32"),
	/**
	 * 总是8个字节。如果数值总是比总是比256大的话，这个类型会比uint64高效。
	 */
	FIXED64("fixed64", "long", "unit64"),
	/**
	 * 总是4个字节。
	 */
	SFIXED32("sfixed32", "int", "int32"),
	/**
	 * 总是8个字节。
	 */
	SFIXED64("sfixed64", "long", "int64"),
	BOOL("bool", "boolean", "bool"),
	/**
	 * 一个字符串必须是UTF-8编码或者7-bit ASCII编码的文本。
	 */
	STRING("string", "String", "string"),
	/**
	 * 可能包含任意顺序的字节数据
	 */
	BYTES("bytes", "com.google.protobuf.ByteString", "string"),
	
	;
	
	private final String protoType;
	private final String javaType;
	private final String csharpType;
	
	private ProtoTypeEnum(String protoType, String javaType, String csharpType) {
		this.protoType = protoType;
		this.javaType = javaType;
		this.csharpType = csharpType;
	}

	public String getProtoType() {
		return protoType;
	}

	public String getJavaType() {
		return javaType;
	}

	public String getCsharpType() {
		return csharpType;
	}
	
	public static Map<ProtoTypeEnum, String> enumMap = new EnumMap<>(ProtoTypeEnum.class);
	static{
		for (ProtoTypeEnum protoType : values()) {
			enumMap.put(protoType, protoType.getProtoType());
		}
	}
	
}
