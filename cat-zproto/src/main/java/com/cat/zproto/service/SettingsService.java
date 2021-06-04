package com.cat.zproto.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cat.zproto.domain.module.ModuleEntity;
import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * 配置服务, 初始化时生成配置相关信息
 * @author Administrator
 *
 */
public class SettingsService implements InitializingBean{
	
	public static final String FILENAME = "src/main/resources/configdata/module.json";
	
	 /**
     * 模块id,模块
     * 只能本模块操作 不提供接口开放给外部调用
     */
    private final Map<Integer, ModuleEntity> moduleEntityMap = new TreeMap<>();
    
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
     * @param id
     * @return  
     * @return ModuleEntity  
     * @date 2021年6月2日下午1:38:10
     */
	public Collection<ModuleEntity> getAllModuleEntity() {
		return moduleEntityMap.values();
	}
	
	  /**
     * 覆盖指定entity
     * @param id
     * @return  
     * @return ModuleEntity  
     * @date 2021年6月2日下午1:38:10
     */
	public void replacModuleEntity(ModuleEntity entity) {
		moduleEntityMap.put(entity.getId(), entity);
		save();
	}
	
	/**
     * 覆盖指定entity
     * @param id
     * @return  
     * @return ModuleEntity  
     * @date 2021年6月2日下午1:38:10
     */
	public void removeModuleEntity(int entityId) {
		moduleEntityMap.remove(entityId);
		save();
	}
	
	private void save() {
		String data = JSON.toJSONString(moduleEntityMap.values(), SerializerFeature.PrettyFormat);
		try {
			FileUtils.writeStringToFile(new File(FILENAME), data, StandardCharsets.UTF_8, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void afterPropertiesSet() throws Exception {
		String content = FileUtils.readFileToString(new File(FILENAME), StandardCharsets.UTF_8);
		List<ModuleEntity> entitys = JSON.parseArray(content, ModuleEntity.class);
		entitys.forEach((entity) ->{
			moduleEntityMap.put(entity.getId(), entity);
		});
		
	}
    
}
