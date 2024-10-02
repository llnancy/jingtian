package io.github.llnancy.middleware.flink.source;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 简单 Socket Server
 *
 * @author llnancy admin@lilu.org.cn
 * @since JDK17 2024/7/20
 */
public class SimpleSocketServer {

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("server started");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("client connected");

                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                // 每隔一秒向 9999 端口发送数据
                executor.scheduleAtFixedRate(() -> {
                    try {
                        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
                        String data = "Constant data at " + System.currentTimeMillis();
                        System.out.println("write: " + data);
                        // 注意添加换行符 \n，因为 flink 是按行读取
                        writer.write(data + "\n");
                        writer.flush();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, 0, 1000, TimeUnit.MILLISECONDS);
            }
        }
    }
}
