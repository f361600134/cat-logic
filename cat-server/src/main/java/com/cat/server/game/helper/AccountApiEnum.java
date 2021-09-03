package com.cat.server.game.helper;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cat.server.common.ServerConfig;
import com.cat.server.core.context.SpringContextHolder;

public enum AccountApiEnum {
	
	/**
	 * 账号验证地址
	 */
	LOGIN("/coral/official/login"),
	
	;
	private String api;
	
	private AccountApiEnum(String api) {
		this.api = api;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}
	
	/**
	 * 缓存跟设置相关的所有api路径<br>
	 * 避免每次请求生成新的字符串
	 */
	public static Map<AccountApiEnum, String> fullPathMap = Collections.synchronizedMap(new EnumMap<>(AccountApiEnum.class));

	/**
	 * 获取api
	 * @param aEnum
	 * @return
	 */
	public static String getApi(AccountApiEnum aEnum) {
		String path = fullPathMap.get(aEnum);
		if (StringUtils.isBlank(path)) {
			synchronized(fullPathMap) {
				ServerConfig config = SpringContextHolder.getBean(ServerConfig.class);
				//组装path
				path = config.getLoginUrl().concat(aEnum.getApi());
				fullPathMap.put(aEnum, path);
			}
		}
		return path;
	}
	
	
}

