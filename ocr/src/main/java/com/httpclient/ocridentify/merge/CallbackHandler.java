package com.httpclient.ocridentify.merge;

import com.google.common.io.CharStreams;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CallbackHandler implements FutureCallback<HttpResponse> {
    @Setter private CountDownLatch countDownLatch;
    @Getter private List<String> resultStrings = new ArrayList<>();

    @Override
    public void completed(HttpResponse result) {
        try {
            System.out.println(result);
            System.out.println("completed once");
            InputStream contentStream = result.getEntity().getContent();
            String contentStr = CharStreams.toString(new InputStreamReader(contentStream));
            System.out.println("=============" + contentStr);
            resultStrings.add(contentStr);
            countDownLatch.countDown();

        } catch (IOException e){e.printStackTrace();}
    }

    @Override
    public void failed(Exception e) {
        System.out.println("Failed..." + e.getMessage());
        countDownLatch.countDown();
    }

    @Override
    public void cancelled() {
        countDownLatch.countDown();
    }

}
