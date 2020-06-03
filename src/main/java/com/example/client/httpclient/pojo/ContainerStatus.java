package com.example.client.httpclient.pojo;

import lombok.Data;

@Data
public class ContainerStatus {
    //open 或者 close
    String status;
    String message;
    boolean isSuccess;
}
