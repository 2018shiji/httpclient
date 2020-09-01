package com.httpclient.imgprovpg.hikvision.plate;

import com.httpclient.imgprovpg.hikvision.pojo.VPortPlate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VPortPlateService {
    @Autowired
    private VPortPlateRepository vPortPlateRepository;

    //plate_id
    public VPortPlate getVPortPlateById(long plateId){
        VPortPlate obj = null;
        if(vPortPlateRepository.findById(plateId).isPresent()){
            obj = vPortPlateRepository.findById(plateId).get();
            System.out.println("*************************\n********************\n*******************");
        }else {
            System.out.println("***************************************************");
        }

        return obj;
    }
}
