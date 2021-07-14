package com.cat.zproto.dto;

public class DataBeanDto {
	
	/**
	 * 依赖对象名字
	 */
	private String assistEntityName;
	/**
	 *  Java字段
	 */
	private String field;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 关键字
	 */
	private String keyword;
	/**
	 * 初始化方法
	 */
	private String init;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 模块id
	 */
	private int moduleId;
	/**
	 * 修改编号id
	 */
	private int indexId;
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAssistEntityName() {
		return assistEntityName;
	}
	public void setAssistEntityName(String assistEntityName) {
		this.assistEntityName = assistEntityName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public int getIndexId() {
		return indexId;
	}
	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getInit() {
		return init;
	}
	public void setInit(String init) {
		this.init = init;
	}
}
