package com.httpclient.core.interact;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public interface ImgProvider {
    List<File> getImgFiles(String imgUri);
}
