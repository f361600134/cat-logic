package com.cat.zproto.domain.proto;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 *协议对象信息
 * @author Jeremy
 */
public class ProtocolObject {
	
	/**
	 * 模块名字,协议文件的名字都会加上PB
	 * 比如PBChat, 模块名字就是chat
	 */
	private String moduleName;
	
	/**
	 * 所在路径
	 */
	private String javaPath = "";
	/**
	 * 输出对象名字
	 */
	private String outClass = "";
	/**
	 * 依赖对象
	 */
	private String dependenceObj = "";

	/**
	 * 协议对象对应的所有协议结构
	 */
	private Map<String, ProtocolStructure> structures = new HashMap<>();

	public String getJavaPath() {
		return javaPath;
	}

	public void setJavaPath(String javaPath) {
		this.javaPath = javaPath;
	}

	public String getOutClass() {
		return outClass;
	}

	public void setOutClass(String outClass) {
		this.outClass = outClass;
		this.setModuleName(outClass);
	}

	public String getDependenceObj() {
		return dependenceObj;
	}

	public void setDependenceObj(String dependenceObj) {
		this.dependenceObj = dependenceObj;
	}

	public Map<String, ProtocolStructure> getStructures() {
		return structures;
	}

	public void setStructures(Map<String, ProtocolStructure> structures) {
		this.structures = structures;
	}
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName.replaceAll("PB", "");
	}
	
	/**
	 * 获取导入路径
	 * 
	 * @return
	 */
	public String getJavaImport() {
		return getJavaPath().concat(".").concat(getOutClass()).concat(".").concat("*");
	}

	/**
	 * 获取依赖对象路径
	 * @return
	 */
	public String getDependenceImport() {
		String ret = getJavaPath();
		if (!StringUtils.isBlank(dependenceObj)) {
			ret = ret.concat(".").concat(getDependenceObj());
		}
		ret = ret.concat(".").concat("*");
		return ret;
	}
	
	@Override
	public String toString() {
		return "ProtocolObject [javaPath=" + javaPath + ", outClass=" + outClass + ", dependenceObj=" + dependenceObj
				+ ", javaImport=" + getJavaImport() + ", dependenceImport" + getDependenceImport() + ", structures="
				+ structures.keySet() + "]";
	}

}
