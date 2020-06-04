package com.example.client.httpclient.pojo.responseParam;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ContainerInfo {
    @JSONField(name = "container_num")
    private String containerNum;
    @JSONField(name = "iso_num")
    private String isoNum;
    @JSONField(name = "message")
    private String message;
    @JSONField(name = "success")
    private boolean success;
}
