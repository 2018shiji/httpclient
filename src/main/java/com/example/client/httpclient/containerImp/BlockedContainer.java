package com.example.client.httpclient.containerImp;

import com.example.client.httpclient.pojo.response.ContainerFrontTail;
import com.example.client.httpclient.pojo.response.ContainerInfo;
import com.example.client.httpclient.pojo.response.ContainerRoofInfo;
import com.example.client.httpclient.pojo.response.ContainerStatus;
import com.example.client.httpclient.util.HttpClientUtilBlock;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BlockedContainer extends ContainerAbs {
    @Autowired
    private HttpClientUtilBlock httpClientUtilBlock;

    /** 一请求一响应完全阻塞版 */
    public List<ContainerFrontTail> getContainerFrontTails(String imageUri){
        initContainer(imageUri);
        List<ContainerFrontTail> results = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            for(int i = 0; i < imageBase64Strings.size(); i++) {
                String imageStr = imageBase64Strings.get(i);
                ContainerFrontTail frontTailPost = httpClientUtilBlock.getContainerFrontTailPost("http://127.0.0.1:8082/doFrontTailPostJson", imageStr, httpClient);
//                ContainerFrontTail frontTailPost = httpClient.getContainerFrontTailPost(CONTAINER_FRONT_TAIL, imageStr, httpClient);
                String fileName = fileNames.get(i);
                frontTailPost.setFileName(fileName);
                results.add(frontTailPost);
                logger.info(fileName + "------->" + frontTailPost.toString());

            }
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) { e.printStackTrace();}
        }

        return results;
    }

    public List<ContainerStatus> getContainerStatuses(String imageUri){
        initContainer(imageUri);
        List<ContainerStatus> results = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            for(int i = 0; i < imageBase64Strings.size(); i++) {
                String imageStr = imageBase64Strings.get(i);
                ContainerStatus statusPost = httpClientUtilBlock.getContainerStatusPost(CONTAINER_STATUS, imageStr, httpClient);
                String fileName = fileNames.get(i);
                statusPost.setFileName(fileName);
                results.add(statusPost);
                System.out.println(fileName + "------->" + statusPost.toString());
            }
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) { e.printStackTrace();}
        }
        return results;
    }

    public List<ContainerInfo> getContainerInfos(String imageUri){
        initContainer(imageUri);
        List<ContainerInfo> results = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            for(int i = 0; i < containerInfoStrings.size(); i++) {
                String infoStr = containerInfoStrings.get(i);
                ContainerInfo infoPost = httpClientUtilBlock.getContainerInfoPost(CONTAINER_INFO, infoStr, httpClient);
                String fileName = fileNames.get(i);
                infoPost.setFileName(fileName);
                results.add(infoPost);
                logger.info(fileName + "------->" + infoPost.toString());
            }
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) { e.printStackTrace();}
        }
        return results;
    }

    public List<ContainerRoofInfo> getContainerRoofInfos(String imageUri){
        initContainer(imageUri);
        List<ContainerRoofInfo> results = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            for(int i = 0; i < containerInfoRoofStrings.size(); i++) {
                String roofInfoStr = containerInfoRoofStrings.get(i);
                ContainerRoofInfo roofInfoPost = httpClientUtilBlock.getContainerRoofInfoPost(CONTAINER_INFO, roofInfoStr, httpClient);
                String fileName = fileNames.get(i);
                roofInfoPost.setFileName(fileName);
                results.add(roofInfoPost);
                logger.info(fileName + "------->" + roofInfoPost.toString());
            }
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) { e.printStackTrace();}
        }
        return results;
    }

}
