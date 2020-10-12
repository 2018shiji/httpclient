package com.httpclient.imgprovpg.hikvision;

import com.httpclient.imgprovpg.hikvision.pojo.VPortPlate;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class PlateRestClientUtil {

    public void getPlateByPlateId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/port/plate/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<VPortPlate> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, VPortPlate.class, 21);
        VPortPlate vPortPlate = responseEntity.getBody();
        if(vPortPlate == null){
            System.out.println("**********空结果集**********");
        }else {
            System.out.println(vPortPlate);
        }
    }

}
