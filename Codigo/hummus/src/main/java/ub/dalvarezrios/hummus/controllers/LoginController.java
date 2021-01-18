package ub.dalvarezrios.hummus.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sun.security.util.Password;
import ub.dalvarezrios.hummus.models.dao.IUserDao;
import ub.dalvarezrios.hummus.models.entity.User;
import ub.dalvarezrios.hummus.models.service.IUserService;

@Controller
public class LoginController {

    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("titulo", "Login");

        return "login/login";
    }


    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("titulo", "Sign up");
        model.addAttribute("user", user);

        return "login/register";
    }

    @PostMapping("/register")
    public String saveUser(User user){
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:about";
    }
}
