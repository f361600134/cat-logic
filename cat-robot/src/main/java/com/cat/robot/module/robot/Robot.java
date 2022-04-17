
package com.cat.robot.module.robot;

public class Robot {
	/* 编号 */
	private int index;
	/* 名字 */
	private String name;
	/* 密码 */
	private String pwd;
	/* 渠道id */
	private int channal;
	/* 服务器id */
	private int serverId;

	/* 服务器返回的机器人玩家id */
	private int playerId;
	/* 机器人登录相关信息 */
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

	public static Robot create() {
//		int accountId = RobotConfig.get().getNextAccountInt();
//		String name = RobotConfig.get().getRobotName()+accountId;
		int accountId = 1;
		String name = "aa";
		Robot robot = new Robot(name, "123456");
		robot.setIndex(accountId);
//		robot.setChannal(Config.channel);
//		robot.setServerId(Config.serverId);
		robot.setChannal(1);
		robot.setServerId(1);
		RobotLogin robotLogin = new RobotLogin(0, 1, "127.0.0.1", "一区", 5101, "sessionKey", 1, "1", "username");
		robot.setRobotLogin(robotLogin);
		return robot;
	}

}
