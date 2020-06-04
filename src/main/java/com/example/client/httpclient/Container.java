package com.example.client.httpclient;

import com.alibaba.fastjson.JSON;
import com.example.client.httpclient.pojo.requestParam.ImagePojo;
import com.example.client.httpclient.pojo.requestParam.InfoPojo;
import com.example.client.httpclient.pojo.responseParam.ContainerFrontTail;
import com.example.client.httpclient.pojo.responseParam.ContainerInfo;
import com.example.client.httpclient.pojo.responseParam.ContainerRoofInfo;
import com.example.client.httpclient.pojo.responseParam.ContainerStatus;
import com.example.client.httpclient.util.HttpClientUtil;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class Container {
    @Autowired
    private HttpClientUtil httpClient;
    private BlockingQueue<String> imageStrs = new LinkedBlockingQueue<String>();
    private BlockingQueue<String> infoContainerStrs = new LinkedBlockingQueue<String>();
    private BlockingQueue<String> infoContainerRoofStrs = new LinkedBlockingQueue<String>();
    public static final String CONTAINER_FRONT_TAIL = "https://zsg-serving.cloudwalk.cn:8081/container_front_tail";
    public static final String CONTAINER_STATUS = "https://zsg-serving.cloudwalk.cn:8080/container_status";
    public static final String CONTAINER_INFO = "https://zsg-serving.cloudwalk.cn:8082/container_info";
    public static final String IMAGE_FILE_PATH = "C:\\Users\\Public\\Nwt\\cache\\recv\\毛骁\\识别图片base64String";
    public static final String IMAGE_FILE_PATH_TEST = "C:\\Users\\Public\\Nwt\\cache\\recv\\毛骁\\识别图片base64Test";

    @PostConstruct
    private void initContainer() throws IOException {
        File image = new File(IMAGE_FILE_PATH_TEST);
        if(image.isDirectory()){
            String[] files = image.list();
            for(int i = 0; i < files.length; i++){
                File imageFile = new File(IMAGE_FILE_PATH_TEST + "/" + files[i]);
                String base64Str = Files.asCharSource(imageFile, Charset.forName("UTF-8")).read();
                initBlockingQueues(base64Str);
            }
        }else{ initBlockingQueues(Files.asCharSource(image, Charset.forName("UTF-8")).read()); }

    }

    public List<ContainerFrontTail> getContainerFrontTails(){
        List<ContainerFrontTail> results = new ArrayList<>();
        try{
            while(true) {
                String imageStr = imageStrs.take();
//                ContainerFrontTail frontTailPost = httpClient.getContainerFrontTailPost("http://127.0.0.1:8080/doHttpClientPost", imageStr);
                ContainerFrontTail frontTailPost = httpClient.getContainerFrontTailPost(CONTAINER_FRONT_TAIL, imageStr);
                results.add(frontTailPost);
                if(imageStrs.isEmpty())break;
            }
        } catch (InterruptedException e){e.printStackTrace();}
        return results;
    }

    public List<ContainerStatus> getContainerStatus(){
        List<ContainerStatus> results = new ArrayList<>();
        try{
            while(true) {
                String imageStr = imageStrs.take();
//                ContainerStatus statusPost = httpClient.getContainerStatusPost("http://127.0.0.1:8080/doHttpClientPost", imageStr);
                ContainerStatus statusPost = httpClient.getContainerStatusPost(CONTAINER_STATUS, imageStr);
                results.add(statusPost);
                if (imageStrs.isEmpty()) break;
            }
        } catch (InterruptedException e){e.printStackTrace();}
        return results;
    }

    public List<ContainerInfo> getContainerInfo(){
        List<ContainerInfo> results = new ArrayList<>();
        try{
            while(true) {
                String infoStr = infoContainerStrs.take();
//                ContainerInfo infoPost = httpClient.getContainerStatusPost("http://127.0.0.1:8080/doHttpClientPost", infoStr);
                ContainerInfo infoPost = httpClient.getContainerInfoPost(CONTAINER_INFO, infoStr);
                results.add(infoPost);
                if (infoContainerStrs.isEmpty()) break;
            }
        } catch (InterruptedException e){e.printStackTrace();}
        return results;
    }

    public List<ContainerRoofInfo> getContainerRoofInfo(){
        List<ContainerRoofInfo> results = new ArrayList<>();
        try{
            while(true) {
                String roofInfoStr = infoContainerRoofStrs.take();
//                ContainerRoofInfo roofInfoPost = httpClient.getContainerRoofInfoPost("http://127.0.0.1:8080/doHttpClientPost", roofInfoStr);
                ContainerRoofInfo roofInfoPost = httpClient.getContainerRoofInfoPost(CONTAINER_INFO, roofInfoStr);
                results.add(roofInfoPost);
                if (infoContainerRoofStrs.isEmpty()) break;
            }
        } catch (InterruptedException e){e.printStackTrace();}
        return results;
    }

    private void initBlockingQueues(String base64Str){
        ImagePojo imagePojo = new ImagePojo(base64Str);
        imageStrs.add(JSON.toJSONString(imagePojo));
        InfoPojo infoRoofPojo = new InfoPojo(base64Str, "roof_model");
        infoContainerRoofStrs.add(JSON.toJSONString(infoRoofPojo));
        InfoPojo infoContainerPojo = new InfoPojo(base64Str, "container_model");
        infoContainerStrs.add(JSON.toJSONString(infoContainerPojo));
//                imageStrs.add("{\n" + "\"image\":" + "\"" + base64Str + "\"" + "\n}");
    }

}
