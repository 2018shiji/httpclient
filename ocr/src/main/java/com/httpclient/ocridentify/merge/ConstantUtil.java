package com.httpclient.ocridentify.merge;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ConstantUtil {
    public static Set<String> imageFormats(){
        Set<String> imageFormats = new HashSet<>();
        imageFormats.add("png");
        imageFormats.add("jpg");
        imageFormats.add("jpeg");
        imageFormats.add("img");
        Set<String> unmodifiedFormats = Collections.unmodifiableSet(imageFormats);

        return unmodifiedFormats;
    }
}
