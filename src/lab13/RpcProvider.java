package lab13;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
public class RpcProvider {
    public static void main(String[] args) {
        Proxy2Impl proxy2Impl = new Proxy2Impl();
        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(9091));
            try(Socket socket = serverSocket.accept()) {
                // ObjectInputStream/ObjectOutputStream 提供了将对象序列化和反序列化的功能
                ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

                // Read request object using custom message format
                CustomRequest request = (CustomRequest) is.readObject();

                // Extract method and arguments from the request object
                String methodName = request.getMethodName();
                Class<?>[] parameterTypes = request.getParameterTypes();
                Object[] arguments = request.getArguments();

                // rpc提供方调用本地的对象的方法
                Object result = Proxy2Impl.class.getMethod(methodName, parameterTypes)
                        .invoke(proxy2Impl, arguments);

                // Create response object with the result
                CustomResponse response = new CustomResponse(result);

                // Serialize and send the response object
                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                os.writeObject(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

