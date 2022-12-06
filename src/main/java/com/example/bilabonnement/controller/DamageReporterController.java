package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.*;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.service.*;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.example.bilabonnement.model.enums.Role.*;

@Controller
public class DamageReporterController {
    DamageReportService damageReportService = new DamageReportService();
    DamageEntryService damageEntryService = new DamageEntryService();
    LeaseContractService leaseContractService = new LeaseContractService();
    CarService carService = new CarService();

    // people with access to this website
    Role[] employeeAccess = new Role[]{DAMAGE_REPORTER, ADMINISTRATION};


    //skal HttpSession være i alle parameterlister?
    /* ^ I de sider hvor user er logget ind ja. User-login er gemt i en cookie
    som tilgås via session for validering af info. (Ifølge hvad vi gennemgik i klassen?) */

// Autowired Pathvariables til senerebrug (hvis nødvendigt eller sjovt)
//    @GetMapping("/damage-report/{id}")
//    public String damageReport(@PathVariable("id") int id, Model model) {
    //model.addAttribute("damage-report", damageReportService.read(id));

    @GetMapping("/damage-reports")
    public String damageReports(Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", ((Role) session.getAttribute("employeeRole")).toString());
        model.addAttribute("employeeName", session.getAttribute("employeeName"));

        List<DamageReport> damageReports = damageReportService.readAll();

        model.addAttribute("listOfDamageReports", damageReports);
        return "damage_registrator/damage-reports";
    }


    @GetMapping("/create-damage-report")
    public String createDamageReport(Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", ((Role) session.getAttribute("employeeRole")).toString());
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));

        // form values and reports for help
        model.addAttribute("currentTime", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        // todo missing sql to select only for specifik employee
        model.addAttribute("cars", carService.readAllUnleasedOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("damageReports", damageReportService.readAllFromEmployee((int) session.getAttribute("employeeID")));

        return "damage_registrator/create-damage-report";
    }

    @PostMapping("/create-new-damage-report")
    public String createDamageReport(HttpSession session, WebRequest req, Model model) {
        // damage report to create
        damageReportService.create(new DamageReport(
                // session parameter
                (int) session.getAttribute("employeeID"),
                // form parameters
                Integer.parseInt(req.getParameter("vehicleID")),
                // todo change to localDate later
                Timestamp.valueOf(LocalDateTime.parse(req.getParameter("datetime")))));

        return "redirect:/create-damage-report";
    }

    @GetMapping("/edit-damage-report")
    public String damageEntry(@RequestParam int reportID, Model model, HttpSession session) { //skal HttpSession være i alle parameterlister?
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", ((Role) session.getAttribute("employeeRole")).toString());
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));


        // add reference lists
        model.addAttribute("damageReport", damageReportService.read(reportID));
        model.addAttribute("damageEntries", damageEntryService.entriesByReport(reportID));
        model.addAttribute("cars", carService.readAllUnleasedOnDate(Date.valueOf(LocalDate.now())));

        return "damage_registrator/edit-damage-report";

        /*

        virker som om vi skal bruge

        READY_FOR_LEASING
        RETURNED_FROM_LEASING

        damagereporter sætter bilen READY_FOR_LEASING ved at lave en skaderapport eller toggle billen tjekket

        dataregistrator få liste over bilder der har overstået leasing periode og kan toggle dem til RETURNED_FROM_LEASING

         */
    }

    @GetMapping("/delete-damage-report")
    public String deleteDamageReport(WebRequest req, @RequestParam String reportID, @RequestParam String returnPage) {
        damageReportService.delete(Integer.parseInt(reportID));

        return "redirect:/" + returnPage;
    }

    @PostMapping("/create-damage-entry")
    public String createDamageEntry(WebRequest req, HttpSession session, Model model) {
        // create entry
        damageEntryService.create(new DamageEntry(
                req.getParameter("skade"),
                req.getParameter("beskrivelse"),
                Double.parseDouble(req.getParameter("pris")),
                Integer.parseInt(req.getParameter("damage-report-id"))));

        return ("redirect:/edit-damage-report?reportID=" + req.getParameter("damage-report-id"));
    }

    @GetMapping("/delete-entry")
    public String deleteEntry(WebRequest req, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";

        // update db
        damageEntryService.delete(Integer.parseInt(req.getParameter("entryID")));

        return ("redirect:/edit-damage-report?reportID=" + req.getParameter("reportID"));
    }

    @PostMapping("/update-damage-report")
    public String updateDamageReport(Model model, WebRequest req) {

        damageReportService.update(new DamageReport(
                Integer.parseInt(req.getParameter("damageReportID")),
                Integer.parseInt(req.getParameter("employeeID")),
                Integer.parseInt(req.getParameter("vehicleID")),
                Timestamp.valueOf(LocalDateTime.parse(req.getParameter("timestamp")))
        ));

        return "redirect:/edit-damage-report?reportID=" + req.getParameter("damageReportID");
    }

}
