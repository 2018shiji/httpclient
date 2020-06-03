package com.example.client.httpclient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HttpclientApplicationTests {

    @Autowired
    HttpClientUtil httpClientUtil;

    @Test
    void contextLoads() {
        httpClientUtil.testSimpleGet();
    }

    @Test
    void testGetJson() throws Exception {
        httpClientUtil.testGetWithJsonEntityRH();
    }

    @Test
    void testPostWithFileEntity(){
        httpClientUtil.testPostWithFileEntity();
    }

    @Test
    void testPostWithStringEntity(){
        try {
            httpClientUtil.testPostWithStringEntity();
        } catch (Exception e){e.printStackTrace();}
    }

    @Test
    void testPostWithJsonEntity(){
        httpClientUtil.testPostWithJsonEntity();
    }


}
