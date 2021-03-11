package com.gexx.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHander extends ChannelInboundHandlerAdapter {


    /**
     * @Description 当通道就绪时就会触发该方法
     * @author gexx
     * @Date 2021/3/11
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client " + ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 服务端：哈哈", CharsetUtil.UTF_8));
    }


    /**
     * @Description 当通道有读取事件时触发
     * @author gexx
     * @Date 2021/3/11
     **/
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(" 服务端回复的:" + buf.toString(CharsetUtil.UTF_8));
        System.out.println(" 服务端地址:" + ctx.channel().remoteAddress());

    }
}
