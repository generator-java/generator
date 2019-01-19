package com.netty.demo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.net.InetAddress;

@ChannelHandler.Sharable
public class NettyOioServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    /**
     * 如果不释放，高并发时报错（netty java.lang.OutOfMemoryError: Direct buffer memory）
     */
    @Override
    public void channelRead(ChannelHandlerContext svrCtx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        ReferenceCountUtil.retain(msg);
        String host = InetAddress.getLocalHost().getHostAddress();
        NettyOioClient client = new NettyOioClient(host,8081);
        ByteBuf ecupMessage = client.start(svrCtx,in);
        ReferenceCountUtil.retain(ecupMessage);

        String serverSay = "hi,client! I have received your  message! ";
        System.out.println(serverSay);


    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        super.channelReadComplete(ctx);

        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
                .addListener(ChannelFutureListener.CLOSE);
    }

}
