package com.cat.zproto.domain.table.po;

import org.apache.commons.text.StringEscapeUtils;

/**
 * 	表单行数据信息, 每一列表示一个类型,表示了详细列类型数据
 * @auth Jeremy
 * @date 2019年9月16日上午10:55:41
 */
public class Properties {
	
	/**
	 * 编号id, 每个字段拥有唯一的一个编号id.增加字段时自动生成
	 */
	private int indexId;

	private String field; // Java字段
	private String desc; // 描述
	private String type; // 类型
	private String tbField; // 表字段
	
	//======其他辅助信息可以为空不设置========
	/**
	 * 泛型类型, List,Map的泛型类型
	 */
	private String genericType;
	/**
	 * 初始化方法
	 */
	private String init;
	/**
	 * 关键字
	 */
	private String keyword;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

//	public void setType(String type) {
//		this.type = type;
//	}
	
	public void setType(String type) {
		this.type = type;
//		this.type = StringEscapeUtils.escapeHtml4(type);
		if (this.type.startsWith("List<") || this.type.startsWith("Set<")) {
			//List<Integer> || Set<Integer>
			String tem = type.replaceAll(" ", "");
			int begin = tem.indexOf("<");
			int end = tem.indexOf(">");
			this.genericType = this.type.substring(begin+1, end);//截取复杂类型的泛型类
		}else if (this.type.startsWith("Map<") || this.type.startsWith("ConcurrentMap<")) {
			//Map<Integer, Integer>
			String tem = this.type.replaceAll(" ", "");
			int begin = tem.indexOf(",");
			int end = tem.indexOf(">");
			this.genericType = tem.substring(begin+1, end);//截取复杂类型的泛型类
		}
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
		this.genericType = "";
		this.init ="";
		this.keyword = "";
	}

	public Properties() {
		this.genericType = "";
		this.init ="";
		this.keyword = "";
	}

	public Properties(String field, String type, String desc) {
		super();
		this.field = field;
		this.type = type;
		this.desc = desc;
	}

	public String getGenericType() {
		return genericType;
	}

//	public void setGenericType(String genericType) {
//		this.genericType = genericType;
//	}

	public String getInit() {
		return init;
	}

	public void setInit(String init) {
		this.init = init;
//		this.init = StringEscapeUtils.escapeHtml4(init);
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public int getIndexId() {
		return indexId;
	}

	public void setIndexId(int indexId) {
		this.indexId = indexId;
	}

	@Override
	public String toString() {
		return "TableBean [field=" + field + ", desc=" + desc + ", type=" + type + ", tbField=" + tbField + "]";
	}

}
