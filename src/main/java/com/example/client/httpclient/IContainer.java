package com.example.client.httpclient;

import com.example.client.httpclient.pojo.response.ContainerFrontTail;
import com.example.client.httpclient.pojo.response.ContainerInfo;
import com.example.client.httpclient.pojo.response.ContainerRoofInfo;
import com.example.client.httpclient.pojo.response.ContainerStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IContainer {

    String CONTAINER_FRONT_TAIL = "https://zsg-serving.cloudwalk.cn:8081/container_front_tail";
    String CONTAINER_STATUS = "https://zsg-serving.cloudwalk.cn:8082/container_status";
    String CONTAINER_INFO = "https://zsg-serving.cloudwalk.cn:8082/container_info";

    List<ContainerFrontTail> getContainerFrontTails(String imageUri);

    List<ContainerStatus> getContainerStatuses(String imageUri);

    List<ContainerInfo> getContainerInfos(String imageUri);

    List<ContainerRoofInfo> getContainerRoofInfos(String imageUri);

}
