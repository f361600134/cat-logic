package com.cat.zproto.domain.proto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cat.zproto.common.SpringContextHolder;
import com.cat.zproto.constant.CommonConstant;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingVersion;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;

public class ProtocolDomain {
	
	private String version;
	
	/**
	 * key: 模块名
	 * value: 协议信息
	 */
	private final Map<String, ProtocolObject> protoMap = Maps.newHashMap();
	
	/**
	 * key:协议名
	 * value: 协议号
	 */
	private final HashBiMap<String, Integer> protoIdMap = HashBiMap.create();
	
	public ProtocolDomain() {
	}
	
	public ProtocolDomain(String version) {
		super();
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, ProtocolObject> getProtoMap() {
		return protoMap;
	}
	
	/**
	 * 通过协议方法名, 获取协议对象信息
	 * @param protoMethodName
	 * @return
	 */
	public ProtocolObject getProtoObject(String protoMethodName) {
		return protoMap.get(protoMethodName);
	}
	
	/**
	 * 通过协议方法名, 获取协议对象信息
	 * @param protoMethodName
	 * @return
	 */
	public ProtocolObject getOrCreateProtoObject(String protoMethodName) {
		ProtocolObject ret = protoMap.get(protoMethodName);
		if (ret == null) {
			ret = new ProtocolObject();
		}
		protoMap.put(protoMethodName, ret);
		return ret;
	}
	
	/**
	 * 通过协议方法名, 获取协议所有对象信息
	 * @param protoMethodName
	 * @return
	 */
	public Collection<ProtocolObject> getAllProtoObject() {
		return protoMap.values();
	}
	
	/**
	 * 添加proto对象
	 * @param protoObj
	 */
	public void putProtoObject(ProtocolObject protoObj) {
		this.protoMap.put(protoObj.getModuleName(), protoObj);
	}
	
	/**
	 * 添加proto对象
	 * @param protoObj
	 */
	public void putProtoObject(Map<String, ProtocolObject> protoObjMap) {
		this.protoMap.putAll(protoObjMap);
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
	public void putProtoId(String protoMethodName, int protoId) {
		protoIdMap.putIfAbsent(protoMethodName, protoId);
	}
	
	/**
	 * 通过协议方法名, 获取协议id
	 * @param protoMethodName
	 * @return
	 */
	public void putProtoId(Map<String, Integer> protoIdMap) {
		protoIdMap.putAll(protoIdMap);
	}
	
	/**
	 * @param protoMethodName
	 * @return
	 */
	public BiMap<String, Integer> getProtoIdMap() {
		return protoIdMap;
	}

	/**
	 * 获取引用obj对象
	 * @param moduleName
	 * @param dtoNames
	 * @return
	 */
	public List<String> getDependanceObj(String moduleName, List<String> dtoNames){
		List<String> ret = new ArrayList<>();
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
	 * 初始化
	 * @throws IOException 
	 */
	public void init(String protoPath, String protoIdPath) throws IOException {
		//基于项目根目录初始化协议
		String content = FileUtils.readFileToString(new File(protoPath), StandardCharsets.UTF_8);
		
		//way2. 基于resources目录的读取配置
//		ClassPathResource resource = new ClassPathResource(protoPath);
//		InputStream inputStream = resource.getInputStream();
//		String content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
		
		List<ProtocolObject> protoData = JSON.parseArray(content, ProtocolObject.class);
		protoData.forEach((p) ->{
			protoMap.put(p.getModuleName(), p);
		});
		
		content = FileUtils.readFileToString(new File(protoIdPath), StandardCharsets.UTF_8);
		//初始化协议号, 基于resources目录的读取配置
//		resource = new ClassPathResource(protoIdPath);
//		inputStream = resource.getInputStream();
//		content = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
		Map<String, Integer> tempMap = JSON.parseObject(content, new TypeReference<Map<String, Integer>>(){});
		protoIdMap.putAll(tempMap);
	}
	
	/**
	 * 初始化
	 * @throws IOException 
	 */
	public void init(Map<String, ProtocolObject> map, HashBiMap<String, Integer> biMap) {
		this.protoMap.putAll(map);
		this.protoIdMap.putAll(biMap);
	}
	
	
	/**
	 * 存储协议对象
	 */
	public void save() {
		//先存储协议
		String data = JSON.toJSONString(protoMap.values(), SerializerFeature.PrettyFormat);
		SettingConfig config = SpringContextHolder.getBean(SettingConfig.class);
		SettingVersion settingVersion = config.getVersionInfo().get(version);
		String path = settingVersion.getProtoDataPath();
		try {
			FileUtils.writeStringToFile(new File(path), data, StandardCharsets.UTF_8, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 存储协议号
	 */
	public void saveProtoId() {
		//再存储协议id
		String data = JSON.toJSONString(protoIdMap, SerializerFeature.PrettyFormat);
		SettingConfig config = SpringContextHolder.getBean(SettingConfig.class);
		SettingVersion settingVersion = config.getVersionInfo().get(version);
		String path = settingVersion.getProtoDataPath();
		try {
			FileUtils.writeStringToFile(new File(path), data, StandardCharsets.UTF_8, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
