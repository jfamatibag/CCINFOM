package view;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import model.Customer;

public class CustomerView {
    static Scanner scanner = new Scanner(System.in);

    public int displayCustomerMenu () {
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
        System.out.println("1. All Customers");
        System.out.println("2. Sort by Name");
        System.out.println("3. Sort by Membership Points");
        System.out.println("4. Go back");
        System.out.print("Enter your choice: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return -1;
        }
    }

    public int promptCustomerNumber (String message) {
        while (true) {
            System.out.print(message);
            try {
                int customer_number = Integer.parseInt(scanner.nextLine());
                if (customer_number > 0) {
                    return customer_number;
                }
                System.out.println("Customer number must be greater than 0.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    public Customer promptCustomerDetails (int customer_number) {
        System.out.print("Enter last name (leave blank if none): ");
        String last_name = scanner.nextLine().trim();

        String first_name;
        while (true) {
            System.out.print("Enter first name: ");
            first_name = scanner.nextLine().trim();
            if (!first_name.isEmpty()) {
                break;
            }
            System.out.println("First name cannot be empty.");
        }

        LocalDate registration_date;
        while (true) {
            System.out.print("Enter registration date (yyyy-mm-dd): ");
            try {
                registration_date = LocalDate.parse(scanner.nextLine().trim());
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date! Use the format yyyy-mm-dd.");
            }
        }

        int membership_points;
        while (true) {
            System.out.print("Enter membership points: ");
            try {
                membership_points = Integer.parseInt(scanner.nextLine());
                if (membership_points >= 0) {
                    break;
                }
                System.out.println("Points cannot be negative.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a numeric value.");
            }
        }

        return new Customer(customer_number, last_name, first_name, registration_date, membership_points);
    }

    public void displayCustomers (List<Customer> customers) {
        System.out.println("\n--- ALL CUSTOMERS ---");

        if (customers.isEmpty()) {
            System.out.println("No customers found in the database.");
            return;
        }

        for (Customer customer : customers) {
            System.out.printf("Customer Number: %d | Last Name: %s | First Name: %s | Registration Date: %s | Membership Points: %d\n",
                    customer.getCustomerNumber(), customer.getLastName(), customer.getFirstName(), customer.getRegistrationDate(), customer.getMembershipPoints());
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