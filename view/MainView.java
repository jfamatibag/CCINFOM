package view;

import java.util.*;

public class MainView {
    static Scanner scanner = new Scanner(System.in);

    public int displayMainMenu () {
        System.out.println("\n=== GAME STORE ===");
        System.out.println("1. Branches");
        System.out.println("2. Games");
        System.out.println("3. Customers");
        System.out.println("4. Sales");
        System.out.println("5. Reports");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return -1;
        }
    }

    public void displayExit () {
        System.out.println("Goodbye!");
    }

    public void displayInvalid () {
        System.out.println("Invalid choice!");
    }
}