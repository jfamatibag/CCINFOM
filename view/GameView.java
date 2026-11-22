package view;

import java.util.*;
import model.Game;

public class GameView {
    static Scanner scanner = new Scanner(System.in);

    public int displayGameMenu () {
        System.out.print("\n");
        System.out.println("1. Add");
        System.out.println("2. Edit");
        System.out.println("3. Remove");
        System.out.println("4. View");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return -1;
        }
    }

    public int displayViewMenu () {
        System.out.print("\n");
        System.out.println("1. All Games");
        System.out.println("2. Filter by Genre");
        System.out.println("3. Filter by Name");
        System.out.println("4. Go back");
        System.out.print("Enter your choice: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return -1;
        }
    }

    public int promptGameCode (String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    public Game promptGameDetails (int game_code) {
        System.out.print("Enter branch code: ");
        String branch_code = scanner.nextLine();

        System.out.print("Enter game name: ");
        String game_name = scanner.nextLine();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        int quantity_in_stock;
        while (true) {
            System.out.print("Enter stock: ");
            try {
                quantity_in_stock = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numeric value.");
            }
        }

        System.out.print("Is the game discontinued? (yes/no): ");
        String input = scanner.nextLine().trim().toLowerCase();
        boolean discontinued = input.equals("yes") || input.equals("y") || input.equals("true");

        return new Game(game_code, branch_code, game_name, genre, quantity_in_stock, discontinued);
    }

    public void displayGames (List<Game> games) {
        System.out.println("\n--- ALL GAMES ---");

        if (games.isEmpty()) {
            System.out.println("No games found in the database.");
            return;
        }
        
        for (Game game : games) {
            System.out.printf("Game Code: %d | Branch Code: %s |  Game Name: %s | Genre: %s | Stock: %d |  Discontinued: %b\n",
                    game.getGameCode(), game.getBranchCode(), game.getGameName(), game.getGenre(), game.getQuantityInStock(), game.isDiscontinued());
        }
    }

    public void displayMessage (String message) {
        System.out.println(message);
    }

    public void displayExit () {
        System.out.println("Goodbye!");
    }

    public void displayInvalid () {
        System.out.println("Invalid choice!");
    }
}