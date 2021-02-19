package ub.dalvarezrios.hummus.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.virtualbox_6_1.IMachine;
import org.virtualbox_6_1.MachineState;
import ub.dalvarezrios.hummus.models.LaunchMode;
import ub.dalvarezrios.hummus.models.VBoxManager;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    VBoxManager vBoxManager = new VBoxManager();
    protected final Log _logger = LogFactory.getLog(this.getClass());

    @GetMapping({"", "/", "/index"})
    public String index(Model model){
        List<IMachine> maquinas = vBoxManager.getMachines(MachineState.PoweredOff);
        model.addAttribute("titulo", "Test");
        model.addAttribute("maquinas", maquinas);

        return "index";
    }

    @GetMapping("/about")
    public String about(Model model, Authentication authentication){

        model.addAttribute("titulo", "About");
        return "about";
    }

}
