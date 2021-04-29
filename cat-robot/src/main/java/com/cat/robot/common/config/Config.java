package com.cat.robot.common.config;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;

import com.cat.robot.util.param.ParamMapping;

@ParamMapping(prefix = "config")
public class Config {
	
	public static String robotName = "fengsc";
	public static String password = "fsc123";
	public static int loginChannel = 98;
	public static String loginUrl = "http://47.98.227.15:8998/official/login";
	//服务器信息
	public static int serverId = 903;
	public static String serverIP = "192.168.31.158";
	public static int serverPort = 8901;
	
	public static int robotStartingIndex = 1;	//机器人开始id
	public static int robotGroupCnt = 10; //机器人每组数量
	public static int robotCountPerGroup = 20; //机器人最大id

	public static int loginDelay = 1; //min.
	public static int scriptDelay = 100; //ms
	
	public void setRobotName(String robotName) {
		Config.robotName = robotName;
		System.out.println("=======setRobotName======");
	}

	public void setPassword(String password) {
		Config.password = password;
	}

	public void setLoginChannel(int loginChannel) {
		Config.loginChannel = loginChannel;
	}

	public void setLoginUrl(String loginUrl) {
		Config.loginUrl = loginUrl;
	}

	public void setServerId(int serverId) {
		Config.serverId = serverId;
	}

	public void setServerIP(String serverIP) {
		Config.serverIP = serverIP;
	}

	public void setServerPort(int serverPort) {
		Config.serverPort = serverPort;
	}

	public void setRobotStartingIndex(int robotStartingIndex) {
		Config.robotStartingIndex = robotStartingIndex;
	}

	public void setRobotGroupCnt(int robotGroupCnt) {
		Config.robotGroupCnt = robotGroupCnt;
	}

	public void setRobotCountPerGroup(int robotCountPerGroup) {
		Config.robotCountPerGroup = robotCountPerGroup;
	}

	public void setLoginDelay(int loginDelay) {
		Config.loginDelay = loginDelay;
	}

	public void setScriptDelay(int scriptDelay) {
		Config.scriptDelay = scriptDelay;
	}

	public static String getConfigInfo() {
		
		Mbeans.obtain();
		
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;
		// 遍历线程组树，获取根线程组
		while (group != null) {
			topGroup = group;
			group = group.getParent();
		}
		
		Runtime runtime = Runtime.getRuntime();
		long usedMemory = runtime.totalMemory() - runtime.freeMemory();
		
		StringBuilder builder = new StringBuilder();
		builder.append("\n\n").append("====================================================").append("\n");
		builder.append("虚拟机可用处理器:").append(runtime.availableProcessors()).append("\n");
		builder.append("当前的活动线程总数:").append(topGroup.activeCount()).append("\n");
		builder.append("线程组总数:").append(topGroup.activeGroupCount()).append("\n");
		builder.append("虚拟机可用最大内存:").append(runtime.maxMemory() / 1024 / 1024).append("M").append("\n");
		builder.append("虚拟机占用总内存:").append(runtime.totalMemory() / 1024 / 1024).append("M").append("\n");
		builder.append("虚拟机空闲内存:").append(runtime.freeMemory() / 1024 / 1024).append("M").append("\n");
		builder.append("当前使用内存:").append(usedMemory / 1024 / 1024).append("M").append("\n");
		builder.append("ObjectPendingFinalizationCount:").append(Mbeans.currObtain.getFinallzationCount).append("\n");
		builder.append("heapMemoryUsage:").append(Mbeans.currObtain.heapMemoryUsage).append("\n");
		builder.append("nonHeapMemoryUsage:").append(Mbeans.currObtain.nonHeapMemoryUsage).append("\n");
		
		builder.append("----------------------------------------------------").append("\n");
		builder.append("当前渠道:").append(loginChannel).append("\n");
		builder.append("账号服连接地址:").append(loginUrl).append("\n");
		builder.append("机器人名字:").append(robotName).append("\n");
		builder.append("机器人编号开始于:").append(robotStartingIndex).append("\n");
		builder.append("每组机器人数量:").append(robotGroupCnt).append("\n");
		builder.append("机器人总量:").append(robotCountPerGroup).append("\n");
		builder.append("机器人登录间隔时长:").append(loginDelay).append("s").append("\n");
		builder.append("执行任务间隔时长:").append(scriptDelay).append("ms").append("\n");
		
		builder.append("----------------------------------------------------").append("\n");
		builder.append("连接的服务器ID:").append(serverId).append("\n");
		builder.append("连接的服务器地址:").append(serverIP).append(":").append(serverPort).append("\n");
		builder.append("====================================================").append("\n\n");
		return builder.toString();
	} 
	
	public static class Mbeans {
		public static Mbeans lastObtain = null;
		public static Mbeans currObtain = null;

		public long obtainTime = 0;
		public long getFinallzationCount;
		public String heapMemoryUsage;
		public String nonHeapMemoryUsage;

		public static Mbeans obtain() {
			lastObtain = currObtain;
			currObtain = new Mbeans();
			if (lastObtain == null)
				lastObtain = currObtain;

			currObtain.obtainTime = System.currentTimeMillis();

			MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
			currObtain.getFinallzationCount = memoryMXBean.getObjectPendingFinalizationCount();
			currObtain.heapMemoryUsage = memoryMXBean.getHeapMemoryUsage().toString();
			currObtain.nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage().toString();

			return currObtain;
		}
	}
	
}
