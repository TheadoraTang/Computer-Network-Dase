//package lab6;
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
//    public void startConnection(String ip, int port) throws IOException {
//// 1. 创建客户端Socket，指定服务器地址，端⼝
//        clientSocket = new Socket(ip, port);
//// 2. 获取输⼊输出流
//        out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(),
//                StandardCharsets.UTF_8), true);
//        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(),
//                StandardCharsets.UTF_8));
//    }
//    public String sendMessage(String msg) throws IOException {
//// 3. 向服务端发送消息
//        out.println(msg);
//// 4. 接收服务端回写信息
//        String resp = in.readLine();
//        return resp;
//    }
//    public void stopConnection() {
//// 关闭相关资源
//        try {
//            if(in!=null) in.close();
//            if(out!=null) out.close();
//            if(clientSocket!=null) clientSocket.close();
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//    }
//    public static void main(String[] args) throws IOException {
//        int port = 9091;
//        TCPClient client = new TCPClient();
//
//        Scanner in = new Scanner(System.in);
//        try {
//            client.startConnection("127.0.0.1", port);
//            String response = client.sendMessage(in.next().toUpperCase());
//            System.out.println(response);
//            in.close();
//        }catch (IOException e){
//            e.printStackTrace();
//        }finally {
//            client.stopConnection();
//        }
//    }
//}
package lab7;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;



public class TCPClient {
    private Socket clientSocket;
    private OutputStream out;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = clientSocket.getOutputStream();
    }

    public void sendMessage(String msg) throws IOException {
        for (int i = 0; i < 1; i++) {
            byte[] bytes = msg.getBytes();
            out.write(bytes);
            out.flush();
        }
    }

    public void stopConnection() {
        // 关闭相关资源
        try {
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
            String message = "NETWORK PRINCIPLE";
            client.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            client.stopConnection();
        }
    }
}
