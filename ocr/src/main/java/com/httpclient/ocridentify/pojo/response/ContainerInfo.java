package com.httpclient.ocridentify.pojo.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContainerInfo {
    @JSONField(name = "container_num")
    private String containerNum;
    @JSONField(name = "iso_num")
    private String isoNum;
    @JSONField(name = "message")
    private String message;
    @JSONField(name = "success")
    private boolean success;

    @JSONField(name = "file_name")
    String fileName;

}
