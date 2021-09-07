package com.cat.robot.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.Commander;
import com.cat.net.network.base.ISession;
import com.cat.net.network.base.Packet;
import com.cat.net.network.controller.DefaultConnectControllerDispatcher;
import com.cat.robot.module.robot.RobotContext;

@Deprecated
public class RobotActor implements IRobotActor {
	
	public static Logger logger = LoggerFactory.getLogger(RobotActor.class);
	
	private DefaultConnectControllerDispatcher dispatcher;
	private RobotContext robotCtx;

	@Override
	public void registDispatcher(DefaultConnectControllerDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	@Override
	public void setContext(RobotContext robotCtx) {
		this.robotCtx = robotCtx;
	}
	
	@Override
	public void setGameSession(ISession gameSession) {
//		this.robotCtx.setGameSession(gameSession);
	}

	@Override
	public void response(Packet packet) {
		try { 
			Commander cmd = dispatcher.getMapper().get(packet.cmd());
			if (cmd == null) {
				return;
			}
//			dispatcher.invoke(robotCtx.getGameSession(), cmd, packet);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


//	@Override
//	public boolean requestGame(RobotContext rc) {
//		return rc.requestGame();
//	}

}
