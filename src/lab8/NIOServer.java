package lab8;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.ServerSocketChannel;
//import java.nio.channels.SocketChannel;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//public class NIOServer {
//    private static List<SocketChannel> channelList = new ArrayList<>();
//    private static int BYTE_LENGTH = 64;
//    public static void main(String[] args) throws IOException {
//// ServerSocketChannel与serverSocket类似
//        ServerSocketChannel serverSocket = ServerSocketChannel.open();
//        serverSocket.socket().bind(new InetSocketAddress(9091));
//// 设置ServerSocketChannel为⾮阻塞
//        serverSocket.configureBlocking(false);
//        System.out.println("服务端启动");
//        for (;;) {
//// accept⽅法不阻塞
//            SocketChannel socketChannel = serverSocket.accept();
//            if (socketChannel != null) {
//                System.out.println("连接成功");
//                socketChannel.configureBlocking(false);
//                channelList.add(socketChannel);
//            }
//// 遍历连接进⾏数据读取
//            Iterator<SocketChannel> iterator = channelList.iterator();
//            while (iterator.hasNext()) {
//                SocketChannel sc = iterator.next();
//                ByteBuffer byteBuffer = ByteBuffer.allocate(BYTE_LENGTH);
//// read⽅法不阻塞
//                int len = sc.read(byteBuffer);
//// 如果有数据，把数据打印出来
//                if (len > 0) {
//                    System.out.println("服务端接收到消息：" + new
//                            String(byteBuffer.array()));
//                } else if (len == -1) {
//// 如果客户端断开，把socket从集合中去掉
//                    iterator.remove();
//                    System.out.println("客户端断开连接");
//                }
//            }
//        }
//    }
//}

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class NIOServer {
    private static int BYTE_LENGTH = 64;
    private Selector selector;

    public static void main(String[] args) throws IOException {
        try {
            new NIOServer().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startServer() throws IOException {
        this.selector = Selector.open();

        // ServerSocketChannel与serverSocket类似
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(9091));

        // 设置⽆阻塞
        serverSocket.configureBlocking(false);

        // 将channel注册到selector
        serverSocket.register(this.selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务端已启动");

        for (;;) {
            // 操作系统提供的⾮阻塞I/O
            int readyCount = selector.select();

            if (readyCount == 0) {
                continue;
            }

            // 处理准备完成的fd
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();

                if (!key.isValid()) {
                    continue;
                }

                if (key.isAcceptable()) {
                    this.accept(key);
                } else if (key.isReadable()) {
                    this.read(key);
                } else if (key.isWritable()) {
                    this.write(key);
                }
            }
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);

        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        System.out.println("已连接: " + remoteAddr);

        // 监听读事件
        channel.register(this.selector, SelectionKey.OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(BYTE_LENGTH);
        int numRead = -1;

        numRead = channel.read(buffer);

        if (numRead == -1) {
            Socket socket = channel.socket();
            SocketAddress remoteAddr = socket.getRemoteSocketAddress();
            System.out.println("连接关闭: " + remoteAddr);
            channel.close();
            key.cancel();
            return;
        }

        byte[] data = new byte[numRead];
        System.arraycopy(buffer.array(), 0, data, 0, numRead);
        System.out.println("服务端已收到消息: " + new String(data));

        // 回写数据给客户端
        ByteBuffer writeBuffer = ByteBuffer.wrap(("已收到消息： " + new String(data)).getBytes());
        channel.write(writeBuffer);

        // 监听写事件，以便下一次回写给客户端
        channel.register(this.selector, SelectionKey.OP_WRITE);
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
//        ByteBuffer writeBuffer = ByteBuffer.wrap("Hello Client".getBytes());
//        channel.write(writeBuffer);
        for(;;){
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            ByteBuffer writeBuffer = ByteBuffer.wrap(str.getBytes());
            channel.write(writeBuffer);
        }
        // 取消写事件的监听
//        key.interestOps(SelectionKey.OP_READ);
    }
}
