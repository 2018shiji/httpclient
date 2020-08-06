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
    public List<ContainerFrontTail> getContainerFrontTailss(String imageUri){
        return (List<ContainerFrontTail>) getResponseObjects(imageUri, imageBase64Strings, ContainerFrontTail.class);
    }

    public List<ContainerStatus> getContainerStatusess(String imageUri){
        return (List<ContainerStatus>) getResponseObjects(imageUri, imageBase64Strings, ContainerStatus.class);
    }

    public List<ContainerInfo> getContainerInfoss(String imageUri){
        return (List<ContainerInfo>) getResponseObjects(imageUri, containerInfoStrings, ContainerInfo.class);
    }

    public List<ContainerRoofInfo> getContainerRoofInfoss(String imageUri){
        return (List<ContainerRoofInfo>) getResponseObjects(imageUri, containerInfoRoofStrings, ContainerRoofInfo.class);
    }

    private List<T> getResponseObjects(String imageUri, List<String> imageStrings, Class targetClass){
        initContainer(imageUri);
        httpClientUtilBlock.setTargetClass(targetClass);
        List<T> results = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            for(int i = 0; i < imageStrings.size(); i++){
                String imageStr = imageStrings.get(i);
                T responsePost = (T) httpClientUtilBlock.getResponsePost("http://127.0.0.1:8082/doFrontTailPostJson", imageStr, httpClient);
                results.add(responsePost);
            }
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) { e.printStackTrace();}
        }

        return results;
    }

    /** 一请求一响应完全阻塞版 */
    public List<ContainerFrontTail> getContainerFrontTails(String imageUri){
        initContainer(imageUri);
        List<ContainerFrontTail> results = new ArrayList<>();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try{
            for(int i = 0; i < imageBase64Strings.size(); i++) {
                String imageStr = imageBase64Strings.get(i);
                ContainerFrontTail frontTailPost = (ContainerFrontTail) httpClientUtilBlock.
                        setTargetClass(ContainerFrontTail.class).getResponsePost("http://127.0.0.1:8082/doFrontTailPostJson", imageStr, httpClient);
//                ContainerFrontTail frontTailPost = (ContainerFrontTail)httpClientUtilBlock.
//                        setTargetClass(ContainerFrontTail.class).getResponsePost(CONTAINER_FRONT_TAIL, imageStr, httpClient);
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
                ContainerStatus statusPost = (ContainerStatus) httpClientUtilBlock.
                        setTargetClass(ContainerStatus.class).getResponsePost(CONTAINER_STATUS, imageStr, httpClient);
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
                ContainerInfo infoPost = (ContainerInfo)httpClientUtilBlock
                        .setTargetClass(ContainerInfo.class).getResponsePost(CONTAINER_INFO, infoStr, httpClient);
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
                ContainerRoofInfo roofInfoPost = (ContainerRoofInfo) httpClientUtilBlock
                        .setTargetClass(ContainerRoofInfo.class).getResponsePost(CONTAINER_INFO, roofInfoStr, httpClient);
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
