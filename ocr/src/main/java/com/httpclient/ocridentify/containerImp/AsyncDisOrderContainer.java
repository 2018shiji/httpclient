package com.httpclient.ocridentify.containerImp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.httpclient.ocridentify.merge.CallbackHandler;
import com.httpclient.ocridentify.merge.HttpClientUtilAsync;
import com.httpclient.ocridentify.pojo.response.ContainerFrontTail;
import com.httpclient.ocridentify.pojo.response.ContainerInfo;
import com.httpclient.ocridentify.pojo.response.ContainerRoofInfo;
import com.httpclient.ocridentify.pojo.response.ContainerStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class AsyncDisOrderContainer extends ContainerAbs {

    private HttpClientUtilAsync asyncClient = new HttpClientUtilAsync();

    /**异步回调无序版*/
    public List<ContainerFrontTail> getContainerFrontTails(String imageUri) {
        initContainer(imageUri);

//        List<String> frontTailStrings = sendRequestWithAsyncDisOrder("http://127.0.0.1:8082/doFrontTailPostJson", imageBase64Strings);

        List<ContainerFrontTail> frontTails = new ArrayList<>();
        List<String> frontTailStrings = sendRequestWithAsyncDisOrder(CONTAINER_FRONT_TAIL, imageBase64Strings);
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

    public List<ContainerStatus> getContainerStatuses(String imageUri) {
        initContainer(imageUri);

        List<ContainerStatus> statuses = new ArrayList<>();
        List<String> statusStrings = sendRequestWithAsyncDisOrder(CONTAINER_STATUS, imageBase64Strings);
        try{
            for(int i = 0; i < statusStrings.size(); i++){
                String resultStr = statusStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                statuses.add(JSON.toJavaObject(jsonObject, ContainerStatus.class));
            }
        } catch (Exception e){e.printStackTrace();}

        return statuses;
    }

    public List<ContainerInfo> getContainerInfos(String imageUri) {
        initContainer(imageUri);

        List<ContainerInfo> infos = new ArrayList<>();
        List<String> infoStrings = sendRequestWithAsyncDisOrder(CONTAINER_INFO, containerInfoStrings);
        try{
            for(int i = 0; i < infoStrings.size(); i++){
                String resultStr = infoStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                infos.add(JSON.toJavaObject(jsonObject, ContainerInfo.class));
            }
        } catch (Exception e){e.printStackTrace();}

        return infos;
    }

    public List<ContainerRoofInfo> getContainerRoofInfos(String imageUri) {
        initContainer(imageUri);

        List<ContainerRoofInfo> roofInfos = new ArrayList<>();
        List<String> roofInfoStrings = sendRequestWithAsyncDisOrder(CONTAINER_INFO, containerInfoRoofStrings);
        try{
            for(int i = 0; i < roofInfoStrings.size(); i++){
                String resultStr = roofInfoStrings.get(i);
                System.out.println("+++++++++++++++++++++++" + resultStr);
                JSONObject jsonObject = JSON.parseObject(resultStr);
                roofInfos.add(JSON.toJavaObject(jsonObject, ContainerRoofInfo.class));
            }
        } catch (Exception e){e.printStackTrace();}

        return roofInfos;
    }


    /**
     * 异步非阻塞，调用后直接返回，返回结果以callBack进行回调，返回结果的顺序不一定与请求发起的顺序一致
     */
    private List<String> sendRequestWithAsyncDisOrder(String remoteUrl, List<String> requestStrings){
        CallbackHandler callbackHandler = new CallbackHandler();
        try {
            CountDownLatch countDownLatch = new CountDownLatch(requestStrings.size());
            callbackHandler.setCountDownLatch(countDownLatch);
            asyncClient.start();
            for(int i = 0; i < requestStrings.size(); i++) {
                String imageStr = requestStrings.get(i);
                asyncClient.sendHttpRequestWithCallback(remoteUrl, imageStr, callbackHandler);
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            asyncClient.close();
        }
        return callbackHandler.getResultStrings();
    }
}
