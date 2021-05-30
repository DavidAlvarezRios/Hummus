package ub.dalvarezrios.hummus.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ub.dalvarezrios.hummus.models.VBoxManager;
import ub.dalvarezrios.hummus.models.entity.DHCPServer;
import ub.dalvarezrios.hummus.models.entity.Exercise;
import ub.dalvarezrios.hummus.models.entity.User;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;
import ub.dalvarezrios.hummus.models.service.IExerciseService;
import ub.dalvarezrios.hummus.models.service.IUserService;
import ub.dalvarezrios.hummus.models.service.IVmService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    IExerciseService exerciseService;
    @Autowired
    IVmService vmService;
    @Autowired
    IUserService userService;

    VBoxManager vBoxManager = new VBoxManager();

    protected final Log _logger = LogFactory.getLog(this.getClass());

    @GetMapping("")
    public String exercises(Model model){
        List<Exercise> exercises = exerciseService.findAll();
        model.addAttribute("titulo", "Ejercicios");
        model.addAttribute("exercices", exercises);

        return "exercises/exercises";
    }

    @GetMapping("/exercise/{id}")
    public String getExercise(@PathVariable(value="id") String id, Model model, Principal principal){

        if(principal == null){
            return "redirect:/about";
        }

        String userName = principal.getName();
        User user = userService.findByUsername(userName);

        Long exerciseId;
        try{
            exerciseId = Long.parseLong(id);
        }catch(NumberFormatException ex){
           return "redirect:/exercises";
        }
        _logger.info("El id del ejercicio es: ".concat(exerciseId.toString()));

        Exercise actualExercise = exerciseService.findOne(exerciseId);
        if(actualExercise == null){
            return "redirect:/exercises";
        }
        String exerciseName = actualExercise.getName();
        int numVms = actualExercise.getNumVms();
        List<VirtualMachine> exerciseVms = new ArrayList<>();
        List<VirtualMachine> vms = vmService.findVMsByExercise(actualExercise);

        for(int i = 0; i < numVms; i++){
            int type = i + 1;
            for(VirtualMachine vm: vms){
                if(vm.getType() == type){
                    vm.setUsed(true);
                    vm.setUser(user);
                    vmService.save(vm);
                    exerciseVms.add(vm);
                }
            }
        }
        // If the necessary Vms to carry out the exercise is greater than one we have to create a dhcp server for the vms.
        if(numVms > 1){
            List<String> vmNames = new ArrayList<>();
            for(VirtualMachine vm: vms){
                vmNames.add(vm.getVm_name());
            }
            _logger.info(vmNames);
            DHCPServer dhcpConf = new DHCPServer();
            String dhcp_name = actualExercise.getName().concat(".").concat(principal.getName());
            dhcpConf.autoconfigure(dhcp_name);
            _logger.info(dhcpConf.getNetname());
            vBoxManager.assignInternalNetworkFromMachineNames(dhcp_name ,vmNames, dhcpConf);
        }

        model.addAttribute("titulo", exerciseName);
        model.addAttribute("id", exerciseId);
        model.addAttribute("machines", exerciseVms);

        return "exercises/exercise";
    }

}
