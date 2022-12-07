package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.example.bilabonnement.model.enums.Role.ADMINISTRATION;
import static com.example.bilabonnement.model.enums.Role.BUSINESS_DEVELOPER;

@Controller
public class AdminController {

    EmployeeService employeeService = new EmployeeService();

    // people with access to these pages
    Role[] employeeAccess = new Role[]{BUSINESS_DEVELOPER, ADMINISTRATION};

    @GetMapping("/alle-medarbejdere")
    public String users(Model model) {

        model.addAttribute("employees", employeeService.readAll());

        System.out.println(employeeService.readAll());


        return "admin/alle-medarbejdere";
    }


}
