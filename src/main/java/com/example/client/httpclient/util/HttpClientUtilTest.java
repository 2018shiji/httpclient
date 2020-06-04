package com.example.client.httpclient.util;

import com.alibaba.fastjson.JSON;
import com.example.client.httpclient.pojo.responseParam.ContainerFrontTail;
import com.example.client.httpclient.pojo.responseParam.ContainerStatus;
import com.example.client.httpclient.respHandler.PostJsonEntityRH;
import com.google.common.io.CharStreams;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * httpClient util: version(4.5.10)
 *
 */
@Component
public class HttpClientUtilTest {
    private HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    private CloseableHttpClient httpClient = httpClientBuilder.build();

    public void testSimpleGet() {

        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/doHttpClientGet");

        CloseableHttpResponse response = null;
        String content = null;
        try {
            response = httpClient.execute(httpGet);
            InputStream inputStream = response.getEntity().getContent();
            content = CharStreams.toString(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            System.out.println("target uri: " + httpGet.getURI());
            System.out.println(content);
            System.out.println("statusLien:" + response.getStatusLine().toString());
            System.out.println("entity:" + response.getEntity().toString());
        }catch (Exception e) {
            e.printStackTrace();
        }finally{try{
            response.close();
        }catch (IOException e){e.printStackTrace();} }

    }

    public void testGetWithJsonEntityRH() throws Exception {
        HttpGet httpGet = new HttpGet("http://127.0.0.1:8080/doHttpClientGetJson?message=111&status=222&success=true");
        PostJsonEntityRH<ContainerStatus> jsonEntityRH = new PostJsonEntityRH<ContainerStatus>();
        jsonEntityRH.setTargetClass(ContainerStatus.class);
        ContainerStatus response = httpClient.execute(httpGet, jsonEntityRH);

        System.out.println(response.getMessage() + "  " + response.getStatus() + "   " + response.isSuccess());

    }

    public void testPostWithFileEntity() {
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/doHttpClientPost");

        File file = new File("E:\\U-download\\base64Str\\test.txt");
        FileEntity fileEntity = new FileEntity(file, ContentType.create("text/plain", "UTF-8"));
        httpPost.setEntity(fileEntity);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            System.out.println(Arrays.toString(response.getAllHeaders()));
            System.out.println(response.getEntity().toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally{try{
            response.close();
        }catch (IOException e){e.printStackTrace();} }

    }

    public void testPostWithStringEntity() throws Exception {
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/doHttpClientPost");

        StringEntity stringEntity =
                new StringEntity("important message", ContentType.create("text/plain", "UTF-8"));
        stringEntity.setChunked(true);
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println("headers:\n" + Arrays.toString(response.getAllHeaders()));
        System.out.println("response entity:\n" + response.getEntity().toString());
        response.close();
    }

    public void testPostWithJsonEntity(){
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8080/doHttpClientPostJson");

        ContainerFrontTail frontTail = new ContainerFrontTail();
        frontTail.setMessage("frontTailMessage");
        frontTail.setStatus("frontTailStatus");
        frontTail.setSuccess(true);

        StringEntity stringEntity =
                new StringEntity(JSON.toJSONString(frontTail), ContentType.create("application/json", "UTF-8"));
        httpPost.setEntity(stringEntity);

        PostJsonEntityRH<ContainerFrontTail> jsonEntityRH = new PostJsonEntityRH<ContainerFrontTail>();
        jsonEntityRH.setTargetClass(ContainerFrontTail.class);

        try {
            ContainerFrontTail result =
                    httpClient.execute(httpPost, jsonEntityRH);
            System.out.println(result.getMessage() + "   " + result.getStatus() + "  " + result.isSuccess());
        }catch (IOException e){e.printStackTrace();}

    }

    public boolean closeHttpClient(){
        try {
            httpClient.close();
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
