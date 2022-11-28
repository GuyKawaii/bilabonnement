package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.DamageEntry;
import com.example.bilabonnement.model.DamageReport;
import com.example.bilabonnement.model.LeaseContract;
import com.example.bilabonnement.service.DamageEntryService;
import com.example.bilabonnement.service.DamageReportService;
import com.example.bilabonnement.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
public class DamageReporterController {

    DamageReportService damageReportService = new DamageReportService();
    DamageEntryService damageEntryService = new DamageEntryService();
    LeaseContractService leaseContractService = new LeaseContractService();


    //skal HttpSession være i alle parameterlister?
    /* ^ I de sider hvor user er logget ind ja. User-login er gemt i en cookie
    som tilgås via session for validering af info. (Ifølge hvad vi gennemgik i klassen?) */

// Autowired Pathvariables til senerebrug (hvis nødvendigt eller sjovt)
//    @GetMapping("/damage-report/{id}")
//    public String damageReport(@PathVariable("id") int id, Model model) {
    //model.addAttribute("damage-report", damageReportService.read(id));

    @GetMapping("/damage-report")
    public String damageReport(Model model) {

         //skal HttpSession være i alle parameterlister?
        // visual element for work
        List<LeaseContract> contracts = leaseContractService.readAll();
        List<DamageReport> damageReports = damageReportService.readAll();

        // add time
        model.addAttribute("currentTime", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        model.addAttribute("leaseContracts", contracts);
        model.addAttribute("damageReports", damageReports);

        System.out.println(contracts);

        return "damage-report";
    }

    @PostMapping("/create_damage_report")
    public String createDamageReport(HttpSession session, WebRequest req, Model model) {
        // get form parameters
        DamageReport damageReport = new DamageReport(
                Timestamp.from(Instant.now()),
                Integer.parseInt(req.getParameter("leaseID")),
                Integer.parseInt(req.getParameter("vehicleID"))
        );
        damageReportService.create(damageReport);

        return "redirect:/damage-report";
    }

    @GetMapping("/damage-entry")
    public String damageEntry(@RequestParam int id, Model model) { //skal HttpSession være i alle parameterlister?
        List<DamageEntry> damageEntries = damageEntryService.entriesByReport(id);

//        // add time
//        model.addAttribute("currentTime", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
//        model.addAttribute("damageReport", damageReportService.read(id));
//        model.addAttribute("damageEntries", damageEntries);

        return "damage-entry";
    }

    @PostMapping("/delete-damage-report")
    public String deleteDamageReport(WebRequest req) {
        int reportID = Integer.parseInt(req.getParameter("reportID"));
        damageReportService.delete(reportID);

        return "redirect:/damage-report";
    }


}
