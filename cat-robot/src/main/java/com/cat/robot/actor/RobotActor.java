package com.cat.robot.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.ISession;
import com.cat.net.network.base.Packet;
import com.cat.net.network.process.CommanderProtocolMapper;
import com.cat.robot.module.robot.RobotContext;

public class RobotActor implements IRobotActor {
	
	public static Logger logger = LoggerFactory.getLogger(RobotActor.class);
	
	private CommanderProtocolMapper dispatcher;
	private RobotContext robotCtx;

	@Override
	public void registDispatcher(CommanderProtocolMapper dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	@Override
	public void setContext(RobotContext robotCtx) {
		this.robotCtx = robotCtx;
	}
	
	@Override
	public void setGameSession(ISession gameSession) {
		this.robotCtx.setGameSession(gameSession);
	}

	@Override
	public void response(Packet packet) {
		try { 
			dispatcher.invoke(robotCtx.getGameSession() , packet);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


//	@Override
//	public boolean requestGame(RobotContext rc) {
//		return rc.requestGame();
//	}

}
