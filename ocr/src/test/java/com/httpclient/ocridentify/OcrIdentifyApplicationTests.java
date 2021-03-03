package com.httpclient.ocridentify;


import com.alibaba.fastjson.JSON;
import com.google.common.io.CharStreams;
import com.httpclient.ocridentify.containerImp.AsyncOrderedContainer;
import com.httpclient.ocridentify.containerImp.BlockedContainer;
import com.httpclient.ocridentify.pojo.response.ContainerFrontTail;
import com.httpclient.ocridentify.pojo.response.ContainerInfo;
import com.httpclient.ocridentify.pojo.response.ContainerRoofInfo;
import com.httpclient.ocridentify.pojo.response.ContainerStatus;
import com.httpclient.ocridentify.util.HttpClientUtilTest;
import com.httpclient.ocridentify.util.SpringUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class OcrIdentifyApplicationTests {

    public static final String IMAGE_FILE_PATH = "C:\\Users\\Public\\Nwt\\cache\\recv\\毛骁\\识别图片";
    public static final String IMAGE_FILE_PATH_TEST = "C:\\Users\\lizhuangjie.chnet\\Desktop\\工作内容\\allpicture\\picture";

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


    @Test
    void containerFrontTail(){
        IContainer container = SpringUtil.getBean(BlockedContainer.class);
        long begin = System.currentTimeMillis();
        List<ContainerFrontTail> containerFrontTails = container.getContainerFrontTails(IMAGE_FILE_PATH_TEST);
        System.out.println(containerFrontTails == null ? "null":containerFrontTails.size());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerFrontTailBlockT(){
        BlockedContainer container = SpringUtil.getBean(BlockedContainer.class);
        long begin = System.currentTimeMillis();
        List<ContainerFrontTail> containerFrontTails = container.getContainerFrontTails(IMAGE_FILE_PATH_TEST);
        System.out.println(containerFrontTails == null ? "null":containerFrontTails.size());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }


    @Test
    void containerInfo(){
        IContainer container = SpringUtil.getBean(BlockedContainer.class);
        long begin = System.currentTimeMillis();
        List<ContainerInfo> containerInfos = container.getContainerInfos(IMAGE_FILE_PATH_TEST);
        System.out.println(containerInfos == null ? "null":containerInfos.size());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerRoofInfo(){
        IContainer container = SpringUtil.getBean(BlockedContainer.class);
        long begin = System.currentTimeMillis();
        List<ContainerRoofInfo> containerRoofInfos = container.getContainerRoofInfos(IMAGE_FILE_PATH_TEST);
        System.out.println(containerRoofInfos == null ? "null":containerRoofInfos.size());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerStatus(){
        IContainer container = SpringUtil.getBean(BlockedContainer.class);
        long begin = System.currentTimeMillis();
        List<ContainerStatus> containerStatus = container.getContainerStatuses(IMAGE_FILE_PATH_TEST);
        System.out.println(containerStatus == null ? "null":containerStatus.size());
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerFrontTailAsync(){
        IContainer container = SpringUtil.getBean(AsyncOrderedContainer.class);
        long begin = System.currentTimeMillis();
        List<ContainerFrontTail> frontTailFutureAsync = container.getContainerFrontTails(IMAGE_FILE_PATH_TEST);
        System.out.println(frontTailFutureAsync);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerStatusAsync(){
        IContainer container = SpringUtil.getBean(AsyncOrderedContainer.class);
        long begin = System.currentTimeMillis();
        List<ContainerStatus> statusFutureAsync = container.getContainerStatuses(IMAGE_FILE_PATH_TEST);
        System.out.println(statusFutureAsync);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerInfoAsync(){
        IContainer container = SpringUtil.getBean(AsyncOrderedContainer.class);
        long begin = System.currentTimeMillis();
        List<ContainerInfo> infoFutureAsync = container.getContainerInfos(IMAGE_FILE_PATH_TEST);
        System.out.println(infoFutureAsync);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
    }

    @Test
    void containerRoofInfoAsync(){
        IContainer container = SpringUtil.getBean(AsyncOrderedContainer.class);
        long begin = System.currentTimeMillis();
        List<ContainerRoofInfo> roofInfoFutureAsync = container.getContainerRoofInfos(IMAGE_FILE_PATH_TEST);
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

    Logger logger = LoggerFactory.getLogger("Test");
    @Test
    void log(){
        MDC.put("mock-message-id", "mock-message-id");
        logger.info("11111111");
        MDC.remove("mock-message-id");
    }

}
