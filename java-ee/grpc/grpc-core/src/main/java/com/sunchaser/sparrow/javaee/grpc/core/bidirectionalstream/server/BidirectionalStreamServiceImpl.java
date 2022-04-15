package com.sunchaser.sparrow.javaee.grpc.core.bidirectionalstream.server;

import io.grpc.stub.StreamObserver;
import org.sunchaser.sparrow.javaee.grpc.core.bidirectionalstream.BidirectionalStreamRequest;
import org.sunchaser.sparrow.javaee.grpc.core.bidirectionalstream.BidirectionalStreamResponse;
import org.sunchaser.sparrow.javaee.grpc.core.bidirectionalstream.BidirectionalStreamServiceGrpc;

/**
 * 双向流式通信服务端逻辑实现
 * @author sunchaser admin@lilu.org.cn
 * @since 2022/1/17
 */
public class BidirectionalStreamServiceImpl extends BidirectionalStreamServiceGrpc.BidirectionalStreamServiceImplBase {
    private StreamObserver<BidirectionalStreamResponse> responseStreamObserver;

    @Override
    public StreamObserver<BidirectionalStreamRequest> bidirectionalStreamCommunication(StreamObserver<BidirectionalStreamResponse> responseObserver) {
        this.responseStreamObserver = responseObserver;
        return new StreamObserver<BidirectionalStreamRequest>() {
            @Override
            public void onNext(BidirectionalStreamRequest bidirectionalStreamRequest) {
                String reqMsg = bidirectionalStreamRequest.getReqMsg();
                System.out.println("[服务端]-[收到客户端消息]：" + reqMsg);
                responseObserver.onNext(
                        BidirectionalStreamResponse.newBuilder()
                                .setRespMsg("hello client, I'm Java grpc server, your message '" + reqMsg + "' has been received.")
                                .build()
                );
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.fillInStackTrace();
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    public StreamObserver<BidirectionalStreamResponse> getResponseStreamObserver() {
        return responseStreamObserver;
    }
}
