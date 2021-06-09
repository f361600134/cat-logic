package com.cat.zproto.constant;

public class CommonConstant {

	/**
	 * 系统设置存储
	 */
	public static final String SYSTEM_SETTING = "src/main/resources/setting/settings.json";
	
	/**
	 * 结构存储路径
	 */
	public static final String RESOURCE_CONFIGDATA_PATH = "src/main/resources/configdata/";
	/**
	 * 模块信息
	 */
	public static final String MODULE_FILE_NAME = "/module.json";
	
	/**
	 * 协议结构信息
	 */
	public static final String PROTO_FILE_NAME = "/protoData.json";
	
	/**
	 * 协议id信息
	 */
	public static final String PROTO_ID_FILE_NAME = "/protoId.json";
	
	/**
	 * 生成路径
	 */
	public static final String GENERATOR_PATH = "./temps/";
	
	/**
	 * 生成代码文件夹
	 */
	public static final String CODE_PACKAGE= "/code";
	
	/**
	 * 生成协议文件夹
	 */
	public static final String PROTO_PACKAGE= "/proto";
	
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
