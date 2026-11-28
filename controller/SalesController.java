package controller;

import java.sql.*;
import model.Sales;
import model.SalesManagement;
import view.SalesView;

public class SalesController {

    public static void main (String [] args){
        SalesView salesView = new SalesView();
        SalesManagement salesModel = new SalesManagement();

        while (true) {
            switch(salesView.displaySalesMenu()) {
                case 1:
                    add(salesView, salesModel);
                    break;
                case 2:
                    edit(salesView, salesModel);
                    break;
                case 3:
                    remove(salesView, salesModel);
                    break;
                case 4:
                    view(salesView, salesModel);
                    break;
                case 5:
                    salesView.displayExit();
                    return;
                default:
                    salesView.displayInvalid();
            }
        }
    }

    public static void add (SalesView salesView, SalesManagement salesModel) {
        try {
            int customer_number = salesView.promptCustomerNumber("Enter customer number: ");

            if (!salesModel.isCustomerExisting(customer_number)) {
                salesView.displayMessage("No customer found with number " + customer_number);
                return;
            }

            int game_code = salesView.promptGameCode("Enter game code: ");
            String branch_code = salesView.promptBranchCode("Enter branch code: ");

            String genre = salesModel.getGameGenre(game_code, branch_code);

            if (genre == null) {
                salesView.displayMessage("No game found with code " + game_code + " in branch " + branch_code);
                return;
            }

            // sale_id is 0 here because the database assigns it
            Sales sale = salesView.promptSaleDetails(0, customer_number, game_code, branch_code, genre);

            if (salesModel.add(sale)) {
                salesView.displayMessage("Sale added successfully!");
            } else {
                salesView.displayMessage("Add failed.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            salesView.displayMessage("Error: The customer, branch, or game does not exist.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void edit (SalesView salesView, SalesManagement salesModel) {
        try {
            int sale_id = salesView.promptSaleId("Enter the sale ID to edit: ");

            if (!salesModel.isSaleIdTaken(sale_id)) {
                salesView.displayMessage("No sale found with ID " + sale_id);
                return;
            }

            int customer_number = salesView.promptCustomerNumber("Enter customer number: ");

            if (!salesModel.isCustomerExisting(customer_number)) {
                salesView.displayMessage("No customer found with number " + customer_number);
                return;
            }

            int game_code = salesView.promptGameCode("Enter game code: ");
            String branch_code = salesView.promptBranchCode("Enter branch code: ");

            String genre = salesModel.getGameGenre(game_code, branch_code);

            if (genre == null) {
                salesView.displayMessage("No game found with code " + game_code + " in branch " + branch_code);
                return;
            }

            Sales sale = salesView.promptSaleDetails(sale_id, customer_number, game_code, branch_code, genre);

            if (salesModel.edit(sale)) {
                salesView.displayMessage("Sale updated successfully!");
            } else {
                salesView.displayMessage("Update failed.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            salesView.displayMessage("Error: The customer, branch, or game does not exist.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void remove (SalesView salesView, SalesManagement salesModel) {
        try {
            int sale_id = salesView.promptSaleId("Enter the ID of the sale to remove: ");

            if (salesModel.remove(sale_id)) {
                salesView.displayMessage("Sale removed successfully!");
            } else {
                salesView.displayMessage("No sale found with ID " + sale_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void view (SalesView salesView, SalesManagement salesModel) {
        while (true) {
            int other_choice = salesView.displayViewMenu();

            if (other_choice == 4) {
                return;
            }

            if (other_choice < 1 || other_choice > 3) {
                salesView.displayInvalid();
                continue;
            }

            try {
                salesView.displaySales(salesModel.view(other_choice));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}