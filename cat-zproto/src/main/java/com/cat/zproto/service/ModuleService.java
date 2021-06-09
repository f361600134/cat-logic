package com.cat.zproto.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cat.zproto.domain.module.ModuleDomain;
import com.cat.zproto.domain.module.ModuleEntity;
import com.cat.zproto.domain.proto.ProtocolConstant;
import com.cat.zproto.domain.proto.ProtocolObject;
import com.cat.zproto.domain.proto.ProtocolStructure;
import com.cat.zproto.domain.system.SettingConfig;
import com.cat.zproto.domain.system.SettingVersion;
import com.cat.zproto.manager.ModuleManager;

@Service
public class ModuleService implements InitializingBean{
	
	@Autowired private SettingConfig setting;
	
	@Autowired private ModuleManager moduleManager;
	
    /**
     * 获取指定entity
     * @param id
     * @return  
     * @return ModuleEntity  
     * @date 2021年6月2日下午1:38:10
     */
	public ModuleEntity getModuleEntity(String version, int id) {
		ModuleDomain moduDomain = moduleManager.getDomain(version);
		if (moduDomain == null) {
			return null;
		}
		return moduDomain.getModuleEntity(id);
	}
	
	/**
     * 获取指定entity
     * @param id
     * @return  
     * @return ModuleEntity  
     * @date 2021年6月2日下午1:38:10
     */
	@SuppressWarnings("unchecked")
	public Collection<ModuleEntity> getAllModuleEntity(String version) {
		ModuleDomain moduDomain = moduleManager.getDomain(version);
		if (moduDomain == null) {
			return Collections.EMPTY_LIST;
		}
		return moduDomain.getAllModuleEntity();
	}
	
	  /**
     * 覆盖指定entity
     * @param id
     * @return  
     * @return ModuleEntity  
     * @date 2021年6月2日下午1:38:10
     */
	public void replacModuleEntity(String version, ModuleEntity entity) {
		ModuleDomain moduDomain = moduleManager.getDomain(version);
		if (moduDomain == null) {
			return;
		}
		moduDomain.replacModuleEntity(entity);
	}
	
	/**
     * 覆盖指定entity
     * @param id
     * @return  
     * @return ModuleEntity  
     * @date 2021年6月2日下午1:38:10
     */
	public void removeModuleEntity(String version, int entityId) {
		ModuleDomain moduDomain = moduleManager.getDomain(version);
		if (moduDomain == null) {
			return;
		}
		moduDomain.removeModuleEntity(entityId);
	}
	
	/**
	 * 反向加载协议结构,
	 * @return void  
	 * @date 2021年6月5日下午10:51:15
	 */
	public boolean reverseLoad(String version, Collection<ProtocolObject> protoMap, Map<String, Integer> protoIdMap) {
		ModuleDomain domain = moduleManager.getOrCreateDomain(version);
		if (domain == null) {
			return false;
		}
		
		for (ProtocolObject protocolObject : protoMap) {
			for (ProtocolStructure struct : protocolObject.getStructureList()) {
				ModuleEntity entity = new ModuleEntity();
				int moduleId = protoIdMap.getOrDefault(struct.getName(), 0);
				if (moduleId <= 0) {
					continue;
				}
				moduleId = moduleId / ProtocolConstant.PTORO_COEFFICIENT;
				entity.setId(moduleId);
				entity.setName(protocolObject.getModuleName());
				entity.setComment("");
				domain.replacModuleEntity(entity);
			}
		}
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Collection<SettingVersion> versions = setting.getVersionInfo().values();
		for (SettingVersion settingVersion : versions) {
			String version = settingVersion.getVersion();
			ModuleDomain domain = moduleManager.getOrCreateDomain(version);
			String moduleInfoPath = settingVersion.modulePath();
			String content = FileUtils.readFileToString(new File(moduleInfoPath), StandardCharsets.UTF_8);
			List<ModuleEntity> entitys = JSON.parseArray(content, ModuleEntity.class);
			entitys.forEach((entity) ->{
				domain.replacModuleEntity(entity);
			});
		}
	}
    
}
