package com.cat.rank.common;

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

	/**
	 * 后台地址
	 */
	@Value("${cat.game.server.backstageUrl}")
	private String backstageUrl;
	/**
	 * 后台地址
	 */
	@Value("${cat.game.server.remoteUrl}")
	private String remoteUrl;

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

}
