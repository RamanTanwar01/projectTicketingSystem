package com.cs.controllers;

import com.cs.entities.Employee;
import com.cs.helper.Message;
import com.cs.repositories.EmployeeRepository;
import com.cs.services.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@Controller
public class RegistrationController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private static final String SIGNUP="signup";
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeServiceImpl employeeService;

    //handler for user registration
    @PostMapping("/doRegister")
    public String registerUser(@Valid @ModelAttribute("employee") Employee employee,BindingResult result, @RequestParam(value = "agreement",defaultValue = "false")boolean agreement, Model model,
                                HttpSession session){
        try {
            if(!agreement){
                throw new Exception("You have not agreed Terms & Conditions");
            }
            if (result.hasErrors()){
                model.addAttribute("employee",employee);
                return SIGNUP;
            }
            int i = employeeRepository.countByEmail(employee.getEmail());
            if(i!=0){
                session.setAttribute("message", new Message("Email already exists","alert-danger"));
                return SIGNUP;
            }
            employee.setPassword(passwordEncoder.encode(employee.getPassword()));
            employeeService.addEmployee(employee);
            model.addAttribute("employee",new Employee());
            session.setAttribute("message",new Message("Registration Successful","alert-success"));
            return SIGNUP;
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("employee",employee);
            session.setAttribute("message", new Message("something went wrong..! " + e.getMessage(),"alert-danger"));
            return SIGNUP;
        }
    }
}
