package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.service.EmployeeService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.support.BeanDefinitionDsl;
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
    public String index(HttpSession session) {

        // fresh session
        if (null == session.getAttribute("employeeID")) return "index";

        // ongoing session
        return "redirect:/role-redirect";
    }

    @PostMapping("/login")
    public String login(WebRequest request, HttpSession session, Model model) {

        // check login credentials
        Employee employee = employeeService.login(request.getParameter("username"), request.getParameter("password"));

        // invalid password or employee
        if (employee == null) {
            model.addAttribute("invalidCredentials", "true"); // todo does not work when using th:if?
            return "redirect:/";

        } else {
            // add session keys
            session.setAttribute("employeeName", employee.getName());
            session.setAttribute("employeeID", employee.getEmployeeID());
            session.setAttribute("employeeRole", employee.getRole());
            session.setAttribute("employeeEmail", employee.getEmail());

            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/role-redirect")
    public String roleRedirect(HttpSession session) {
        Role role = (Role) session.getAttribute("employeeRole");

        // early return for null (switch cant use null)
        if (role == null) return "redirect:/logout";

        // role redirect
        switch (role) {
            case DATA_REGISTRATION -> {
                return "redirect:/data-registration";
            }
            case DAMAGE_REPORTER -> {
                return "redirect:/create-damage-report";
            }
            case BUSINESS_DEVELOPER -> {
                return "redirect:/finance";
            }
            case ADMINISTRATION -> {
                return "redirect:/admin";
            }
            default -> {
                throw new RuntimeException("cannot switch on null");
            }
        }
    }

    @GetMapping("/validUserTmp") // todo remove when other pages are added
    public String validUserTmp() {
        return "validUserTmp";
    }


}
