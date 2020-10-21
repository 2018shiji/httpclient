package com.httpclient.ocridentify.containerImp;

import com.httpclient.ocridentify.pojo.response.ContainerFrontTail;
import com.httpclient.ocridentify.pojo.response.ContainerInfo;
import com.httpclient.ocridentify.pojo.response.ContainerRoofInfo;
import com.httpclient.ocridentify.pojo.response.ContainerStatus;
import com.httpclient.core.util.HttpClientUtilBlock;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BlockedContainer<T> extends ContainerAbs {
    private HttpClientUtilBlock httpClientUtilBlock = new HttpClientUtilBlock();

    /** 一请求一响应完全阻塞版 **/
    public List<ContainerFrontTail> getContainerFrontTails(String imageUri){
        List<ContainerFrontTail> containerFrontTails =
                (List<ContainerFrontTail>) getResponseObjects(imageUri, imageBase64Strings, ContainerFrontTail.class);
        for(int i = 0; i < containerFrontTails.size(); i++){
            containerFrontTails.get(i).setFileName(fileNames.get(i));
        }
        return containerFrontTails;
    }

    public List<ContainerStatus> getContainerStatuses(String imageUri){
        List<ContainerStatus> containerStatuses =
                (List<ContainerStatus>) getResponseObjects(imageUri, imageBase64Strings, ContainerStatus.class);
        for(int i = 0; i < containerStatuses.size(); i++){
            containerStatuses.get(i).setFileName(fileNames.get(i));
        }
        return containerStatuses;
    }

    public List<ContainerInfo> getContainerInfos(String imageUri){
        List<ContainerInfo> containerInfos =
                (List<ContainerInfo>) getResponseObjects(imageUri, containerInfoStrings, ContainerInfo.class);
        for(int i = 0; i < containerInfos.size(); i++){
            containerInfos.get(i).setFileName(fileNames.get(i));
        }
        return containerInfos;
    }

    public List<ContainerRoofInfo> getContainerRoofInfos(String imageUri){
        List<ContainerRoofInfo> containerRoofInfos =
                (List<ContainerRoofInfo>) getResponseObjects(imageUri, containerInfoRoofStrings, ContainerRoofInfo.class);
        for(int i = 0; i < containerRoofInfos.size(); i++){
            containerRoofInfos.get(i).setFileName(fileNames.get(i));
        }
        return containerRoofInfos;
    }

    private List<T> getResponseObjects(String imageUri, List<String> imageStrings, Class targetClass){
        initContainer(imageUri);
        httpClientUtilBlock.setTargetClass(targetClass);
        List<T> results = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            for(int i = 0; i < imageStrings.size(); i++){
                String imageStr = imageStrings.get(i);
                T responsePost = (T) httpClientUtilBlock.getPostResponse("http://127.0.0.1:8083/doFrontTailPostJson", imageStr, httpClient);
                results.add(responsePost);
            }
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) { e.printStackTrace();}
        }

        return results;
    }

}
