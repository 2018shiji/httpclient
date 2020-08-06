package com.httpclient.ocridentify;

import com.alibaba.fastjson.JSON;
import com.httpclient.ocridentify.containerImp.AsyncOrderedContainer;
import com.httpclient.ocridentify.containerImp.BlockedContainer;
import com.httpclient.ocridentify.pojo.response.ContainerFrontTail;
import com.httpclient.ocridentify.pojo.response.ContainerInfo;
import com.httpclient.ocridentify.pojo.response.ContainerRoofInfo;
import com.httpclient.ocridentify.pojo.response.ContainerStatus;
import com.httpclient.ocridentify.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class Navigation {
    Logger logger = LoggerFactory.getLogger(Navigation.class);

    @ResponseBody
    @RequestMapping("getStatuses")
    public String doGetStatuses(String imageUri){
        IContainer container = SpringUtil.getBean(BlockedContainer.class);
        long begin = System.currentTimeMillis();
        String filePath = formatImageUri(imageUri);
        List<ContainerStatus> statuses = container.getContainerStatuses(filePath);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

        System.out.println(JSON.toJSONString(statuses));

        return JSON.toJSONString(statuses);
    }

    @ResponseBody
    @RequestMapping("getStatusesAsync")
    public String doGetStatusesAsync(String imageUri){
        IContainer container = SpringUtil.getBean(AsyncOrderedContainer.class);
        long begin = System.currentTimeMillis();
        String filePath = formatImageUri(imageUri);
        List<ContainerStatus> result = container.getContainerStatuses(filePath);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }


    @ResponseBody
    @RequestMapping("get")
    public String doGetFrontTails(String imageUri){
        IContainer container = SpringUtil.getBean(BlockedContainer.class);
        long begin = System.currentTimeMillis();
        String filePath = formatImageUri(imageUri);
        List<ContainerFrontTail> result = container.getContainerFrontTails(filePath);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("getFrontTails")
    public String doPostFrontTails(@RequestParam MultipartFile[] files){
        IContainer container = SpringUtil.getBean(BlockedContainer.class);
        System.out.println(files);
        String filePath = getImageFileUri(files);

        List<ContainerFrontTail> result = container.getContainerFrontTails(filePath);
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("getFrontTailsAsync")
    public String doGetFrontTailsAsync(String imageUri){
        IContainer container = SpringUtil.getBean(AsyncOrderedContainer.class);
        long begin = System.currentTimeMillis();
        String filePath = formatImageUri(imageUri);
        List<ContainerFrontTail> result = container.getContainerFrontTails(filePath);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }

    @ResponseBody
    @RequestMapping("getInfos")
    public String doGetContainerInfos(String imageUri){
        IContainer container = SpringUtil.getBean(BlockedContainer.class);
        long begin = System.currentTimeMillis();
        String filePath = formatImageUri(imageUri);
        List<ContainerInfo> infos = container.getContainerInfos(filePath);
        System.out.println(JSON.toJSONString(infos));
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

        return JSON.toJSONString(infos);
    }

    @ResponseBody
    @RequestMapping("getInfosAsync")
    public String doGetInfosAsync(String imageUri){
        IContainer container = SpringUtil.getBean(AsyncOrderedContainer.class);
        long begin = System.currentTimeMillis();
        String filePath = formatImageUri(imageUri);
        List<ContainerInfo> result = container.getContainerInfos(filePath);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");
        System.out.println(JSON.toJSONString(result));

        return JSON.toJSONString(result);
    }


    @ResponseBody
    @RequestMapping("getRoofInfos")
    public String doGetContainerRoofInfos(String imageUri){
        IContainer container = SpringUtil.getBean(BlockedContainer.class);
        long begin = System.currentTimeMillis();
        String filePath = formatImageUri(imageUri);
        List<ContainerRoofInfo> roofInfos = container.getContainerRoofInfos(filePath);
        System.out.println(JSON.toJSONString(roofInfos));
        logger.info(JSON.toJSONString(roofInfos));

        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin)/1000.0 + "s");

        return JSON.toJSONString(roofInfos);
    }

    @ResponseBody
    @RequestMapping("getRoofInfosAsync")
    public String doGetRoofInfosAsync(String imageUri){
        IContainer container = SpringUtil.getBean(AsyncOrderedContainer.class);
        long begin = System.currentTimeMillis();
        String filePath = formatImageUri(imageUri);
        List<ContainerRoofInfo> result = container.getContainerRoofInfos(filePath);
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

    private String getImageFileUri(MultipartFile[] files){
        String filePath = "C:\\Users\\Public\\Nwt\\cache\\recv\\毛骁\\destination\\";
        try {
            File directory = new File(filePath);
            if(!directory.exists()){
                directory.mkdir();
            }
            for(String name : directory.list()){
                File temp = new File(filePath + name);
                temp.delete();
            }
            for(int i = 0; i < files.length; i++) {
                String fileName = files[i].getOriginalFilename();
                File destination = new File(filePath + fileName);
                files[i].transferTo(destination);
            }
        } catch (IOException e) { e.printStackTrace(); }
        return filePath;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "OK...";
    }
}
