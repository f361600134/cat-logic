package com.cat.zproto.domain.proto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	 * 所在路径,需要生成到的java路径
	 */
	private String javaPath;
	/**
	 * 输出对象名字
	 * 比如:PBChat
	 */
	private String outClass;
	/**
	 * 依赖对象<br>
	 * java_outer_classname对于本类, 表示内类名<br>
	 * 对于其他类,表示引用类名<br>
	 * 如果被其他类引用, 则java_outer_classname视为引入包名
	 */
	private List<String> dependenceObjs = new ArrayList<>();

	/**
	 * key: 名字
	 * value: 协议结构
	 * 协议对象对应的所有协议结构
	 */
	private Map<String, ProtocolStructure> structures = new LinkedHashMap<>();
	
	public ProtocolObject() {
		this.moduleName = "";
		this.javaPath = "";
		this.outClass = "";
	}

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
	}


	public List<String> getDependenceObjs() {
		return dependenceObjs;
	}

	public void setDependenceObjs(List<String> dependenceObjs) {
		this.dependenceObjs = dependenceObjs;
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
		this.moduleName = moduleName;
	}
	
	public void addDependenceObj(String dependenceName) {
		this.dependenceObjs.add(dependenceName);
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
	 * 获取结构列表<br>
	 * @return
	 */
	public Collection<ProtocolStructure> getStructureList() {
		return structures.values();
	}

//	/**
//	 * 获取依赖对象路径
//	 * @return
//	 */
//	public String getDependenceImport() {
//		String ret = getJavaPath();
//		if (!StringUtils.isBlank(dependenceObj)) {
//			ret = ret.concat(".").concat(getDependenceObj());
//		}
//		ret = ret.concat(".").concat("*");
//		return ret;
//	}
	
//	/**
//	 * 获取依赖对象路径
//	 * @return
//	 */
//	public List<String> getDependenceImport() {
//		List<String> result = new ArrayList<>();
//		String javaPath = getJavaPath();
//		for (String res : result) {
//			if (!StringUtils.isBlank(javaPath)) {
//				res = javaPath.concat(".").concat(res);
//			}
//			res = res.concat(".").concat("*");
//		}
//		
////		if (!StringUtils.isBlank(javaPath)) {
////			ret = ret.concat(".").concat(getDependenceObj());
////		}
//		ret = ret.concat(".").concat("*");
//		return ret;
//	}
	
	/**
	 * 替换协议结构
	 */
	public void replaceStructures(Map<String, ProtocolStructure> protocolStructureMap) {
		this.structures.clear();
		this.structures.putAll(protocolStructureMap);
	}
	
	/**
	 * 替换协议结构
	 */
	public void replaceStructures(List<ProtocolStructure> protocolStructures) {
		this.structures.clear();
		protocolStructures.forEach((p)->{
			this.structures.put(p.getName(), p);
		});
	}
	
	@Override
	public String toString() {
		return "ProtocolObject [javaPath=" + javaPath + ", outClass=" + outClass 
				+ ", javaImport=" + getJavaImport() + ", structures="
				+ structures.keySet() + "]";
	}

}
