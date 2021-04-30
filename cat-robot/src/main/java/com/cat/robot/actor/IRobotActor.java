package com.cat.robot.actor;

import com.cat.net.network.base.GameSession;
import com.cat.net.network.base.Packet;
import com.cat.net.network.process.ControllerDispatcher;
import com.cat.robot.module.robot.RobotContext;

public interface IRobotActor {
	
	//	注册协议分发器
	void registDispatcher(ControllerDispatcher obj);
	
	//	设置机器人上下文
	void setContext(RobotContext robotCtx);
	
	//	设置session
	void setGameSession(GameSession gameSession);
	
	//消息响应
	void response(Packet packet);
	
//	public boolean requestGame(RobotContext robotCtx);
}
