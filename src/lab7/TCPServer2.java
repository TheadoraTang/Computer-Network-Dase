package lab7;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer2 {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        // 1. 创建⼀个服务器端Socket，即ServerSocket，监听指定端⼝
        serverSocket = new ServerSocket(port);
        // 2. 调⽤accept()⽅法开始监听，阻塞等待客户端的连接
        System.out.println("服务器已启动，等待客户端连接...");
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    handle(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    private void handle(Socket socket) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("接收到客户端消息：" + line);
                writer.write("收到消息：" + line + "\n");
                writer.flush();
            }
        } finally {
            socket.close();
        }
    }

    public void stop() {
        // 关闭相关资源
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 9091;
        TCPServer server = new TCPServer();
        try {
            server.start(port);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}

