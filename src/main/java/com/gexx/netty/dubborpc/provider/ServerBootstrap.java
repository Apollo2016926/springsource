package com.gexx.netty.dubborpc.provider;


import com.gexx.netty.dubborpc.netty.NettyServer;

public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.start("127.0.0.1", 5555);
    }
}
