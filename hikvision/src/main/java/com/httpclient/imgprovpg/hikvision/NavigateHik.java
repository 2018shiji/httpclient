package com.httpclient.imgprovpg.hikvision;

import com.httpclient.imgprovpg.hikvision.dock.VPortDockService;
import com.httpclient.imgprovpg.hikvision.plate.VPortPlateService;
import com.httpclient.imgprovpg.hikvision.pojo.VPortDock;
import com.httpclient.imgprovpg.hikvision.pojo.VPortPlate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("port")
public class NavigateHik {
    Logger logger = LoggerFactory.getLogger(NavigateHik.class);

    @Autowired
    private VPortDockService vPortDockService;

    @Autowired
    private VPortPlateService vPortPlateService;

    @GetMapping("dock/{id}")
    public ResponseEntity<VPortDock> getArticleByIdDock(@PathVariable("id") Integer id) {
        VPortDock plate = vPortDockService.getVPortDockById(id);
        logger.info(plate.toString());
        return new ResponseEntity<VPortDock>(plate, HttpStatus.OK);
    }

    @GetMapping("plate/{id}")
    public ResponseEntity<VPortPlate> getArticleByIdPlate(@PathVariable("id") Integer id) {
        VPortPlate plate = vPortPlateService.getVPortPlateById(id);
        logger.info(plate.toString());
        return new ResponseEntity<VPortPlate>(plate, HttpStatus.OK);
    }
}
