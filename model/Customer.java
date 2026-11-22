package model;

import java.time.LocalDate;

public class Customer {
    private int customer_number;
    private String last_name;
    private String first_name;
    private LocalDate registration_date;
    private int membership_points;

    public Customer (int customer_number, String last_name, String first_name, LocalDate registration_date, int membership_points) {
        this.customer_number = customer_number;
        this.last_name = last_name;
        this.first_name = first_name;
        this.registration_date = registration_date;
        this.membership_points = membership_points;
    }

    public int getCustomerNumber () {
        return customer_number;
    }

    public String getLastName () {
        return last_name;
    }

    public String getFirstName () {
        return first_name;
    }

    public LocalDate getRegistrationDate () {
        return registration_date;
    }

    public int getMembershipPoints () {
        return membership_points;
    }
}