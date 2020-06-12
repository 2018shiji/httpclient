package com.example.client.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.client.httpclient.pojo.requestParam.ImagePojo;
import com.example.client.httpclient.pojo.requestParam.InfoPojo;
import com.example.client.httpclient.pojo.responseParam.ContainerFrontTail;
import com.example.client.httpclient.pojo.responseParam.ContainerInfo;
import com.example.client.httpclient.pojo.responseParam.ContainerRoofInfo;
import com.example.client.httpclient.pojo.responseParam.ContainerStatus;
import com.example.client.httpclient.taskThread.ContainerThread;
import com.example.client.httpclient.util.ConstantUtil;
import com.example.client.httpclient.util.HttpClientUtil;
import com.example.client.httpclient.util.HttpClientUtilAsync;
import com.google.common.io.Files;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.*;

@Component
public class Container {
    @Autowired private HttpClientUtil httpClient;
    @Autowired private HttpClientUtilAsync asyncClient;
    private BlockingQueue<String> imageBase64Strings = new LinkedBlockingQueue<String>();
    private BlockingQueue<String> containerInfoStrings = new LinkedBlockingQueue<String>();
    private BlockingQueue<String> containerInfoRoofStrings = new LinkedBlockingQueue<String>();
    public static final String CONTAINER_FRONT_TAIL = "https://zsg-serving.cloudwalk.cn:8081/container_front_tail";
    public static final String CONTAINER_STATUS = "https://zsg-serving.cloudwalk.cn:8082/container_status";
    public static final String CONTAINER_INFO = "https://zsg-serving.cloudwalk.cn:8082/container_info";


    private List<String> fileNames = new ArrayList();

    public Logger logger = LoggerFactory.getLogger(Container.class);


    /** 一请求一响应完全阻塞版 */
    public List<ContainerFrontTail> getContainerFrontTails(String imageUri){
        initContainer(imageUri);
        List<ContainerFrontTail> results = new ArrayList<>();
        try{
            int i = 0;
            while(true) {
                String imageStr = imageBase64Strings.take();
                ContainerFrontTail frontTailPost = httpClient.getContainerFrontTailPost("http://127.0.0.1:8082/doFrontTailPostJson", imageStr);
//                ContainerFrontTail frontTailPost = httpClient.getContainerFrontTailPost(CONTAINER_FRONT_TAIL, imageStr);
                String fileName = fileNames.get(i++);
                frontTailPost.setFileName(fileName);
                results.add(frontTailPost);
                logger.info(fileName + "------->" + frontTailPost.toString());
                if(imageBase64Strings.isEmpty())break;
            }
        } catch (InterruptedException e){e.printStackTrace();}

        return results;
    }

    public List<ContainerStatus> getContainerStatus(String imageUri){
        initContainer(imageUri);
        List<ContainerStatus> results = new ArrayList<>();
        try{
            int i = 0;
            while(true) {
                String imageStr = imageBase64Strings.take();
                ContainerStatus statusPost = httpClient.getContainerStatusPost(CONTAINER_STATUS, imageStr);
                String fileName = fileNames.get(i++);
                statusPost.setFileName(fileName);
                results.add(statusPost);
                System.out.println(fileName + "------->" + statusPost.toString());
                if (imageBase64Strings.isEmpty()) break;
            }
        } catch (InterruptedException e){e.printStackTrace();}
        return results;
    }

    public List<ContainerInfo> getContainerInfos(String imageUri){
        initContainer(imageUri);
        List<ContainerInfo> results = new ArrayList<>();
        try{
            int i = 0;
            while(true) {
                String infoStr = containerInfoStrings.take();
                ContainerInfo infoPost = httpClient.getContainerInfoPost(CONTAINER_INFO, infoStr);
                String fileName = fileNames.get(i++);
                infoPost.setFileName(fileName);
                results.add(infoPost);
                logger.info(fileName + "------->" + infoPost.toString());
                if (containerInfoStrings.isEmpty()) break;
            }
        } catch (InterruptedException e){e.printStackTrace();}
        return results;
    }

    public List<ContainerRoofInfo> getContainerRoofInfos(String imageUri){
        initContainer(imageUri);
        List<ContainerRoofInfo> results = new ArrayList<>();
        try{
            int i = 0;
            while(true) {
                String roofInfoStr = containerInfoRoofStrings.take();
                ContainerRoofInfo roofInfoPost = httpClient.getContainerRoofInfoPost(CONTAINER_INFO, roofInfoStr);
                String fileName = fileNames.get(i++);
                roofInfoPost.setFileName(fileName);
                results.add(roofInfoPost);
                logger.info(fileName + "------->" + roofInfoPost.toString());
                if (containerInfoRoofStrings.isEmpty()) break;
            }
        } catch (InterruptedException e){e.printStackTrace();}
        return results;
    }

    /** 异步回调无序版，需client，server两端定义好序列号serialNumber实现顺序 */
    public List<ContainerFrontTail> getFrontTailFutureAsync(String imageUri) {
        initContainer(imageUri);
        sendRequestAsyncCallback("http://127.0.0.1:8082/doFrontTailPostJson");
//        sendRequestAsync(CONTAINER_FRONT_TAIL);

        List<ContainerFrontTail> frontTails = new ArrayList<>();
        List<String> frontTailStrings = asyncClient.getCallbackHandler().getResultStrings();
        try{
            for(int i = 0; i < frontTailStrings.size(); i++){
                String resultStr = frontTailStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                frontTails.add(JSON.toJavaObject(jsonObject, ContainerFrontTail.class));
            }
        } catch (Exception e){e.printStackTrace();}

        return frontTails;
    }

    /** 异步线程池多任务提交版， 任务的提交顺序与Future列表存在顺序对应关系，故有序。*/
    public List<ContainerFrontTail> getFrontTailFutureMT(String imageUri) {
        initContainer(imageUri);
        List<ContainerFrontTail> frontTails = new ArrayList<>();
        List<String> frontTailStrings = sendRequestAsyncMultiThread(CONTAINER_FRONT_TAIL, imageBase64Strings);
//        List<String> frontTailStrings = sendRequestAsyncMultiThread("http://127.0.0.1:8082/doFrontTailPostJson", imageBase64Strings);
        try{
            int j = 0;
            for(int i = 0; i < frontTailStrings.size(); i++){
                String resultStr = frontTailStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                ContainerFrontTail containerFrontTail = JSON.toJavaObject(jsonObject, ContainerFrontTail.class);
                String fileName = fileNames.get(j++);
                containerFrontTail.setFileName(fileName);
                logger.info(fileName + "------->" + containerFrontTail.toString());
                frontTails.add(containerFrontTail);
            }
        } catch (Exception e){e.printStackTrace();}

        return frontTails;
    }

    public List<ContainerStatus> getContainerStatusAsyncMT(String imageUri){
        initContainer(imageUri);
        List<String> statusStrings = sendRequestAsyncMultiThread(CONTAINER_STATUS, imageBase64Strings);

        List<ContainerStatus> statuses = new ArrayList<>();
        try{
            int j = 0;
            for(int i = 0; i < statusStrings.size(); i++){
                String resultStr = statusStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                ContainerStatus containerStatus = JSON.toJavaObject(jsonObject, ContainerStatus.class);
                String fileName = fileNames.get(j++);
                containerStatus.setFileName(fileName);
                logger.info(fileName + "------->" + containerStatus.toString());
                statuses.add(containerStatus);
            }
        } catch (Exception e){e.printStackTrace();}

        return statuses;
    }

    public List<ContainerInfo> getContainerInfoAsyncMT(String imageUri){
        initContainer(imageUri);
        List<ContainerInfo> infos = new ArrayList<>();
        List<String> infoStrings = sendRequestAsyncMultiThread(CONTAINER_INFO, containerInfoStrings);
        try{
            int j = 0;
            for(int i = 0; i < infoStrings.size(); i++){
                String resultStr = infoStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                ContainerInfo containerInfo = JSON.toJavaObject(jsonObject, ContainerInfo.class);
                String fileName = fileNames.get(j++);
                containerInfo.setFileName(fileName);
                logger.info(fileName + "------->" + containerInfo.toString());
                infos.add(containerInfo);
            }
        } catch (Exception e){e.printStackTrace();}

        return infos;
    }

    public List<ContainerRoofInfo> getContainerRoofInfoAsyncMT(String imageUri){
        initContainer(imageUri);
        List<ContainerRoofInfo> roofInfos = new ArrayList<>();
        List<String> roofInfoStrings = sendRequestAsyncMultiThread(CONTAINER_INFO, containerInfoRoofStrings);
        try{
            int j = 0;
            for(int i = 0; i < roofInfoStrings.size(); i++){
                String resultStr = roofInfoStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                ContainerRoofInfo containerRoofInfo = JSON.toJavaObject(jsonObject, ContainerRoofInfo.class);
                String fileName = fileNames.get(j++);
                containerRoofInfo.setFileName(fileName);
                logger.info(fileName + "------->" + containerRoofInfo.toString());
                roofInfos.add(containerRoofInfo);
            }
        } catch (Exception e){e.printStackTrace();}

        return roofInfos;
    }

    private void sendRequestAsyncCallback(String remoteUrl){
        try {
            CountDownLatch countDownLatch = new CountDownLatch(imageBase64Strings.size());
            asyncClient.start();
            while (true) {
                String imageStr = imageBase64Strings.take();
                asyncClient.sendHttpRequestAsyncCallback(remoteUrl, imageStr, countDownLatch);
                if (imageBase64Strings.isEmpty()) break;
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            asyncClient.close();
        }
    }

    private List<String> sendRequestAsyncMultiThread(String remoteUrl, BlockingQueue<String> requestStrings){
        List<String> results = new ArrayList<>();
        try {
            CountDownLatch countDownLatch = new CountDownLatch(requestStrings.size());
            asyncClient.start();
            ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
            List<Callable<String>> responseCalls = new ArrayList<>();
            while (true) {
                String imageStr = requestStrings.take();
                Future<HttpResponse> httpResponseFuture = asyncClient.sendHttpRequestAsyncMultiThread(remoteUrl, imageStr);
                responseCalls.add(new ContainerThread(httpResponseFuture, countDownLatch));

                if (requestStrings.isEmpty()) break;
            }

            List<Future<String>> futures = singleThreadExecutor.invokeAll(responseCalls, 60, TimeUnit.SECONDS);
            for(int i = 0; i < futures.size(); i++){
                try {
                    String s = futures.get(i).get();
                    results.add(s);
                }catch (Exception e){e.printStackTrace();}
            }

            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            asyncClient.close();
        }
        return results;
    }

    private void initContainer(String imageUri) {
        clearCollections();
        File image = new File(imageUri);
        if(image.isDirectory()){
            String[] files = image.list();
            for(int i = 0; i < files.length; i++){
                File imageFile = new File(imageUri + "/" + files[i]);

                initBlockingQueues(imageFile);
            }
        }else{ initBlockingQueues(image); }

    }

    private void initBlockingQueues(File imageFile){
        Set<String> unmodifiedImgFormats = ConstantUtil.imageFormats();

        String base64Str = "";
        try {
            File imageStr = imageFile;
            fileNames.add(imageFile.getName());
            int lastIndex = imageFile.getName().lastIndexOf(".");
            if (unmodifiedImgFormats.contains(imageFile.getName().substring(lastIndex+1))) {
                FileInputStream fileInputStream = new FileInputStream(imageFile);
                byte[] data = new byte[fileInputStream.available()];
                fileInputStream.read(data);
                /** 普通的base64加解密方法 */
                base64Str = Base64.getEncoder().encodeToString(data);
                fileInputStream.close();
            }else if("txt".equals(imageFile.getName().substring(lastIndex+1))){
                base64Str = Files.asCharSource(imageStr, Charset.forName("UTF-8")).read();
            }else {
                System.out.println("*********格式未可知*********");
            }
        } catch (Exception e) {e.printStackTrace();}

        ImagePojo imagePojo = new ImagePojo(base64Str);
        imageBase64Strings.add(JSON.toJSONString(imagePojo));
        InfoPojo infoRoofPojo = new InfoPojo(base64Str, "roof_model");
        containerInfoRoofStrings.add(JSON.toJSONString(infoRoofPojo));
        InfoPojo infoContainerPojo = new InfoPojo(base64Str, "container_model");
        containerInfoStrings.add(JSON.toJSONString(infoContainerPojo));
//                imageStrs.add("{\n" + "\"image\":" + "\"" + base64Str + "\"" + "\n}");
    }

    private void clearCollections(){
        this.fileNames.clear();
        this.imageBase64Strings.clear();
        this.containerInfoStrings.clear();
        this.containerInfoRoofStrings.clear();
    }
}
