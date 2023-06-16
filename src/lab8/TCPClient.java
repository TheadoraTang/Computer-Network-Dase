package lab8;
//
//import java.io.*;
//import java.net.Socket;
//import java.nio.charset.StandardCharsets;
//import java.util.Scanner;
//
//public class TCPClient {
//    private Socket clientSocket;
//    private PrintWriter out;
//    private BufferedReader in;
//    private Thread receiveThread;
//
//    public void startConnection(String ip, int port) throws IOException {
//        // 1. 创建客户端Socket，指定服务器地址，端⼝
//        clientSocket = new Socket(ip, port);
//        // 2. 获取输入输出流
//        out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),
//                StandardCharsets.UTF_8), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),
//                StandardCharsets.UTF_8));
//        // 3. 启动接收消息线程
//        receiveThread = new Thread(() -> {
//            try {
//                while (true) {
//                    String msg = in.readLine();
//                    if (msg == null) {
//                        System.out.println("服务端已断开连接");
//                        break;
//                    }
//                    System.out.println("收到服务端消息：" + msg);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//        receiveThread.start();
//    }
//
//    public void sendMessage(String msg) throws IOException {
//        // 4. 向服务端发送消息
//        out.println(msg);
//    }
//
//    public void stopConnection() {
//        // 关闭相关资源
//        try {
//            if (in != null) in.close();
//            if (out != null) out.close();
//            if (clientSocket != null) clientSocket.close();
//            if (receiveThread != null) receiveThread.interrupt();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        int port = 9091;
//        TCPClient client = new TCPClient();
//        Scanner scanner = new Scanner(System.in);
//        try {
//            client.startConnection("127.0.0.1", port);
//            while (true) {
//                String msg = scanner.nextLine();
//                if (msg.equalsIgnoreCase("quit")) {
//                    break;
//                }
//                client.sendMessage(msg);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            scanner.close();
//            client.stopConnection();
//        }
//    }
//}
//


import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        // 1. 创建客户端Socket，指定服务器地址，端⼝
        clientSocket = new Socket(ip, port);
        // 2. 获取输⼊输出流
        out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),
                StandardCharsets.UTF_8), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),
                StandardCharsets.UTF_8));
    }

    public String sendMessage(String msg) throws IOException {
        // 3. 向服务端发送消息
        out.println(msg);
        // 4. 接收服务端回写信息
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() {
        // 关闭相关资源
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 9091;
        TCPClient client = new TCPClient();
        try {
            client.startConnection("127.0.0.1", port);
            String response = client.sendMessage("⽤户名: ECNUDaSE;");
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.stopConnection();
        }
    }
}
