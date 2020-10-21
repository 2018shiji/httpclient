package com.httpclient.ocridentify.containerImp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.httpclient.ocridentify.pojo.response.ContainerFrontTail;
import com.httpclient.ocridentify.pojo.response.ContainerInfo;
import com.httpclient.ocridentify.pojo.response.ContainerRoofInfo;
import com.httpclient.ocridentify.pojo.response.ContainerStatus;
import com.google.common.io.CharStreams;
import com.httpclient.core.util.HttpClientUtilAsync;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Component
public class AsyncOrderedContainer extends ContainerAbs {
    private HttpClientUtilAsync asyncClient = new HttpClientUtilAsync();

    /** 异步提交有序版，任务的提交顺序与Future列表存在顺序对应关系，故有序。*/
    public List<ContainerFrontTail> getContainerFrontTails(String imageUri) {
        initContainer(imageUri);
        List<ContainerFrontTail> frontTails = new ArrayList<>();
        List<String> frontTailStrings = sendRequestWithAsyncOrdered(CONTAINER_FRONT_TAIL, imageBase64Strings);
//        List<String> frontTailStrings = sendRequestWithAsyncOrdered("http://127.0.0.1:8082/doFrontTailPostJson", imageBase64Strings);
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
        List<String> statusStrings = sendRequestWithAsyncOrdered(CONTAINER_STATUS, imageBase64Strings);

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
        List<String> infoStrings = sendRequestWithAsyncOrdered(CONTAINER_INFO, containerInfoStrings);
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
        List<String> roofInfoStrings = sendRequestWithAsyncOrdered(CONTAINER_INFO, containerInfoRoofStrings);
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

    /**
     * 异步阻塞，调用后以耗费时间最久的请求作为整个请求集的响应时间
     * 通过将请求发起时持有的Future按顺序存储，后续按集合中的Future的顺序进行响应结果的获取，
     * 进而保证了请求与结果之间能够顺序的一一对应，即请求与响应结果是一一顺序对应的。
     */
    private List<String> sendRequestWithAsyncOrdered(String remoteUrl, List<String> requestStrings){
        List<String> results = new ArrayList<>();
        try {
            CountDownLatch countDownLatch = new CountDownLatch(requestStrings.size());
            asyncClient.start();
            ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
            List<Callable<String>> responseCalls = new ArrayList<>();
            for(int i = 0; i < requestStrings.size(); i++) {
                String imageStr = requestStrings.get(i);
                Future<HttpResponse> httpResponseFuture = asyncClient.sendHttpRequestWithFuture(remoteUrl, imageStr);
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
