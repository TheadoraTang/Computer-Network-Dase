package lab8;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.nio.charset.StandardCharsets;
//import java.util.Scanner;
//
//// 处理从客户端读数据的线程
//class ClientReadHandler extends Thread {
//    private final BufferedReader bufferedReader;
//    ClientReadHandler(InputStream inputStream) {
//        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream,
//                StandardCharsets.UTF_8));
//    }
//    @Override
//    public void run() {
//        try {
//            while (true) {
//// 拿到客户端⼀条数据
//                String str = bufferedReader.readLine();
//                if (str == null) {
//                    System.out.println("读到的数据为空");
//                    break;
//                } else {
//                    System.out.println("读到客户端消息：" + str);
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//// 处理向客户端写数据的线程
//class ClientWriteHandler extends Thread {
//    private final PrintWriter printWriter;
//    private final Scanner sc;
//    ClientWriteHandler(OutputStream outputStream) {
//        this.printWriter = new PrintWriter(new OutputStreamWriter(outputStream,
//                StandardCharsets.UTF_8), true);
//        this.sc = new Scanner(System.in);
//    }
//    void send(String str){
//        this.printWriter.println(str);
//    }
//    @Override
//    public void run() {
//        while (sc.hasNext()) {
//// 拿到控制台数据
//            String str = sc.next();
//            send(str);
//        }
//    }
//}
//
//class ClientHandler extends Thread {
//    private Socket socket;
//    private final ClientReadHandler clientReadHandler;
//    private final ClientWriteHandler clientWriteHandler;
//    ClientHandler(Socket socket) throws IOException{
//        this.socket = socket;
//        this.clientReadHandler = new ClientReadHandler(socket.getInputStream());
//        this.clientWriteHandler = new ClientWriteHandler(socket.getOutputStream());
//    }
//    @Override
//    public void run() {
//        super.run();
//        clientReadHandler.start();
//        clientWriteHandler.start();
//    }
//}
//
//public class TCPServer {
//    private ServerSocket serverSocket;
//
//    public void start(int port) throws IOException {
//        // 1. 创建⼀个服务器端Socket，即ServerSocket，监听指定端⼝
//        serverSocket = new ServerSocket(port);
//        // 2. 调⽤accept()⽅法开始监听，阻塞等待客户端的连接
//        System.out.println("阻塞等待客户端连接中...");
//        for (;;) {
//            Socket socket = serverSocket.accept();
//            ClientHandler ch = new ClientHandler(socket);
//            ch.start();
//        }
//    }
//
//    private void handle(Socket socket) throws IOException {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println("接收到客户端消息：" + line);
//                writer.flush();
//            }
//        } finally {
//            socket.close();
//        }
//    }
//
//    public void stop() {
//        // 关闭相关资源
//        try {
//            if (serverSocket != null) serverSocket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        int port = 9091;
//        TCPServer server = new TCPServer();
//        try {
//            server.start(port);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            server.stop();
//        }
//    }
//}

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TCPServer {
    private ServerSocket serverSocket;
    private ArrayList<Socket> clients;

    public void start(int port) throws IOException {
        // 1. 创建ServerSocket，指定端口号
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();

        while (true) {
            // 2. 等待客户端连接，阻塞式
            Socket clientSocket = serverSocket.accept();
            clients.add(clientSocket);
            System.out.println("客户端 " + clientSocket.getRemoteSocketAddress() + " 已连接");

            // 3. 发送当前已连接的客户端地址给所有客户端
            StringBuilder sb = new StringBuilder("当前已连接的客户端地址: ");
            for (Socket socket : clients) {
                sb.append(socket.getRemoteSocketAddress()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length()); // 删除最后一个逗号和空格
            sendMessageToAllClients(sb.toString());

            // 4. 获取Socket的字节输入流，并准备读取客户端发送的信息
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));

            // 6. 关闭输入输出流和客户端Socket
            clientSocket.close();
            clients.remove(clientSocket);
            System.out.println("客户端 " + clientSocket.getRemoteSocketAddress() + " 已断开连接");
        }
    }

    private void sendMessageToAllClients(String message) throws IOException {
        for (Socket socket : clients) {
            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            out.println(message);
        }
    }

    public void stop() {
        // 关闭相关资源
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
            for (Socket socket : clients) {
                if (socket != null) {
                    socket.close();
                }
            }
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
