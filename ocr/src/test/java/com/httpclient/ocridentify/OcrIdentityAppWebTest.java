package com.httpclient.ocridentify;

import com.httpclient.ocridentify.containerImp.BlockedContainer;
import com.httpclient.ocridentify.pojo.response.ContainerFrontTail;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

@SpringBootTest
public class OcrIdentityAppWebTest {

    @MockBean
    BlockedContainer blockedContainer;

    void initMockImpl(){
        when(blockedContainer.getContainerFrontTails(any(String.class))).thenReturn(
                Arrays.asList(
                        new ContainerFrontTail("1", "1", true, "1"),
                        new ContainerFrontTail("2", "2", false, "2")
                ));
    }

    RestClientUtil client = new RestClientUtil();

    @Test
    void testFrontTailsBlock(){
        initMockImpl();
        client.getStatuses();
    }
}
