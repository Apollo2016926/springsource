package com.gexx.nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class ChatRoomClient {
    private SocketChannel socketChannel;
    private Selector selector;
    private final static String HOST = "127.0.0.1";
    private final static int PORT = 5000;
    private String userName;

    public ChatRoomClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(userName + " is Ok");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ChatRoomClient chatRoomClient = new ChatRoomClient();
        new Thread() {
            public void run() {
                while (true) {
                    chatRoomClient.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.next();
            chatRoomClient.sendInfo(s);
        }

    }

    public void sendInfo(String info) {
        info = userName + " 说: " + info;
        try {
            socketChannel.write(ByteBuffer.wrap(info.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void readInfo() {
        try {
            int select = selector.select(2000);
            if (select > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        channel.configureBlocking(false);
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg);
                        //转发
                    }
                }
                iterator.remove();
            } else {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
