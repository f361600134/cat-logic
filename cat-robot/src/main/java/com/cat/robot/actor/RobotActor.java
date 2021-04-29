package com.cat.robot.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.Packet;
import com.cat.net.network.process.ControllerProcessor;
import com.cat.robot.common.context.SpringContextHolder;
import com.cat.robot.module.robot.RobotContext;

public class RobotActor implements IRobotActor {
	
	public static Logger logger = LoggerFactory.getLogger(RobotActor.class);
	
//	private ControllerProcessor dispatcher;
//	private Dispatcher dispatcher = new Dispatcher();
	private RobotContext robotCtx;

	@Override
	public void registCmdObj(Object obj) {
		//TODO 通过扫描注册
//		dispatcher.regist(obj);
//		dispatcher.regist(ArtifactHandler.class);
//		dispatcher.regist(BagHandler.class);
//		dispatcher.regist(LoginHandler.class);
//		dispatcher.regist(StageHandler.class);
//		dispatcher.regist(HeroHandler.class);
//		dispatcher.regist(CommonHandler.class);
//		dispatcher.regist(RankHandler.class);
//		dispatcher.regist(OfficialHandler.class);
	}
	
	@Override
	public void setContext(RobotContext robotCtx) {
		this.robotCtx = robotCtx;
	}

	@Override
	public void response(Packet packet) {
		try { 
			int cmd = packet.cmd();
			ControllerProcessor dispatcher = SpringContextHolder.getBean(ControllerProcessor.class);
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
