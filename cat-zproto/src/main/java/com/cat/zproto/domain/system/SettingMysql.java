package com.cat.zproto.domain.system;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 数据源是mysql数据库<br>
 * 如果数据源是excel表/nosql怎么办<br>
 * 所以此处是否需要支持多数据源? 还是说只支持一个mysql即可<br>
 * 目前先实现mysql版本
 */
public class SettingMysql {
	private String url;
	private String username;
	private String password;
	//库名,不配置了,直接通url截取
	private String dbName;
	//初始连接, 不配置了, 直接写死1条连接
	private int initialSize;

	public SettingMysql() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		//截取dbName
		int end = url.indexOf("?");
		int start = url.lastIndexOf("/", end)+1;
		if (start > 0 && end > 0) {
			this.dbName = url.substring(start, end);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public int getInitialSize() {
		return initialSize;
	}

	public void setInitialSize(int initialSize) {
		this.initialSize = initialSize;
	}
	
	public DruidDataSource newDruidDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(this.getUrl());
		dataSource.setUsername(this.getUsername());
		dataSource.setPassword(this.getPassword());
		dataSource.setInitialSize(this.getInitialSize());
		return dataSource;
	}
	
}
