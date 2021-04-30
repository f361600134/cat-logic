package com.cat.server.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.cat.net.core.executor.DisruptorDispatchTask;
import com.cat.net.core.executor.DisruptorStrategy;
import com.cat.net.network.base.Commander;
import com.cat.net.network.base.GameSession;
import com.cat.net.network.base.Packet;
import com.cat.net.network.controller.DefaultServerController;

import io.netty.buffer.ByteBuf;

/**
 * 游戏服分发处理器
 */
//@Primary
//@Component
public class GameServerController extends DefaultServerController {
	
	private static final Logger log = LoggerFactory.getLogger(GameServerController.class);
	
	public GameServerController(){
		log.info("注册[GameServerController]服务");
	}
	
	public void onConnect(GameSession session) {
		log.info("自定义分发器, 客户端连接游戏服:{}", session.getChannel().remoteAddress());
	}

	public void onReceive(GameSession session, ByteBuf message) {
		log.info("自定义分发器, 客户端请求消息:{}", session.getChannel().remoteAddress());
		Packet packet = null;
		try {
			if (!serverRunning) {
				log.error("服务器不在运行状态, 舍弃消息"); 
				return;
			} 
			packet = Packet.decode(message);
			Commander commander = processor.getCommander(packet.cmd());
			if (commander == null) {
				log.info("收到未处理协议, cmd=[{}]",  packet.cmd());
				return;
			}
			if (commander.isMustLogin()) {
//				if (session.getPlayerId() <= 0L) { // 未登录
//					log.debug("协议[{}]需要登录成功后才能请求", packet.cmd()); 
//					S2CCommonReply reply = S2CCommonReply.newBuilder().setProtoCode(packet.cmd()).setSuccess(1).build();
//					SimpleProtocol proto = SimpleProtocol.create(ProtocolCode.COMMON_S2C_REPLY, reply);
//					session.send(Packet.encode(proto));
//					return;
//				}
//				PlayerContext playerCtx = session.getUserData();
//				IPlayerActor playerActor = playerCtx.getPlayerActor();
//				playerActor.process(session, processor, packet);
			} else {
				DisruptorDispatchTask task = new DisruptorDispatchTask(processor, session, packet);
				DisruptorStrategy.get(DisruptorStrategy.SINGLE).execute(session.getId(), task);
//				DisruptorStrategy.get(DisruptorStrategy.SINGLE)
//				.execute(session.getId(), new DisruptorDispatchTask(processor, session, packet));
				//group.execute(session.getId(), new DisruptorDispatchTask(processor, session, packet));
				log.info("====> GameServerHandler onReceive, threadName:{}", Thread.currentThread().getName());
			}
		} catch (Exception e) {
			if (packet == null) {
				log.error("协议解析失败", e);
			} else {
				log.error("Packet调用过程出错, cmd={}", packet.cmd(), e);
			}
		} 
	}

	@Override
	public void onClose(GameSession session) {
		log.info("自定义分发器, 客户端连接断开:{}", session.getChannel().remoteAddress());
	}

	@Override
	public void onException(GameSession session, Throwable e) {
		log.error("自定义分发器, 游戏协议通信过程出错", e);
	}
}
