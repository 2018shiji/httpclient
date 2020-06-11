package com.example.client.httpclient.pojo.responseParam;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ContainerStatus {
    //open 或者 close
    @JSONField(name = "status")
    String status;
    @JSONField(name = "message")
    String message;
    @JSONField(name = "success")
    boolean success;
    @JSONField(name = "file_name")
    String fileName;
}
