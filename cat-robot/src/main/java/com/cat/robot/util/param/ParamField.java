package com.cat.robot.util.param;

import java.util.HashMap;
import java.util.Map;

public class ParamField {

	private String prefix; // 不包含下标的name
	private String fullName; // 包含下标的name
	private String mainWord; // 关键字,配置和变量对应
	private String keyIndex; // 下表或标主键

	private final String EMPTY_KEY = "EMPTY_KEY";
	// Key:filed, value:propertiy.value
	private Map<String, String> field2value;

	public ParamField(String prefix, String mainWord, String keyIndex, String fullName, String field, String value) {
		super();
		this.prefix = prefix;
		this.mainWord = mainWord;
		this.keyIndex = keyIndex;
		this.fullName = fullName;
		this.field2value = new HashMap<String, String>();
		this.putField(field, value);
	}

	public ParamField(String prefix, String mainWord, String value) {
		super();
		this.prefix = prefix;
		this.mainWord = mainWord;
		// this.keyIndex = keyIndex;
		this.fullName = mainWord;
		this.field2value = new HashMap<String, String>();
		this.putField(null, value);
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Map<String, String> getField2value() {
		return field2value;
	}

	public void setField2value(Map<String, String> field2value) {
		this.field2value = field2value;
	}

	public void putField(String field, String value) {
		if (field == null || field.equals("")) {
			field = EMPTY_KEY;
		}
		value = value.trim();
		this.field2value.put(field, value);
	}
	
	public void putFields(Map<String, String> field2value) {
		this.field2value.putAll(field2value);
	}

	public String getValue() {
		return this.getValue(null);
	}

	/**
	 * 是否基础类型
	 * 
	 * @param isPrimitive
	 * @return
	 * @return String
	 * @date 2019年3月3日上午1:26:57
	 */
	public Object getValue(boolean isPrimitive) {
		if (isPrimitive) {
			return this.getValue(null);
		}
		return this.getField2value();
	}

	public String getValue(String field) {
		if (field == null || field.equals("")) {
			field = EMPTY_KEY;
		}
		return this.field2value.get(field);
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public ParamField() {
		super();
	}

	public String getMainWord() {
		return mainWord;
	}

	public void setMainWord(String mainWord) {
		this.mainWord = mainWord;
	}

	public String getKeyIndex() {
		return keyIndex;
	}

	public void setKeyIndex(String keyIndex) {
		this.keyIndex = keyIndex;
	}

	@Override
	public String toString() {
		return "ParamField [prefix=" + prefix + ", fullName=" + fullName + ", mainWord=" + mainWord + ", field2value=" + field2value + "]";
	}

}
