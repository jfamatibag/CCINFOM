package view;

import java.util.*;
import model.Report1;
import model.Report2;
import model.Report3;
import model.Report4;

public class ReportView {
    static Scanner scanner = new Scanner(System.in);

    public int displayReportMenu () {
        System.out.println("\n--- Report Menu ---");
        System.out.println("Please select an option:");
        System.out.println("1. Generate Monthly Revenue Report per Branch");
        System.out.println("2. Generate Monthly Discontinued Games Stock Report per Branch");
        System.out.println("3. Generate Monthly Sales Report by Genre");
        System.out.println("4. Generate Monthly Membership Points Report per Branch");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input! Please enter a number.");
            return -1;
        }
    }

   

    public String promptMonth(String message) {
        while(true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Please enter a month.");
        }
    }


    public void displayListReport1 (List<Report1> reports, int monthNumber) {
        System.out.println("\nGenerating Monthly Revenue Report per Branch...");

        if (reports.isEmpty()) {
            System.out.println("No reports found in the database.");
            return;
        }
        
        System.out.println("\n--- MONTHLY REVENUE REPORT PER BRANCH (MONTH " + monthNumber + ") ---");
        for (Report1 report : reports) {
            System.out.printf("Branch Code: %-4s | Branch Name: %-24s | Total Paid: %.2f%n",
                    report.getBranchCode(), report.getBranchName(), report.getTotalPaid());
        }
    }
    
    public void displayListReport2 (List<Report2> reports, int monthNumber) {
        System.out.println("\nGenerating Monthly Discontinued Games Stock Report per Branch...");

        if (reports.isEmpty()) {
            System.out.println("No reports found in the database.");
            return;
        }
        
        System.out.println("\n--- DISCONTINUED TITLES BY BRANCH (MONTH " + monthNumber + ") ---");
        for (Report2 report : reports) {
            System.out.printf("Branch: %-24s (%-4s) | Game [%-4d]: %-18s | Remaining Stock: %4d | Sold in Month: %4d%n",
            		report.getBranchName(), report.getBranchCode(), report.getGameCode(), 
            		report.getGameName(), report.getRemainingInventory(), report.getUnitsSold());
        }
    }
    
    public void displayListReport3 (List<Report3> reports, int monthNumber) {
        System.out.println("\nGenerating Monthly Sales Report by Genre...");
        
        if (reports.isEmpty()) {
            System.out.println("No reports found in the database.");
            return;
        }
        
        System.out.println("\n--- MONTHLY SALES BY GENRE (MONTH " + monthNumber + ") ---");
        for (Report3 report : reports) {
            System.out.printf("Genre: %-15s | Units Sold: %5d | Total Revenue: %.2f%n",
            		report.getGenre(), report.getUnitsSold(), report.getTotalRevenue());
        }
    }
    
    public void displayListReport4 (List<Report4> reports) {
        System.out.println("\nGenerating Monthly Membership Points Report per Branch...");

        if (reports.isEmpty()) {
            System.out.println("No reports found in the database.");
            return;
        }

        for (Report4 report : reports) {
        	System.out.printf("Branch: %-24s (%-4s) | Rank #%3d | Customer [%d]: %-18s %-18s | Points: %4d%n",
        			report.getBranchName(), report.getBranchCode(), report.getRank(), report.getCustomerNumber(),
        			report.getFirstName(), report.getLastName(), report.getMembershipPoints());
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