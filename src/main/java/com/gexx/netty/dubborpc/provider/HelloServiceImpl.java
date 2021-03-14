package com.gexx.netty.dubborpc.provider;

import com.gexx.netty.dubborpc.inerface.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        System.out.println("收到消费者消息：" + msg);
        if (msg != null) {
            return "这是生产者的回复:" + msg;
        }
        return "生产者说：咋不传点东西过来";
    }
}
