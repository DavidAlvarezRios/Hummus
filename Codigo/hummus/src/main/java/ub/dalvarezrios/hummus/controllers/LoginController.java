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
import org.springframework.web.bind.annotation.RequestParam;
import ub.dalvarezrios.hummus.models.entity.Exercise;
import ub.dalvarezrios.hummus.models.entity.Role;
import ub.dalvarezrios.hummus.models.entity.User;
import ub.dalvarezrios.hummus.models.service.IExerciseService;
import ub.dalvarezrios.hummus.models.service.IRoleService;
import ub.dalvarezrios.hummus.models.service.IUserService;
import ub.dalvarezrios.hummus.validation.EmailValidator;
import ub.dalvarezrios.hummus.validation.UsernameValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IExerciseService exerciseService;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UsernameValidator usernameValidator;
    @Autowired
    private EmailValidator emailValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.addValidators(usernameValidator, emailValidator);
    }

    protected final Log _logger = LogFactory.getLog(this.getClass());

    @GetMapping("/login")
    public String login(@RequestParam(value="error", required = false) String error,
                        Model model, Principal principal){
        model.addAttribute("titulo", "Login");

        if(principal != null){
            return "redirect:/about";
        }

        if(error != null){
            model.addAttribute("error", "Nombre de usuario o contraseña incorrecta");
        }


        return "login/login";
    }


    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        //user.setEnabled(true);
        model.addAttribute("titulo", "Sign up");
        model.addAttribute("user", user);

        return "login/register";
    }
    //public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
     //                     @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {

    @PostMapping("/register")
    public String saveUser(@Valid User user, BindingResult result, Model model){

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Sign up");
            return "login/register";
        }

        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
/*        List<Exercise> exerciseList = exerciseService.findAll();
        user.setExercises(exerciseList);*/
        Role role = new Role(user, "ROLE_USER");
        userService.save(user);
        roleService.save(role);

        return "redirect:about";
    }
}
