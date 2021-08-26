package com.cat.generator.core.param;

public class ParamField {

	private String prefix; // 前缀不包含关键字
	private String fullName; // 包含关键字的全名
	private String mainWord; // 关键字
	private String keyIndex; // 下标或key
	private String value;

	public ParamField(String prefix, String mainWord, String keyIndex, String value) {
		super();
		this.prefix = prefix;
		this.mainWord = mainWord;
		this.keyIndex = keyIndex;
		this.fullName = prefix + "." + mainWord;
		this.value = value;
	}

	public ParamField(String prefix, String mainWord, String value) {
		super();
		this.prefix = prefix;
		this.mainWord = mainWord;
		this.keyIndex = mainWord;
		this.fullName = prefix + "." + mainWord;
		this.value = value;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "ParamField [prefix=" + prefix + ", fullName=" + fullName + ", mainWord=" + mainWord + ", keyIndex="
				+ keyIndex + ", value=" + value + "]";
	}

}
