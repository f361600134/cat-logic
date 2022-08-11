package com.cat.server.core.config;

import java.util.Map;

import com.cat.server.core.config.container.IConfigContainer;
import com.cat.server.core.config.container.IGameConfig;

public interface IConfigManager {
	
	/**
	 * 根据第一个配置, 某些配置只有一项, 并且默认configId为1
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	 public <T extends IGameConfig> T getUniqueConfig(Class<T> clazz);
	
	/**
	 * 判断是否存在此配置
	 * @param <T>
	 * @param clazz 配置类
	 * @param id 配置id
	 * @return true: 存在此配置, false: 不存在
	 */
	 public <T extends IGameConfig> boolean contains(Class<T> clazz, int id);
	
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
