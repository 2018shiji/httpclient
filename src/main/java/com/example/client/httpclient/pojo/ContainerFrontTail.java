package com.example.client.httpclient.pojo;

import lombok.Data;

@Data
public class ContainerFrontTail {
    //tail 或者 front
    String status;
    String message;
    boolean success;
}
