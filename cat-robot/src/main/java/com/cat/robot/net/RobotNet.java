package com.cat.robot.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.tcp.TcpProtocolEncoder;
import com.cat.robot.module.robot.RobotContext;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.concurrent.DefaultThreadFactory;

public class RobotNet {
	
	private final Logger logger = LoggerFactory.getLogger(RobotNet.class.getName());
	
	private static final String GAME_CONNECT_THREAD_NAME = "GAME_ROBOT_CONNECTOR";
	
	private RobotContext robotContext;
	
	public RobotNet(RobotContext robotContext) {
		this.robotContext = robotContext;
	}
	
	public boolean connectToServer() {
		try {
			DefaultThreadFactory tf0 = new DefaultThreadFactory(GAME_CONNECT_THREAD_NAME);
			NioEventLoopGroup loopGroup = new NioEventLoopGroup(1, tf0);
			
			Bootstrap bootstrap = new Bootstrap();
			bootstrap = new Bootstrap();
			bootstrap.group(loopGroup)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.SO_REUSEADDR, true)
					.option(ChannelOption.SO_RCVBUF, 128 * 1024)
					.option(ChannelOption.SO_SNDBUF, 128 * 1024)
					.option(ChannelOption.SO_KEEPALIVE, true)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel ch) throws Exception {
							ChannelPipeline pipeline = ch.pipeline();
							//heartbeat
//							pipeline.addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
//							pipeline.addLast(new HeartBeatClientHandler());
							
							// inbound
							pipeline.addLast("lengthDecoder", new LengthFieldBasedFrameDecoder(1024 * 1024, 0, 4, 0, 4)); // 1M 
							pipeline.addLast("clientHandler", new GameClientHandler(robotContext.getRobotAcotr()));
							
							// outbound
							pipeline.addLast("lengthEncoder", new LengthFieldPrepender(4));
							pipeline.addLast("protocolEncoder", new TcpProtocolEncoder());
							
						}
					});
			
			Channel channel = bootstrap.connect(robotContext.getGameServerIp(), robotContext.getGameServerPort()).sync().channel();
//			robotContext.setChannel(channel);
			return true;
		} catch (Exception e) {
			logger.error("连接初始化过程出现异常", e);
			return false;
		}
	}
	
}
