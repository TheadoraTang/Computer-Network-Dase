package lab13;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RpcConsumer {
    public static void main(String[] args) {
        IProxy2 iProxy2 = (IProxy2) Proxy.newProxyInstance(
                IProxy2.class.getClassLoader(),
                new Class<?>[]{IProxy2.class},
                new iProxy2Handler()
        );
        System.out.println(iProxy2.sayHi("alice"));
    }
}
class iProxy2Handler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(9091));

        // Create request object with method name and arguments
        CustomRequest request = new CustomRequest(method.getName(), method.getParameterTypes(), args);

        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());

        // Write request object using custom message format
        os.writeObject(request);

        // Read response object using custom message format
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        CustomResponse response = (CustomResponse) is.readObject();

        return response.getResult();
    }
}
class CustomRequest implements Serializable {
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] arguments;

    public CustomRequest(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.arguments = arguments;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getArguments() {
        return arguments;
    }
}

class CustomResponse implements Serializable {
    private Object result;

    public CustomResponse(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
