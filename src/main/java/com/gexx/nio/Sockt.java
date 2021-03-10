package com.gexx.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Sockt {
    public static void main(String[] args) throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 5555);
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接中.....先洗个脚");
            }
        }
        String sendStr = "我爱你不问归期";
        ByteBuffer buffer = ByteBuffer.wrap(sendStr.getBytes());
        socketChannel.write(buffer);
    }
}
