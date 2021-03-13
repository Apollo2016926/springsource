package com.gexx.netty.simple.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ChatGroupClient {


    private String ip;
    private int port;

    public ChatGroupClient() {
        this.ip = "127.0.0.1";
        this.port = 5000;
    }

    public static void main(String[] args) {
        new ChatGroupClient().run();
    }


    public void run() {
        EventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(eventExecutors)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //向pipeline添加解码器
                            pipeline.addLast("decoder", new StringDecoder());
                            //向pipeline添加编码器
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new ChatGroupClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(ip, port).sync();
            System.out.println("----" + channelFuture.channel().localAddress() + "-----");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                channelFuture.channel().writeAndFlush(s + "\r\n");

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            eventExecutors.shutdownGracefully();
        }
    }
}
