package com.sunchaser.sparrow.javaee.grpc.core.bidirectionalstream.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.sunchaser.sparrow.javaee.grpc.core.bidirectionalstream.BidirectionalStreamResponse;

import java.io.IOException;
import java.util.Scanner;

/**
 * 双向流式通信服务端
 * @author sunchaser admin@lilu.org.cn
 * @since 2022/1/17
 */
public class BidirectionalStreamServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 6666;
        BidirectionalStreamServiceImpl streamService = new BidirectionalStreamServiceImpl();
        Server server = ServerBuilder.forPort(port)
                .addService(streamService)
                .build()
                .start();
        System.out.println("Bidirectional Stream GRPC Server started, listening on " + port);
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("EOF".equals(line)) {
                    break;
                }
                try {
                    streamService.getResponseStreamObserver()
                            .onNext(
                                    BidirectionalStreamResponse.newBuilder()
                                            .setRespMsg(line)
                                            .build()
                            );
                } catch (Exception e) {
                    System.out.println("[error]-没有客户端连接");
                    e.printStackTrace();
                }
            }
        }).start();
        server.awaitTermination();
    }
}
