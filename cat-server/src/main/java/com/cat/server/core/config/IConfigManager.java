package com.cat.server.core.config;

import java.util.Map;

import com.cat.server.core.config.container.IConfigContainer;
import com.cat.server.core.config.container.IGameConfig;

public interface IConfigManager {
	
	/**
	 * 根据id获取配置
	 * @param <T>
	 * @param clazz
	 * @param id
	 * @return
	 */
	 public <T extends IGameConfig> T getConfig(Class<T> clazz, int id);
	 
	 /**
	  * 获取所有配置
	  * @param <T>
	  * @param clazz
	  * @return
	  */
	 public <T extends IGameConfig> Map<Integer, T> getAllConfigs(Class<T> clazz);
	 
	 /**
	  * 获取配置容器
	  * @return
	  */
	 public Map<Class<? extends IGameConfig>, IConfigContainer<?>> getContainers();
	 

}
