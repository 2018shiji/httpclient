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
