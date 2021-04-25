package com.cat.server.core.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.server.common.ClassManager;
import com.cat.server.core.config.annotation.ConfigPath;
import com.cat.server.core.config.annotation.ConfigUrl;
import com.cat.server.core.config.container.DefaultLocalConfigContainer;
import com.cat.server.core.config.container.DefaultRemoteConfigContainer;
import com.cat.server.core.config.container.IConfigContainer;
import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.context.SpringContextHolder;
import com.cat.server.core.lifecycle.Lifecycle;
import com.cat.server.core.lifecycle.Priority;

public class ConfigManager implements IConfigManager, Lifecycle {

	private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

	private final Map<Class<? extends IGameConfig>, IConfigContainer<?>> containers;

	public ConfigManager() {
		this.containers = new HashMap<>();
	}

	public static ConfigManager getInstance() {
		return SpringContextHolder.getBean(ConfigManager.class);
	}

	@Override
	public <T extends IGameConfig> T getConfig(Class<T> clazz, int id) {
		IConfigContainer<T> container = getContainer(clazz);
		if (container == null) {
			return null;
		}
		return container.getConfig(id);
	}

	@Override
	public <T extends IGameConfig> Map<Integer, T> getAllConfigs(Class<T> clazz) {
		IConfigContainer<T> container = getContainer(clazz);
		if (container == null) {
			return null;
		}
		return container.getAllConfigs();
	}

	@Override
	public Map<Class<? extends IGameConfig>, IConfigContainer<?>> getContainers() {
		return containers;
	}

	@SuppressWarnings("unchecked")
	public <T extends IGameConfig> IConfigContainer<T> getContainer(Class<T> clazz) {
		if (clazz == null) {
			throw new NullPointerException("getContainer error.clazz is null.");
		}
		IConfigContainer<?> container = containers.get(clazz);
		if (container == null) {
			logger.warn("getContainer[{}] fail.container not exists.", clazz.getName());
			return null;
		}
		return (IConfigContainer<T>) container;
	}

	@SuppressWarnings("rawtypes")
	public void registerContainer(IConfigContainer<? extends IGameConfig> container) {
		Class<? extends IGameConfig> configClazz = container.getConfigClazz();
		IConfigContainer<?> oldContainer = containers.put(configClazz, container);
		if (oldContainer == null) {
			return;
		}
		Class<? extends IConfigContainer> curContainer = container.getClass();
		Class<? extends IConfigContainer> otherContainer = oldContainer.getClass();
		if (otherContainer.equals(curContainer)) {
			logger.warn("repeat register config[{}].", configClazz.getName());
		} else {
			logger.error("repeat register config[{}].container1[{}],container2[{}]", configClazz.getName(),
					otherContainer.getName(), curContainer.getName());
		}
	}

	@SuppressWarnings("unchecked")
	public void onInitialize() throws Exception {
		// 初始化本地配置
		Collection<Class<?>> clazzList = ClassManager.instance().getClassByAnnotationClass(ConfigPath.class);
		for (Class<?> clazz : clazzList) {
			Class<IGameConfig> claz = (Class<IGameConfig>) clazz;
			this.registerContainer(new DefaultLocalConfigContainer<>(claz));
		}
		// 初始化远程配置
		clazzList = ClassManager.instance().getClassByAnnotationClass(ConfigUrl.class);
		for (Class<?> clazz : clazzList) {
			Class<IGameConfig> claz = (Class<IGameConfig>) clazz;
			this.registerContainer(new DefaultRemoteConfigContainer<>(claz));
		}
		for (IConfigContainer<?> container : containers.values()) {
			try {
				container.loadAll();
				container.afterLoad(true);
			} catch (Exception e) {
				logger.error("config[" + container.getConfigClazz().getName() + "] loadAll error.", e);
				throw e;
			}
		}
	}

	@Override
	public void stop() {
	}

	@Override
	public void start() throws Throwable {
		this.onInitialize();
	}

	@Override
	public int priority() {
		return Priority.LOGIC_INITIALIZATION.getPriority();
	}

}
