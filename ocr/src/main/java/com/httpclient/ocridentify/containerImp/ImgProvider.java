package com.httpclient.ocridentify.containerImp;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public interface ImgProvider {
    List<File> getImgFiles(String imgUri);
}
