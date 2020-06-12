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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
        long begin = System.currentTimeMillis();
        List<ContainerStatus> statuses = container.getContainerStatus(formatImageUri(imageUri));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

        System.out.println(JSON.toJSONString(statuses));

        return JSON.toJSONString(statuses);
    }


    @ResponseBody
    @RequestMapping("getStatusesAsync")
    public String doGetStatusesAsync(String imageUri){
        long begin = System.currentTimeMillis();
        List<ContainerStatus> result = container.getContainerStatusAsyncMT(formatImageUri(imageUri));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }


    @ResponseBody
    @RequestMapping("get")
    public String doGetFrontTails(String imageUri){
        long begin = System.currentTimeMillis();
        List<ContainerFrontTail> result = container.getContainerFrontTails(formatImageUri(imageUri));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("getFrontTails")
    public String doPostFrontTails(@RequestParam MultipartFile[] files){
        System.out.println(files);
        //todo 如果存在则清空
        String filePath = "C:\\Users\\Public\\Nwt\\cache\\recv\\毛骁\\destination\\";
        try {
            for(int i = 0; i < files.length; i++) {
                String fileName = files[i].getOriginalFilename();
                File destination = new File(filePath + fileName);
                files[i].transferTo(destination);
            }
        } catch (IOException e) { e.printStackTrace(); }

        List<ContainerFrontTail> result = container.getContainerFrontTails(filePath);
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("getFrontTailsAsync")
    public String doGetFrontTailsAsync(String imageUri){
        long begin = System.currentTimeMillis();
        List<ContainerFrontTail> result = container.getFrontTailFutureMT(formatImageUri(imageUri));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("getInfos")
    public String doGetContainerInfos(String imageUri){
        long begin = System.currentTimeMillis();
        List<ContainerInfo> infos = container.getContainerInfos(formatImageUri(imageUri));
        System.out.println(JSON.toJSONString(infos));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

        return JSON.toJSONString(infos);
    }

    @ResponseBody
    @RequestMapping("getInfosAsync")
    public String doGetInfosAsync(String imageUri){
        long begin = System.currentTimeMillis();
        List<ContainerInfo> result = container.getContainerInfoAsyncMT(formatImageUri(imageUri));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }


    @ResponseBody
    @RequestMapping("getRoofInfos")
    public String doGetContainerRoofInfos(String imageUri){
        long begin = System.currentTimeMillis();
        List<ContainerRoofInfo> roofInfos = container.getContainerRoofInfos(formatImageUri(imageUri));
        System.out.println(JSON.toJSONString(roofInfos));
        logger.info(JSON.toJSONString(roofInfos));

        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

        return JSON.toJSONString(roofInfos);
    }

    @ResponseBody
    @RequestMapping("getRoofInfosAsync")
    public String doGetRoofInfosAsync(String imageUri){
        long begin = System.currentTimeMillis();
        List<ContainerRoofInfo> result = container.getContainerRoofInfoAsyncMT(formatImageUri(imageUri));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));
        logger.info(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }

    private String formatImageUri(String imageUri){
        System.out.println(imageUri);
        String imageUriT = imageUri.replaceFirst(":", ":\\\\")
                .replaceAll("\\\\", "\\\\\\\\");
        System.out.println(imageUriT);
        File file = new File(imageUriT);
        System.out.println(file.isDirectory() + file.getName() + file.list());
        return imageUriT;
    }
}
