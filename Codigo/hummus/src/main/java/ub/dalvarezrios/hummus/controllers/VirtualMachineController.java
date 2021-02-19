package ub.dalvarezrios.hummus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ub.dalvarezrios.hummus.models.LaunchMode;
import ub.dalvarezrios.hummus.models.VBoxManager;

@Controller
public class VirtualMachineController {

    //VBoxManager vBoxManager = new VBoxManager();


    @GetMapping("/create_vm")
    public String create_vm_page(Model model){

        return "vm/create_vm_page";
    }

    @GetMapping("/mv")
    public String openVM(@RequestParam String machineName, Model model){
        //vBoxManager.launchMachine(machineName, LaunchMode.headless);
        model.addAttribute("titulo", machineName);
        return "vm/display";
    }


}
