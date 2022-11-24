package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.leaseContract;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;

@Controller
public class DataRegistrationController {

    @GetMapping("/registration")
    public String registrationPage(HttpSession session) {

        return "registration";
    }

    @PostMapping("/makeContract")
    public String makeContract(HttpSession session, WebRequest req, Model model) {
        model.addAttribute(new leaseContract(
                req.getAttribute("leaseID"),
                ))
        model.getAttribute()
    }
}
