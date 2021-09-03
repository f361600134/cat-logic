package com.cat.server.core.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.Commander;
import com.cat.net.network.base.ISession;
import com.cat.net.network.base.Packet;
import com.cat.net.network.controller.DefaultConnectControllerDispatcher;

import io.netty.buffer.ByteBuf;

/**
 * 游戏服分发处理器, 开发人员使用时注册
 */
//@Primary
//@Component
@Deprecated
public class GameServerController extends DefaultConnectControllerDispatcher {
	
	private static final Logger log = LoggerFactory.getLogger(GameServerController.class);
	
	public GameServerController(){
	}
	
	public void onConnect(ISession session) {
		log.info("自定义分发器, 客户端连接游戏服:{}", session.getChannel().remoteAddress());
	}

	public void onReceive(ISession session, ByteBuf message) {
		Packet packet = null;
		try {
			if (!serverRunning) {
				log.warn("服务器不在运行状态, 舍弃消息"); 
				return;
			} 
			packet = Packet.decode(message);
			Commander commander = mapper.get(packet.cmd());
			if (commander == null) {
				log.info("收到未处理协议, cmd=[{}]",  packet.cmd());
				return;
			}
			if (commander.isMustLogin()) {
				if (session.getUserData() != null) { // 未登录
					log.info("协议[{}]需要登录成功后才能请求", packet.cmd()); 
//					S2CCommonReply reply = S2CCommonReply.newBuilder().setProtoCode(packet.cmd()).setSuccess(1).build();
//					SimpleProtocol proto = SimpleProtocol.create(ProtocolCode.COMMON_S2C_REPLY, reply);
//					session.send(Packet.encode(proto));
					return;
				}
			} 
//			DisruptorDispatchTask task = new DisruptorDispatchTask(processor, session, packet);
//			DisruptorStrategy.get(DisruptorStrategy.SINGLE).execute(session.getSessionId(), task);
		} catch (Exception e) {
			if (packet == null) {
				log.error("协议解析失败", e);
			} else {
				log.error("Packet调用过程出错, cmd={}", packet.cmd(), e);
			}
		} 
	}

	@Override
	public void onClose(ISession session) {
		log.info("自定义分发器, 客户端连接断开:{}", session.getChannel().remoteAddress());
	}

	@Override
	public void onException(ISession session, Throwable e) {
		log.error("自定义分发器, 游戏协议通信过程出错", e);
	}
}
