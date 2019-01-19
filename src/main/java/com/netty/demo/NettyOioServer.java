package com.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
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
        EventLoopGroup group = new OioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(group)
                    .option(ChannelOption.SO_BACKLOG,1024*1024)
                    .option(ChannelOption.SO_RCVBUF,1024*1024)
                    .channel(OioServerSocketChannel.class)
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
