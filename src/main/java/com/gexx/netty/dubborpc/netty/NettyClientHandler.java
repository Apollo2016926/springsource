package com.gexx.netty.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    private ChannelHandlerContext context;
    private String result;
    private String param;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;

    }


    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        result = msg.toString();
        notify();//唤醒等待线程

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }


    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        wait();//等待channelRead唤醒
        return result;

    }

    void setParam(String param) {
        this.param = param;
    }
}
