package com.httpclient.core.util;

import com.httpclient.core.respHandler.PostJsonEntityRH;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class HttpClientUtilBlock<T> {
    Class targetClass;


    public HttpClientUtilBlock<T> setTargetClass(Class targetClass){
        this.targetClass = targetClass;
        return this;
    }

    public T getResponsePost(String remoteUrl, String base64Str, CloseableHttpClient httpClient){
        if(targetClass == null){
            System.out.println("!!!!!!! 请先设置好目标类型  !!!!!!!");
            return null;
        }
        HttpPost httpPost = new HttpPost(remoteUrl);
        StringEntity stringEntity =
                new StringEntity(base64Str, ContentType.create("application/json", "UTF-8"));
        httpPost.setEntity(stringEntity);

        PostJsonEntityRH<T> jsonEntityRH = new PostJsonEntityRH<>();
        jsonEntityRH.setTargetClass(targetClass);

        T result = null;
        try{
            long begin = System.currentTimeMillis();
            System.out.println("------------before execute------------" +
                    new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
            result = httpClient.execute(httpPost, jsonEntityRH);
            System.out.println("------------after execute------------" + (System.currentTimeMillis()-begin)/1000.0 + "s Thread:" + Thread.currentThread().getId());
            System.out.println(result);
        } catch (IOException e){e.printStackTrace();}

        return result;
    }


}
