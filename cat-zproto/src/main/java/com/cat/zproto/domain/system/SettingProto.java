package com.cat.zproto.domain.system;

import java.util.HashMap;
import java.util.Map;

/**
 * proto相关配置<br>
 */
public class SettingProto {
	
	/**
	 * proto.exe的路径
	 */
	private String protoExePath;
	
	/**
	 * protp文件路径
	 */
	private String protoPath;

	/**
	 * proto生成的文件路径<br>
	 * key:语言,value:路径<br>
	 * 配置在此map内的路径,均可以生成<br>
	 * eg: key:Java, value:javaPath eg: key:c# , value:csharpPath eg: key:nodejs,
	 * value:nodejsPath
	 */
	private Map<String, String> generatorPath = new HashMap<>();

	/**
	 * 生成的Java所在路径
	 */
	private String javaPackagePath;
	
	/**
	 * 协议id生成排序方式<br>
	 * 1.成对排序, 请求/响应的方式有序生成协议号, <br>
	 * 	ReqXXX 1001  AckXXX 1002<br>
	 *  ReqYYY 1003  AckYYY 1004<br>
	 * 2.先请求, 后响应, <br>
	 *  ReqXXX 1001 ReqYYY 1002<br>
	 *  AckXXX 1003 AckYYY 1004<br>
	 */
	private int protoIdSortBy;
	
	
	public String getProtoPath() {
		return protoPath;
	}

	public void setProtoPath(String protoPath) {
		this.protoPath = protoPath;
	}

	public Map<String, String> getGeneratorPath() {
		return generatorPath;
	}

	public void setGeneratorPath(Map<String, String> generatorPath) {
		this.generatorPath = generatorPath;
	}

	public String getJavaPackagePath() {
		return javaPackagePath;
	}

	public void setJavaPackagePath(String javaPackagePath) {
		this.javaPackagePath = javaPackagePath;
	}

	public String getProtoExePath() {
		return protoExePath;
	}

	public void setProtoExePath(String protoExePath) {
		this.protoExePath = protoExePath;
	}

	public int getProtoIdSortBy() {
		return protoIdSortBy;
	}

	public void setProtoIdSortBy(int protoIdSortBy) {
		this.protoIdSortBy = protoIdSortBy;
	}
	
}
