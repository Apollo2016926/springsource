package com.gexx.netty.simple.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatGroupServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * @Description 连接建立最先执行
     * @Author gexx
     * @Date 2021/3/13
     **/
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        channelGroup.writeAndFlush("[客户端] " + channel.remoteAddress() + " 加入聊天室\n");
        channelGroup.add(channel);
    }


    /**
     * @Description channel 处于活动状态
     * @author gexx
     * @Date 2021/3/13
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + " 上线。。");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (ch != channel) { //转发
                ch.writeAndFlush("[客户]" + channel.remoteAddress() + " 说 :" + msg);
            } else {
                ch.writeAndFlush("[自己] 说：" + msg);
            }
        });


    }


    /**
     * @Description 处于非活动状态
     * @Author gexx
     * @Date 2021/3/13
     **/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        System.out.println(ctx.channel().remoteAddress() + " 离线了。。");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[客户端]" + channel.remoteAddress() + " 离开了\n");

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
