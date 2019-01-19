package com.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://blog.csdn.net/weixin_38009046/article/details/81985091
 *
 * eg:
 *
 * public static void main(String[] args) {
 *
 *         ByteBuf buffer = Unpooled.buffer(7);
 *         ByteBuf buffer1 = Unpooled.buffer(10);
 *
 *         buffer.writeBytes("version".getBytes());
 *         buffer1.writeBytes("version".getBytes());
 *
 *         ByteBuf byteBuf = Unpooled.wrappedBuffer(buffer, buffer1);
 *
 *         byteBuf.writeBytes("src".getBytes());
 *         byteBuf.writeBytes("src".getBytes());
 *         System.out.println(byteBuf.readerIndex());
 *         System.out.println(byteBuf.writerIndex());
 *     }
 * 作者：weixin_38009046
 */
@ChannelHandler.Sharable
public class NettyOioClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    Logger logger = LoggerFactory.getLogger(NettyOioClientHandler.class);

    private ByteBuf byteBuf;
    private ByteBuf respData = Unpooled.compositeBuffer();
//    private ByteBuf respData = Unpooled.buffer(50);

    public NettyOioClientHandler(ByteBuf byteBuf) {
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
