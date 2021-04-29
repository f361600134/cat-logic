
package com.cat.robot.module.robot;

public class Robot {
	
	private int index; //下标
	private String name;
	private String pwd;
	private int channal;
	private int serverId;
	
	private int playerId;
	private RobotLogin robotLogin;
	
	public Robot(String name, String pwd) {
		super();
		this.name = name;
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public RobotLogin getRobotLogin() {
		return robotLogin;
	}
	public void setRobotLogin(RobotLogin robotLogin) {
		this.robotLogin = robotLogin;
	}
	public int getChannal() {
		return channal;
	}
	public void setChannal(int channal) {
		this.channal = channal;
	}
	public int getServerId() {
		return serverId;
	}
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
//	public static Robot create() {
//		int accountId = RobotConfig.get().getNextAccountInt();
//		String name = RobotConfig.get().getRobotName()+accountId;
//		Robot robot = new Robot(name, "123456");
//		robot.setIndex(accountId);
//		robot.setChannal(Config.channel);
//		robot.setServerId(Config.serverId);
//		return robot;
//	}
	
	

}
