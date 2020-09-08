package com.httpclient.ocridentify.containerImp;

import com.alibaba.fastjson.JSON;
import com.httpclient.core.interact.ImgProvider;
import com.httpclient.imgprovpg.hikvision.interact.ImgProvDefault;
import com.httpclient.imgprovpg.hikvision.interact.ImgProvHikVision;
import com.httpclient.ocridentify.IContainer;
import com.httpclient.ocridentify.pojo.request.ImagePojo;
import com.httpclient.ocridentify.pojo.request.InfoPojo;
import com.google.common.io.Files;
import com.httpclient.core.util.ConstantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.*;

@Component
public abstract class ContainerAbs implements IContainer {
    ImgProvider imgProvider = new ImgProvDefault();

    protected List<String> imageBase64Strings = new ArrayList<>();
    protected List<String> containerInfoStrings = new ArrayList<String>();
    protected List<String> containerInfoRoofStrings = new ArrayList<String>();
    protected List<String> fileNames = new ArrayList();

    public Logger logger = LoggerFactory.getLogger(ContainerAbs.class);

    protected void initContainer(String imageUri) {
        List<File> imgFiles = new ArrayList<>();
        imgFiles = imgProvider.getImgFiles(imageUri);

        clearCollections();
        imgFiles.forEach(item -> { initBlockingQueues(item); });

    }

    private void initBlockingQueues(File imageFile){
        Set<String> unmodifiedImgFormats = ConstantUtil.imageFormats();

        String base64Str = "";
        try {
            File imageStr = imageFile;
            fileNames.add(imageFile.getName());
            int lastIndex = imageFile.getName().lastIndexOf(".");
            if (unmodifiedImgFormats.contains(imageFile.getName().substring(lastIndex+1))) {
                FileInputStream fileInputStream = new FileInputStream(imageFile);
                byte[] data = new byte[fileInputStream.available()];
                fileInputStream.read(data);
                /** 普通的base64加解密方法 */
                base64Str = Base64.getEncoder().encodeToString(data);
                fileInputStream.close();
            }else if("txt".equals(imageFile.getName().substring(lastIndex+1))){
                base64Str = Files.asCharSource(imageStr, Charset.forName("UTF-8")).read();
            }else {
                System.out.println("*********格式未可知*********");
            }
        } catch (Exception e) {e.printStackTrace();}

        ImagePojo imagePojo = new ImagePojo(base64Str);
        imageBase64Strings.add(JSON.toJSONString(imagePojo));
        InfoPojo infoRoofPojo = new InfoPojo(base64Str, "roof_model");
        containerInfoRoofStrings.add(JSON.toJSONString(infoRoofPojo));
        InfoPojo infoContainerPojo = new InfoPojo(base64Str, "container_model");
        containerInfoStrings.add(JSON.toJSONString(infoContainerPojo));
//                imageStrs.add("{\n" + "\"image\":" + "\"" + base64Str + "\"" + "\n}");
    }

    private void clearCollections(){
        this.fileNames.clear();
        this.imageBase64Strings.clear();
        this.containerInfoStrings.clear();
        this.containerInfoRoofStrings.clear();

    }

}
