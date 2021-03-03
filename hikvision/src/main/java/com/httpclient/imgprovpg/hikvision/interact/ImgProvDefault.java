package com.httpclient.imgprovpg.hikvision.interact;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

@Component("ImgProvDefault")
public class ImgProvDefault implements ImgProvider {

    @Override
    public List<File> getImgFiles(String imgUri) {

        List<File> fileList = new ArrayList<>();
        File image = new File(imgUri);
        if(image.isDirectory()){
            String[] files = image.list();
            for(int i = 0; i < files.length; i++){
                File imageFile = new File(imgUri + "/" + files[i]);
                fileList.add(imageFile);
            }
        }else{ fileList.add(image); }

        return fileList;
    }

}
