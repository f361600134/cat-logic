package com.cat.robot.module.robot;

public class RobotLogin {
	
	private String username;//数据库账号,生成的唯一标识name
	private int code; //错误码标识
	private int id;	//服务器id
	private String ip;//游戏服ip
	private String name;//服务器名
	private int port;//游戏服端口
	private String sessionKey;//临时登录身份
	private int status;//0:流畅,1:繁忙,2:火爆,3:爆满,4:维护,5:关闭
	private String webip;		//webip
	private Integer webport;	//web端口
	private String inputName; //玩家输入的账号
	private Integer loginSid; //登录服id

	private String tips;
	
	public RobotLogin() {}
	
	public RobotLogin(int code, int id, String ip, String name, int port, String sessionKey, int status,
			String success, String username) {
		super();
		this.code = code;
		this.id = id;
		this.ip = ip;
		this.name = name;
		this.port = port;
		this.sessionKey = sessionKey;
		this.status = status;
		this.username = username;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public boolean isSuccess() {
		return code == 1;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public String getWebip() {
		return webip;
	}
	public void setWebip(String webip) {
		this.webip = webip;
	}
	public Integer getWebport() {
		return webport;
	}
	public void setWebport(Integer webport) {
		this.webport = webport;
	}
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
		return "RobotLogin [username=" + username + ", code=" + code + ", id=" + id + ", ip=" + ip + ", name=" + name + ", port=" + port + ", sessionKey=" + sessionKey + ", status=" + status + ", webip=" + webip + ", webport=" + webport + ", inputName=" + inputName + ", loginSid=" + loginSid + ", tips=" + tips + "]";
	}
}
