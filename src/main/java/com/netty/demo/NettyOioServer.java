package com.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;

public class NettyOioServer {

    private final Integer port;

    public NettyOioServer(Integer port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new NettyOioServer(8898).start();
    }


    public void start() throws Exception {


        final NettyOioServerHandler handler = new NettyOioServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .option(ChannelOption.SO_BACKLOG, 1024 * 1024 * 1024)// 配置TCP参数
                    .option(ChannelOption.SO_SNDBUF, 1024 * 1024 * 1024) // 设置发送缓冲大小
                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024 * 1024) // 这是接收缓冲大小
                    .option(ChannelOption.SO_KEEPALIVE, true) // 保持连接
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(handler);
                        }
                    });
            ChannelFuture cf = bootstrap.bind().sync();
            cf.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }


    }
}
