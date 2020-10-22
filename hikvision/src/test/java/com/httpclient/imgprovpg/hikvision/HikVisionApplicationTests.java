package com.httpclient.imgprovpg.hikvision;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HikVisionApplicationTests {

    PlateRestClientUtil plateUtil = new PlateRestClientUtil();
    DockRestClientUtil dockUtil = new DockRestClientUtil();

    @Test
    void contextLoads() {
        System.out.println("123");
    }

    @Test
    void getPlateById(){
        plateUtil.getPlateByPlateId();
    }

    @Test
    void getDockById(){
        dockUtil.getDockByDockId();
    }

    Logger logger = LoggerFactory.getLogger(HikVisionApplicationTests.class);
    @Test
    void log(){
        MDC.put("mock-message-id", "mock-message-id");
        logger.info("11111111");
        MDC.remove("mock-message-id");
    }
}
