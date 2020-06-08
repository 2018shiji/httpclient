package com.example.client.httpclient.respHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.CharStreams;
import lombok.Setter;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.ContentDecoder;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.protocol.AbstractAsyncResponseConsumer;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

public class ResponseConsumer<T> extends AsyncCharConsumer<T> {
    private T result;
    @Setter private Class<T> targetClass;

    @Override
    protected void onCharReceived(CharBuffer charBuffer, IOControl ioControl) throws IOException {
        System.out.println("char receive");
    }

    @Override
    protected void onResponseReceived(HttpResponse httpResponse) throws HttpException, IOException {
        System.out.println("response receive");
    }

    @Override
    protected T buildResult(HttpContext httpContext) throws Exception {
        System.out.println("build result");
        HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
        System.out.println(clientContext.getTargetHost());
        System.out.println(clientContext.getRequest());
//        HttpEntity httpEntity = clientContext.getResponse().getEntity();
//        InputStreamReader inputStreamReader = new InputStreamReader(httpEntity.getContent());
//        String resultStr = CharStreams.toString(inputStreamReader);
//        System.out.println(resultStr);
//        JSONObject jsonObject = JSON.parseObject(resultStr);
//        result = JSON.toJavaObject(jsonObject, targetClass);
//        return result;
        JSONObject jsonObject = JSON.parseObject("{\"message\":\"responseMessage\",\"status\":\"responseStatus\",\"success\":true}");
        result = JSON.toJavaObject(null, targetClass);
        return result;
    }

}
