package com.httpclient.ocridentify;

import com.httpclient.ocridentify.containerImp.BlockedContainer;
import com.httpclient.ocridentify.pojo.response.ContainerFrontTail;
import com.httpclient.ocridentify.pojo.response.ContainerStatus;
import org.junit.jupiter.api.Test;
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

//    @MockBean
//    BlockedContainer blockedContainerMock;

    @SpyBean
    BlockedContainer blockedContainerSpy;

//    void initMockImpl(){
//        when(blockedContainerMock.getContainerStatuses("123")).thenReturn(
//                Arrays.asList(
//                        new ContainerStatus("1", "1", true, "1"),
//                        new ContainerStatus("2", "2", false, "2")
//                ));
//    }

    void initSpyImp(){
        doReturn(Arrays.asList(
                        new ContainerStatus("1", "1", true, "1"),
                        new ContainerStatus("2", "2", false, "2"),
                        new ContainerStatus("3", "3", false, "3")
                )).when(blockedContainerSpy).getResponseObjects(eq("123"), any(), any());
    }


//    @Test
//    void testBlockedContainer(){
//        initMockImpl();
//        List containerStatus = blockedContainerMock.getContainerStatuses("123");
//        System.out.println(containerStatus.toString());
//    }

    @Autowired
    Navigation navigation;

//    @Test
//    void testBlockedContainerLocal(){
//        initMockImpl();
//        String string = navigation.doGetStatuses("123");
//        System.out.println(string);
//    }

    @Test
    void testBlockedContainerLocalSpy(){
        initSpyImp();
        String result = navigation.doGetStatuses("123");
        System.out.println("------------->" + result);
    }

}
