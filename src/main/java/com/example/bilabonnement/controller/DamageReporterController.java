package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.*;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
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
    public String damageReports(Model model) {
        model.addAttribute("Role", DATA_REGISTRATION);

        List<DamageReport> damageReports = damageReportService.readAll();

        model.addAttribute("listOfDamageReports", damageReports);
        return "damage_registrator/damage-reports";
    }


    @GetMapping("/create-damage-report")
    public String createDamageReport(Model model, HttpSession session) {
        // validate employee access // todo add this to all get mappings to verify who can access it
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), new Role[]{DAMAGE_REPORTER, ADMINISTRATION}))
            return "redirect:/logout";

        // visual element for work
        List<Car> cars = carService.readAll();
        List<DamageReport> damageReports = damageReportService.readAll();

        // add time
        model.addAttribute("currentTime", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        model.addAttribute("cars", cars);
        model.addAttribute("damageReports", damageReports);

        return "damage_registrator/create-damage-report";
    }

    @PostMapping("/make_damage_report")
    public String createDamageReport(HttpSession session, WebRequest req, Model model) {

        // todo remove tmp add session user
        session.setAttribute("employee", new Employee(102, "2", "DAMAGE_REPORTER user", "123", DAMAGE_REPORTER));

        // session
        Employee employee = (Employee) session.getAttribute("employee");

        System.out.println(req.getParameter("datetime"));


        DamageReport damageReport = new DamageReport(
                // session parameter
                employee.getEmployeeID(),

                // form parameters
                Integer.parseInt(req.getParameter("vehicleID")),
                // todo change to localDate later
                Timestamp.valueOf(LocalDateTime.parse(req.getParameter("datetime")))
        );

        damageReportService.create(damageReport);

        return "redirect:/damage-report";
    }

    @GetMapping("/edit-damage-report")
    public String damageEntry(@RequestParam int reportID, Model model) { //skal HttpSession være i alle parameterlister?
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
        damageEntryService.create(new DamageEntry(
                req.getParameter("skade"),
                req.getParameter("beskrivelse"),
                Double.parseDouble(req.getParameter("pris")),
                Integer.parseInt(req.getParameter("damage-report-id"))));

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
