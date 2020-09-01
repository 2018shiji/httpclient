package com.httpclient.imgprovpg.hikvision.plate;

import com.httpclient.imgprovpg.hikvision.pojo.VPortPlate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface VPortPlateRepository extends CrudRepository<VPortPlate, Long> {

}
