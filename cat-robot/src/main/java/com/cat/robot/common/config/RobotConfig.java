package com.cat.robot.common.config;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

import com.cat.robot.util.RecordingReadUtil;

public class RobotConfig {

	private static RobotConfig defaultIns;

	/** 脚本 */
	private LinkedList<RecordingReadUtil.SavePacket> scriptRecords;
	private AtomicInteger accountGenerator;
	private String ROBOT_NAME;

	RobotConfig() {
		accountGenerator = new AtomicInteger(Config.robotStartingIndex);
		ROBOT_NAME = Config.robotName;
	}

	/**
	 * 机器人配置重置,如果超过了指定机器人数量,那么重新开始
	 * 
	 * @return void
	 * @date 2019年6月13日上午12:03:55
	 */
	public void reset() {
		accountGenerator = new AtomicInteger(Config.robotStartingIndex);
	}

	public String getRobotName() {
		return ROBOT_NAME;
	}

	/**
	 * 获取下一个账号id, 如果账号id大于机器人总数量, 则重置账号生成器
	 * 
	 * @return
	 * @return int
	 * @date 2019年6月13日上午12:12:25
	 */
	public int getNextAccountInt() {
		int accountId = accountGenerator.getAndIncrement();
		if (accountId >= Config.robotCountPerGroup) {
			accountGenerator = new AtomicInteger(Config.robotStartingIndex);
		}
		return accountId;
	}

	public void loadScript() {
		scriptRecords = RecordingReadUtil.fromFile();
	}

	public LinkedList<RecordingReadUtil.SavePacket> getScriptRecords() {
		return scriptRecords;
	}

	public static RobotConfig get() {
		if (defaultIns == null) {
			defaultIns = create();
		}
		return defaultIns;
	}

	public static RobotConfig create() {
		try {
			RobotConfig config = new RobotConfig();
			config.loadScript();
			return config;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
