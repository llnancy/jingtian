package com.sunchaser.sparrow.javaee.grpc.core.bidirectionalstream.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.sunchaser.sparrow.javaee.grpc.core.bidirectionalstream.BidirectionalStreamRequest;
import org.sunchaser.sparrow.javaee.grpc.core.bidirectionalstream.BidirectionalStreamResponse;
import org.sunchaser.sparrow.javaee.grpc.core.bidirectionalstream.BidirectionalStreamServiceGrpc;

import java.util.Scanner;

/**
 * 双向流式通信客户端
 * @author sunchaser admin@lilu.org.cn
 * @since 2022/1/17
 */
public class BidirectionalStreamClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6666)
                .usePlaintext()
                .build();
        StreamObserver<BidirectionalStreamRequest> requestStreamObserver = BidirectionalStreamServiceGrpc.newStub(channel)
                .bidirectionalStreamCommunication(new StreamObserver<BidirectionalStreamResponse>() {
                    @Override
                    public void onNext(BidirectionalStreamResponse bidirectionalStreamResponse) {
                        System.out.println("[客户端]-[收到服务端消息]：" + bidirectionalStreamResponse.getRespMsg());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                    }

                    @Override
                    public void onCompleted() {
                    }
                });
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            if ("EOF".equals(line)) {
                requestStreamObserver.onCompleted();
                break;
            }
            try {
                requestStreamObserver.onNext(
                        BidirectionalStreamRequest.newBuilder()
                                .setReqMsg(line)
                                .build()
                );
            } catch (Exception e) {
                System.out.println("[error]-客户端异常");
                e.printStackTrace();
            }
        }
    }
}
