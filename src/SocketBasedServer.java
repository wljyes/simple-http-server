import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Server打开后需要手动关闭
public class SocketBasedServer implements Runnable {
    private int port = 8080;
    private ServerSocket socket;
    private ExecutorService executorService;

    public SocketBasedServer(int port) {
        this.port = port;
    }

    public void run() {
        openServerSocket();
        executorService = Executors.newCachedThreadPool();
        while (true) {
            try {
                Socket client = socket.accept();
                executorService.submit(new WorkThread("Hello Client!", client));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openServerSocket() {
        try {
            socket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("open socket on port " + port + " error");
            throw new RuntimeException(e);
        }
    }

    private class WorkThread implements Runnable {
        private String greeting;
        private Socket clientSocket;

        WorkThread(String greeting, Socket clientSocket) {
            this.greeting = greeting;
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    OutputStream os = clientSocket.getOutputStream()) {
                String requestMsg = reader.readLine();
                System.out.println("收到客户端发来的请求：" + requestMsg);
                os.write(("HTTP/1.1 200 OK\n\nWorkThread: " + greeting).getBytes());
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //因为accept会阻塞，所以server必须放在单独一个线程中
        SocketBasedServer server = new SocketBasedServer(9000);
        executorService.submit(server);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final int id = i;
            //客户端线程
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Socket socket = new Socket("127.0.0.1", 9000);
                        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                        writer.write("Hello Server, I'm client " + (id + 1) + "\n");
                        writer.flush();
                        socket.shutdownInput();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            threads.add(thread);
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
