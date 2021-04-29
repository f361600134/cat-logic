package com.cat.robot.util.param;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * 读取配置文件并且解析, 以键值对的方式缓存到临时map
 * 
 * @auth Jeremy
 * @date 2019年2月12日下午5:06:53
 */
public class PropertiesBase {

	public final Logger logger = LoggerFactory.getLogger(PropertiesBase.class);
	
	private String configPath;

	private static final String defaultConfigPath = "src/main/resources/application.properties";
	private static Properties properties;
	public Map<String, TreeMap<String, ParamField>> paramMap = Maps.newHashMap();

	
	public PropertiesBase (String configPath) {
		this.configPath = configPath;
	}
	
	public PropertiesBase () {
		
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
	public void properties() {
		properties = new Properties();
		try {
			String path = !StringUtils.isBlank(configPath) ? configPath : defaultConfigPath;
			properties.load(new FileInputStream(path));
			for (Object key : properties.keySet()) {
				String k = key.toString();
				// 判断是否为集合
				ParamField paramField = split(k);
				try {
					TreeMap<String, ParamField> fieldMap = paramMap.get(paramField.getMainWord());
					if (fieldMap == null) {
						fieldMap = Maps.newTreeMap();
						fieldMap.put(paramField.getFullName(), paramField);
					} else {
						ParamField olderField = fieldMap.get(paramField.getFullName());
						if (olderField != null) {
							olderField.putFields(paramField.getField2value());
						} else {
							fieldMap.put(paramField.getFullName(), paramField);
						}
					}
					paramMap.put(paramField.getMainWord(), fieldMap);
				} catch (Exception e) {
					logger.error("paramField:{}", paramField);
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
			String field = (to == k.length() - 1) ? null : k.substring(k.lastIndexOf(".") + 1, k.length());
			// 解析出关键字
			String temp = k.substring(0, to);
			String mainWord = temp.substring(temp.lastIndexOf(".") + 1, from);
			// 解析出前缀
			String prefix = temp.substring(0, temp.lastIndexOf("."));
			// 解析出全名
			String fullName = k.substring(0, to + 1);
			// 解析出值
			String value = properties.getProperty(k);
			paramField = new ParamField(prefix, mainWord, keyIndex, fullName, field, value);
		} else {
			String mainWord = k.substring(k.lastIndexOf(".") + 1, k.length());
			// 解析出前缀
			String prefix = k.substring(0, k.lastIndexOf("."));
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
	public TreeMap<String, ParamField> getValue(String key) {
		return paramMap.get(key);
	}

}
