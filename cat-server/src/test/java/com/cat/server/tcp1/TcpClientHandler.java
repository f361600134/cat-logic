package com.cat.server.tcp1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cat.net.network.base.Packet;
import com.cat.server.game.module.chat.proto.ReqChatBuilder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 客户端处理类
 */
public class TcpClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
	
	private static final Logger log = LoggerFactory.getLogger(TcpClientHandler.class);

    /**
     * 在到服务器的连接已经建立之后将被调用
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("==================server channelActive==================");
        ReqChatBuilder req = ReqChatBuilder.newInstance();
    	Packet obj = Packet.encode(req);
    	ctx.writeAndFlush(obj);
    }
    
    /**
     * 当从服务器接收到一个消息时被调用
     * @param channelHandlerContext
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
    	Packet packet = Packet.decode(byteBuf);
    	log.info("Client received: {}", packet.cmd());
    }

    /**
     * 在处理过程中引发异常时被调用
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
