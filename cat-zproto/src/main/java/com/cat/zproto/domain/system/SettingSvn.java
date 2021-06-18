package com.cat.zproto.domain.system;

public class SettingSvn {
	
	/**
	 * svn账号
	 */
	private String account;
	/**
	 * svn密码
	 */
	private String password;
	/**
	 * 资源checkout路径
	 */
	private String sourceCheckOutUrl;
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSourceCheckOutUrl() {
		return sourceCheckOutUrl;
	}

	public void setSourceCheckOutUrl(String sourceCheckOutUrl) {
		this.sourceCheckOutUrl = sourceCheckOutUrl;
	}
	
}
