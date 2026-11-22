package controller;

import java.sql.*;
import model.Customer;
import model.CustomerManagement;
import view.CustomerView;

public class CustomerController {

    public static void main (String [] args){
        CustomerView customerView = new CustomerView();
        CustomerManagement customerModel = new CustomerManagement();

        while (true) {
            switch(customerView.displayCustomerMenu()) {
                case 1:
                    add(customerView, customerModel);
                    break;
                case 2:
                    edit(customerView, customerModel);
                    break;
                case 3:
                    remove(customerView, customerModel);
                    break;
                case 4:
                    view(customerView, customerModel);
                    break;
                case 5:
                    customerView.displayExit();
                    return;
                default:
                    customerView.displayInvalid();
            }
        }
    }

    public static void add (CustomerView customerView, CustomerManagement customerModel) {
        try {
            int customer_number = customerView.promptCustomerNumber("Enter customer number: ");

            if (customerModel.isCustomerNumberTaken(customer_number)) {
                customerView.displayMessage("Error: Customer number " + customer_number + " already exists!");
                return;
            }

            Customer customer = customerView.promptCustomerDetails(customer_number);

            if (customerModel.add(customer)) {
                customerView.displayMessage("Customer added successfully!");
            } else {
                customerView.displayMessage("Add failed.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            customerView.displayMessage("Error: Either there are missing Customer details or the Customer Number is already taken.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void edit (CustomerView customerView, CustomerManagement customerModel) {
        try {
            int customer_number = customerView.promptCustomerNumber("Enter the customer number to edit: ");

            if (!customerModel.isCustomerNumberTaken(customer_number)) {
                customerView.displayMessage("No customer found with number: " + customer_number);
                return;
            }

            Customer customer = customerView.promptCustomerDetails(customer_number);

            if (customerModel.edit(customer)) {
                customerView.displayMessage("Customer updated successfully!");
            } else {
                customerView.displayMessage("Update failed.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            customerView.displayMessage("Error: There are contradicting Customer details.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void remove (CustomerView customerView, CustomerManagement customerModel) {
        try {
            int customer_number = customerView.promptCustomerNumber("Enter the number of the customer to remove: ");

            if (customerModel.remove(customer_number)) {
                customerView.displayMessage("Customer removed successfully!");
            } else {
                customerView.displayMessage("No customer found with number: " + customer_number);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            customerView.displayMessage("Error: Cannot remove this customer because they have sales records.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void view (CustomerView customerView, CustomerManagement customerModel) {
        while (true) {
            int other_choice = customerView.displayViewMenu();

            if (other_choice == 4) {
                return;
            }

            if (other_choice < 1 || other_choice > 3) {
                customerView.displayInvalid();
                continue;
            }

            try {
                customerView.displayCustomers(customerModel.view(other_choice));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}