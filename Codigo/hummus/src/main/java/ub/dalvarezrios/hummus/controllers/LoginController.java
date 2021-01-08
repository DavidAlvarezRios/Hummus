package ub.dalvarezrios.hummus.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("titulo", "Login");

        return "login/login";
    }


    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("titulo", "Sign up");

        return "login/register";
    }
}
