package com.example.client.httpclient.respHandler;

import com.google.common.io.CharStreams;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import java.util.concurrent.CountDownLatch;

public class CallbackHandlerT<T> implements FutureCallback<T> {
    @Setter private CountDownLatch countDownLatch;

    @Override
    public void completed(T result) {
        System.out.println("completed once");
        System.out.println(result);
        countDownLatch.countDown();
    }

    @Override
    public void failed(Exception e) {
        System.out.println("Failed...");
        e.printStackTrace();
        countDownLatch.countDown();
    }

    @Override
    public void cancelled() {
        countDownLatch.countDown();
    }

}
