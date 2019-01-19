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
    private ByteBuf respData = Unpooled.compositeBuffer();
//    private ByteBuf respData = Unpooled.buffer(50);

    public NettyOioClientHandlerTest(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }

    public ByteBuf getRespData() {
        return respData;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println(respData);
        respData = Unpooled.wrappedBuffer(respData, byteBuf);
        System.out.println(respData);
        //不能自动释放，还会调用然后返回给子系统
        String ret = byteBuf.toString(CharsetUtil.UTF_8);
        ReferenceCountUtil.retain(byteBuf);
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
