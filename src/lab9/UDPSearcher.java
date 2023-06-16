package lab9;

//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.InetAddress;
//import java.nio.charset.StandardCharsets;
//
//public class UDPSearcher {
//    public static void main(String[] args) throws IOException {
//        // 1. 定义要发送的数据
//        String sendData = "用户名admin; 密码123";
//        byte[] sendBytes = sendData.getBytes(StandardCharsets.UTF_8);
//
//        // 2. 创建发送者端的DatagramSocket对象
//        DatagramSocket datagramSocket = new DatagramSocket(9092);
//
//        // 3. 创建数据报，包含要发送的数据
//        DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length,
//                InetAddress.getLocalHost(), 9091);
//
//        // 4. 向接收者端发送数据报
//        datagramSocket.send(sendPacket);
//        System.out.println("数据发送完毕...");
//
//        // Task 1 TODO: 准备接收Provider的回送消息; 查看接收信息并打印
//        byte[] buf = new byte[1024];
//        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
//        datagramSocket.receive(receivePacket);
//
//        String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
//        System.out.println("接收者回复的消息: " + receivedData);
//
//        // 5. 关闭datagramSocket
//        datagramSocket.close();
//    }
//}

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSearcher {
    public static void main(String[] args) throws IOException {
        // 1. 定义要发送的数据
        int port = 30000;
        String sendData = MessageUtil.buildWithPort(port);
        byte[] sendBytes = sendData.getBytes();

        // 2. 创建发送者端的DatagramSocket对象
        DatagramSocket datagramSocket = new DatagramSocket();

        // Enable broadcast
        datagramSocket.setBroadcast(true);

        // 3. 创建数据报，包含要发送的数据
        DatagramPacket sendPacket = new DatagramPacket(sendBytes, sendBytes.length,
                InetAddress.getByName("255.255.255.255"), 9091);

        // 4. 向接收者端发送数据报
        datagramSocket.send(sendPacket);
        System.out.println("数据发送完毕...");

        // 5. 准备接收Provider的回送消息
        datagramSocket = new DatagramSocket(port);

        byte[] receiveBuf = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
        datagramSocket.receive(receivePacket);

        // 6. 解析接收到的回复消息
        String receivedData = new String(receivePacket.getData(), 0, receivePacket.getLength());
        String tag = MessageUtil.parseTag(receivedData);
        System.out.println("Tag: " + tag);

        // 7. 关闭datagramSocket
        datagramSocket.close();
    }
}


