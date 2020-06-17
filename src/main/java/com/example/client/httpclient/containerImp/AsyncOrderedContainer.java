package com.example.client.httpclient.containerImp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.client.httpclient.pojo.response.ContainerFrontTail;
import com.example.client.httpclient.pojo.response.ContainerInfo;
import com.example.client.httpclient.pojo.response.ContainerRoofInfo;
import com.example.client.httpclient.pojo.response.ContainerStatus;
import com.example.client.httpclient.util.HttpClientUtilAsync;
import com.google.common.io.CharStreams;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class AsyncOrderedContainer extends ContainerAbs {
    @Autowired
    private HttpClientUtilAsync asyncClient;

    /** 异步线程池多任务提交版， 任务的提交顺序与Future列表存在顺序对应关系，故有序。*/
    public List<ContainerFrontTail> getContainerFrontTails(String imageUri) {
        initContainer(imageUri);
        List<ContainerFrontTail> frontTails = new ArrayList<>();
//        List<String> frontTailStrings = sendRequestAsyncMultiThread(CONTAINER_FRONT_TAIL, imageBase64Strings);
        List<String> frontTailStrings = sendRequestAsyncMultiThread("http://127.0.0.1:8082/doFrontTailPostJson", imageBase64Strings);
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

    public List<ContainerStatus> getContainerStatuses(String imageUri){
        initContainer(imageUri);
        List<String> statusStrings = sendRequestAsyncMultiThread(CONTAINER_STATUS, imageBase64Strings);

        List<ContainerStatus> statuses = new ArrayList<>();
        try{
            for(int i = 0; i < statusStrings.size(); i++){
                String resultStr = statusStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                ContainerStatus containerStatus = JSON.toJavaObject(jsonObject, ContainerStatus.class);
                String fileName = fileNames.get(i);
                containerStatus.setFileName(fileName);
                logger.info(fileName + "------->" + containerStatus.toString());
                statuses.add(containerStatus);
            }
        } catch (Exception e){e.printStackTrace();}

        return statuses;
    }

    public List<ContainerInfo> getContainerInfos(String imageUri){
        initContainer(imageUri);
        List<ContainerInfo> infos = new ArrayList<>();
        List<String> infoStrings = sendRequestAsyncMultiThread(CONTAINER_INFO, containerInfoStrings);
        try{
            for(int i = 0; i < infoStrings.size(); i++){
                String resultStr = infoStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                ContainerInfo containerInfo = JSON.toJavaObject(jsonObject, ContainerInfo.class);
                String fileName = fileNames.get(i);
                containerInfo.setFileName(fileName);
                logger.info(fileName + "------->" + containerInfo.toString());
                infos.add(containerInfo);
            }
        } catch (Exception e){e.printStackTrace();}

        return infos;
    }

    public List<ContainerRoofInfo> getContainerRoofInfos(String imageUri){
        initContainer(imageUri);
        List<ContainerRoofInfo> roofInfos = new ArrayList<>();
        List<String> roofInfoStrings = sendRequestAsyncMultiThread(CONTAINER_INFO, containerInfoRoofStrings);
        try{
            for(int i = 0; i < roofInfoStrings.size(); i++){
                String resultStr = roofInfoStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                ContainerRoofInfo containerRoofInfo = JSON.toJavaObject(jsonObject, ContainerRoofInfo.class);
                String fileName = fileNames.get(i);
                containerRoofInfo.setFileName(fileName);
                logger.info(fileName + "------->" + containerRoofInfo.toString());
                roofInfos.add(containerRoofInfo);
            }
        } catch (Exception e){e.printStackTrace();}

        return roofInfos;
    }

    private List<String> sendRequestAsyncMultiThread(String remoteUrl, List<String> requestStrings){
        List<String> results = new ArrayList<>();
        try {
            CountDownLatch countDownLatch = new CountDownLatch(requestStrings.size());
            asyncClient.start();
            ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
            List<Callable<String>> responseCalls = new ArrayList<>();
            for(int i = 0; i < requestStrings.size(); i++) {
                String imageStr = requestStrings.get(i);
                Future<HttpResponse> httpResponseFuture = asyncClient.sendHttpRequestAsyncMultiThread(remoteUrl, imageStr);
                responseCalls.add(new ContainerThread(httpResponseFuture, countDownLatch));
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

    class ContainerThread implements Callable<String> {

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

}
