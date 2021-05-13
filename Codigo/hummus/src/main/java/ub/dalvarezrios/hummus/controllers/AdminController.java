package ub.dalvarezrios.hummus.controllers;

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
import ub.dalvarezrios.hummus.models.entity.Exercise;
import ub.dalvarezrios.hummus.models.entity.Role;
import ub.dalvarezrios.hummus.models.entity.User;
import ub.dalvarezrios.hummus.models.service.IExerciseService;
import ub.dalvarezrios.hummus.models.service.IRoleService;
import ub.dalvarezrios.hummus.models.service.IUserService;
import ub.dalvarezrios.hummus.validation.EmailValidator;
import ub.dalvarezrios.hummus.validation.UsernameValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IExerciseService exerciseService;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/create_exercise")
    public String createExercise(Model model){
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

        return "redirect:/about";
    }

}
