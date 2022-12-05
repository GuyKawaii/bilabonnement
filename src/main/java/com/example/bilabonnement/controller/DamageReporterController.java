package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.*;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.service.*;
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
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), new Role[]{DAMAGE_REPORTER, ADMINISTRATION}))
            return "redirect:/logout";
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
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), new Role[]{DAMAGE_REPORTER, ADMINISTRATION}))
            return "redirect:/logout";
        // session navbar
        model.addAttribute("employeeRole", ((Role) session.getAttribute("employeeRole")).toString());
        model.addAttribute("employeeName", session.getAttribute("employeeName"));

        // visual element for work
        List<Car> cars = carService.readAllUnleasedOnDate(Date.valueOf(LocalDate.now()));
        List<DamageReport> damageReports = damageReportService.readAll();


        // add time
        model.addAttribute("currentTime", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        model.addAttribute("cars", cars);
        model.addAttribute("damageReports", damageReports);

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
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), new Role[]{DAMAGE_REPORTER, ADMINISTRATION}))
            return "redirect:/logout";
        // session navbar
        model.addAttribute("employeeRole", ((Role) session.getAttribute("employeeRole")).toString());
        model.addAttribute("employeeName", session.getAttribute("employeeName"));


        // add reference lists
        model.addAttribute("damageReport", damageReportService.read(reportID));
        model.addAttribute("damageEntries", damageEntryService.entriesByReport(reportID));

        return "damage_registrator/edit-damage-report";
    }

    @GetMapping("/delete-damage-report")
    public String deleteDamageReport(WebRequest req, @RequestParam String reportID, @RequestParam String returnPage) {
        damageReportService.delete(Integer.parseInt(reportID));

        return "redirect:/" + returnPage;
    }

    @PostMapping("/create-damage-entry")
    public String createDamageEntry(WebRequest req) {

        // todo add such that it does not work if session is not valid and it cannot find report to add to


        // create entry
        damageEntryService.create(new DamageEntry(req.getParameter("skade"), req.getParameter("beskrivelse"), Double.parseDouble(req.getParameter("pris")), Integer.parseInt(req.getParameter("damage-report-id"))));

        return ("redirect:/damage-entry?id=" + req.getParameter("damage-report-id"));
    }

    @PostMapping("/delete-entry")
    public String deleteEntry(WebRequest req) {

        // get parameters
        int damageReportID = Integer.parseInt(req.getParameter("reportID"));
        int entryID = Integer.parseInt(req.getParameter("entryID"));

        // update db
        damageEntryService.delete(entryID);

        return ("redirect:/damage-entry?id=" + damageReportID);
    }

}
