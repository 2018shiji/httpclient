package com.httpclient.core.respHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.CharStreams;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;
import java.io.InputStreamReader;

@Data
public class PostJsonEntityRH<T> implements ResponseHandler<T> {
    private Class targetClass;

    @Override
    public T handleResponse(HttpResponse httpResponse) throws IOException {
        StatusLine statusLine = httpResponse.getStatusLine();
        HttpEntity entity = httpResponse.getEntity();
        if(statusLine.getStatusCode() >= 300)
            throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
        if(entity == null)
            throw new ClientProtocolException("Response contains no content");
        String content;
        content = CharStreams.toString(new InputStreamReader(entity.getContent()));
        System.out.println(content);
        JSONObject jsonObject = JSON.parseObject(content);

        return (T)JSON.toJavaObject(jsonObject, targetClass);

    }
}
