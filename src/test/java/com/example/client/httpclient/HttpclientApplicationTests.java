package com.example.client.httpclient;

import com.example.client.httpclient.pojo.responseParam.ContainerFrontTail;
import com.example.client.httpclient.pojo.responseParam.ContainerInfo;
import com.example.client.httpclient.pojo.responseParam.ContainerRoofInfo;
import com.example.client.httpclient.pojo.responseParam.ContainerStatus;
import com.example.client.httpclient.util.HttpClientUtilTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class HttpclientApplicationTests {

    @Autowired
    HttpClientUtilTest httpClientUtilTest;

    @Test
    void contextLoads() {
        httpClientUtilTest.testSimpleGet();
    }

    @Test
    void testGetJson() throws Exception {
        httpClientUtilTest.testGetWithJsonEntityRH();
    }

    @Test
    void testPostWithFileEntity(){
        httpClientUtilTest.testPostWithFileEntity();
    }

    @Test
    void testPostWithStringEntity(){
        try {
            httpClientUtilTest.testPostWithStringEntity();
        } catch (Exception e){e.printStackTrace();}
    }

    @Test
    void testPostWithJsonEntity(){
        httpClientUtilTest.testPostWithJsonEntity();
    }


    @Autowired
    Container container;

    @Test
    void containerFrontTail(){
        long begin = System.currentTimeMillis();
        List<ContainerFrontTail> containerFrontTails = container.getContainerFrontTails();
        System.out.println(containerFrontTails == null ? "null":containerFrontTails.size());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerInfo(){
        long begin = System.currentTimeMillis();
        List<ContainerInfo> containerInfos = container.getContainerInfo();
        System.out.println(containerInfos == null ? "null":containerInfos.size());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerRoofInfo(){
        long begin = System.currentTimeMillis();
        List<ContainerRoofInfo> containerRoofInfos = container.getContainerRoofInfo();
        System.out.println(containerRoofInfos == null ? "null":containerRoofInfos.size());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerStatus(){
        long begin = System.currentTimeMillis();
        List<ContainerStatus> containerStatus = container.getContainerStatus();
        System.out.println(containerStatus == null ? "null":containerStatus.size());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerFrontTailMultiThreads() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6, 6, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        long begin = System.currentTimeMillis();
        for(int i = 0; i < 6; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println("Thread.id:" + Thread.currentThread().getId() + "time:" + System.currentTimeMillis()/1000.0);
                List<ContainerFrontTail> containerFrontTails = container.getContainerFrontTails();
                System.out.println(containerFrontTails == null ? "null" : containerFrontTails.size());
            });
        }

        while(threadPoolExecutor.getActiveCount()!=0){
            Thread.sleep(20);
        }
        threadPoolExecutor.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

    }

    @Test
    void containerStatusMultiThreads() throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(14, 14, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        long begin = System.currentTimeMillis();
        for(int i = 0; i < 14; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println("Thread.id:" + Thread.currentThread().getId() + "time:" + System.currentTimeMillis()/1000.0);
                List<ContainerStatus> containerStatus = container.getContainerStatus();
                System.out.println(containerStatus == null ? "null" : containerStatus.size());
            });
        }
        threadPoolExecutor.shutdown();
        while(!threadPoolExecutor.awaitTermination(100, TimeUnit.MILLISECONDS)){
            Thread.sleep(100);
        }
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

    }
}
