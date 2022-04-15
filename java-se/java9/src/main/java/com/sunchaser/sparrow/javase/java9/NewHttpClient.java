package com.sunchaser.sparrow.javase.java9;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * jdk9全新HttpClient客户端
 * 需要在module-info.java中requires引入jdk.incubator.httpclient;模块
 *
 * @author sunchaser admin@lilu.org.cn
 * @since JDK9 2022/2/11
 */
public class NewHttpClient {
    public static void main(String[] args) throws Exception {
        syncHttpGet();
        asyncHttpGet();
    }

    /**
     * 异步HTTP调用，返回CompletableFuture
     */
    private static void asyncHttpGet() throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create("http://www.baidu.com"))
                .header("user-agent", "sunchaser")
                .GET()
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandler.asString();
        CompletableFuture<HttpResponse<String>> cf = httpClient.sendAsync(httpRequest, bodyHandler);

        cf.thenApply(HttpResponse::body).thenAccept(System.out::println);

        HttpResponse<String> httpResponse = cf.get();
        String body = httpResponse.body();
        System.out.println(body);
    }

    /**
     * 同步HTTP调用
     */
    private static void syncHttpGet() throws Exception {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create("http://www.baidu.com"))
                .header("user-agent", "sunchaser")
                .GET()
                .build();
        HttpResponse.BodyHandler<String> bodyHandler = HttpResponse.BodyHandler.asString();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, bodyHandler);
        int statusCode = httpResponse.statusCode();
        String body = httpResponse.body();
        System.out.println(statusCode);
        System.out.println(body);
    }
}
