package com.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.socket.oio.OioSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NettyOioClient {
    private static final Logger logger = LoggerFactory.getLogger(NettyOioClient.class);


    private final Integer port;
    private final String host;

    public NettyOioClient(String host, Integer port) {
        this.port = port;
        this.host = host;
    }

    private static final EventLoopGroup group = new NioEventLoopGroup();
    private static final Bootstrap bootstrap = new Bootstrap();

    static {
        bootstrap.group(group)
                .channel(NioSocketChannel.class);
    }

    public ByteBuf start(ByteBuf byteBuf) throws Exception {


        NettyOioClientHandler handler = new NettyOioClientHandler(byteBuf);
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024 * 1024)// 配置TCP参数
                .option(ChannelOption.SO_RCVBUF, 1024 * 1024)
                .option(ChannelOption.SO_KEEPALIVE, true) // 保持连接
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
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


    }

    public static void main(String[] args) throws Exception {
        long start0 = System.currentTimeMillis();

        for (int i = 0; i < 5000; i++) {
            StringBuilder sb = new StringBuilder();
            String msg = ",hello 8898=" + (i);
            sb.append(msg);
            long start = System.currentTimeMillis();
            ByteBuf start1 = new NettyOioClient("127.0.0.1", 8898).start(Unpooled.copiedBuffer(sb.toString(), CharsetUtil.UTF_8));

            long consumeTime = System.currentTimeMillis() - start;
            logger.info("client time=" + consumeTime + ", content=================" + start1.toString(CharsetUtil.UTF_8));
        }
        long consumeTime0 = System.currentTimeMillis() - start0;
        logger.info("================client time0=" + consumeTime0);
        System.out.println("================client time0=" + consumeTime0);

    }
}
