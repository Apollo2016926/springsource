package com.gexx.netty.simple.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class ChatGroupServer {
    private int port;

    public ChatGroupServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {


            ServerBootstrap bootstrapServer = new ServerBootstrap();

            bootstrapServer.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline添加解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipeline添加编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new ChatGroupServerHandler());

                        }
                    });

            System.out.println("服务器启动。。。。");

            ChannelFuture channelFuture = bootstrapServer.bind(port).sync();
//监听关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    public static void main(String[] args) {
        new ChatGroupServer(5000).run();
    }
}
