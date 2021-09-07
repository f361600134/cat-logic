package com.cat.robot.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.DefaultSession;
import com.cat.net.network.base.ISession;
import com.cat.net.network.base.Packet;
import com.cat.robot.actor.IRobotActor;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@Deprecated
public class GameClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	private final Logger logger = LoggerFactory.getLogger(GameClientHandler.class.getName());
	private IRobotActor robotActor;
	
	public GameClientHandler(IRobotActor robotActor) { 
		this.robotActor = robotActor;//AkkaContext.createTypedActor(IRobotActor.class, RobotActor.class);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.info("连接服务:{}", ctx.channel().remoteAddress());
		ISession gameSession = DefaultSession.create(ctx.channel());
		this.robotActor.setGameSession(gameSession);
	}

	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		logger.info("断开服务:{}", ctx.channel().remoteAddress());
	}

	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		Packet pacekt = Packet.decode(in);
		robotActor.response(pacekt);
		pacekt = null;
	}


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
	}

}
