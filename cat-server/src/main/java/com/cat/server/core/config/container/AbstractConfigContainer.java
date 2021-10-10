package com.cat.server.core.config.container;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public abstract class AbstractConfigContainer<T extends IGameConfig> implements IConfigContainer<T>{
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	/**
     * 配置类
     */
    protected final Class<T> configClazz;
	
	/**
     * id,配置
     */
    protected Map<Integer, T> configMap;
    
	public AbstractConfigContainer(Class<T> configClazz) {
    	this.configMap = new HashMap<>();
    	this.configClazz = configClazz;
    }
    
    @Override
    public Class<T> getConfigClazz() {
    	return configClazz;
    }
    
    @Override
    public void loadAll() throws Exception {
        String content = readContent();
        load(content);
    }

    @Override
    public void load(String content) throws Exception {
        List<T> allConfigs = parseConfigs(content);
        putAllConfigs(allConfigs);
        logger.debug("config[{}] load success.size:{}", configClazz.getName(), configMap.size());
    }
    
    /**
     * 解析配置文件内容 转为配置类
     * 
     * @param content 配置文件内容
     * @return
     * @throws IOException
     */
    protected List<T> parseConfigs(String content) throws IOException {
    	return JSON.parseArray(content, configClazz);
    }
    
    /**
     * 读取配置文本<br>
     * 暂不支持同时读取多个文件/地址的配置
     * 
     * @return
     * @throws IOException
     */
    protected abstract String readContent() throws IOException;
    
    protected synchronized void putAllConfigs(List<T> allConfigs) {
        Map<Integer, T> newConfigMap = new HashMap<>();
        for (T config : allConfigs) {
            int id = config.getId();
            if (newConfigMap.containsKey(id)) {
                throw new RuntimeException("config[" + configClazz.getName() + "] has repeat id[" + id + "]");
            }
            config.parse();
            newConfigMap.put(id, config);
        }
        configMap = newConfigMap;
    }
    
    @Override
    public T getConfig(int id) {
    	 return configMap.get(id);
    }
	
    @Override
	public void afterLoad(boolean startup) {
		
	}
    
    @Override
	public Map<Integer, T> getAllConfigs() {
		return configMap;
	}
}
