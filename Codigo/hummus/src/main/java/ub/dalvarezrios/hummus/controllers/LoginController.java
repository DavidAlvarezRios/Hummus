package ub.dalvarezrios.hummus.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ub.dalvarezrios.hummus.models.dao.IUserDao;
import ub.dalvarezrios.hummus.models.entity.User;

@Controller
public class LoginController {

    @Autowired
    private IUserDao userDao;

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
        userDao.save(user);
        return "redirect:about";
    }
}
