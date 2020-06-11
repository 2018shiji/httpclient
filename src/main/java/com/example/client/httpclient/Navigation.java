package com.example.client.httpclient;

import com.alibaba.fastjson.JSON;
import com.example.client.httpclient.pojo.responseParam.ContainerFrontTail;
import com.example.client.httpclient.pojo.responseParam.ContainerInfo;
import com.example.client.httpclient.pojo.responseParam.ContainerRoofInfo;
import com.example.client.httpclient.pojo.responseParam.ContainerStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class Navigation {
    @Autowired
    Container container;
    Logger logger = LoggerFactory.getLogger(Navigation.class);

    @ResponseBody
    @RequestMapping("getStatuses")
    public String doGetStatuses(String imageUri){
        System.out.println(imageUri);
        String imageUriT = imageUri.replaceFirst(":", ":\\\\\\\\")
                .replaceAll("\\.", "\\\\\\\\");
        System.out.println(imageUriT);
        File file = new File(imageUriT);
        System.out.println(file.isDirectory() + file.getName() + file.list());
        long begin = System.currentTimeMillis();
        List<ContainerStatus> statuses = container.getContainerStatus();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

        System.out.println(JSON.toJSONString(statuses));

        return JSON.toJSONString(statuses);
    }

    @ResponseBody
    @RequestMapping("getStatusesAsync")
    public String doGetStatusesAsync(){
        long begin = System.currentTimeMillis();
        List<ContainerStatus> result = container.getContainerStatusAsyncMT();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }


    @ResponseBody
    @RequestMapping("getFrontTails")
    public String doGetFrontTails(){
        long begin = System.currentTimeMillis();
        List<ContainerFrontTail> result = container.getContainerFrontTails();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("getFrontTailsAsync")
    public String doGetFrontTailsAsync(){
        long begin = System.currentTimeMillis();
        List<ContainerFrontTail> result = container.getFrontTailFutureMT();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("getInfos")
    public String doGetContainerInfos(){
        long begin = System.currentTimeMillis();
        List<ContainerInfo> infos = container.getContainerInfos();
        System.out.println(JSON.toJSONString(infos));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

        return JSON.toJSONString(infos);
    }

    @ResponseBody
    @RequestMapping("getInfosAsync")
    public String doGetInfosAsync(){
        long begin = System.currentTimeMillis();
        List<ContainerInfo> result = container.getContainerInfoAsyncMT();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }


    @ResponseBody
    @RequestMapping("getRoofInfos")
    public String doGetContainerRoofInfos(){
        long begin = System.currentTimeMillis();
        List<ContainerRoofInfo> roofInfos = container.getContainerRoofInfos();
        System.out.println(JSON.toJSONString(roofInfos));
        logger.info(JSON.toJSONString(roofInfos));

        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

        return JSON.toJSONString(roofInfos);
    }

    @ResponseBody
    @RequestMapping("getRoofInfosAsync")
    public String doGetRoofInfosAsync(){
        long begin = System.currentTimeMillis();
        List<ContainerRoofInfo> result = container.getContainerRoofInfoAsyncMT();
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));
        logger.info(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }
}
