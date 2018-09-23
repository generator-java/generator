package com.bootstrap.clent;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author fanhb on 2018/9/23
 * @desc xxxx
 */
public class BootstrapTest {


    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new SimpleChannelInboundHandler<ByteBuf>() {

                        @Override
                        protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
                            System.out.println("Received data!");
                        }
                    });

            ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost", 8080));
            future.addListener((ChannelFutureListener) channelFuture -> {
                if (channelFuture.isSuccess()) {
                    System.out.println("connection established!");
                } else {
                    System.out.println("connection attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            });
        }


    }
}
