package model;

import java.sql.*;
import java.util.*;
import static connection.DatabaseConnection.getConnection;

public class ReportManagement {

	public int getMonthNumber(String month) {
        switch (month.toLowerCase()) {
        	case "january":   return 1;
            case "february":  return 2;
            case "march":     return 3;
            case "april":     return 4;
            case "may":       return 5;
            case "june":      return 6;
            case "july":      return 7;
            case "august":    return 8;
            case "september": return 9;
            case "october":   return 10;
            case "november":  return 11;
            case "december":  return 12;
            default:          return -1;
        }
	}

    public List<Report1> getMonthlyRevenuePerBranch(int month) throws SQLException {
        String sql = """
            SELECT b.branch_code,
                   b.branch_name,
                   COALESCE(SUM(s.amount_paid), 0) AS total_paid
            FROM branch b
            LEFT JOIN sales s
                   ON b.branch_code = s.branch_code
                  AND EXTRACT(MONTH FROM s.purchase_date) = ?
            GROUP BY b.branch_code, b.branch_name
            ORDER BY total_paid DESC
            """;

        List<Report1> results = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, month);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Report1(
                            rs.getString("branch_code"),
                            rs.getString("branch_name"),
                            rs.getDouble("total_paid")));
                }
            }
        }
        return results;
    }

    public List<Report2> getDiscontinuedGamesStockPerBranch(int month) throws SQLException {
        String sql = """
            SELECT b.branch_code,
                   b.branch_name,
                   g.game_code,
                   g.game_name,
                   g.quantity_in_stock AS remaining_inventory,
                   COALESCE(SUM(s.quantity), 0) AS units_sold_in_month
            FROM branch b
            INNER JOIN game g
                   ON b.branch_code = g.branch_code
            LEFT JOIN sales s
                   ON g.game_code = s.game_code
                  AND g.branch_code = s.branch_code
                  AND EXTRACT(MONTH FROM s.purchase_date) = ?
            WHERE g.discontinued = 1
            GROUP BY b.branch_code,
                     b.branch_name,
                     g.game_code,
                     g.game_name,
                     g.quantity_in_stock
            ORDER BY b.branch_code,
                     g.game_name ASC
            """;

        List<Report2> results = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, month);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Report2(
                            rs.getString("branch_code"),
                            rs.getString("branch_name"),
                            rs.getInt("game_code"),
                            rs.getString("game_name"),
                            rs.getInt("remaining_inventory"),
                            rs.getInt("units_sold_in_month")));
                }
            }
        }
        return results;
    }

    public List<Report3> getMonthlySalesByGenre(int month) throws SQLException {
        String sql = """
            SELECT g.genre,
                   COALESCE(SUM(s.amount_paid), 0) AS total_revenue,
                   COALESCE(SUM(s.quantity), 0) AS units_sold
            FROM game g
            LEFT JOIN sales s
                   ON g.game_code = s.game_code
                  AND g.branch_code = s.branch_code
                  AND EXTRACT(MONTH FROM s.purchase_date) = ?
            GROUP BY g.genre
            ORDER BY total_revenue DESC,
                     g.genre
            """;

        List<Report3> results = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, month);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Report3(
                            rs.getString("genre"),
                            rs.getDouble("total_revenue"),
                            rs.getInt("units_sold")));
                }
            }
        }
        return results;
    }

    public List<Report4> getMembershipPointsPerBranch(int month) throws SQLException {
        String sql = """
            SELECT b.branch_code,
                   b.branch_name,
                   (
                       SELECT COUNT(DISTINCT c2.membership_points) + 1
                       FROM sales s2
                       INNER JOIN customer c2
                              ON s2.customer_number = c2.customer_number
                       WHERE s2.branch_code = b.branch_code
                         AND EXTRACT(MONTH FROM s2.purchase_date) = ?
                         AND c2.membership_points > c.membership_points
                   ) AS branch_rank,
                   c.customer_number,
                   c.first_name,
                   c.last_name,
                   c.membership_points
            FROM branch b
            INNER JOIN sales s
                   ON b.branch_code = s.branch_code
            INNER JOIN customer c
                   ON s.customer_number = c.customer_number
            WHERE EXTRACT(MONTH FROM s.purchase_date) = ?
              AND (
                  SELECT COUNT(DISTINCT c2.membership_points)
                  FROM sales s2
                  INNER JOIN customer c2
                         ON s2.customer_number = c2.customer_number
                  WHERE s2.branch_code = b.branch_code
                    AND EXTRACT(MONTH FROM s2.purchase_date) = ?
                    AND c2.membership_points > c.membership_points
              ) < 3
            GROUP BY b.branch_code,
                     b.branch_name,
                     c.customer_number,
                     c.first_name,
                     c.last_name,
                     c.membership_points
            ORDER BY b.branch_code ASC,
                     branch_rank ASC,
                     c.customer_number ASC
            """;

        List<Report4> results = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, month);
            stmt.setInt(2, month);
            stmt.setInt(3, month);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    results.add(new Report4(
                            rs.getString("branch_code"),
                            rs.getString("branch_name"),
                            rs.getInt("branch_rank"),
                            rs.getInt("customer_number"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("membership_points")));
                }
            }
        }
        return results;
    }
}