package com.cat.zproto.domain.proto;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 协议对象信息
 * 
 * @author Jeremy
 */
public class ProtocolObject {

	/**
	 * 模块名字,协议文件的名字都会加上PB 比如PBChat, 模块名字就是chat
	 */
	private String moduleName;

	/**
	 * 所在路径,需要生成到的java路径
	 */
	private String javaPath;
	/**
	 * 输出对象名字 比如:PBChat
	 */
	private String outClass;
	/**
	 * 依赖对象<br>
	 * java_outer_classname对于本类, 表示内类名<br>
	 * 对于其他类,表示引用类名<br>
	 * 如果被其他类引用, 则java_outer_classname视为引入包名
	 */
	private Set<String> dependenceObjs = new HashSet<>();

	/**
	 * key: 协议名字 value: 协议结构 协议对象对应的所有协议结构
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

	public Set<String> getDependenceObjs() {
		return dependenceObjs;
	}

	public void setDependenceObjs(Set<String> dependenceObjs) {
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
	@JSONField(serialize = false)
	public String getJavaImport() {
		return getJavaPath().concat(".").concat(getOutClass()).concat(".").concat("*");
	}

	/**
	 * 获取结构列表<br>
	 * 
	 * @return
	 */
	@JSONField(serialize = false)
	public Collection<ProtocolStructure> getStructureList() {
		return structures.values();
	}

	/**
	 * 获取依赖对象路径
	 * 
	 * @return
	 */
	@JSONField(serialize = false)
	public String getDependenceImport() {
		String ret = getJavaPath();
		if (!dependenceObjs.isEmpty()) {
			ret = ret.concat(".").concat("*");
		}
		return ret;
	}

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
		protocolStructures.forEach((p) -> {
			this.structures.put(p.getName(), p);
		});
	}

//	List<ProtocolStructure> protoPBStructList = Lists.newArrayList();
//	List<ProtocolStructure> protoReqStructList = Lists.newArrayList();
//	Map<String, ProtocolStructure> protoAckStructMap = Maps.newHashMap();
//	Map<String, ProtocolStructure> structureMap = protoObject.getStructures();
//	
//	for (String key : structureMap.keySet()) {
//		if (key.startsWith(ProtocolConstant.RESP_PREFIX)) {
//			String newKey = key.replace(ProtocolConstant.RESP_PREFIX, ProtocolConstant.REQ_PREFIX);
//			protoAckStructMap.put(newKey, structureMap.get(key));
//		}else if (key.startsWith(ProtocolConstant.REQ_PREFIX)) {
//			protoReqStructList.add(structureMap.get(key));
//		}else if (key.startsWith(ProtocolConstant.PB_PREFIX)) {
//			protoPBStructList.add(structureMap.get(key));
//		}
//	}

//	@JSONField(serialize = false)
//	public List<ProtocolStructure> getProtoReqStructList() {
//		List<ProtocolStructure> protoReqStructList = new ArrayList<>();
//		for (String key : structures.keySet()) {
//			if (key.startsWith(ProtocolConstant.REQ_PREFIX)) {
//				protoReqStructList.add(structures.get(key));
//			}
//		}
//		return protoReqStructList;
//	}
//	
//	@JSONField(serialize = false)
//	public Map<String, ProtocolStructure> getProtoAckStructMap() {
//		return protoAckStructMap;
//	}
//	
//	@JSONField(serialize = false)
//	public List<ProtocolStructure> getProtoPBStructList() {
//		List<ProtocolStructure> protoPBStructList = new ArrayList<>();
//		for (String key : structures.keySet()) {
//			if (key.startsWith(ProtocolConstant.PB_PREFIX)) {
//				protoPBStructList.add(structures.get(key));
//			}
//		}
//		return protoPBStructList;
//	}

	@Override
	public String toString() {
		return "ProtocolObject [javaPath=" + javaPath + ", outClass=" + outClass + ", javaImport=" + getJavaImport()
				+ ", structures=" + structures + "]";
	}

}
