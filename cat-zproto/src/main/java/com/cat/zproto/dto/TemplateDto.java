package com.cat.zproto.dto;

/**
 * 模板dto
 * @author Administrator
 *
 */
public class TemplateDto {
	
	private String fileName;
	private String content;
	
	public TemplateDto() {}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
