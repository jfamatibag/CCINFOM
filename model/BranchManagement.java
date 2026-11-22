package model;

import java.sql.*;
import java.util.*;
import static connection.DatabaseConnection.getConnection;

public class BranchManagement {

    public boolean isBranchCodeTaken (String branch_code) throws SQLException {
        String sql = "SELECT 1 FROM branch WHERE branch_code = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, branch_code);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean add (Branch branch) throws SQLException {
        String sql = "INSERT INTO branch (branch_code, branch_name, street_address, city) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, branch.getBranchCode());
            stmt.setString(2, branch.getBranchName());
            stmt.setString(3, branch.getStreetAddress());
            stmt.setString(4, branch.getCity());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean edit (Branch branch) throws SQLException {
        String sql = "UPDATE branch SET branch_name = ?, street_address = ?, city = ? WHERE branch_code = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

        	
            stmt.setString(1, branch.getBranchName());
            stmt.setString(2, branch.getStreetAddress());
            stmt.setString(3, branch.getCity());
            stmt.setString(4, branch.getBranchCode());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean remove (String branch_code) throws SQLException {
        String sql = "DELETE FROM branch WHERE branch_code = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, branch_code);

            return stmt.executeUpdate() > 0;
        }
    }
    

    public List<Branch> view (int other_choice) throws SQLException {
        String sql = "";

        switch (other_choice) {
            case 1:
                sql = "SELECT * FROM branch";
                break;
            case 2:
                sql = "SELECT * FROM branch ORDER BY branch_name";
                break;
            case 3:
                sql = "SELECT * FROM branch ORDER BY city";
                break;
        }

        List<Branch> branches = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String branch_code = rs.getString("branch_code");
                String branch_name = rs.getString("branch_name");
                String street_address = rs.getString("street_address");
                String city = rs.getString("city");

                branches.add(new Branch(branch_code, branch_name, street_address, city));
            }
        }

        return branches;
    }
}
