package com.example.client.httpclient.taskThread;

import com.google.common.io.CharStreams;
import org.apache.http.HttpResponse;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

public class ContainerThread implements Callable<String> {

    private Future<HttpResponse> future;
    private CountDownLatch countDownLatch;

    public ContainerThread(Future<HttpResponse> future, CountDownLatch countDownLatch){
        this.future = future;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public String call() {
        String contentStr = null;
        try{
            HttpResponse httpResponse = future.get();
            InputStream content = httpResponse.getEntity().getContent();
            contentStr = CharStreams.toString(new InputStreamReader(content));
            System.out.println("//////////////////////    " + contentStr);
            countDownLatch.countDown();
        }catch(Exception e) {
            countDownLatch.countDown();
            e.printStackTrace();
        }
        return contentStr;
    }
}
