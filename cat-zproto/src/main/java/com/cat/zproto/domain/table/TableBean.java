package com.cat.zproto.domain.table;

/**
 * 	表单行数据信息, 每一列表示一个类型,表示了详细列类型数据
 * @auth Jeremy
 * @date 2019年9月16日上午10:55:41
 */
public class TableBean {

	private String field; // Java字段
	private String desc; // 描述
	private String type; // 类型
	private String tbField; // 表字段

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTbField() {
		return tbField;
	}

	public void setTbField(String tbField) {
		this.tbField = tbField;
	}

	public TableBean() {
	}

	public TableBean(String field, String type, String desc) {
		super();
		this.field = field;
		this.type = type;
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "TableBean [field=" + field + ", desc=" + desc + ", type=" + type + ", tbField=" + tbField + "]";
	}

}
