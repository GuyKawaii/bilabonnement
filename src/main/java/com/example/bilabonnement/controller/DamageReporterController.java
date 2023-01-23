package com.example.bilabonnement.controller;

import com.example.bilabonnement.model.*;
import com.example.bilabonnement.model.enums.Role;
import com.example.bilabonnement.model.enums.State;
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
import static com.example.bilabonnement.model.enums.State.*;

@Controller
public class DamageReporterController {
    DamageReportService damageReportService = new DamageReportService();
    DamageEntryService damageEntryService = new DamageEntryService();
    LeaseContractService leaseContractService = new LeaseContractService();
    CarService carService = new CarService();
    EmployeeService employeeService = new EmployeeService();

    // people with access to these pages
    Role[] employeeAccess = new Role[]{DAMAGE_REPORTER};



    /**
     * @author daniel(GuyKawaii)
     */
    @GetMapping("/create-damage-report")
    public String createDamageReport(Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));

        // form values and reports for help
        model.addAttribute("currentTime", LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        model.addAttribute("employees", employeeService.readAllWithRole(DAMAGE_REPORTER));
        model.addAttribute("cars", carService.readAllUnleasedOnDate(Date.valueOf(LocalDate.now())));
        model.addAttribute("damageReports", damageReportService.readAllFromEmployee((int) session.getAttribute("employeeID")));

        return "damage-registrator/create-damage-report";
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @PostMapping("/create-new-damage-report")
    public String createDamageReport(HttpSession session, WebRequest req, Model model) {

        // damage report to create
        damageReportService.create(new DamageReport(
                 Integer.parseInt(req.getParameter("employeeID")),
                Integer.parseInt(req.getParameter("vehicleID")),
                Timestamp.valueOf(LocalDateTime.parse(req.getParameter("datetime")))));

        // update car state
        carService.updateState(
                Integer.parseInt(req.getParameter("vehicleID")),
                READY);

        return "redirect:/create-damage-report";
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @GetMapping("/edit-damage-report")
    public String damageEntry(@RequestParam int reportID, Model model, HttpSession session) { //skal HttpSession være i alle parameterlister?
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));


        // add reference lists
        model.addAttribute("damageReport", damageReportService.read(reportID));
        model.addAttribute("damageEntries", damageEntryService.entriesByReport(reportID));
        model.addAttribute("cars", carService.readAllUnleasedOnDate(Date.valueOf(LocalDate.now())));

        return "damage-registrator/edit-damage-report";
    }

    @GetMapping("/delete-damage-report")
    public String deleteDamageReport(WebRequest req, @RequestParam String reportID, @RequestParam String returnPage) {
        damageReportService.delete(Integer.parseInt(reportID));

        return "redirect:/" + returnPage;
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @GetMapping("/damage-reports")
    public String damageReports(Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));

        List<DamageReport> damageReports = damageReportService.readAll();

        model.addAttribute("listOfDamageReports", damageReports);
        return "damage-registrator/damage-reports";
    }


    /**
     * @author daniel(GuyKawaii)
     */
    @PostMapping("/create-damage-entry")
    public String createDamageEntry(WebRequest req, HttpSession session, Model model) {
        // create entry
        damageEntryService.create(new DamageEntry(
                req.getParameter("skade"),
                req.getParameter("beskrivelse"),
                Double.parseDouble(req.getParameter("pris").replace(',', '.')),
                Integer.parseInt(req.getParameter("damage-report-id"))));

        return ("redirect:/edit-damage-report?reportID=" + req.getParameter("damage-report-id"));
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @GetMapping("/delete-entry")
    public String deleteEntry(WebRequest req, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";

        // update db
        damageEntryService.delete(Integer.parseInt(req.getParameter("entryID")));

        return ("redirect:/edit-damage-report?reportID=" + req.getParameter("reportID"));
    }

    /**
     * @author daniel(GuyKawaii)
     */
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

    /**
     * @author daniel(GuyKawaii)
     */
    @GetMapping("checkout-cars")
    public String checkOutCars(Model model, HttpSession session) {
        // validate employee access
        if (!EmployeeService.validEmployeeRole((Role) session.getAttribute("employeeRole"), employeeAccess))
            return "redirect:/role-redirect";
        // session navbar
        model.addAttribute("employeeRole", session.getAttribute("employeeRole"));
        model.addAttribute("employeeName", session.getAttribute("employeeName"));
        model.addAttribute("employeeID", session.getAttribute("employeeID"));

        model.addAttribute("unleasedReturnedCars", carService.readAllUnleasedOnDateWithState(Date.valueOf(LocalDate.now()), RETURNED));
        model.addAttribute("states", employeeService.getEmployeeStateSelect(DAMAGE_REPORTER));
        model.addAttribute("date", LocalDate.now());

        return "/damage-registrator/checkout-cars";
    }

    /**
     * @author daniel(GuyKawaii)
     */
    @PostMapping("/update-damage-state")
    public String updateCarState(WebRequest req) {

        carService.updateState(
                Integer.parseInt(req.getParameter("vehicleID")),
                State.valueOf(req.getParameter("state")));

        return "redirect:/checkout-cars";
    }

}
