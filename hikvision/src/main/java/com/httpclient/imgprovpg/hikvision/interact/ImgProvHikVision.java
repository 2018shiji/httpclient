package com.httpclient.imgprovpg.hikvision.interact;

import com.httpclient.core.interact.ImgProvider;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component("ImgProvHikVision")
public class ImgProvHikVision implements ImgProvider {
    @Override
    public List<File> getImgFiles(String imgUri) {
        List<File> files = new ArrayList<>();
        return files;
    }
}
