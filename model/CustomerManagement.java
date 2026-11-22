package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import static connection.DatabaseConnection.getConnection;

public class CustomerManagement {

    public boolean isCustomerNumberTaken (int customer_number) throws SQLException {
        String sql = "SELECT 1 FROM customer WHERE customer_number = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customer_number);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean add (Customer customer) throws SQLException {
        String sql = "INSERT INTO customer (customer_number, last_name, first_name, registration_date, membership_points) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customer.getCustomerNumber());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getFirstName());
            stmt.setDate(4, java.sql.Date.valueOf(customer.getRegistrationDate()));
            stmt.setInt(5, customer.getMembershipPoints());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean edit (Customer customer) throws SQLException {
        String sql = "UPDATE customer SET last_name = ?, first_name = ?, registration_date = ?, membership_points = ? WHERE customer_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getLastName());
            stmt.setString(2, customer.getFirstName());
            stmt.setDate(3, java.sql.Date.valueOf(customer.getRegistrationDate()));
            stmt.setInt(4, customer.getMembershipPoints());
            stmt.setInt(5, customer.getCustomerNumber());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean remove (int customer_number) throws SQLException {
        String sql = "DELETE FROM customer WHERE customer_number = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, customer_number);

            return stmt.executeUpdate() > 0;
        }
    }

    public List<Customer> view (int other_choice) throws SQLException {
        String sql = "";

        switch (other_choice) {
            case 1:
                sql = "SELECT * FROM customer";
                break;
            case 2:
                sql = "SELECT * FROM customer ORDER BY last_name, first_name";
                break;
            case 3:
                sql = "SELECT * FROM customer ORDER BY membership_points DESC";
                break;
        }

        List<Customer> customers = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int customer_number = rs.getInt("customer_number");
                String last_name = rs.getString("last_name");
                String first_name = rs.getString("first_name");
                LocalDate registration_date = rs.getDate("registration_date").toLocalDate();
                int membership_points = rs.getInt("membership_points");

                customers.add(new Customer(customer_number, last_name, first_name, registration_date, membership_points));
            }
        }

        return customers;
    }
}