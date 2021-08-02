package com.cat.zproto.domain.table.po;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.ArrayList;

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
	/**
	 *  Java字段
	 */
	private String field;
	/**
	 * 描述
	 */
	private String desc;
	/**
	 * 类型, 如果用excel表格编辑, 特殊符号需要转义.
	 */
	private String type;
	/**
	 * 表字段
	 */
	private String tbField;
	
	//======其他辅助信息可以为空不设置========
	/**
	 * 泛型类型, List,Map的泛型类型
	 */
	private String genericType;
	/**
	 * 初始化方法, 如果用excel表格编辑,返回渲染前需要转义成符号
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
		//return type;
		return StringEscapeUtils.unescapeHtml4(type);
	}

//	public void setType(String type) {
//		this.type = type;
//	}
	
	public void setType(String type) {
		this.type = type;
		//兼容layui转义问题, 如果带有尖括号,服务器转义后存储,否则直接保存类型
		if (this.type.contains("<") && this.type.contains(">")){
			this.type = StringEscapeUtils.escapeHtml4(type);
		}
		//如果是复杂类型, 则获取到泛型类型
		String temp = StringEscapeUtils.unescapeHtml4(this.type);
		if (temp.contains("List<") || temp.contains("Set<")) {
			//List<Integer> || Set<Integer>
			String tem = temp.replaceAll(" ", "");
			int begin = tem.indexOf("<");
			int end = tem.indexOf(">");
			//截取复杂类型的泛型类
			this.genericType = temp.substring(begin+1, end);
		}else if (temp.contains("Map<")) {
			//Map<Integer, Integer>
			String tem = temp.replaceAll(" ", "");
			int begin = tem.indexOf(",");
			int end = tem.indexOf(">");
			//截取复杂类型的泛型类
			this.genericType = tem.substring(begin+1, end);
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
		//return init;
		return StringEscapeUtils.unescapeHtml4(init);
	}

	public void setInit(String init) {
		this.init = init;
		//如果没有设置初始化目标, 则默认目标
		if (StringUtils.isBlank(init) && !StringUtils.isBlank(this.type)){
			if (this.type.contains("List")){
				this.init = "new ArrayList<>()";
			}else if (this.type.contains("Set")){
				this.init = "new HashSet<>()";
			}else if (this.type.contains("Map")){
				this.init = "new HashMap<>()";
			}
		}
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
