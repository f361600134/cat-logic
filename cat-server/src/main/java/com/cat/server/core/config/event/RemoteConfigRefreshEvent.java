package com.cat.server.core.config.event;

import com.cat.server.core.config.container.IGameConfig;
import com.cat.server.core.event.BaseEvent;

/**
 * 远程文件刷新事件
 * @author Jeremy
 */
public class RemoteConfigRefreshEvent extends BaseEvent {
	
	public static String ID = RemoteConfigRefreshEvent.class.getSimpleName();
	
	private final Class< ? extends IGameConfig> configClazz;
	private final String fileName;
	
	private RemoteConfigRefreshEvent(Class< ? extends IGameConfig> configClazz, String fileName) {
		this.configClazz = configClazz;
		this.fileName = fileName; 
	}

	public String getFileName() {
		return fileName;
	} 
	
	public Class<? extends IGameConfig> getConfigClazz() {
		return configClazz;
	}
	
	public static RemoteConfigRefreshEvent create(Class< ? extends IGameConfig> configClazz, String fileName){
        return new RemoteConfigRefreshEvent(configClazz, fileName);
    }
	
}
