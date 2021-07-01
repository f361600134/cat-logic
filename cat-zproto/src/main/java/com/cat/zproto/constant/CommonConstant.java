package com.cat.zproto.constant;

/**
 * 全局静态常量
 * 因为依赖配置的相对路径,所以通过spring从配置中获取文件
 * @auth Jeremy
 * @date 2021年6月12日下午4:10:58
 */

public class CommonConstant {
//	/**
//	 * 系统设置存储
//	 */
//	public static String SYSTEM_SETTING = "src/main/resources/setting/settings.json";
//	@Value("${systemSetting}")
//	private String systemSetting;
	public static final String SYSTEM_SETTING = "setting/settings.json";
//	
//	/**
//	 * 结构存储路径
//	 */
//	public static String RESOURCE_CONFIGDATA_PATH = "src/main/resources/configdata/";
//	public static final String RESOURCE_CONFIGDATA_PATH = "classpath:configdata";
	public static String RESOURCE_CONFIGDATA_PATH = "./configdata";
	
	/**
	 * ftl模板存储路径
	 */
	public static String FTL_CODE_PATH = "./ftl/code/";
	
	/**
	 * ftl模板存储路径
	 */
	public static String FTL_PROTO_PATH = "./ftl/proto";
	/**
	 * 编译器的路径
	 */
//	public static final String PROTO_EXE_PATH = "proto3/exec";
	
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
	 * 下载目录, 也是压缩代码的目录
	 */
	public static final String DOWNLOAD_PACKAGE= "./download";
	
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
