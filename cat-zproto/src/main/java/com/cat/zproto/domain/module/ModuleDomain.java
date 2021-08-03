package com.cat.zproto.domain.module;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cat.zproto.common.SpringContextHolder;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingVersion;
import com.cat.zproto.domain.table.po.TableEntity;
import com.cat.zproto.dto.TableFreemarkerDto;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ModuleDomain {

	private String version;
	private Map<Integer, ModuleEntity> moduleEntityMap = new TreeMap<>();

	public ModuleDomain() {

	}
	public ModuleDomain(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<Integer, ModuleEntity> getModuleEntityMap() {
		return moduleEntityMap;
	}

	/**
	 * 获取指定entity
	 * @param id
	 * @return
	 * @return ModuleEntity
	 * @date 2021年6月2日下午1:38:10
	 */
	public ModuleEntity getModuleEntity(int id) {
		return moduleEntityMap.get(id);
	}

	/**
	 * 获取指定entity
	 * @return ModuleEntity
	 * @date 2021年6月2日下午1:38:10
	 */
	public Collection<ModuleEntity> getAllModuleEntity() {
		return moduleEntityMap.values();
	}

	/**
	 * 覆盖指定entity
	 * @return ModuleEntity
	 * @date 2021年6月2日下午1:38:10
	 */
	public void replacModuleEntity(ModuleEntity entity) {
		moduleEntityMap.put(entity.getId(), entity);
		this.save();
	}
	
	/**
	 * 覆盖指定entity
	 * @return ModuleEntity
	 * @date 2021年6月2日下午1:38:10
	 */
	public void initModuleEntity(List<ModuleEntity> entitys) {
		if (entitys == null || entitys.isEmpty()) {
			return;
		}
		entitys.forEach((e)->{
			moduleEntityMap.put(e.getId(), e);
		});
	}

	/**
	 * 覆盖指定entity
	 * @return ModuleEntity
	 * @date 2021年6月2日下午1:38:10
	 */
	public void removeModuleEntity(int entityId) {
		moduleEntityMap.remove(entityId);
		this.save();
	}
	
	/**
	 * 存储结构信息
	 */
	private void save() {
		String data = JSON.toJSONString(moduleEntityMap.values(), SerializerFeature.PrettyFormat);
		try {
			SettingConfig config = SpringContextHolder.getBean(SettingConfig.class);
			SettingVersion settingVersion = config.getVersionInfo().get(version);
			String moduleInfoPath = settingVersion.getModulePath();
			FileUtils.writeStringToFile(new File(moduleInfoPath), data, StandardCharsets.UTF_8, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
