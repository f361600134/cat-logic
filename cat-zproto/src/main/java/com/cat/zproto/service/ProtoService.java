package com.cat.zproto.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.zproto.assist.serial.IGenProtoId;
import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.domain.proto.ProtocolConstant;
import com.cat.zproto.domain.proto.ProtocolDomain;
import com.cat.zproto.domain.proto.ProtocolField;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingVersion;
import com.cat.zproto.manager.ProtocolManager;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

@Service
public class ProtoService implements InitializingBean{
	
	public static final Logger logger = LoggerFactory.getLogger(ProtoService.class.getName());
	
	@Autowired private SettingConfig setting;
	
	@Autowired private List<IGenProtoId> genProtoIds;
	
	@Autowired private ProtocolManager protocolManager;
	
	
	/**
	 * 生成类型<br>
	 * 协议生成器
	 */
	public final Map<Integer, IGenProtoId> genProtoIdMap = new HashMap<>();
	
	public Pair<Map<String, ProtocolObject>, HashBiMap<String, Integer>> parse(String path) {
//		Pair<Map<String, ProtocolObject>, HashBiMap<String, Integer>> pair 
		HashBiMap<String, Integer> biMap = null;
		Map<String, ProtocolObject> ret = new HashMap<>();
		File folder = new File(path);
		for(File file : folder.listFiles()) {
			if(!file.getName().endsWith(".proto")) {
				continue;
			}
			if (file.getName().equals("PBProtocol.proto")) {
				//解析协议号对应的方法名
				biMap = parseProtoId(file);
				continue;
			}
			Pair<List<String>, List<List<String>>> pair = getProtocolScopeList(file);
			List<String> headList = pair.getLeft();
			List<List<String>> scopeList = pair.getRight();
			ProtocolObject object = parseObject(headList);
			for(List<String> scope : scopeList) {
				try {
					object.getStructures().putAll(parseStructure(scope));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ret.put(object.getModuleName(), object);
		}
		return Pair.of(ret, biMap);
	}
	
	/**
	 * 构建一个proto对象
	 * @return
	 */
	private ProtocolObject parseObject(List<String> headList) {
		final String quo = ProtocolConstant.QUOTATION;
		final String javaPackage = ProtocolConstant.JAVA_PACKAGE;
		final String javaOuterClassname = ProtocolConstant.JAVA_OUTER_CLASSNAME;
		final String javaImport = ProtocolConstant.IMPORT;
		final String spot = ProtocolConstant.SPOT;
		
		ProtocolObject object = new ProtocolObject();
		for(String line : headList) {
			try {
				if (line.contains(javaPackage)) {
				//解析所在目录
				int startIndex = line.indexOf(quo)+1;
				int lastIndex = line.lastIndexOf(quo);
				object.setJavaPath(line.substring(startIndex, lastIndex));
			}
			else if (line.contains(javaOuterClassname)) {
				//解析类名
				int startIndex = line.indexOf(quo)+1;
				int lastIndex = line.lastIndexOf(quo);
				String name = line.substring(startIndex, lastIndex);
				object.setOutClass(name);
				name = name.replaceAll(ProtocolConstant.PB_PREFIX, "");
				object.setModuleName(name);
			}
			else if (line.contains(javaImport)) {
				//解析依赖项
				int startIndex = line.indexOf(quo)+1;
				int lastIndex = line.lastIndexOf(spot);
				String name = line.substring(startIndex, lastIndex);
				object.addDependenceObj(name);
			}
			} catch (Exception e) {
				logger.error("parseObject error");
				e.printStackTrace();
			}
		}
		return object;
	}
	
	
	/**
	 * 解释出一个协议结构
	 * @param structList
	 * @return
	 */
	private Map<String, ProtocolStructure> parseStructure(List<String> structList) {
		final String message = ProtocolConstant.MESSAGE;
		final String slash = ProtocolConstant.DOUBLE_SLASH;
		final String leftBracket = ProtocolConstant.LEFT_BRACKET;
		final String rightBracket = ProtocolConstant.RIGHT_BRACKET;
		final String space = ProtocolConstant.SPACE;
		final String empty = ProtocolConstant.EMPTH_STR;
	
		Map<String, ProtocolStructure> temp = Maps.newHashMap();
		ProtocolStructure struct = new ProtocolStructure();
		for(String line : structList) {
			try {
				line = line.trim();

				if(line.startsWith(slash)) {
					//解析类注释
					String comment = line.replaceAll(slash, empty);
					comment = comment.replaceAll(slash, empty);
					struct.setComment(comment);
				} else if(line.startsWith(message)) {
					//此行是message结构体, 
					String name = line.replaceAll(message, empty);
					name = name.replaceAll("\\"+leftBracket, empty);
					name = name.replaceAll("\\"+rightBracket, empty);
					name = name.replaceAll(space, empty);
					struct.setName(name);
				}
				else {
					//最后解析构内的字段
					if (line.equals(empty))
						continue;
					if(line.contains(leftBracket))
						continue;
					if(line.contains(rightBracket))
						continue;
					
					ProtocolField field = new ProtocolField();
					String[] temps = line.split(slash);
					if(temps.length > 1) {
						field.setComment(temps[1].replaceAll(slash, empty).trim());
					}
					String[] fieldStrs = temps[0].substring(0, temps[0].indexOf("=")).split(space);
					if(fieldStrs.length>2) {
						field.setIdentifier(fieldStrs[0].trim());
						field.setType(fieldStrs[1].trim());
						field.setName(fieldStrs[2].trim());
					}else {
						field.setType(fieldStrs[0].trim());
						field.setName(fieldStrs[1].trim());
					}
					struct.getFields().add(field);
				}
			} catch (Exception e) {
				logger.info("parseStructure error, line="+line);
				e.printStackTrace();
			}
		}
		temp.put(struct.getName(), struct);
		return temp;
	}
	
	/**
	 * 解析出协议名对应的协议id
	 * @param protocolFile
	 * @return
	 */
	private HashBiMap<String, Integer> parseProtoId(File protocolFile) {
		HashBiMap<String, Integer> ret = HashBiMap.create();
		//协议结构列表
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(protocolFile)));
			String line = reader.readLine();
			boolean start = false;
			while(line != null) {
				if (line.startsWith("enum")) {
					start = true;
				}
				if (start && line.contains("=")) {
					String arr[] = line.split("=");
					String key = RegExUtils.replaceAll(arr[0], " ", "").replaceAll("	", "");
					String value = RegExUtils.replaceAll(arr[1], " ", "").replaceAll(";", "");
					ret.putIfAbsent(key, Integer.valueOf(value));
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			logger.error("protocolFile:{}", protocolFile.getName());
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 划分每个协议结构的字符串,
	 * @param protocolFile
	 * @return
	 */
	private Pair<List<String>, List<List<String>>> getProtocolScopeList(File protocolFile) {
		//协议头结构
		List<String> headList = new ArrayList<>();
		//协议结构列表
		List<List<String>> scopeList = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(protocolFile)));
			List<String> lineList = new ArrayList<>();
			//标记是否为协议结构
			boolean scoping = false;
			//单个协议结构的内容列表
			List<String> scopeBuilder = null;
			String line = reader.readLine();
			while(line != null) {
				//协议结构开始
				if(line.startsWith("message")) {
					scoping = true;
					scopeBuilder = new ArrayList<>();
					//加入主体结构之前的信息
					headList.addAll(lineList);
					//结构的备注
					String comment = lineList.get(lineList.size() - 1);
					if(comment.startsWith("//")) {
						scopeBuilder.add(comment);
					}
				}
				//协议结构结束
				if(line.contains("}")) {
					scopeBuilder.add(line.substring(0, line.indexOf("}") + 1));
					scopeList.add(scopeBuilder);
					scoping = false;
				}
				if(scoping) {
					scopeBuilder.add(line);
				}
				lineList.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			logger.error("protocolFile:{}", protocolFile.getName());
			e.printStackTrace();
		}
		return Pair.of(headList, scopeList);
	}
	
	/**
	 * 获取对外依赖的对象<br>
	 * 根据依赖的DTO对象, 判断如果匹配, 表示依赖了此对象,返回给调用层
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getDependanceObj(String version, String moduleName, List<String> dtoNames){
		ProtocolDomain protocolDomain = protocolManager.getDomain(version);
		if (protocolDomain == null) {
			return Collections.EMPTY_LIST;
		}
		if (dtoNames == null || dtoNames.isEmpty()) {
			return Collections.EMPTY_LIST;
		}
		return protocolDomain.getDependanceObj(moduleName, dtoNames);
	}
	
	/**
	 * proto修改, 修改proto后, 会伴随着协议号的修改
	 * 1. 读取配置重新设置,
	 * 2. 修改协议列表,直接替换成新的
	 * @return 返回被修改的最新的协议对象
	 */
	public ProtocolObject protoObjectUpdate(String version, String moduleName, List<ProtocolStructure> protoStructure) {
		ProtocolDomain protocolDomain =  protocolManager.getOrCreateDomain(version);
		ProtocolObject protoObject = protocolDomain.getOrCreateProtoObject(moduleName);
		//	替换掉协议信息
		protoObject.replaceStructures(protoStructure);
		//	根据配置重新设置
		String javaPackage = setting.getProto().getJavaPackagePath();
		protoObject.setJavaPath(javaPackage);
		String PbObj = ProtocolConstant.PB_PREFIX.concat(com.cat.zproto.util.StringUtils.firstCharUpper(moduleName));
		protoObject.setOutClass(PbObj);
		//查询所有引用类
		List<String> dtoNames = new ArrayList<>();
		for (ProtocolStructure protocolStructure : protoStructure) {
			if (StringUtils.startsWith(protocolStructure.getName(), ProtocolConstant.PB_PREFIX)) {
				dtoNames.add(protocolStructure.getName());
			}
		}
		List<String> dependans = protocolDomain.getDependanceObj(moduleName, dtoNames);
		protoObject.getDependenceObjs().addAll(dependans);
		//存储
		protocolDomain.save();
		return protoObject;
	}
	
	/**
	 * 重新生成所有的协议id<br>
	 */
	public BiMap<String, Integer> genProtoIds(String version, Collection<ModuleEntity> moduleEntitys) {
		ProtocolDomain protocolDomain = protocolManager.getDomain(version);
		if (protocolDomain == null) {
			return null;
		}
		protocolDomain.getProtoIdMap().clear();
		//根据使用者定义的排序方式生成协议号
		IGenProtoId genProtoId = genProtoIdMap.get(setting.getProto().getProtoIdSortBy());
		moduleEntitys.forEach(module->{
			ProtocolObject protoObject = protocolDomain.getProtoObject(module.getName());
			Map<String, Integer> tempMap = genProtoId.genProtoIds(module.getId(), protoObject);
			protocolDomain.putProtoId(tempMap);
		});
		protocolDomain.save();
		return protocolDomain.getProtoIdMap();
	}
	
	/**
	 * 反向加载协议结构, 从proto文件内加载
	 *   只有本地缓存内无数据时, 才支持反向加载
	 * @return void  
	 * @date 2021年6月5日下午10:51:15
	 */
	public boolean reverseLoad(String version) {
		ProtocolDomain protocolDomain = protocolManager.getDomain(version);
		if (protocolDomain == null) {
			return false;
		}
		if (!protocolDomain.getProtoMap().isEmpty() || !protocolDomain.getProtoIdMap().isEmpty()) {
			return false;
		}
		String protoPath = setting.getProto().getProtoPath();
		Pair<Map<String, ProtocolObject>, HashBiMap<String, Integer>> pair = this.parse(protoPath);
		protocolDomain.init(pair.getLeft(), pair.getRight());
		protocolDomain.save();
		return true;
	}
	
	/**
	 * 通过协议方法名, 获取协议对象信息
	 * @param protoMethodName
	 * @return
	 */
	public ProtocolObject getProtoObject(String version, String protoMethodName) {
		ProtocolDomain protocolDomain = protocolManager.getDomain(version);
		if (protocolDomain == null) {
			return null;
		}
		return protocolDomain.getProtoObject(protoMethodName);
	}
	
	/**
	 * @param protoMethodName
	 * @return
	 */
	public BiMap<String, Integer> getProtoIdMap(String version) {
		ProtocolDomain protocolDomain = protocolManager.getDomain(version);
		if (protocolDomain == null) {
			return null;
		}
		return protocolDomain.getProtoIdMap();
	}
	
	/**
	 * 通过协议方法名, 获取协议所有对象信息
	 * @param protoMethodName
	 * @return
	 */
	public Collection<ProtocolObject> getAllProtoObject(String version) {
		ProtocolDomain protocolDomain = protocolManager.getDomain(version);
		if (protocolDomain == null) {
			return null;
		}
		return protocolDomain.getAllProtoObject();
	}
	
	
//	@SuppressWarnings("unchecked")
//	public Map<String, Integer> getSortProtoIdMap(String version, Collection<ModuleEntity> moduleEntitys){
//		ProtocolDomain protocolDomain = protocolManager.getDomain(version);
//		if (protocolDomain == null) {
//			return Collections.EMPTY_MAP;
//		}
//		Map<String, Integer> linkedMap = new LinkedHashMap<>();
//		for (ModuleEntity moduleEntity : moduleEntitys) {
//			ProtocolObject protocolObject = this.protoMap.get(moduleEntity.getName());
//			for (ProtocolStructure struct : protocolObject.getStructureList()) {
//				int protoId = this.protoIdMap.getOrDefault(struct.getName(), 0);
//				if (protoId == 0) {
//					continue;
//				}
//				linkedMap.put(struct.getName(), protoId);
//			}
//		}
//		return linkedMap;
//	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//初始化协议生成器
		for (IGenProtoId iGenProtoId : genProtoIds) {
			genProtoIdMap.put(iGenProtoId.type(), iGenProtoId);
		}
		//加载proto信息
		Collection<SettingVersion> versions = setting.getVersionInfo().values();
		for (SettingVersion settingVersion : versions) {
			String version = settingVersion.getVersion();
			ProtocolDomain domain = protocolManager.getOrCreateDomain(version);
			String protoPath = settingVersion.protoDataPath();
			String protoIdPath = settingVersion.protoIdPath();
			domain.init(protoPath, protoIdPath);
		}
	}
	
}
