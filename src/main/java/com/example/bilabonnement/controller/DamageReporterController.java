package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.DamageReport;
import com.example.bilabonnement.service.DamageReportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Controller
public class DamageReporterController {

    DamageReportService damageService = new DamageReportService();

    @GetMapping("/damage-report")
    public String damageReport(){ //skal HttpSession være i alle parameterlister?
        return "damage-report";
    }
    @PostMapping("/create-damage-report")
    public String createDamageReport(HttpSession session, WebRequest req, Model model){
        DamageReport damageReport = new DamageReport(
                Timestamp.from(Instant.now()),
                Integer.parseInt(req.getParameter("leaseID")),
                Integer.parseInt(req.getParameter("vehicleID"))
        );
        damageService.create(damageReport);

        return "redirect:/damage-report";
    }

}
