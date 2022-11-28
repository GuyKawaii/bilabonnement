package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    EmployeeService employeeService = new EmployeeService();

    // login page
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String login(WebRequest request, HttpSession session, Model model) {

        // get values
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // check employee
        Employee employee = employeeService.getEmployee(email);

        System.out.println(employee);

        // invalid password or user
        if (employee == null || !password.equals(employee.getPassword())) {
            model.addAttribute("invalidCredentials", true);
            return "index";
        }

        // add user to session
        session.setAttribute("employee", employee);

        // role redirect
        switch (employee.getRole()) {

            case DATA_REGISTRATION -> {
                return "redirect:/";
            }
            case DAMAGE_REPORTER -> {
                return "redirect:/";
            }
            case BUSINESS_DEVELOPER -> {
                return "redirect:/";
            }
            case ADMINISTRATION -> {
                return "redirect:/";
            }
        }



        return "redirect:/validUserTmp";
    }

    @GetMapping("/validUserTmp")
    public String vaidUserTmp() {
        return "validUserTmp";
    }


}
