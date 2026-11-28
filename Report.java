// javac -cp ".;mysql-connector-j-26.10.0.jar" Report.java
// java -cp ".;mysql-connector-j-26.10.0.jar" Report.java

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Report {
    public static int choice;
    static Scanner scanner = new Scanner(System.in);

    static final String URL = "jdbc:mysql://localhost:3306/dbprojectapp";
    static final String USER = "root";
    static final String PASSWORD = "kiko1736";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {        
        while (true) {
            
        System.out.println("Welcome to the Report Menu!");
        System.out.println("Please select an option:");
        System.out.println("1. Generate Monthly Revenue Report per Branch");
        System.out.println("2. Generate Monthly Discontinued Games Stock Report per Branch");
        System.out.println("3. Generate Monthly Sales Report by Genre");
        System.out.println("4. Generate Monthly Membership Points Report per Branch");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");

        choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    generateReport1();
                    break;
                case 2:
                    generateReport2();
                    break;
                case 3:
                    generateReport3();
                    break;
                case 4:
                    generateReport4();
                    break;
                case 5:
                    System.out.println("Exiting the program. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
                }
        }
    }

    public static int getMonthNumber(String month) {
        switch (month) {
            case "january":
                return 1;
            case "february":
                return 2;
            case "march":
                return 3;
            case "april":
                return 4;
            case "may":
                return 5;
            case "june":
                return 6;
            case "july":
                return 7;
            case "august":
                return 8;
            case "september":
                return 9;
            case "october":
                return 10;
            case "november":
                return 11;
            case "december":
                return 12;
            default:
                System.out.println("Invalid month. Please try again.");
                return -1; // Invalid month
        }
    }

    public static void generateReport1() {
        String month = "";
        String sql = "";
        int monthNumber = 0;
        System.out.print("Month of: ");
        month = scanner.next().toLowerCase();
        monthNumber = getMonthNumber(month);
        if (monthNumber < 1) {
            return;
        }
        sql = String.format("""
            SELECT b.branch_code,
                   b.branch_name,
                   COALESCE(SUM(s.amount_paid), 0) AS total_paid
            FROM branch b
            LEFT JOIN sales s
                   ON b.branch_code = s.branch_code
                  AND EXTRACT(MONTH FROM s.purchase_date) = %d
            GROUP BY b.branch_code, b.branch_name
            ORDER BY total_paid DESC
            """, monthNumber);

    System.out.println("Generating Monthly Revenue Report per Branch...");

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        try (ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n--- MONTHLY REVENUE REPORT PER BRANCH (MONTH " + monthNumber + ") ---");
            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;
                String branchCode = rs.getString("branch_code");
                String branchName = rs.getString("branch_name");
                double totalPaid = rs.getDouble("total_paid");

                System.out.printf("Branch Code: %-4s | Branch Name: %-24s | Total Paid: %.2f%n",
                        branchCode, branchName, totalPaid);
            }

            if (!hasRows) {
                System.out.println("No branch records found.");
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateReport2() {
        String month = "";
        String sql = "";
        int monthNumber = 0;
        System.out.print("Month of: ");
        month = scanner.next().toLowerCase();
        monthNumber = getMonthNumber(month);
        if (monthNumber < 1) {
            return;
        }
        sql = String.format("""
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
                  AND EXTRACT(MONTH FROM s.purchase_date) = %d
            WHERE g.discontinued = 1
            GROUP BY b.branch_code,
                     b.branch_name,
                     g.game_code,
                     g.game_name,
                     g.quantity_in_stock
            ORDER BY b.branch_code ,
                     g.game_name ASC
            """, monthNumber);

        System.out.println("Generating Monthly Discontinued Games Stock Report per Branch...");
        try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        try (ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n--- DISCONTINUED TITLES BY BRANCH (MONTH " + monthNumber + ") ---");
            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;
                String branchCode = rs.getString("branch_code");
                String branchName = rs.getString("branch_name");
                int gameCode = rs.getInt("game_code");
                String gameName = rs.getString("game_name");
                int remainingInventory = rs.getInt("remaining_inventory");
                int unitsSold = rs.getInt("units_sold_in_month");

                System.out.printf("Branch: %-24s (%-4s) | Game [%-4d]: %-18s | Remaining Stock: %4d | Sold in Month: %4d%n",
                        branchName, branchCode, gameCode, gameName, remainingInventory, unitsSold);
            }

            if (!hasRows) {
                System.out.println("No discontinued titles found for the selected month.");
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateReport3() {
        String month = "";
        String sql = "";
        int monthNumber = 0;
        System.out.print("Month of: ");
        month = scanner.next().toLowerCase();
        monthNumber = getMonthNumber(month);
        if (monthNumber < 1) {
            System.out.println("Invalid month entered.");
            return;
        }
        sql = String.format("""
            SELECT g.genre,
                   COALESCE(SUM(s.amount_paid), 0) AS total_revenue,
                   COALESCE(SUM(s.quantity), 0) AS units_sold
            FROM game g
            LEFT JOIN sales s
                   ON g.game_code = s.game_code
                  AND g.branch_code = s.branch_code
                  AND EXTRACT(MONTH FROM s.purchase_date) = %d
            GROUP BY g.genre
            ORDER BY total_revenue DESC,
                     g.genre
            """, monthNumber);

        System.out.println("Generating Monthly Sales Report by Genre...");

        try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        try (ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n--- MONTHLY SALES BY GENRE (MONTH " + monthNumber + ") ---");
            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;
                String genre = rs.getString("genre");
                double totalRevenue = rs.getDouble("total_revenue");
                int unitsSold = rs.getInt("units_sold");

                System.out.printf("Genre: %-15s | Units Sold: %5d | Total Revenue: %.2f%n",
                        genre, unitsSold, totalRevenue);
            }

            if (!hasRows) {
                System.out.println("No genre records found.");
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void generateReport4() {
        String month = "";
        String sql = "";
        int monthNumber = 0;
        System.out.print("Month of: ");
        month = scanner.next().toLowerCase();
        monthNumber = getMonthNumber(month);
        if (monthNumber < 1) {
            System.out.println("Invalid month entered.");
            return;
        }
        sql = String.format("""
            SELECT b.branch_code,
                   b.branch_name,
                   (
                       SELECT COUNT(DISTINCT c2.membership_points) + 1
                       FROM sales s2
                       INNER JOIN customer c2
                              ON s2.customer_number = c2.customer_number
                       WHERE s2.branch_code = b.branch_code
                         AND EXTRACT(MONTH FROM s2.purchase_date) = %d
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
            WHERE EXTRACT(MONTH FROM s.purchase_date) = %d
              AND (
                  SELECT COUNT(DISTINCT c2.membership_points)
                  FROM sales s2
                  INNER JOIN customer c2
                         ON s2.customer_number = c2.customer_number
                  WHERE s2.branch_code = b.branch_code
                    AND EXTRACT(MONTH FROM s2.purchase_date) = %d
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
            """, monthNumber, monthNumber, monthNumber);

        System.out.println("Generating Monthly Membership Points Report per Branch...");

        try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        try (ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n--- TOP CUSTOMERS BY BRANCH (MONTH " + monthNumber + ") ---");
            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;
                String branchCode = rs.getString("branch_code");
                String branchName = rs.getString("branch_name");
                int rank = rs.getInt("branch_rank");
                int customerNumber = rs.getInt("customer_number");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int points = rs.getInt("membership_points");

                System.out.printf("Branch: %-24s (%-4s) | Rank #%d | Customer [%d]: %-18s %-18s | Points: %4d%n",
                        branchName, branchCode, rank, customerNumber, firstName, lastName, points);
            }

            if (!hasRows) {
                System.out.println("No customer purchases found for the selected month.");
            }
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
