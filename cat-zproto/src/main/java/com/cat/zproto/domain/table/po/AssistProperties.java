package com.cat.zproto.domain.table.po;

import org.apache.commons.lang3.StringUtils;

public class AssistProperties extends Properties{
	
	/**
	 * 辅助对象名字
	 */
	private String entityName;
	/**
	 * 字段信息
	 */
	private String fieldDetails;

	public String getFieldDetails() {
		return fieldDetails;
	}

	public void setFieldDetails(String fieldDetails) {
		this.fieldDetails = fieldDetails;
		if (StringUtils.isBlank(fieldDetails)) {
			return;
		}
		String [] props = fieldDetails.trim().split("_");
		setType(props[0]);
		setField(props[1]);
	}
	


	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	
}
