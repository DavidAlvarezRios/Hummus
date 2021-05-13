package ub.dalvarezrios.hummus.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ub.dalvarezrios.hummus.models.VBoxManager;
import ub.dalvarezrios.hummus.models.entity.DHCPServer;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;
import ub.dalvarezrios.hummus.models.service.IDHCPServerService;
import ub.dalvarezrios.hummus.models.service.IVmService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dhcp")
public class DHCPController {

    protected final Log _logger = LogFactory.getLog(this.getClass());
    VBoxManager vBoxManager = new VBoxManager();

    @Autowired
    IDHCPServerService idhcpServerService;

    @Autowired
    IVmService vmService;

    @GetMapping("/hola")
    public String hola(Model model){
        return "dhcp/dhcp_test";
    }

    @PostMapping("/test")
    public String test(Model model){

        DHCPServer dhcp = new DHCPServer();

        dhcp.setEnabled(true);
        dhcp.setIp("10.10.10.1");
        dhcp.setNetMask("255.255.255.0");
        dhcp.setLower_ip("10.10.10.2");
        dhcp.setUpper_ip("10.10.10.12");
        dhcp.setNetname("testlab3");
        dhcp.setUsed(false);


        List<VirtualMachine> vms = vmService.findAll();
        List<String> vm_names = new ArrayList<>();

        for(VirtualMachine vm : vms){
            vm_names.add(vm.getVm_name());
        }

        boolean fail = vBoxManager.assignInternalNetworkFromMachineNames(dhcp.getNetname(), vm_names, dhcp);
        if(!fail){
            //idhcpServerService.save(dhcp);
        }

        return "redirect:/about";
    }

}
