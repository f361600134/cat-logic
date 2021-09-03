package com.cat.robot.actor;

import com.cat.net.network.base.ISession;
import com.cat.net.network.base.Packet;
import com.cat.net.network.process.CommanderProtocolMapper;
import com.cat.robot.module.robot.RobotContext;

public interface IRobotActor {
	
	//	注册协议分发器
	void registDispatcher(CommanderProtocolMapper obj);
	
	//	设置机器人上下文
	void setContext(RobotContext robotCtx);
	
	//	设置session
	void setGameSession(ISession gameSession);
	
	//消息响应
	void response(Packet packet);
	
//	public boolean requestGame(RobotContext robotCtx);
}
