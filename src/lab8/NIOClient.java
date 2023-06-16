package lab8;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {

    private static int BYTE_LENGTH = 64;
    private Selector selector;
    private ByteBuffer writeBuffer = ByteBuffer.allocate(BYTE_LENGTH);
    private ByteBuffer readBuffer = ByteBuffer.allocate(BYTE_LENGTH);

    public static void main(String[] args) throws IOException {
        new NIOClient().startClient();
    }

    private void startClient() throws IOException {
        // 打开socket通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置为非阻塞
        socketChannel.configureBlocking(false);
        // 打开选择器
        this.selector = Selector.open();
        // 注册连接事件
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        // 发起连接
        socketChannel.connect(new InetSocketAddress("localhost", 9091));
        System.out.println("客户端已启动");
        for (; ; ) {
            int readyCount = selector.select();
            if (readyCount == 0) {
                continue;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                if (!key.isValid()) {
                    continue;
                }
                if (key.isConnectable()) {
                    this.connect(key);
                } else if (key.isReadable()) {
                    this.read(key);
                } else if (key.isWritable()) {
                    this.write(key);
                }
            }
        }
    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        // 判断此通道上是否正在进行连接操作
        if (socketChannel.isConnectionPending()) {
            // 完成连接
            socketChannel.finishConnect();
        }
        // 设置为非阻塞
        socketChannel.configureBlocking(false);
        // 注册读事件
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 发送消息
        writeBuffer.clear();
        writeBuffer.put("Hello Server".getBytes());
        writeBuffer.flip();
        socketChannel.write(writeBuffer);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        readBuffer.clear();
        int numRead;
        try {
            numRead = channel.read(readBuffer);
        } catch (IOException e) {
            key.cancel();
            channel.close();
            return;
        }
        if (numRead == -1) {
            key.cancel();
            channel.close();
            return;
        }
        byte[] data = new byte[numRead];
        System.arraycopy(readBuffer.array(), 0, data, 0, numRead);
        System.out.println("客户端已收到消息: " + new String(data));
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.register(this.selector, SelectionKey.OP_READ);
    }
}