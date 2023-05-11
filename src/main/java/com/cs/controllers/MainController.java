package com.cs.controllers;

import com.cs.entities.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    //handler for home page
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("title","Home - Project Ticketing System");
        return "home";
    }

    //handler for signup page
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("title","Register - Project Ticketing System");
        model.addAttribute("employee",new Employee());
        return "signup";
    }

    //handler for login page
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("title","Login Page");
        return "login";
    }
}
