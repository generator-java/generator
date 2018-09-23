package com.bootstrap.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.charset.Charset;

/**
 * @author fanhb on 2018/9/23
 * @desc xxxx
 */
public class BootstrapServerTest {

    public static void main(String[] args) throws InterruptedException {
        final ByteBuf byteBuf = Unpooled.copiedBuffer("Hi!\r\t\n", Charset.forName("UTF-8"));
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel
                                    .pipeline()
                                    .addLast(new ChannelHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            ctx.writeAndFlush(byteBuf.duplicate())
                                                    .addListener(ChannelFutureListener.CLOSE);
                                        }
                                    });
                        }
                    });
            ChannelFuture sync = serverBootstrap.bind(8080).sync();
            sync.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully().sync();
        }

    }
}
