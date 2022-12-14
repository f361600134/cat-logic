package com.cat.server.tcp;

import java.net.InetSocketAddress;

import com.cat.net.network.tcp.TcpProtocolEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient() {
        this(0);
    }

    public EchoClient(int port) {
        this("localhost", port);
    }

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group) // 注册线程池
                    .channel(NioSocketChannel.class) // 使用NioSocketChannel来作为连接用的channel类
                    .remoteAddress(new InetSocketAddress(this.host, this.port)) // 绑定连接端口和host信息
                    .handler(new ChannelInitializer<SocketChannel>() { // 绑定连接初始化器
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                	ch.pipeline().addLast(new TcpProtocolEncoder());
                                    ch.pipeline().addLast(new EchoClientHandler());
                                }
                            });

            ChannelFuture cf = b.connect().sync(); // 异步连接服务器
            cf.channel().closeFuture().sync(); // 异步等待关闭连接channel
        } finally {
            group.shutdownGracefully().sync(); // 释放线程池资源
        }
    }

    public static void main(String[] args) throws Exception {
        new EchoClient("127.0.0.1", 5001).start(); // 连接127.0.0.1/65535，并启动
    }
}