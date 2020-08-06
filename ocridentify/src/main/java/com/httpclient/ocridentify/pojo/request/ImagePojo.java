package com.httpclient.ocridentify.pojo.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImagePojo {
    @JSONField(name = "image")
    private String imageBase64Str;

}
