package com.qxx.thirdservice.service;

import com.qxx.common.util.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

public class ServerSocketService {
    public static void main(String[] args) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 创建服务器
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        // 绑定监听端口
        socketChannel.bind(new InetSocketAddress(2799));
        // 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true){
            // accept建立与客户端的连接，SocketChannel用来与客户端之间通信
            SocketChannel clientChannel = socketChannel.accept();
            channels.add(clientChannel);
            for (SocketChannel channel : channels) {
                // 接收客户端发送的数据
                channel.read(buffer);
                buffer.flip();
                ByteBufferUtil.debugRead(buffer);
                buffer.clear();
            }
        }
    }
}
