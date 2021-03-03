package com.httpclient.ocridentify;

import com.httpclient.ocridentify.containerImp.BlockedContainer;
import com.httpclient.ocridentify.containerImp.ContainerAbs;
import com.httpclient.ocridentify.pojo.response.ContainerFrontTail;
import com.httpclient.ocridentify.pojo.response.ContainerInfo;
import com.httpclient.ocridentify.pojo.response.ContainerStatus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;

@SpringBootTest
public class OcrIdentityAppWebTest {
    @Autowired
    Navigation navigation;
    //BlockedContainer -------->exception(out of array) ------>fileName did not initialized
//    @SpyBean
//    BlockedContainer blockedContainerSpy;

//    void initSpyImp(){
//        doReturn(Arrays.asList(
//                        new ContainerStatus("1", "1", true, "1"),
//                        new ContainerStatus("2", "2", false, "2"),
//                        new ContainerStatus("3", "3", false, "3")
//                )).when(blockedContainerSpy).getResponseObjects(eq("123"), any(), any());
//    }

//    @Test
//    void testBlockedContainerLocalSpy(){
//        initSpyImp();
//        String result = navigation.doGetStatuses("123");
//        System.out.println("------------->" + result);
//    }

    @MockBean
    BlockedContainer blockedContainerMock;

    void initMockImpl(){
        when(blockedContainerMock.getContainerStatuses(anyString())).thenReturn(
                Arrays.asList(
                        new ContainerFrontTail("frontTailStatus1", "message1", true, "fileName1"),
                        new ContainerFrontTail("frontTailStatus2", "message2", true, "fileName2")
                ));
    }

    @Test
    void testBlockedContainer(){
        initMockImpl();
        List containerStatus = blockedContainerMock.getContainerStatuses("imageUri");
        System.out.println(containerStatus.toString());
    }

    @Test
    void testBlockedContainerLocal(){
        initMockImpl();
        String string = navigation.doGetStatuses("imageUri");
        System.out.println(string);
    }

    @Test
    void testBlockedContainerMDC(){
        initMockImpl();
        MDC.put("mock-message-id", "test-blocked-frontTail");
        String string = navigation.doGetStatuses("imageUri");
        System.out.println(string);
    }

}
