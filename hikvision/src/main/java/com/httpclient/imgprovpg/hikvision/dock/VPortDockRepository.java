package com.httpclient.imgprovpg.hikvision.dock;

import com.httpclient.imgprovpg.hikvision.pojo.VPortDock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface VPortDockRepository extends CrudRepository<VPortDock, Long> {

}
