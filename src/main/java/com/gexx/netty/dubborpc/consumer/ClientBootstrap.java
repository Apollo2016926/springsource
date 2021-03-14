package com.gexx.netty.dubborpc.consumer;

import com.gexx.netty.dubborpc.inerface.HelloService;
import com.gexx.netty.dubborpc.netty.NettyClient;

public class ClientBootstrap {

    public static final String provideName = "Hello#";


    public static void main(String[] args) {
        NettyClient consumer = new NettyClient();
        HelloService service = (HelloService) consumer.getBean(HelloService.class, provideName);
        String g干啥呢 = service.hello("g干啥呢");

        System.out.println("-----" + g干啥呢);
    }
}
