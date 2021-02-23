package ub.dalvarezrios.hummus.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ub.dalvarezrios.hummus.models.LaunchMode;
import ub.dalvarezrios.hummus.models.PortScanner;
import ub.dalvarezrios.hummus.models.VBoxManager;
import ub.dalvarezrios.hummus.models.entity.User;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;
import ub.dalvarezrios.hummus.models.service.IUserService;
import ub.dalvarezrios.hummus.models.service.IVmService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
public class VirtualMachineController {

    //VBoxManager vBoxManager = new VBoxManager();
    @Autowired
    IVmService vmService;
    @Autowired
    IUserService userService;
    @Autowired
    PortScanner ps;

    protected final Log _logger = LogFactory.getLog(this.getClass());

    @GetMapping("/create_vm")
    public String create_vm_page(Model model, Principal principal){

        // If user is not authenticated redirect to login page.
        if(principal == null){
            return "redirect:/login";
        }

        // Get the authenticated user
        String username = principal.getName();
        User user = userService.findByUsername(username);

        // Search for a port
        String port = "";
        for(int i = 1024; i < 65665; i++){
            String port_str = Integer.toString(i);
            if(!ps.isPortInDB(port_str)){
                if(!ps.isPortInUse("localhost", i)){
                    port = port_str;
                    break;
                }
            }
        }
        if(port.equals("")){
            return "redirect:/about";
        }

        // Create the vm object and save it in the database
        VirtualMachine vm = new VirtualMachine("test1", port, user);
        vmService.save(vm);

        return "vm/create_vm_page";
    }

    @GetMapping("/mv")
    public String openVM(@RequestParam String machineName, Model model, HttpServletRequest request){
        //vBoxManager.launchMachine(machineName, LaunchMode.headless);
        model.addAttribute("titulo", machineName);
        String port = vmService.findByMachineName(machineName).getPort();
        request.setAttribute("port", port);

        request.setAttribute("machineName", machineName);
        return "forward:/display"; //This could be done with a FlashMap and a redirect too
    }

    @GetMapping("/my_vms")
    public String myVms(Model model, Principal principal){

        if(principal == null){
            return "redirect:/login";
        }

        String username = principal.getName();

        User actualUser = userService.findByUsername(username);

        List<VirtualMachine> userMachines = vmService.findAllVmByUserID(actualUser.getId());

        model.addAttribute("username", username);
        model.addAttribute("machines", userMachines);

        return "vm/my_vms";
    }


}
