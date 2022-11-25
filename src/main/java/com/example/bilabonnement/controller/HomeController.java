package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.sql.SQLOutput;

@Controller
public class HomeController {

    EmployeeService employeeService = new EmployeeService();

    // login page
    @GetMapping("/")
    public String index() {
        return "index";
    }

//    @PostMapping("/registration")
//    public String registration(WebRequest request) {
//
//        // get registration values
//        String email = request.getParameter("emailRegistration");
//        String password = request.getParameter("passwordRegistration");
//
//        // check if already exists
//        boolean exists = employeeService.employeeExistsByEmail(email);
//
//        if (exists) {
//            return "index"
//        } else {
//            Employee employee = new
//        }
//
//
//
//        return "index";
//    }

    @PostMapping("/login")
    public String login(WebRequest request, HttpSession session, Model model) {

        // get registration values
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // check employee
        Employee employee = employeeService.getEmployee(email);

        System.out.println(employee);

        // invalid password or user
        if (employee == null || !password.equals(employee.getPassword()) ) {
            model.addAttribute("employeeNotFound", true);
            return "redirect:/";
        }

        // add user to session
        session.setAttribute("employee", employee);
        return "redirect:/validUserTmp";
    }

    @GetMapping("/validUserTmp")
    public String vaidUserTmp() {
        return "validUserTmp";
    }


}
