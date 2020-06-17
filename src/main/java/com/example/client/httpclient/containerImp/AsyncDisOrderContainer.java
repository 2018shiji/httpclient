package com.example.client.httpclient.containerImp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.client.httpclient.pojo.response.ContainerFrontTail;
import com.example.client.httpclient.pojo.response.ContainerInfo;
import com.example.client.httpclient.pojo.response.ContainerRoofInfo;
import com.example.client.httpclient.pojo.response.ContainerStatus;
import com.example.client.httpclient.util.HttpClientUtilAsync;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@Component
public class AsyncDisOrderContainer extends ContainerAbs {
    @Autowired
    private HttpClientUtilAsync asyncClient;

    /** 异步回调无序版，
     * 当需要顺序时，
     * 需client，server两端定义好序列号serialNumber以实现顺序
     * */
    public List<ContainerFrontTail> getContainerFrontTails(String imageUri) {
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

    public List<ContainerStatus> getContainerStatuses(String imageUri) {
        initContainer(imageUri);
        sendRequestAsyncCallback(CONTAINER_STATUS);

        List<ContainerStatus> statuses = new ArrayList<>();
        List<String> statusStrings = asyncClient.getCallbackHandler().getResultStrings();
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
        sendRequestAsyncCallback(CONTAINER_INFO);

        List<ContainerInfo> infos = new ArrayList<>();
        List<String> infoStrings = asyncClient.getCallbackHandler().getResultStrings();
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
        sendRequestAsyncCallback(CONTAINER_INFO);

        List<ContainerRoofInfo> roofInfos = new ArrayList<>();
        List<String> roofInfoStrings = asyncClient.getCallbackHandler().getResultStrings();
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


    private void sendRequestAsyncCallback(String remoteUrl){
        try {
            CountDownLatch countDownLatch = new CountDownLatch(imageBase64Strings.size());
            asyncClient.start();
            for(int i = 0; i < imageBase64Strings.size(); i++) {
                String imageStr = imageBase64Strings.get(i);
                asyncClient.sendHttpRequestAsyncCallback(remoteUrl, imageStr, countDownLatch);
            }
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            asyncClient.close();
        }
    }
}
