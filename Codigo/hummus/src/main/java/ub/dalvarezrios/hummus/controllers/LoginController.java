package ub.dalvarezrios.hummus.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ub.dalvarezrios.hummus.models.dao.IUserDao;
import ub.dalvarezrios.hummus.models.entity.Role;
import ub.dalvarezrios.hummus.models.entity.User;
import ub.dalvarezrios.hummus.models.service.IRoleService;
import ub.dalvarezrios.hummus.models.service.IUserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class LoginController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private PasswordEncoder encoder;

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
        Role role = new Role(user, "ROLE_USER");
        userService.save(user);
        roleService.save(role);

        return "redirect:about";
    }
}
