package com.gexx.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {


    public static void main(String[] args) throws InterruptedException {
        //1.创建两个线程组 bossGroup只是处理连接请求，真正的客户端业务处理，会交给workGroup
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        //创建服务端的启动对象配置参数
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            //链式编程
            bootstrap.group(bossGroup, workGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioSocketChannel作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到连接的个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置保持活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//设置管道处理器
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHander());
                        }
                    });
            System.out.println("服务器 is ok ...");
            //启动服务器
            ChannelFuture cf = bootstrap.bind(5555).sync();
            //对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
