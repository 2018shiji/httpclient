package com.example.client.httpclient.pojo.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ContainerFrontTail {
    //tail 或者 front
    @JSONField(name = "status")
    String status;
    @JSONField(name = "message")
    String message;
    @JSONField(name = "success")
    boolean success;

    @JSONField(name = "file_name")
    String fileName;

}
