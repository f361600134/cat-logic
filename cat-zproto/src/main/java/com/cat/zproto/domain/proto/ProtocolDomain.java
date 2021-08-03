package com.cat.zproto.domain.proto;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cat.zproto.constant.CommonConstant;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cat.zproto.common.SpringContextHolder;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingVersion;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtocolDomain {


	private static Logger logger = LoggerFactory.getLogger(ProtocolDomain.class);
	
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
		this.protoIdMap.putIfAbsent(protoMethodName, protoId);
	}
	
	/**
	 * 通过协议方法名, 获取协议id
	 * @return
	 */
	public void replaceAllProtoId(Map<String, Integer> protoIdMap) {
		this.protoIdMap.clear();
		this.protoIdMap.putAll(protoIdMap);
	}
	
	/**
	 * @return
	 */
	public BiMap<String, Integer> getProtoIdMap() {
		return this.protoIdMap;
	}

	/**
	 * 获取引用obj对象
	 * @param moduleName
	 * @param dtoNames
	 * @return
	 */
	public List<String> getDependanceObj(String moduleName, List<String> dtoNames){
		SettingConfig config = SpringContextHolder.getBean(SettingConfig.class);
		List<String> ret = new ArrayList<>();
		for (ProtocolObject ptoto : protoMap.values()) {
			if (StringUtils.equals(ptoto.getModuleName(), moduleName)) {
				continue;
			}
			for (ProtocolStructure struct : ptoto.getStructures().values()) {
//				if (!StringUtils.startsWith(struct.getName(), ProtocolConstant.PB_PREFIX) ) {
				if (!StringUtils.startsWith(struct.getName(), config.getProto().getPbPrefix()) ) {
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
	 * 获取所有PB对象
	 * @return
	 */
	public List<String> getAllPbProtoName(String prefix){
		List<String> ret = new ArrayList<>();
		for (ProtocolObject ptoto : protoMap.values()) {
			for (ProtocolStructure struct : ptoto.getStructures().values()) {
				if (!StringUtils.startsWith(struct.getName(), prefix) ) {
					continue;
				}
				ret.add(struct.getName());
			}
		}
		return ret;
	}
	
	/**
	 * 初始化
	 * @throws IOException 
	 */
	public void init(String protoPath, String protoIdPath) throws IOException {
		//加载协议文件
		File file = new File(protoPath);
		for (File f: file.listFiles()) {
			String content = FileUtils.readFileToString(f, StandardCharsets.UTF_8);
			ProtocolObject protocolObject = JSON.parseObject(content, ProtocolObject.class);
			protoMap.put(protocolObject.getModuleName(), protocolObject);
		}
		//加载协议id
		String content = FileUtils.readFileToString(new File(protoIdPath), StandardCharsets.UTF_8);
		Map<String, Integer> tempMap = JSON.parseObject(content, new TypeReference<Map<String, Integer>>(){});
		this.protoIdMap.putAll(tempMap);
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
	 * 存储指定协议对象
	 */
	public void save(String moduleName)  {
		SettingConfig config = SpringContextHolder.getBean(SettingConfig.class);
		SettingVersion settingVersion = config.getVersionInfo().get(version);
		String versionPath = settingVersion.getProtoDataDir();
		try {
			//创建目录
			FileUtils.forceMkdir(new File(versionPath));
		}catch (Exception e){
			e.printStackTrace();
		}
		ProtocolObject protocolObject = protoMap.get(moduleName);
		if (protocolObject == null){
			return;
		}
		String data = JSON.toJSONString(protocolObject, SerializerFeature.PrettyFormat);
		String path = versionPath.concat(File.separator).concat(protocolObject.getModuleName()).concat(CommonConstant.JSON_SUBFIX);
		try {
			FileUtils.writeStringToFile(new File(path), data, StandardCharsets.UTF_8, false);
		}catch (Exception e){
			e.printStackTrace();
			logger.error("ProrocalDomain save error, ", e);
		}
	}


	/**
	 * 存储所有协议对象<br>
	 */
	public void saveAll()  {
		for (ProtocolObject protocolObject : protoMap.values()) {
			this.save(protocolObject.getModuleName());
		}
		this.saveProtoId();
	}
	
	/**
	 * 存储协议号<br>
	 *     当协议号发生改变, 覆盖缓存内的所有协议号, 并保存到本地
	 */
	public void saveProtoId() {
		//再存储协议id
		String data = JSON.toJSONString(this.protoIdMap, SerializerFeature.PrettyFormat);
		SettingConfig config = SpringContextHolder.getBean(SettingConfig.class);
		SettingVersion settingVersion = config.getVersionInfo().get(version);
		String path = settingVersion.getProtoDataDir();
		try {
			FileUtils.writeStringToFile(new File(path), data, StandardCharsets.UTF_8, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
