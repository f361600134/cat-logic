package com.cat.server.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 游戏服配置
 * 
 * @author Jeremy
 * @date 2020年6月29日
 *
 */
@Configuration
public class ServerConfig {

	// 服务器ip
	@Value("${cat.game.server.serverId}")
	private int serverId;
	// 后台地址
	@Value("${cat.game.server.backstageUrl}")
	private String backstageUrl;
	// 后台地址
	@Value("${cat.game.server.remoteUrl}")
	private String remoteUrl;
	// 后台地址
	@Value("${cat.game.server.loginUrl}")
	private String loginUrl;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public String getBackstageUrl() {
		return backstageUrl;
	}

	public void setBackstageUrl(String backstageUrl) {
		this.backstageUrl = backstageUrl;
	}

	public String getRemoteUrl() {
		return remoteUrl;
	}

	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	
}
