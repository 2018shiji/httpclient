package com.httpclient.core.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class ConstantUtil {
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
