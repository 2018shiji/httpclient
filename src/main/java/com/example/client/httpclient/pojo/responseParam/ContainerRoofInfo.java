package com.example.client.httpclient.pojo.responseParam;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ContainerRoofInfo {
    @JSONField(name = "message")
    private String message;
    @JSONField(name = "roof_num")
    private String roofNum;
    @JSONField(name = "success")
    private boolean success;
}
