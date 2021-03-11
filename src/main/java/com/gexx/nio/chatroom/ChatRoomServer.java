package com.gexx.nio.chatroom;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class ChatRoomServer {
    private ServerSocketChannel listenChannel;
    private Selector selector;
    private final static int PORT = 5000;

    public ChatRoomServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.configureBlocking(false);
            listenChannel.bind(new InetSocketAddress(PORT));
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ChatRoomServer chatRoomServer = new ChatRoomServer();
        chatRoomServer.listen();
    }

    public void listen() {
        try {
            while (true) {
                int select = selector.select(2000);
                if (select > 0) {
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel socket = listenChannel.accept();
                            socket.configureBlocking(false);
                            socket.register(selector, SelectionKey.OP_READ);
                            System.out.println(socket.getRemoteAddress() + " 上线了");
                        }
                        if (key.isReadable()) {

                            readData(key);
                        }

                        iterator.remove();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int read = channel.read(buffer);
            if (read > 0) {
                String msg = new String(buffer.array());
                System.out.println("from 客户端：" + msg);
                //转发
                forwardMsg(channel, msg);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + "  离线了");
                key.cancel();
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void forwardMsg(SocketChannel except, String msg) throws IOException {
        for (SelectionKey key : selector.keys()) {
            SelectableChannel channel = key.channel();
            if (channel instanceof SocketChannel && channel != except) {
                SocketChannel targetChannel = (SocketChannel) channel;

                targetChannel.write(ByteBuffer.wrap(msg.getBytes()));
            }

        }
    }
}
