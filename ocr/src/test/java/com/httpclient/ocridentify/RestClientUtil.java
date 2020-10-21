package com.httpclient.ocridentify;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class RestClientUtil {

    public void getStatuses(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://127.0.0.1:8082/getFrontTailsTimed?imageUri=123";
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        String body = responseEntity.getBody();
        System.out.println(body);
    }
}
