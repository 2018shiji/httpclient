package com.httpclient.imgprovpg.hikvision;

import org.junit.jupiter.api.Test;
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


}
