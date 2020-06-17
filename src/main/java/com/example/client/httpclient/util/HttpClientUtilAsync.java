package com.example.client.httpclient.util;

import com.example.client.httpclient.respHandler.CallbackHandler;
import lombok.Getter;
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
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

@Component
public class HttpClientUtilAsync {
    private CloseableHttpAsyncClient asyncClient;
    @Getter private CallbackHandler callbackHandler = new CallbackHandler();

    private void initAsyncClient() throws IOReactorException {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(60000)
                .setSocketTimeout(30000)
                .setConnectionRequestTimeout(30000)
                .build();

        IOReactorConfig ioReactorCfg = IOReactorConfig.custom()
                .setIoThreadCount(4)
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

    public Future<HttpResponse> sendHttpRequestAsyncCallback(String remoteUrl, String base64Str, CountDownLatch countDownLatch) {

        Future<HttpResponse> responseFuture = null;

        HttpPost httpPost = new HttpPost(remoteUrl);

        StringEntity stringEntity =
                new StringEntity(base64Str, ContentType.create("application/json", "UTF-8"));
        httpPost.setEntity(stringEntity);

        callbackHandler.setCountDownLatch(countDownLatch);

        long begin = System.currentTimeMillis();
        System.out.println("------------before execute------------" +
                new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
        responseFuture = asyncClient.execute(httpPost, callbackHandler);
        System.out.println("------------after execute------------" + (System.currentTimeMillis() - begin) / 1000.0);

        return responseFuture;

    }

    public Future<HttpResponse> sendHttpRequestAsyncMultiThread(String remoteUrl, String base64Str) {

        Future<HttpResponse> responseFuture = null;

        HttpPost httpPost = new HttpPost(remoteUrl);

        StringEntity stringEntity =
                new StringEntity(base64Str, ContentType.create("application/json", "UTF-8"));
        httpPost.setEntity(stringEntity);

        long begin = System.currentTimeMillis();
        System.out.println("------------before execute------------" +
                new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
        responseFuture = asyncClient.execute(httpPost, null);
        System.out.println("------------after execute------------" + (System.currentTimeMillis() - begin) / 1000.0);

        return responseFuture;

    }

    public void start(){
        try {
            initAsyncClient();
        }catch (IOReactorException e){e.printStackTrace();}
        this.asyncClient.start();
    }

    public void close(){
        try{
            this.asyncClient.close();
        } catch (IOException e){e.printStackTrace();}
    }

}
