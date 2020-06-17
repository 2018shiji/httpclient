package com.example.client.httpclient;

import com.example.client.httpclient.containerImp.AsyncOrderedContainer;
import com.example.client.httpclient.containerImp.BlockedContainer;
import com.example.client.httpclient.containerImp.MultiThreadedContainer;
import com.example.client.httpclient.pojo.response.ContainerFrontTail;
import com.example.client.httpclient.pojo.response.ContainerInfo;
import com.example.client.httpclient.pojo.response.ContainerRoofInfo;
import com.example.client.httpclient.pojo.response.ContainerStatus;
import com.example.client.httpclient.util.HttpClientUtilTest;
import com.example.client.httpclient.util.SpringUtil;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class HttpclientApplicationTests {

    public static final String IMAGE_FILE_PATH = "C:\\Users\\Public\\Nwt\\cache\\recv\\毛骁\\识别图片";
    public static final String IMAGE_FILE_PATH_TEST = "C:\\Users\\Public\\Nwt\\cache\\recv\\毛骁\\识别图片base64Test";

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
    void threadPoolFrontTailMT() {
        IContainer container = SpringUtil.getBean(MultiThreadedContainer.class);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        ((MultiThreadedContainer)container).setExecutor(executorService);
        long begin = System.currentTimeMillis();
        List<ContainerFrontTail> containerFrontTails = container.getContainerFrontTails(IMAGE_FILE_PATH_TEST);
        System.out.println(containerFrontTails == null ? "null":containerFrontTails.size());
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
