package view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import model.Sales;

public class SalesView {
    static Scanner scanner = new Scanner(System.in);

    public int displaySalesMenu () {
        System.out.println("\n--- Sales Menu ---");
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
    	System.out.println("\n--- Sales View---");
        System.out.println("1. All Sales");
        System.out.println("2. Sort by Date");
        System.out.println("3. Sort by Branch");
        System.out.println("4. Go back");
        System.out.print("Enter your choice: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return -1;
        }
    }

    public int promptSaleId (String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    public int promptCustomerNumber (String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
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

    public String promptBranchCode (String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && input.length() == 4) {
                return input;
            }
            System.out.println("Invalid input! Branch code must be 4 characters.");
        }
    }

    // sale_id is passed in (0 when adding, since the database assigns it)
    public Sales promptSaleDetails (int sale_id, int customer_number, int game_code, String branch_code, String genre) {
        LocalDate purchase_date;
        while (true) {
            System.out.print("Enter purchase date (YYYY-MM-DD): ");
            try {
                purchase_date = LocalDate.parse(scanner.nextLine().trim());
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date! Use the format YYYY-MM-DD.");
            }
        }

        double amount_paid;
        while (true) {
            System.out.print("Enter amount paid: ");
            try {
                amount_paid = Double.parseDouble(scanner.nextLine());
                if (amount_paid < 0) {
                    System.out.println("Amount cannot be negative.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numeric value.");
            }
        }

        int quantity;
        while (true) {
            System.out.print("Enter quantity: ");
            try {
                quantity = Integer.parseInt(scanner.nextLine());
                if (quantity < 1) {
                    System.out.println("Quantity must be at least 1.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numeric value.");
            }
        }

        return new Sales(sale_id, customer_number, game_code, branch_code, purchase_date, amount_paid, genre, quantity);
    }

    public void displaySales (List<Sales> sales) {
        System.out.println("\n--- ALL SALES ---");

        if (sales.isEmpty()) {
            System.out.println("No sales found in the database.");
            return;
        }

        for (Sales sale : sales) {
            System.out.printf("Sale ID: %-4d | Customer: %-4d | Game Code: %-4d | Branch Code: %-4s | Date: %s | Amount: %10.2f | Genre: %-10s | Qty: %-3d\n",
                    sale.getSaleId(), sale.getCustomerNumber(), sale.getGameCode(), sale.getBranchCode(),
                    sale.getPurchaseDate(), sale.getAmountPaid(), sale.getGenre(), sale.getQuantity());
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