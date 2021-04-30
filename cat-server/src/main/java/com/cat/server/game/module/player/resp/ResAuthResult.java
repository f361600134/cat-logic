package com.cat.server.game.module.player.resp;

public class ResAuthResult {

	// 错误码
	private int code;
	// 错误描述
	private String tips;
	// 成功
	private String username;// 数据库账号,生成的唯一标识name
	private String name; // 服务器名
	private Integer id; // 服务器id
	private String ip; // 游戏服ip
	private Integer port; // 游戏服端口
	private Integer status; // 0:流畅,1:繁忙,2:爆满,3:未开服,4:维护,5:关闭,6测试
	private String sessionKey; // 临时登录身份
//	private String webip; // webip
//	private Integer webport; // web端口
	private String inputName; // 玩家输入的账号
	private Integer loginSid; // 登录服id

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

//	public String getWebip() {
//		return webip;
//	}
//
//	public void setWebip(String webip) {
//		this.webip = webip;
//	}
//
//	public Integer getWebport() {
//		return webport;
//	}
//
//	public void setWebport(Integer webport) {
//		this.webport = webport;
//	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public Integer getLoginSid() {
		return loginSid;
	}

	public void setLoginSid(Integer loginSid) {
		this.loginSid = loginSid;
	}

	@Override
	public String toString() {
		return "ResAuthResult [code=" + code + ", tips=" + tips + ", username=" + username + ", name=" + name + ", id="
				+ id + ", ip=" + ip + ", port=" + port + ", status=" + status + ", sessionKey=" + sessionKey
				+ ", inputName=" + inputName + ", loginSid=" + loginSid + "]";
	}
	
}
