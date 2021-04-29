package com.cat.robot.actor;

import com.cat.net.network.base.Packet;
import com.cat.robot.module.robot.RobotContext;

public interface IRobotActor {
	
	//注册监听
	void registCmdObj(Object obj);
	
	//设置机器人上下文
	void setContext(RobotContext robotCtx);
	
	//消息响应
	void response(Packet packet);
	
//	public boolean requestGame(RobotContext robotCtx);
}
