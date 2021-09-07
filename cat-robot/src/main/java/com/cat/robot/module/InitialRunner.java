package com.cat.robot.module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cat.robot.common.lifecycle.Lifecycle;
import com.cat.robot.module.chat.proto.ReqChat;
import com.cat.robot.module.robot.Robot;
import com.cat.robot.module.robot.RobotContext;

//	机器人启动函数
@Component
public class InitialRunner implements Lifecycle {

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
			context.serverLogin();
//			Thread.sleep(100);
			// 测试GM命令
//			ReqChat req = ReqChat.create();
//			req.setChatChannel(0);
//			req.setContent("@resource 10001,1,10002,-10");
//			req.setPlayerId(-1);
//			context.send(req);

			ReqChat req = ReqChat.create();
			req.setChannel(0);
			req.setContent("@mail sendMail,1");
			req.setRecvId(-1);
			context.send(req);

		} catch (Exception e) {
			log.error("服务器初始化过程出现异常, 启动失败", e);
		}
	}

	@Override
	public void start() throws Throwable {
		this.run();
	}

}
