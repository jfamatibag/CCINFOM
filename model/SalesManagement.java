package model;

import java.sql.*;
import java.util.*;
import static connection.DatabaseConnection.getConnection;

public class SalesManagement {

    public boolean isSaleIdTaken (int sale_id) throws SQLException {
        String sql = "SELECT 1 FROM sales WHERE sale_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, sale_id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean isCustomerExisting (int customer_number) throws SQLException {
        String sql = "SELECT 1 FROM customer WHERE customer_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customer_number);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    // Returns the game's genre, or null if the game does not exist in that branch
    public String getGameGenre (int game_code, String branch_code) throws SQLException {
        String sql = "SELECT genre FROM game WHERE game_code = ? AND branch_code = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, game_code);
            stmt.setString(2, branch_code);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("genre");
                }
                return null;
            }
        }
    }

    // sale_id is AUTO_INCREMENT, so it is not part of the INSERT
    public boolean add (Sales sale) throws SQLException {
        String sql = "INSERT INTO sales (customer_number, game_code, branch_code, purchase_date, amount_paid, genre, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sale.getCustomerNumber());
            stmt.setInt(2, sale.getGameCode());
            stmt.setString(3, sale.getBranchCode());
            stmt.setDate(4, java.sql.Date.valueOf(sale.getPurchaseDate()));
            stmt.setDouble(5, sale.getAmountPaid());
            stmt.setString(6, sale.getGenre());
            stmt.setInt(7, sale.getQuantity());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean edit (Sales sale) throws SQLException {
        String sql = "UPDATE sales SET customer_number = ?, game_code = ?, branch_code = ?, purchase_date = ?, amount_paid = ?, genre = ?, quantity = ? WHERE sale_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sale.getCustomerNumber());
            stmt.setInt(2, sale.getGameCode());
            stmt.setString(3, sale.getBranchCode());
            stmt.setDate(4, java.sql.Date.valueOf(sale.getPurchaseDate()));
            stmt.setDouble(5, sale.getAmountPaid());
            stmt.setString(6, sale.getGenre());
            stmt.setInt(7, sale.getQuantity());
            stmt.setInt(8, sale.getSaleId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean remove (int sale_id) throws SQLException {
        String sql = "DELETE FROM sales WHERE sale_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sale_id);

            return stmt.executeUpdate() > 0;
        }
    }

    public List<Sales> view (int other_choice) throws SQLException {
        String sql = "";

        switch (other_choice) {
            case 1:
                sql = "SELECT * FROM sales";
                break;
            case 2:
                sql = "SELECT * FROM sales ORDER BY purchase_date";
                break;
            case 3:
                sql = "SELECT * FROM sales ORDER BY branch_code, purchase_date";
                break;
        }

        List<Sales> sales = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int sale_id = rs.getInt("sale_id");
                int customer_number = rs.getInt("customer_number");
                int game_code = rs.getInt("game_code");
                String branch_code = rs.getString("branch_code");
                java.time.LocalDate purchase_date = rs.getDate("purchase_date").toLocalDate();
                double amount_paid = rs.getDouble("amount_paid");
                String genre = rs.getString("genre");
                int quantity = rs.getInt("quantity");

                sales.add(new Sales(sale_id, customer_number, game_code, branch_code, purchase_date, amount_paid, genre, quantity));
            }
        }

        return sales;
    }
}