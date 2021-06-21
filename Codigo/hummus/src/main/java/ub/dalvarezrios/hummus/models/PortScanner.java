package ub.dalvarezrios.hummus.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;
import ub.dalvarezrios.hummus.models.service.IVmService;
import ub.dalvarezrios.hummus.models.service.VmService;

import java.net.Socket;
import java.net.SocketException;
import java.util.List;

@Service
public class PortScanner {

    @Autowired
    private IVmService vmService;

    public PortScanner(){

    }

    private boolean isPortInUse(String host, int port) {

        // Assume no connection is possible.
        boolean result = false;

        try {
            (new Socket(host, port)).close();
            result = true;
        }
        catch(Exception e) {
            // Could not connect.
        }

        return result;
    }

    private boolean isPortInDB(String port){

        List<VirtualMachine> vms = vmService.findAll();

        if(!vms.isEmpty()){
            for(VirtualMachine vm : vms){
                if(vm.getPort().equals(port)){
                    return true;
                }
            }
        }
        return false;
    }

    public String getAvailablePort(){
        String port = "";
        for(int i = 1024; i < 65535; i++){
            String port_str = Integer.toString(i);
            if(!isPortInDB(port_str) && !isPortInUse("localhost", i)){
                port = port_str;
                break;
            }
        }
        return port;
    }


}
