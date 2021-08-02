package com.cat.zproto.dto;

import com.cat.zproto.domain.table.po.AssistProperties;
import com.cat.zproto.domain.table.po.Properties;

import java.util.List;

public class DataBeanDto {
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 模块id
	 */
	private int moduleId;
	/**
	 * 自身的属性
	 */
	private List<Properties> propertiesDtos;
	/**
	 * 依赖对象的属性
	 */
	private List<AssistProperties> assistPropertiesDtos;
	
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
	public List<Properties> getPropertiesDtos() {
		return propertiesDtos;
	}
	public void setPropertiesDtos(List<Properties> propertiesDtos) {
		this.propertiesDtos = propertiesDtos;
	}
	public List<AssistProperties> getAssistPropertiesDtos() {
		return assistPropertiesDtos;
	}
	public void setAssistPropertiesDtos(List<AssistProperties> assistPropertiesDtos) {
		this.assistPropertiesDtos = assistPropertiesDtos;
	}

	
//	public static class PropertiesDto{
//		/**
//		 * 依赖对象名字
//		 */
//		private String assistEntityName;
//		/**
//		 * 修改编号id
//		 */
//		private int indexId;
//		/**
//		 *  Java字段
//		 */
//		private String field;
//		/**
//		 * 描述
//		 */
//		private String desc;
//		/**
//		 * 类型
//		 */
//		private String type;
//		/**
//		 * 关键字
//		 */
//		private String keyword;
//		/**
//		 * 初始化方法
//		 */
//		private String init;
//		
//		public String getField() {
//			return field;
//		}
//		public void setField(String field) {
//			this.field = field;
//		}
//		public String getDesc() {
//			return desc;
//		}
//		public void setDesc(String desc) {
//			this.desc = desc;
//		}
//		public String getType() {
//			return type;
//		}
//		public void setType(String type) {
//			this.type = type;
//		}
//		public String getAssistEntityName() {
//			return assistEntityName;
//		}
//		public void setAssistEntityName(String assistEntityName) {
//			this.assistEntityName = assistEntityName;
//		}
//		public int getIndexId() {
//			return indexId;
//		}
//		public void setIndexId(int indexId) {
//			this.indexId = indexId;
//		}
//		public String getKeyword() {
//			return keyword;
//		}
//		public void setKeyword(String keyword) {
//			this.keyword = keyword;
//		}
//		public String getInit() {
//			return init;
//		}
//		public void setInit(String init) {
//			this.init = init;
//		}
//	}


}
