package com.example.bilabonnement.controller;

import com.example.bilabonnement.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    EmployeeService employeeService = new EmployeeService();

    @GetMapping("/alle-medarbejdere")
    public String users(Model model) {

        model.addAttribute("employees", employeeService.readAll());

        System.out.println(employeeService.readAll());


        return "admin/alle-medarbejdere";
    }


}
