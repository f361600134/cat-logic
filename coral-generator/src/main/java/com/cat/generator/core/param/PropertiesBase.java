package com.cat.generator.core.param;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

/**
 * 读取配置文件并且解析, 以键值对的方式缓存到临时map
 * 
 * @auth Jeremy
 * @date 2019年2月12日下午5:06:53
 */
public class PropertiesBase {

	public final Logger logger = LoggerFactory.getLogger(PropertiesBase.class);

	private String configPath;

	private static final String defaultConfigPath = "resource/config.properties";
	private static Properties properties;

	/**
	 * key: prefixName, 即: 前缀+对象名 Multimap: index, 下标主键 或者 miankey?
	 */
	public Map<String, Multimap<String, ParamField>> paramMap = Maps.newHashMap();

	public PropertiesBase(String configPath) {
		this.configPath = configPath;
	}

	public PropertiesBase() {

	}

	public static PropertiesBase instance(String configPath) {
		PropertiesBase base = new PropertiesBase(configPath);
		base.properties();
		return base;
	}

	/**
	 * 解析配置表, 并且以键值对的方式缓存到map中
	 * 
	 * @return void
	 * @date 2019年2月12日下午5:10:22
	 */
	private void properties() {
		properties = new Properties();
		try {
			String path = !StringUtils.isBlank(configPath) ? configPath : defaultConfigPath;
			properties.load(new FileInputStream(path));
			for (Object key : properties.keySet()) {
				String k = key.toString();
				// 判断是否为集合
				ParamField paramField = split(k);
				try {
					Multimap<String, ParamField> fieldMap = paramMap.get(paramField.getPrefix());
					if (fieldMap == null) {
						fieldMap = ArrayListMultimap.create();
						paramMap.put(paramField.getPrefix(), fieldMap);
					}
					fieldMap.put(paramField.getKeyIndex(), paramField);
				} catch (Exception e) {
					logger.info("paramField:{}", paramField);
					logger.error("", e);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
//		logger.info("paramMap:" + paramMap);
	}

	private ParamField split(String k) {
		ParamField paramField;
		if (k.contains("[")) {
			int from = k.indexOf("[");
			int to = k.indexOf("]");
			// 解析出中括号内的内容, 作为key或index
			String keyIndex = k.substring(from + 1, to);
			// 解析出字段内容
			String mainWord = (to == k.length() - 1) ? null : k.substring(k.lastIndexOf(".") + 1, k.length());
			// 解析出关键字
			String temp = k.substring(0, to);
			// 解析出前缀
			String prefix = temp.substring(0, from);
			// 解析出值
			String value = properties.getProperty(k);
			paramField = new ParamField(prefix, mainWord, keyIndex, value);
		} else {
			int lastIndex = k.lastIndexOf(".");
			String mainWord = k.substring(lastIndex + 1, k.length());
			// 解析出前缀
			String prefix = k.substring(0, lastIndex);
			String value = properties.getProperty(k);
			paramField = new ParamField(prefix, mainWord, value);
		}
		return paramField;
	}

	public boolean containsKey(String key) {
		return paramMap.containsKey(key);
	}

	/**
	 * 根据mainWord获取到treemap
	 * 
	 * @param key
	 * @return
	 * @return ParamField
	 * @date 2019年3月3日上午12:40:23
	 */
	public Multimap<String, ParamField> getValue(String key) {
		return paramMap.get(key);
	}
}
