package com.httpclient.imgprovpg.hikvision;

import com.httpclient.imgprovpg.hikvision.pojo.VPortDock;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class DockRestClientUtil {
    public void getDockByDockId() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/port/dock/{id}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
        ResponseEntity<VPortDock> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, VPortDock.class, 21);
        VPortDock vPortDock = responseEntity.getBody();
        if(vPortDock == null){
            System.out.println("**********空结果集**********");
        }else {
            System.out.println("plateId:" + vPortDock.getDockId() + ", plateNo:" + vPortDock.getPlateNo()
                    + ", ctime:" + vPortDock.getCtime() + ", photoId:" + vPortDock.getPhotos().get(0).getPhotoId());
        }
    }
}
