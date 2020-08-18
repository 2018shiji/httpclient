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

//    public ContainerFrontTail getContainerFrontTailPost(String remoteUrl, String base64Str, CloseableHttpClient httpClient) {
//        HttpPost httpPost = new HttpPost(remoteUrl);
//        StringEntity stringEntity =
//                new StringEntity(base64Str, ContentType.create("application/json", "UTF-8"));
//        httpPost.setEntity(stringEntity);
//
//        PostJsonEntityRH<ContainerFrontTail> jsonEntityRH = new PostJsonEntityRH<ContainerFrontTail>();
//        jsonEntityRH.setTargetClass(ContainerFrontTail.class);
//
//        ContainerFrontTail frontTail = null;
//        try {
//            long begin = System.currentTimeMillis();
//            System.out.println("------------before execute------------" +
//                    new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
//            frontTail = httpClient.execute(httpPost, jsonEntityRH);
//            System.out.println("------------after execute------------" + (System.currentTimeMillis()-begin)/1000.0 + "s Thread:" + Thread.currentThread().getId());
//            System.out.println(frontTail.getMessage() + "   " + frontTail.getStatus() + "  " + frontTail.isSuccess());
//        }catch (IOException e){e.printStackTrace();}
//        return frontTail;
//    }
//
//    public ContainerStatus getContainerStatusPost(String remoteUrl, String base64Str, CloseableHttpClient httpClient) {
//        HttpPost httpPost = new HttpPost(remoteUrl);
//        StringEntity stringEntity =
//                new StringEntity(base64Str, ContentType.create("application/json", "UTF-8"));
//        httpPost.setEntity(stringEntity);
//
//        PostJsonEntityRH<ContainerStatus> jsonEntityRH = new PostJsonEntityRH<ContainerStatus>();
//        jsonEntityRH.setTargetClass(ContainerStatus.class);
//
//        ContainerStatus status = null;
//        try{
//            long begin = System.currentTimeMillis();
//            System.out.println("------------before execute------------" +
//                    new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
//            status = httpClient.execute(httpPost, jsonEntityRH);
//            System.out.println("------------after execute------------" + (System.currentTimeMillis()-begin)/1000.0 + "Thread:" + Thread.currentThread().getId());
//            System.out.println(status.getMessage() + "   " + status.getStatus() + "  " + status.isSuccess());
//        }catch (IOException e){e.printStackTrace();}
//        return status;
//    }
//
//    public ContainerInfo getContainerInfoPost(String remoteUrl, String base64Str, CloseableHttpClient httpClient) {
//        HttpPost httpPost = new HttpPost(remoteUrl);
//        StringEntity stringEntity =
//                new StringEntity(base64Str, ContentType.create("application/json", "UTF-8"));
//        httpPost.setEntity(stringEntity);
//
//        PostJsonEntityRH<ContainerInfo> jsonEntityRH = new PostJsonEntityRH<ContainerInfo>();
//        jsonEntityRH.setTargetClass(ContainerInfo.class);
//
//        ContainerInfo info = null;
//        try{
//            long begin = System.currentTimeMillis();
//            System.out.println("------------before execute------------" +
//                    new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
//            info = httpClient.execute(httpPost, jsonEntityRH);
//            System.out.println("------------after execute------------" + (System.currentTimeMillis()-begin)/1000.0 + "Thread:" + Thread.currentThread().getId());
//            System.out.println(info.getContainerNum() + "   " + info.getIsoNum() + "   " + info.getMessage() + "  " + info.isSuccess());
//        }catch (IOException e){e.printStackTrace();}
//        return info;
//    }
//
//    public ContainerRoofInfo getContainerRoofInfoPost(String remoteUrl, String base64Str, CloseableHttpClient httpClient) {
//        HttpPost httpPost = new HttpPost(remoteUrl);
//        StringEntity stringEntity =
//                new StringEntity(base64Str, ContentType.create("application/json", "UTF-8"));
//        httpPost.setEntity(stringEntity);
//
//        PostJsonEntityRH<ContainerRoofInfo> jsonEntityRH = new PostJsonEntityRH<ContainerRoofInfo>();
//        jsonEntityRH.setTargetClass(ContainerRoofInfo.class);
//
//        ContainerRoofInfo roofInfo = null;
//        try{
//            long begin = System.currentTimeMillis();
//            System.out.println("------------before execute------------" +
//                    new SimpleDateFormat("HH:mm:ss:SSS").format(Calendar.getInstance().getTime()));
//            roofInfo = httpClient.execute(httpPost, jsonEntityRH);
//            System.out.println("------------after execute------------" + (System.currentTimeMillis()-begin)/1000.0 + "Thread:" + Thread.currentThread().getId());
//            System.out.println(roofInfo.getMessage() + "   " + roofInfo.getRoofNum() + "  " + roofInfo.isSuccess());
//        }catch (IOException e){e.printStackTrace();}
//        return roofInfo;
//    }


}
