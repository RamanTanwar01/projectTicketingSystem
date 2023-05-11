package com.cs.controllers;

import com.cs.entities.Employee;
import com.cs.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

import static java.lang.System.*;


@Controller
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal)
    {
        String userName = principal.getName();
        //get employee using userName
        Employee employee = employeeRepository.getEmployeeByEmail(userName);
        out.println(employee);

        model.addAttribute("employee",employee);
        return "developer/dev_dashboard";
    }



}
