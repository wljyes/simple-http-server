import entity.RequestEntity;
import entity.ResponseEntity;
import factory.RequestEntityFactory;
import factory.RequestHandleList;
import handler.HttpRequestHandler;
import util.StringUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 支持静态资源的访问，以及类似tomcat的servlet路径映射
 */
public class SimpleHttpServer implements Runnable{
    private int serverPort;
    private ServerSocket serverSocket;
    //产生用于处理请求的线程
    private ExecutorService exec;
    //请求处理器
    private RequestHandleList handleList = RequestHandleList.getRequestHandleList();

    public SimpleHttpServer(int serverPort) {
        this();
        this.serverPort = serverPort;
    }

    public SimpleHttpServer() {
        serverPort = 8080;
        exec = Executors.newCachedThreadPool();
    }

    public SimpleHttpServer(int serverPort, ExecutorService exec) {
        this.serverPort = serverPort;
        this.exec = exec;
    }

    @Override
    public void run() {
        openServerSocket();
        while (true) {
            try {
                //阻塞
                Socket clientSocket = serverSocket.accept();
                //accept成功后解除阻塞
                exec.execute(new RequestHandleTask(clientSocket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void openServerSocket() {
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (IOException e) {
            System.out.println("开启ServerSocket失败!");
            throw new RuntimeException(e);
        }
    }

    /**
     * 用于处理http请求的Task
     */
    public class RequestHandleTask implements Runnable {
        //客户端socket连接
        private Socket clientSocket;

        RequestHandleTask(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            StringBuilder requestMessage = new StringBuilder();
            try {
                InputStream in = clientSocket.getInputStream();
                int c;
                do {
                    c = in.read();
                    requestMessage.append((char) c);
                } while (in.available() > 0);
            } catch (IOException e) {
                e.printStackTrace();
            }

            RequestEntity requestEntity = RequestEntityFactory.getEntity(requestMessage.toString());
            ResponseEntity responseEntity = handleList.handleRequest(requestEntity);

            System.out.println(responseEntity.toString());

            try {
                OutputStream os = clientSocket.getOutputStream();
                os.write(responseEntity.toString().getBytes());
                os.flush();
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new SimpleHttpServer(9000)).start();
    }
}
