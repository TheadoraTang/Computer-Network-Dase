package Final;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private static boolean isRunning = true; // 布尔值控制服务器是否继续运行，如果执行shutdown就停止运行

    // 从指定端口接受客户端连接请求并创建一个新的线程处理每个客户端的请求
    public static void main(String[] args) {
        int port = 8081; // 服务器监听的端口号
        // try就像一个网，把try{}里面的代码所抛出的异常都网住，然后把异常交给catch{}里面的代码去处理。
        try {
            // 创建一个服务器端Socket，鉴定指定的端口
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Web Server listening on port " + port);

            //当服务器仍然在运行的时候
            while (isRunning) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected");

                // 创建一个新的线程来处理客户端请求
                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
            }

            serverSocket.close();
            System.out.println("Web Server stopped");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ClientHandler是一个内部类，实现了Runnable接口，用于处理客户端请求的线程，Runnable接口创建线程
    static class ClientHandler implements Runnable {
        private Socket clientSocket; // 新的Socket用于表示与客户端的连接

        // 初始化并传入客户端的Socket对象
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        // 表示线程的执行逻辑
        @Override
        public void run() {
            try {
                // 获取输入流和输出流
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

                // 读取请求报文的第一行
                String requestLine = in.readLine();
                System.out.println("Request: " + requestLine);

                // 判空处理
                // 判空处理是为了确保接收到的请求报文的第一行不为空。请求报文的第一行包含了请求方法和请求路径等重要信息，
                // 如果为空，则表示接收到的请求不是有效的HTTP请求，可能是由于连接异常或其他错误导致的。
                if (requestLine == null) {
                    return; // 终止当前请求处理
                }

                // 解析请求方法和请求路径
                String[] requestParts = requestLine.split(" "); // 用空格拆分请求行
                String method = requestParts[0]; // 存储请求方法
                String path = requestParts[1]; // 存储请求路径

                // 处理GET请求
                if (method.equals("GET")) {
                    if (path.equals("/index.html")) {
                        // 构造HTTP响应报文
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/html\r\n" +
                                "\r\n" +
                                "<html><body>10215501437</body></html>";

                        // 发送响应
                        out.write(response);
                        out.flush();
                    } else if (path.equals("/shutdown")) {
                        // 关闭服务器
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "\r\n" +
                                "Server is shutting down";

                        out.write(response);
                        out.flush();
                        isRunning = false;
                        System.exit(0);
                    } else {
                        // 构造404响应报文
                        String response = "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "\r\n" +
                                "404 Not Found";

                        // 发送响应
                        out.write(response);
                        out.flush();
                    }
                } else if (method.equals("POST")) {
                    // 处理POST请求
                    if (path.equals("/index.html")) {
                        // 处理有效路径的逻辑
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "\r\n" +
                                "Post request received successfully";

                        // 发送响应
                        out.write(response);
                        out.flush();
                    } else {
                        // 构造404响应报文
                        String response = "HTTP/1.1 404 Not Found\r\n" +
                                "Content-Type: text/plain\r\n" +
                                "\r\n" +
                                "404 Not Found";

                        // 发送响应
                        out.write(response);
                        out.flush();
                    }
                }

                // 关闭连接
                clientSocket.close();
                System.out.println("Client disconnected");
            } catch (IOException e) {
                e.printStackTrace();
            }// catch用来捕捉可能发生的IO异常并打印异常堆栈跟踪
        }
    }
}

