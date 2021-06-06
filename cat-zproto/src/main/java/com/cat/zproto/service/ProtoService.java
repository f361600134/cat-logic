package com.cat.zproto.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.core.result.SystemCodeEnum;
import com.cat.zproto.core.result.SystemResult;
import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.domain.proto.ProtocolConstant;
import com.cat.zproto.domain.proto.ProtocolField;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolParser;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

@Service
public class ProtoService implements InitializingBean{
	
	public static final Logger logger = LoggerFactory.getLogger(ProtocolParser.class.getName());
	
	@Autowired private SettingConfig setting;
	
	
	/**
	 * key:协议名
	 * value: 协议号
	 */
	public final HashBiMap<String, Integer> protoIdMap = HashBiMap.create();
	
	/**
	 * key: 模块名
	 * value: 协议信息
	 */
	public final Map<String, ProtocolObject> protoMap = Maps.newHashMap();
	
	public void parse(String path) {
		File folder = new File(path);
		for(File file : folder.listFiles()) {
			if(!file.getName().endsWith(".proto")) {
				continue;
			}
			if (file.getName().equals("PBProtocol.proto")) {
				//解析协议号对应的方法名
				parseProtoId(file);
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
			protoMap.put(object.getModuleName(), object);
		}
//		for(ProtocolObject ps:protoMap.values()) {
//			System.out.println(ps);
//		}
//		logger.info("protoId:{}", protoIdMap.keySet());
//		logger.info("groupList:{}", protoMap.keySet());
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
					protoIdMap.putIfAbsent(key, Integer.valueOf(value));
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			logger.error("protocolFile:{}", protocolFile.getName());
			e.printStackTrace();
		}
		return protoIdMap;
	}
	
	/**
	 * 划分每个协议结构的字符串,
	 * @param protocolFile
	 * @return
	 */
	@JSONField(serialize =  false)
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
	 * 通过协议方法名, 获取协议对象信息
	 * @param protoMethodName
	 * @return
	 */
	@JSONField(serialize =  false)
	public ProtocolObject getProtoObject(String protoMethodName) {
		return protoMap.get(protoMethodName);
	}
	
	/**
	 * 通过协议方法名, 获取协议所有对象信息
	 * @param protoMethodName
	 * @return
	 */
	@JSONField(serialize =  false)
	public Collection<ProtocolObject> getAllProtoObject() {
		return protoMap.values();
	}
	
	/**
	 * 通过协议方法名, 获取协议id
	 * @param protoMethodName
	 * @return
	 */
	public int getProtoId(String protoMethodName) {
		return protoIdMap.get(protoMethodName);
	}
	
	/**
	 * 通过协议方法名, 获取协议id
	 * @param protoMethodName
	 * @return
	 */
	public BiMap<String, Integer> getProtoIds() {
		return protoIdMap;
	}
	
	/**
	 * 获取对外依赖的对象<br>
	 * 根据依赖的DTO对象, 判断如果匹配, 表示依赖了此对象,返回给调用层
	 * @return
	 */
	public List<String> getDependanceObj(String moduleName, List<String> dtoNames){
		List<String> ret = new ArrayList<>();
		if (dtoNames == null || dtoNames.isEmpty()) {
			return ret;
		}
		for (ProtocolObject ptoto : protoMap.values()) {
			if (StringUtils.equals(ptoto.getModuleName(), moduleName)) {
				continue;
			}
			for (ProtocolStructure struct : ptoto.getStructures().values()) {
				if (!StringUtils.startsWith(struct.getName(), ProtocolConstant.PB_PREFIX) ) {
					continue;
				}
				if(dtoNames.contains(struct.getName())) {
					ret.add(ptoto.getModuleName());
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * proto对象修改
	 * 1. 读取配置重新设置,
	 * 2. 修改协议列表,直接替换成新的
	 * @return 返回被修改的最新的协议对象
	 */
	public ProtocolObject protoObjectUpdate(String moduleName, List<ProtocolStructure> protoStructure) {
		ProtocolObject protoObject = getProtoObject(moduleName);
		if (protoObject == null) {
			return null;
		}
		//		替换掉协议信息
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
		List<String> dependans = getDependanceObj(moduleName, dtoNames);
		protoObject.getDependenceObjs().addAll(dependans);
		save();
		return protoObject;
	}
	
	/**
	 * 反向加载协议结构, 从proto文件内加载
	 *   只有本地缓存内无数据时, 才支持反向加载
	 * @return void  
	 * @date 2021年6月5日下午10:51:15
	 */
	public boolean reverseLoad() {
		if (!protoMap.isEmpty() || !protoIdMap.isEmpty()) {
			return false;
		}
		String protoPath = setting.getProto().getProtoPath();
		this.parse(protoPath);
		save();
		return true;
	}
	
	private void save() {
		//先存储协议
		String data = JSON.toJSONString(protoMap.values(), SerializerFeature.PrettyFormat);
		try {
			FileUtils.writeStringToFile(new File(CommonConstant.PROTO_INFO_PATH), data, StandardCharsets.UTF_8, false);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("save protoMap error, e", e);
		}
		//再存储协议id
		data = JSON.toJSONString(protoIdMap, SerializerFeature.PrettyFormat);
		try {
			FileUtils.writeStringToFile(new File(CommonConstant.PROTO_ID_PATH), data, StandardCharsets.UTF_8, false);
		} catch (IOException e) {
			logger.error("save protoIdMap error, e", e);
		}
	}
	
	@JSONField(serialize = false)
	public Map<String, Integer> getSortProtoIdMap(Collection<ModuleEntity> moduleEntitys){
		Map<String, Integer> linkedMap = new LinkedHashMap<>();
		for (ModuleEntity moduleEntity : moduleEntitys) {
			ProtocolObject protocolObject = this.protoMap.get(moduleEntity.getName());
			for (ProtocolStructure struct : protocolObject.getStructureList()) {
				int protoId = this.protoIdMap.getOrDefault(struct.getName(), 0);
				if (protoId == 0) {
					continue;
				}
				linkedMap.put(struct.getName(), protoId);
			}
		}
		return linkedMap;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//加载proto信息
		File file = new File(CommonConstant.PROTO_INFO_PATH);
		String content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		List<ProtocolObject> protoData = JSON.parseArray(content, ProtocolObject.class);
		protoData.forEach((p) ->{
			protoMap.put(p.getModuleName(), p);
		});
		//加载协议文件
		file = new File(CommonConstant.PROTO_ID_PATH);
		content = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		Map<String, Integer> tempMap = JSON.parseObject(content, new TypeReference<Map<String, Integer>>(){});
		protoIdMap.putAll(tempMap);
		logger.info("protoMap:{}, protoIdMap:{}",protoMap.size(), protoIdMap.size());
	}
	
}
