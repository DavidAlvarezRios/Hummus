package ub.dalvarezrios.hummus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExerciseController {

    @GetMapping("/exercises")
    public String exercises(Model model){
        model.addAttribute("titulo", "Ejercicios");

        return "exercises/exercises";
    }

}
