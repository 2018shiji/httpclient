package com.example.client.httpclient;

import com.example.client.httpclient.pojo.responseParam.ContainerFrontTail;
import com.example.client.httpclient.pojo.responseParam.ContainerInfo;
import com.example.client.httpclient.pojo.responseParam.ContainerRoofInfo;
import com.example.client.httpclient.pojo.responseParam.ContainerStatus;
import com.example.client.httpclient.util.HttpClientUtilTest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
        List<ContainerInfo> containerInfos = container.getContainerInfos();
        System.out.println(containerInfos == null ? "null":containerInfos.size());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerRoofInfo(){
        long begin = System.currentTimeMillis();
        List<ContainerRoofInfo> containerRoofInfos = container.getContainerRoofInfos();
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
    void containerFrontTailAsync(){
        long begin = System.currentTimeMillis();
        List<ContainerFrontTail> frontTailFutureAsync = container.getFrontTailFutureMT();
        System.out.println(frontTailFutureAsync);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerStatusAsync(){
        long begin = System.currentTimeMillis();
        List<ContainerStatus> statusFutureAsync = container.getContainerStatusAsyncMT();
        System.out.println(statusFutureAsync);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerInfoAsync(){
        long begin = System.currentTimeMillis();
        List<ContainerInfo> infoFutureAsync = container.getContainerInfoAsyncMT();
        System.out.println(infoFutureAsync);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerRoofInfoAsync(){
        long begin = System.currentTimeMillis();
        List<ContainerRoofInfo> roofInfoFutureAsync = container.getContainerRoofInfoAsyncMT();
        System.out.println(roofInfoFutureAsync);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void test(){
        StringEntity stringEntity =
                new StringEntity("111", ContentType.create("UTF-8"));
        StringEntity stringEntity1 =
                new StringEntity("111", ContentType.create("UTF-8"));
        System.out.println(stringEntity.equals(stringEntity1));

    }

    @Test
    void log(){
        Logger logger = LoggerFactory.getLogger("Test");
        logger.info("11111111");
    }

}
