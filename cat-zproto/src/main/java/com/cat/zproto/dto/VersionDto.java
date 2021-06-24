package com.cat.zproto.dto;

@Deprecated
public class VersionDto {
	
	/**
	 * 版本号,对内版本号
	 */
	private String version;
	/**
	 * 初始化日期
	 */ 
	private String initDate;
	
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getInitDate() {
		return initDate;
	}
	
	public void setInitDate(String initDate) {
		this.initDate = initDate;
	}
	
	public VersionDto() {
	}
	
	public VersionDto(String version, String initDate) {
		super();
		this.version = version;
		this.initDate = initDate;
	}

}
