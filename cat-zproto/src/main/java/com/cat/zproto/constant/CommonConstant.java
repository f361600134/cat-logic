package com.cat.zproto.constant;

public class CommonConstant {

	/**
	 * 系统设置存储
	 */
	public static final String SYSTEM_SETTING = "src/main/resources/configdata/settings.json";
	
	/**
	 * 模块信息
	 */
	public static final String MODULE_INFO_PATH = "src/main/resources/configdata/module.json";
	
	/**
	 * 协议结构信息
	 */
	public static final String PROTO_INFO_PATH = "src/main/resources/configdata/protoData.json";
	
	/**
	 * 协议id信息
	 */
	public static final String PROTO_ID_PATH = "src/main/resources/configdata/protoId.json";
	
	/**
     * protoc的执行语句格式<br>
     * {protoc} -I={protoPath} --java_out={javaPath} {moduleName}.proto
     */
	public final static String PROTOC_EXECUTE_COMMAND_FORMAT = "%s -I=%s --java_out=%s %s.proto";
	
	/**
     * protoc的执行语句格式<br>
     * {protoc} -I={protoPath} --{java}_out={javaPath} {moduleName}.proto
     */
	public final static String PROTOC_EXECUTE_FORMAT = "%s -I=%s --%s_out=%s %s.proto";

	/**
	 * 生成协议结果返回
	 */
	public final static String GENERATE_RESULT = "[ %s ] 结果:%S";
	
}
