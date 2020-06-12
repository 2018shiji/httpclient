package com.example.client.httpclient.pojo.requestParam;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InfoPojo {
    @JSONField(name = "image")
    private String imageBase64Str;
    @JSONField(name = "model_type")
    private String modelType;
}