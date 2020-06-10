package com.example.client.httpclient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Navigation {

    @RequestMapping
    public String getContainerFrontTail(){
        return "";
    }
}
