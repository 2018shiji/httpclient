package com.example.client.httpclient.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.client.httpclient.pojo.responseParam.ContainerFrontTail;
import com.example.client.httpclient.respHandler.CallbackHandler;
import com.example.client.httpclient.respHandler.CallbackHandlerT;
import com.example.client.httpclient.respHandler.ResponseConsumer;
import com.google.common.io.CharStreams;
import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@Component
public class HttpClientUtilAsync {
    @Getter private CloseableHttpAsyncClient asyncClient;
    @Getter private CallbackHandler callbackHandler = new CallbackHandler();

    @PostConstruct
    public void initAsyncClient() throws IOReactorException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setSocketTimeout(10000)
                .setConnectionRequestTimeout(1000)
                .build();

        IOReactorConfig ioReactorCfg = IOReactorConfig.custom()
                .setIoThreadCount(2)
                .setSoKeepAlive(true)
                .build();

        ConnectingIOReactor connReactor = new DefaultConnectingIOReactor(ioReactorCfg);
        PoolingNHttpClientConnectionManager connManager =
                new PoolingNHttpClientConnectionManager(connReactor);
        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(100);

        asyncClient = HttpAsyncClients.custom()
                .setConnectionManager(connManager)
                .setDefaultRequestConfig(requestConfig)
                .build();

    }

    public Future<ContainerFrontTail> getContainerFrontTailPostCF(String remoteUrl, String base64Str, CountDownLatch countDownLatch){
        Future<ContainerFrontTail> responseFuture = null;

        HttpPost httpPost = new HttpPost(remoteUrl);
        StringEntity stringEntity =
                new StringEntity(base64Str, ContentType.create("UTF-8"));
        httpPost.setEntity(stringEntity);
        HttpAsyncRequestProducer requestProducer = HttpAsyncMethods.create(httpPost);

        ResponseConsumer<ContainerFrontTail> frontTailConsumer = new ResponseConsumer<ContainerFrontTail>();
        frontTailConsumer.setTargetClass(ContainerFrontTail.class);

        CallbackHandlerT<ContainerFrontTail> callbackHandler = new CallbackHandlerT<ContainerFrontTail>();

        callbackHandler.setCountDownLatch(countDownLatch);

        long begin = System.currentTimeMillis();
        System.out.println("------------before execute------------" +
                new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
        responseFuture = asyncClient.execute(requestProducer, frontTailConsumer, callbackHandler);
        System.out.println("------------after execute------------" + (System.currentTimeMillis() - begin) / 1000.0);

        return responseFuture;

    }


    public Future<HttpResponse> getContainerFrontTailPost(String remoteUrl, String base64Str, CountDownLatch countDownLatch) {

        Future<HttpResponse> responseFuture = null;

        HttpPost httpPost = new HttpPost(remoteUrl);

        StringEntity stringEntity =
            new StringEntity(base64Str, ContentType.create("UTF-8"));
        httpPost.setEntity(stringEntity);

        callbackHandler.setCountDownLatch(countDownLatch);

        long begin = System.currentTimeMillis();
        System.out.println("------------before execute------------" +
                new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
        responseFuture = asyncClient.execute(httpPost, callbackHandler);
        System.out.println("------------after execute------------" + (System.currentTimeMillis() - begin) / 1000.0);

        return responseFuture;

    }


}
