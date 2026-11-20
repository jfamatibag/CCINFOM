import java.sql.*;
import java.util.*;

public class Game {
    public static int choice;
    static Scanner scanner = new Scanner(System.in);

    static final String URL = "jdbc:mysql://localhost:3306/dbprojectapp";
    static final String USER = "root";
    static final String PASSWORD = "kiko1736";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main (String [] args){
        while (true) {
            System.out.print("\n");
            System.out.println("1. Add");
            System.out.println("2. Edit");
            System.out.println("3. Remove");
            System.out.println("4. View");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }


        switch(choice) {
            case 1:
                add();
                break;
            case 2:
                edit();
                break;
            case 3:
                remove();
                break;
            case 4:
                view();
                break;
            case 5:
                System.out.println("Goodbye!");
                scanner.close();
                return;
            default:
                System.out.println("Invalid choice!");
        }
        }
    }
    
    public static boolean isGameCodeTaken(int game_code) throws SQLException {
        String sql = "SELECT 1 FROM game WHERE game_code = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, game_code);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public static void add () {
        try {
            System.out.print("Enter game code: ");
            int game_code = Integer.parseInt(scanner.nextLine());

            if (isGameCodeTaken(game_code)) {
                System.out.println("Error: Game code " + game_code + " already exists!");
                return;
            }

            System.out.print("Enter branch code: ");
            String branch_code = scanner.nextLine();

            System.out.print("Enter game name: ");
            String game_name = scanner.nextLine();

            System.out.print("Enter genre: ");
            String genre = scanner.nextLine();

            System.out.print("Enter stock: ");
            int quantity_in_stock = Integer.parseInt(scanner.nextLine());

            System.out.print("Is the game discontinued? (yes/no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            boolean discontinued = input.equals("yes") || input.equals("y") || input.equals("true");

            // try {
            //     System.out.print("Is the game discontinued? (true/false): ");
            //     boolean discontinued = scanner.nextBoolean();
            //     scanner.nextLine(); 
            // } catch (InputMismatchException e) {
            //     scanner.nextLine(); 
            //     System.out.println("Invalid input! Please type true or false.");
            // }

            String sql = "INSERT INTO game (game_code, branch_code, game_name, genre, quantity_in_stock, discontinued) VALUES (?, ?, ?, ?, ?, ?)";

            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, game_code);
                stmt.setString(2, branch_code);
                stmt.setString(3, game_name);
                stmt.setString(4, genre);
                stmt.setInt(5, quantity_in_stock);
                stmt.setBoolean(6, discontinued);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Game added successfully!");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a numeric value.");
        } catch (SQLIntegrityConstraintViolationException e) {
        System.out.println("Error: Either the Branch Code does not exist or the Game Code is already taken.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void edit () {
        try {
            System.out.print("Enter the game code to edit: ");
            int game_code = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter new branch code: ");
            String branch_code = scanner.nextLine();

            System.out.print("Enter new game name: ");
            String game_name = scanner.nextLine();

            System.out.print("Enter new genre: ");
            String genre = scanner.nextLine();

            System.out.print("Enter new stock: ");
            int quantity_in_stock = Integer.parseInt(scanner.nextLine());

            System.out.print("Is the game discontinued? (yes/no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            boolean discontinued = input.equals("yes") || input.equals("y") || input.equals("true");

            // try {
            //     System.out.print("Is the game discontinued? (true/false): ");
            //     boolean discontinued = scanner.nextBoolean();
            //     scanner.nextLine(); 
            // } catch (InputMismatchException e) {
            //     scanner.nextLine(); 
            //     System.out.println("Invalid input! Please type true or false.");
            // }

            String sql = "UPDATE game SET branch_code = ?, game_name = ?, genre = ?, quantity_in_stock = ?, discontinued = ? WHERE game_code = ?";

            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, branch_code);
                stmt.setString(2, game_name);
                stmt.setString(3, genre);
                stmt.setInt(4, quantity_in_stock);
                stmt.setBoolean(5, discontinued);
                stmt.setInt(6, game_code);

                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Game updated successfully!");
                } else {
                    System.out.println("No game found with code: " + game_code);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Update cancelled.");
        } catch (SQLIntegrityConstraintViolationException e) {
        System.out.println("Error: Either the Branch Code does not exist or the Game Code is already taken.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void remove () {
        try {
            System.out.print("Enter the code of the game to remove: ");
            int game_code = Integer.parseInt(scanner.nextLine());

            String sql = "DELETE FROM game WHERE game_code = ?";

            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, game_code);

                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Game removed successfully!");
                } else {
                    System.out.println("No game found with code: " + game_code);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid game code! Please enter a number.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void view () {
        String sql = "";
        int other_choice;

        while (true) {
            System.out.print("\n");
            System.out.println("1. All Games");
            System.out.println("2. Filter by Genre");
            System.out.println("3. Filter by Name");
            System.out.println("4. Go back");
            System.out.print("Enter your choice: ");

            try {
                other_choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }
        
        switch (other_choice) {
            case 1:
                sql = "SELECT * FROM game";
                break;
            case 2:
                sql = "SELECT * FROM game ORDER BY genre";
                break;
            case 3:
                sql = "SELECT * FROM game ORDER BY game_name";
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice!");
                continue;
        }
        break;
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n--- ALL GAMES ---");
            boolean hasRows = false;

            while (rs.next()) {
                hasRows = true;
                int game_code = rs.getInt("game_code");
                String branch_code = rs.getString("branch_code");
                String game_name = rs.getString("game_name");
                String genre = rs.getString("genre");
                int quantity_in_stock = rs.getInt("quantity_in_stock");
                boolean discontinued = rs.getBoolean("discontinued");

                System.out.printf("\nGame Code: %d | Branch Code: %s |  Game Name: %s | Genre: %s | Stock: %d |  Discontinued: %b%n ",
                        game_code, branch_code, game_name, genre, quantity_in_stock, discontinued);
            }

            if (!hasRows) {
                System.out.println("No games found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}