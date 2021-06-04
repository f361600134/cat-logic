package com.cat.zproto.domain.system;
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
	private String dbName;
	private int initialSize;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

}
