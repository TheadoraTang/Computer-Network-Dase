//package lab6;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.nio.charset.StandardCharsets;
//public class TCPServer {
//    private ServerSocket serverSocket;
//    private Socket clientSocket;
//    private PrintWriter out;
//    private BufferedReader in;
//    public void start(int port) throws IOException {
//// 1. 创建⼀个服务器端Socket，即ServerSocket，监听指定端⼝
//        serverSocket = new ServerSocket(port);
//// 2. 调⽤accept()⽅法开始监听，阻塞等待客户端的连接
//        System.out.println("阻塞等待客户端连接中...");
//        clientSocket = serverSocket.accept();
//// 3. 获取Socket的字节输出流
//        out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),
//                StandardCharsets.UTF_8), true);
//// 4. 获取Socket的字节输⼊流，并准备读取客户端发送的信息
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),
//                StandardCharsets.UTF_8));
//// 5. 阻塞读取客户端发送的信息
////        String str = in.readLine();
////        System.out.println("我是服务端，客户端说： " + str);
////// 消息回写
////        out.println("服务端已收到消息" + str);
//        String str;
//        while((str = in.readLine())!= null){
//            System.out.println("我是服务端，客户端说： " + str);
//// 消息回写
//            out.println("服务端已收到消息" + str);
//        }
//    }
//    public void stop(){
//// 关闭相关资源
//        try {
//            if(in!=null) in.close();
//            if(out!=null) out.close();
//            if(clientSocket!=null) clientSocket.close();
//            if(serverSocket!=null) serverSocket.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args) {
//        int port = 9091;
//        TCPServer server=new TCPServer();
//        try {
//            server.start(port);
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            server.stop();
//        }
//    }
//}

//package lab6;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.nio.charset.StandardCharsets;
//
//public class TCPServer {
//    private ServerSocket serverSocket;
//
//    public void start(int port) throws IOException {
//        // 1. 创建⼀个服务器端Socket，即ServerSocket，监听指定端⼝
//        serverSocket = new ServerSocket(port);
//        // 2. 调⽤accept()⽅法开始监听，阻塞等待客户端的连接
//        System.out.println("阻塞等待客户端连接中...");
//        while (true) {
//            Socket socket = serverSocket.accept();
//            new Thread(() -> {
//                try {
//                    handle(socket);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
//    }
//
//    private void handle(Socket socket) throws IOException {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
//             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println("接收到客户端消息：" + line);
//                writer.write("收到消息：" + line + "\n");
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
package lab7;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private static int BYTE_LENGTH = 64;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("阻塞等待客户端连接中...");
        clientSocket = serverSocket.accept();
        InputStream is = clientSocket.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[BYTE_LENGTH];
        int len;
        while ((len = is.read(bytes)) != -1) {
            baos.write(bytes, 0, len);
            if (len < BYTE_LENGTH) {
                System.out.println("服务端已收到消息: " + new String(baos.toByteArray(), StandardCharsets.UTF_8).trim());
                baos.reset();
            }
        }
    }

    public void stop() {
        // 关闭相关资源
        try {
            if (clientSocket != null) clientSocket.close();
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
