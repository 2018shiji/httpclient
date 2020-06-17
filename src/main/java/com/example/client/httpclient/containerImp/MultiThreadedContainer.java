package com.example.client.httpclient.containerImp;

import com.example.client.httpclient.pojo.response.ContainerFrontTail;
import com.example.client.httpclient.pojo.response.ContainerInfo;
import com.example.client.httpclient.pojo.response.ContainerRoofInfo;
import com.example.client.httpclient.pojo.response.ContainerStatus;
import com.example.client.httpclient.util.HttpClientUtilBlock;
import lombok.Setter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

@Component
public class MultiThreadedContainer extends ContainerAbs {
    @Autowired
    private HttpClientUtilBlock httpClientUtilBlock;
    @Setter private ExecutorService executor;

    /**
     * 阻塞的多请求多线程多客户端
     * @param imageUri
     * @return
     */
    public List<ContainerFrontTail> getContainerFrontTails(String imageUri){
        initContainer(imageUri);
        CountDownLatch countDownLatch = new CountDownLatch(imageBase64Strings.size());
        List<ContainerFrontTail> results = new ArrayList<>(imageBase64Strings.size());

        for (int i = 0; i < imageBase64Strings.size(); i++) {
            int finalI = i;
            executor.submit(() -> {
                ContainerFrontTail containerFrontTail = null;
                CloseableHttpClient httpClient = HttpClients.createDefault();
                try {
                    String imageStr = imageBase64Strings.get(finalI);
                    containerFrontTail = httpClientUtilBlock.
                            getContainerFrontTailPost("http://127.0.0.1:8082/doFrontTailPostJson", imageStr, httpClient);
//                    containerFrontTail = httpClientUtil.getContainerFrontTailPost(CONTAINER_FRONT_TAIL, imageStr, httpClient);
                    countDownLatch.countDown();
                } finally {
                    try {
                        httpClient.close();
                    } catch (IOException e) { e.printStackTrace();}
                }
                results.add(finalI, containerFrontTail);
            });
        }
        try {
            countDownLatch.await();
        }catch (InterruptedException e){e.printStackTrace();}
        for(int i = 0; i < results.size(); i++){
            String fileName = fileNames.get(i);
            logger.info(fileName + "------->" + results.get(i).toString());
        }
        return results;
    }

    public List<ContainerStatus> getContainerStatuses(String imageUri){
        initContainer(imageUri);
        CountDownLatch countDownLatch = new CountDownLatch(imageBase64Strings.size());
        List<ContainerStatus> results = new ArrayList<>(imageBase64Strings.size());

        for (int i = 0; i < imageBase64Strings.size(); i++) {
            int finalI = i;
            executor.submit(() -> {
                ContainerStatus containerStatus = null;
                CloseableHttpClient httpClient = HttpClients.createDefault();
                try {
                    String imageStr = imageBase64Strings.get(finalI);
                    containerStatus = httpClientUtilBlock.
                            getContainerStatusPost("http://127.0.0.1:8082/doFrontTailPostJson", imageStr, httpClient);
//                    containerStatus = httpClientUtil.getContainerStatusPost(CONTAINER_FRONT_TAIL, imageStr, httpClient);
                    countDownLatch.countDown();
                } finally {
                    try {
                        httpClient.close();
                    } catch (IOException e) { e.printStackTrace();}
                }
                results.add(finalI, containerStatus);
            });
        }
        try {
            countDownLatch.await();
        }catch (InterruptedException e){e.printStackTrace();}
        for(int i = 0; i < results.size(); i++){
            String fileName = fileNames.get(i);
            logger.info(fileName + "------->" + results.get(i).toString());
        }
        return results;
    }

    public List<ContainerInfo> getContainerInfos(String imageUri){
        initContainer(imageUri);
        CountDownLatch countDownLatch = new CountDownLatch(imageBase64Strings.size());
        List<ContainerInfo> results = new ArrayList<>(imageBase64Strings.size());

        for (int i = 0; i < imageBase64Strings.size(); i++) {
            int finalI = i;
            executor.submit(() -> {
                ContainerInfo containerInfo = null;
                CloseableHttpClient httpClient = HttpClients.createDefault();
                try {
                    String imageStr = imageBase64Strings.get(finalI);
                    containerInfo = httpClientUtilBlock.getContainerInfoPost(CONTAINER_FRONT_TAIL, imageStr, httpClient);
                    countDownLatch.countDown();
                } finally {
                    try {
                        httpClient.close();
                    } catch (IOException e) { e.printStackTrace();}
                }
                results.add(finalI, containerInfo);
            });
        }
        try {
            countDownLatch.await();
        }catch (InterruptedException e){e.printStackTrace();}
        for(int i = 0; i < results.size(); i++){
            String fileName = fileNames.get(i);
            logger.info(fileName + "------->" + results.get(i).toString());
        }
        return results;
    }

    public List<ContainerRoofInfo> getContainerRoofInfos(String imageUri){
        initContainer(imageUri);
        CountDownLatch countDownLatch = new CountDownLatch(imageBase64Strings.size());
        List<ContainerRoofInfo> results = new ArrayList<>(imageBase64Strings.size());

        for (int i = 0; i < imageBase64Strings.size(); i++) {
            int finalI = i;
            executor.submit(() -> {
                ContainerRoofInfo containerRoofInfo = null;
                CloseableHttpClient httpClient = HttpClients.createDefault();
                try {
                    String imageStr = imageBase64Strings.get(finalI);
                    containerRoofInfo = httpClientUtilBlock.getContainerRoofInfoPost(CONTAINER_FRONT_TAIL, imageStr, httpClient);
                    countDownLatch.countDown();
                } finally {
                    try {
                        httpClient.close();
                    } catch (IOException e) { e.printStackTrace();}
                }
                results.add(finalI, containerRoofInfo);
            });
        }
        try {
            countDownLatch.await();
        }catch (InterruptedException e){e.printStackTrace();}
        for(int i = 0; i < results.size(); i++){
            String fileName = fileNames.get(i);
            logger.info(fileName + "------->" + results.get(i).toString());
        }
        return results;
    }

}
