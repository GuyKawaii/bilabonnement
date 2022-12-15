package com.example.bilabonnement.model;
/**
 * @author daniel(GuyKawaii)
 * @author Mikas(CodeClod)
 * @author Ian(DatJustino)
 * @author Veronica(Rhod1um)
 */
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LeaseContract {
    private Integer leaseID;
    private Date startDate;
    private Date endDate;
    private double monthlyPrice;
    private int customerID;
    private int vehicleID;
    private int employeeID;
    private List<Optional> leaseOptionals;

    //Create med ID
    public LeaseContract(Integer leaseID, Date startDate, Date endDate, double monthlyPrice, int customerID, int vehicleID, int employeeID, List<Optional> leaseOptionals) {
        this.leaseID = leaseID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyPrice = monthlyPrice;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
        this.employeeID = employeeID;
        this.leaseOptionals = leaseOptionals;
    }
    //Create uden ID
    public LeaseContract(Date startDate, Date endDate, double monthlyPrice, int customerID, int vehicleID, int employeeID, List<Optional> leaseOptionals) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.monthlyPrice = monthlyPrice;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
        this.employeeID = employeeID;
        this.leaseOptionals = leaseOptionals;
    }

    public int getMonths() {
        // number months - needs correction of one day as Period.between(startDateInclusive, endDateExclusive)
        return Period.between(startDate.toLocalDate() , endDate.toLocalDate().plusDays(1)).getMonths();
    }

    public Integer getLeaseID() {
        return leaseID;
    }

    public void setLeaseID(int leaseID) {
        this.leaseID = leaseID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public List<Optional> getLeaseOptionals() {
        return leaseOptionals;
    }

    public void setLeaseOptionals(List<Optional> leaseOptionals) {
        this.leaseOptionals = leaseOptionals;
    }

    public double totalMonthlyPrice() {
        return  monthlyPrice + optionalsPrice();
    }

    public double optionalsPrice() {
        double optionalPrice = 0;

        // add optional price
        for (Optional optional: leaseOptionals) optionalPrice += optional.getPricePrMonth();

        return optionalPrice;
    }

    public int getOptionalsAmount() {
        return leaseOptionals.size();
    }

    @Override
    public String toString() {
        return "LeaseContract{" +
            "leaseID=" + leaseID +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", monthlyPrice=" + monthlyPrice +
            ", customerID=" + customerID +
            ", vehicleID=" + vehicleID +
            "}\n";
    }
}
