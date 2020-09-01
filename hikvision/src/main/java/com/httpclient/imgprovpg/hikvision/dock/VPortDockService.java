package com.httpclient.imgprovpg.hikvision.dock;

import com.httpclient.imgprovpg.hikvision.pojo.VPortDock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VPortDockService {
    @Autowired
    private VPortDockRepository vPortDockRepository;

    //dock_id
    public VPortDock getVPortDockById(long dockId){
        VPortDock obj = null;
        if(vPortDockRepository.findById(dockId).isPresent()){
            obj = vPortDockRepository.findById(dockId).get();
            System.out.println("()()()(()()(()()()())()))");
        }else{
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }
        return obj;
    }
}
