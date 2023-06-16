package Final;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Proxy {
    private static final String SERVER_HOST = "127.0.0.1";//初始服务器的主机名
    private static final int SERVER_PORT = 8081; // 初始服务器的端口号


    public static void main(String[] args) {
        int port = 8082; // 代理服务器监听的端口号

        try {
            // 创建代理服务器的Serversocket对象，用于接收客户端的连接请求
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Proxy Server is listening on port " + port);

            while (true) {
                // 等待客户端连接，并返回与客户端通信的Socket对象。
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                // 创建一个新的线程来处理客户端请求
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 实现Runnable接口
    static class ClientHandler implements Runnable {
        private Socket clientSocket;// 新的Socket用于表示与客户端的连接

        // 初始化并传入客户端的Socket对象
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        // 实现Runnable接口所要求的方法
        @Override
        public void run() {
            try {
                // 获取输入流和输出流
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // 从客户端socket读取输入流
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream()); // 向客户端socket输出流写入数据

                // 读取请求报文的第一行
                String requestLine = in.readLine();
                System.out.println("Request: " + requestLine); // 用于调试

                // 创建与初始服务器的连接
                Socket serverSocket = new Socket(SERVER_HOST, SERVER_PORT);
                // 从初始服务器Socket的输入流中获取数据
                BufferedReader serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                // 通过初始服务器Socket的输出流发送数据
                PrintWriter serverOut = new PrintWriter(serverSocket.getOutputStream());

                // 发送与初始服务器相同的HTTP请求
                serverOut.println(requestLine);
                serverOut.println("Host: " + SERVER_HOST);
                serverOut.println(); // 头部和请求体之间的空行，表示请求结束
                serverOut.flush();

                // 读取初始服务器的响应并转发给客户端
                String line;
                while ((line = serverIn.readLine()) != null) {
                    out.println(line);
                } // 从初始服务器的输入流读取响应数据，并将其逐行写入客户端的输出流，实现数据的转发
                out.flush(); // 刷新客户端的输出流，确保数据发送到客户端

                // 关闭Socket
                clientSocket.close();
                serverSocket.close();

                System.out.println("Client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



