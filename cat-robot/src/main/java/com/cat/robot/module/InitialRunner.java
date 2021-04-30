package com.cat.robot.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cat.robot.common.lifecycle.Lifecycle;
import com.cat.robot.module.robot.Robot;
import com.cat.robot.module.robot.RobotContext;

//	机器人启动函数
@Component
public class InitialRunner implements Lifecycle{
	
	private static final Logger log = LoggerFactory.getLogger(InitialRunner.class);
	
	
	public InitialRunner() {
	}
	
	public void run() throws Exception {
		try {
//			TcpClientStarter starter = new TcpClientStarter("127.0.0.1", 5001, new DefaultServerController());
//			starter.start();
			Robot robot = Robot.create();
			RobotContext context = RobotContext.create(robot);
			context.gameServerConnection();
			context.serverChat();
		} catch (Exception e) {
			log.error("服务器初始化过程出现异常, 启动失败", e);
		}
	}
	
	
	@Override
	public void start() throws Throwable {
		this.run();
	}
	
}
