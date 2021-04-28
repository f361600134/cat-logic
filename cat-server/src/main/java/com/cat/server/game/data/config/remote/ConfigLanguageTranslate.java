package com.cat.server.game.data.config.remote;

import java.util.HashMap;
import java.util.Map;

import com.cat.server.core.config.annotation.ConfigUrl;
import com.cat.server.core.config.container.IGameConfig;

/**
 * 多语言具体内容
 *
 * @author lpg 2020年12月17日
 */
//@ConfigUrl("language_translate.json")
public class ConfigLanguageTranslate implements IGameConfig {

	private int id; //唯一id
	private Map<String, String> content;
	private long updateTime;
	
	public ConfigLanguageTranslate(int id) {
		this.id = id;
		this.content = new HashMap<>();
	}
	
	public Map<String, String> getContent() {
		return content;
	}
	public void setContent(Map<String, String> content) {
		this.content = content;
	}
	
	public long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
	public void addContent(String k, String v) {
		this.content.put(k, v);
	}
	
	@Override
	public String toString() {
		return "LanguageTranslate [content=" + content + ", updateTime=" + updateTime +"]";
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

}
