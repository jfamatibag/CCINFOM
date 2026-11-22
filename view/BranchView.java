package view;

import java.util.*;
import model.Branch;

public class BranchView {
    static Scanner scanner = new Scanner(System.in);

    public int displayBranchMenu () {
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
        System.out.println("1. All Branches");
        System.out.println("2. Filter by Name");
        System.out.println("3. Filter by City");
        System.out.println("4. Go back");
        System.out.print("Enter your choice: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return -1;
        }
    }

    public String promptBranchCode(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && input.length() == 4) {
                return input;
            }
            System.out.println("Invalid input! Branch code must be 4 characters.");
        }
    }

    public Branch promptBranchDetails (String branch_code) {
        System.out.print("Enter branch name: ");
        String branch_name = scanner.nextLine();

        System.out.print("Enter street address: ");
        String street_address = scanner.nextLine();

        System.out.print("Enter city: ");
        String city = scanner.nextLine();

        return new Branch(branch_code, branch_name, street_address, city);
    }

    public void displayBranches (List<Branch> branches) {
        System.out.println("\n--- ALL BRANCHES ---");

        if (branches.isEmpty()) {
            System.out.println("No branches found in the database.");
            return;
        }

        for (Branch branch : branches) {
            System.out.printf("Branch Code: %s | Branch Name: %s | Street Address: %s | City: %s\n",
                    branch.getBranchCode(), branch.getBranchName(), branch.getStreetAddress(), branch.getCity());
        }
    }

    public void displayMessage (String message) {
        System.out.println(message);
    }

    public void displayExit () {
        System.out.println("Returning...");
    }

    public void displayInvalid () {
        System.out.println("Invalid choice!");
    }
}