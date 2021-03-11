package com.gexx.netty.simple.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //得到管道
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("MyHttpServerCodec",new HttpClientCodec());
        pipeline.addLast("MyHttpServerHandler", new HttpServerHandler());


    }



}
