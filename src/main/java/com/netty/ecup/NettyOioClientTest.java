package com.netty.ecup;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NettyOioClientTest {
    private static final Logger logger = LoggerFactory.getLogger(NettyOioClientTest.class);


    private final Integer port;
    private final String host;

    public NettyOioClientTest(String host, Integer port) {
        this.port = port;
        this.host = host;
    }


    public ByteBuf start(ByteBuf byteBuf) throws Exception {
        EventLoopGroup group = new OioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            NettyOioClientHandlerTest handler = new NettyOioClientHandlerTest(byteBuf);
            bootstrap.group(group)
                    .option(ChannelOption.SO_BACKLOG, 1024 * 1024)
                    .option(ChannelOption.SO_RCVBUF, 1024 * 1024)
                    .channel(OioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {

                            socketChannel.pipeline().addLast(handler);
                        }
                    });
            ChannelFuture cf = bootstrap.connect().sync();
            cf.channel().closeFuture().sync();
            return handler.getRespData();
        } finally {

            group.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws Exception {
        long start0 = System.currentTimeMillis();

        for (int i = 0; i < 10000; i++) {
            StringBuilder sb = new StringBuilder();
            String msg = ",hello 8898=" + (i + 100);
            sb.append(msg);
            long start = System.currentTimeMillis();
            new NettyOioClientTest("127.0.0.1", 8898).start(Unpooled.copiedBuffer(sb.toString(), CharsetUtil.UTF_8));
            long consumeTime = System.currentTimeMillis() - start;
            logger.info("client time=" + consumeTime);
        }
        long consumeTime0 = System.currentTimeMillis() - start0;
        logger.info("================client time0=" + consumeTime0);
        System.out.println("================client time0=" + consumeTime0);

    }
}
