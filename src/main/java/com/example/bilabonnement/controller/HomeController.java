package com.example.bilabonnement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.sql.Date;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }


}
