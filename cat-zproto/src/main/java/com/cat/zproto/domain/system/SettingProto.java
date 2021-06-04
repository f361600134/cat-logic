package com.cat.zproto.domain.system;

import java.util.HashMap;
import java.util.Map;

/**
 * proto相关配置<br>
 */
public class SettingProto {
	
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
	
}
