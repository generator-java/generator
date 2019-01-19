package com.netty.ecup;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class NettyOioClientHandlerTest extends SimpleChannelInboundHandler<ByteBuf> {
    Logger logger = LoggerFactory.getLogger(NettyOioClientHandlerTest.class);

    private ByteBuf byteBuf;
//    private ByteBuf respData = Unpooled.buffer(50);

    public NettyOioClientHandlerTest(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        String ret = byteBuf.toString(CharsetUtil.UTF_8);
        logger.info("client received: " + ret);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);

        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();

    }
}
