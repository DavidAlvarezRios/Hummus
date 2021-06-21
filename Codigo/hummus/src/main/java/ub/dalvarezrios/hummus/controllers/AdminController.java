package ub.dalvarezrios.hummus.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ub.dalvarezrios.hummus.models.PortScanner;
import ub.dalvarezrios.hummus.models.VBoxManager;
import ub.dalvarezrios.hummus.models.entity.Exercise;
import ub.dalvarezrios.hummus.models.entity.Role;
import ub.dalvarezrios.hummus.models.entity.User;
import ub.dalvarezrios.hummus.models.entity.VirtualMachine;
import ub.dalvarezrios.hummus.models.service.IExerciseService;
import ub.dalvarezrios.hummus.models.service.IRoleService;
import ub.dalvarezrios.hummus.models.service.IUserService;
import ub.dalvarezrios.hummus.models.service.IVmService;
import ub.dalvarezrios.hummus.validation.EmailValidator;
import ub.dalvarezrios.hummus.validation.UsernameValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IExerciseService exerciseService;
    @Autowired
    private IVmService vmService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private PortScanner ps;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsernameValidator usernameValidator;
    @Autowired
    private EmailValidator emailValidator;

    protected final Log _logger = LogFactory.getLog(this.getClass());
    private VBoxManager vBoxManager = new VBoxManager();

    @GetMapping("")
    public String admin_page(Model model){
        model.addAttribute("titulo", "Página Administrador");
        return "admin/admin";
    }

    @GetMapping("/create_exercise")
    public String createExercise(Model model){
        _logger.info("Hola");
        model.addAttribute("titulo", "Crear Ejercicio");
        Exercise exercise = new Exercise();
        model.addAttribute("exercise", exercise);
        return "admin/create_exercise";
    }

    @PostMapping("/create_exercise")
    public String saveExercise(@Valid Exercise exercise, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("titulo", "Crear Ejercicio");
            return "admin/create_exercise";
        }
        exerciseService.save(exercise);

        return "redirect:/admin";
    }

    @GetMapping("/create_vm")
    public String createVm(Model model){
        model.addAttribute("titulo", "Crear VM");
        model.addAttribute("vm", new VirtualMachine());

        return "admin/create_vm";
    }

    @PostMapping("/create_vm")
    public String saveVm(@Valid VirtualMachine vm, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("titulo", "Crear VM");
            return "admin/create_vm";
        }

        //Check if virtual machine exists
        if(!vBoxManager.machineExists(vm.getVm_name())){
            _logger.info("Machine doesn't exists");
            return "admin/create_vm";
        }

        if(vmService.findByMachineName(vm.getVm_name()) != null){
            _logger.info("Machine already exists in database");
            return "admin/create_vm";
        }

        //Search for available port
        String port = ps.getAvailablePort();

        if(port.equals("")){
            _logger.info("There is no port available");
            return "redirect:/about";
        }

        vBoxManager.setPortVRDE(vm.getVm_name(), port);

        vm.setPort(port);
        vmService.save(vm);

        return "redirect:/admin";
    }
    @GetMapping("/create_admin")
    public String createAdmin(Model model){
        User admin = new User();
        model.addAttribute("titulo", "Sign up");
        model.addAttribute("admin", admin);

        return "admin/create_admin";
    }

    @PostMapping("/create_admin")
    public String saveAdmin(@Valid User admin, BindingResult result, Model model){

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Sign up");
            return "admin/create_admin";
        }

        admin.setEnabled(true);
        admin.setPassword(encoder.encode(admin.getPassword()));
        Role role = new Role(admin, "ROLE_ADMIN");
        userService.save(admin);
        roleService.save(role);

        return "redirect:/admin";
    }

}
