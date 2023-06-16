package lab9;

//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//
//public class UDPProvider {
//    public static void main(String[] args) throws IOException {
//        // 1. 创建接收者端的DatagramSocket，并指定端口
//        DatagramSocket datagramSocket = new DatagramSocket(9091);
//
//        // 2. 创建数据报，用于接收客户端发来的数据
//        byte[] buf = new byte[1024];
//        DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
//
//        // 3. 接收客户端发送的数据，此方法在收到数据报之前会一直阻塞
//        System.out.println("阻塞等待发送者的消息...");
//        datagramSocket.receive(receivePacket);
//
//        // 4. 解析数据
//        String ip = receivePacket.getAddress().getHostAddress();
//        int port = receivePacket.getPort();
//        int len = receivePacket.getLength();
//        String data = new String(receivePacket.getData(), 0, len);
//        System.out.println("我是接收者，" + ip + ":" + port + " 的发送者说: " + data);
//
//        // Task 1 TODO: 准备回送数据; 创建数据报，用于发回给发送端; 发送数据报
//        String responseData = data;
//        byte[] responseBytes = responseData.getBytes();
//        DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length,
//                receivePacket.getAddress(), receivePacket.getPort());
//        datagramSocket.send(responsePacket);
//
//        // 5. 关闭datagramSocket
//        datagramSocket.close();
//    }
//}
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.UUID;

public class UDPProvider {
    public static void main(String[] args) throws IOException {
        // 1. 创建接收者端的DatagramSocket，并指定端口
        DatagramSocket datagramSocket = new DatagramSocket(9091);

        // 2. 创建数据报，用于接收客户端发来的数据
        byte[] buf = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(buf, 0, buf.length);

        // 3. 接收客户端发送的数据，此方法在收到数据报之前会一直阻塞
        System.out.println("阻塞等待发送者的消息...");
        datagramSocket.receive(receivePacket);

        // 4. 解析数据
        String ip = receivePacket.getAddress().getHostAddress();
        int port = receivePacket.getPort();
        int len = receivePacket.getLength();
        String data = new String(receivePacket.getData(), 0, len);
        System.out.println("我是接收者，" + ip + ":" + port + " 的发送者说: " + data);

        // 5. 解析端口号
        int replyPort = MessageUtil.parsePort(data);
        if (replyPort != -1) {
            // 6. 构建回复消息
            String tag = UUID.randomUUID().toString();
            String replyData = MessageUtil.buildWithTag(tag);

            // 7. 创建回复数据报
            byte[] replyBytes = replyData.getBytes();
            DatagramPacket replyPacket = new DatagramPacket(replyBytes, replyBytes.length,
                    receivePacket.getAddress(), replyPort);

            // 8. 发送回复数据报
            datagramSocket.send(replyPacket);
            System.out.println(replyData);
        }

        // 9. 关闭datagramSocket
        datagramSocket.close();
    }
}
