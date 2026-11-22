package controller;

import java.sql.*;
import model.Branch;
import model.BranchManagement;
import view.BranchView;

public class BranchController {

    public static void main (String [] args){
        BranchView branchView = new BranchView();
        BranchManagement branchModel = new BranchManagement();

        while (true) {
            switch(branchView.displayBranchMenu()) {
                case 1:
                    add(branchView, branchModel);
                    break;
                case 2:
                    edit(branchView, branchModel);
                    break;
                case 3:
                    remove(branchView, branchModel);
                    break;
                case 4:
                    view(branchView, branchModel);
                    break;
                case 5:
                    branchView.displayExit();
                    return;
                default:
                    branchView.displayInvalid();
            }
        }
    }

    public static void add (BranchView branchView, BranchManagement branchModel) {
        try {
            String branch_code = branchView.promptBranchCode("Enter branch code: ");

            if (branchModel.isBranchCodeTaken(branch_code)) {
                branchView.displayMessage("Error: Branch code " + branch_code + " already exists!");
                return;
            }

            Branch branch = branchView.promptBranchDetails(branch_code);

            if (branchModel.add(branch)) {
            	branchView.displayMessage("Branch added successfully!");
            } else {
                branchView.displayMessage("Update failed.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            branchView.displayMessage("Error: Cannot remove this branch because it still has games or sales.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void edit (BranchView branchView, BranchManagement branchModel) {
        try {
            String branch_code = branchView.promptBranchCode("Enter the branch code to edit: ");

            if (!branchModel.isBranchCodeTaken(branch_code)) {
            	branchView.displayMessage("No branch found with code: " + branch_code);
                return;
            }

            Branch branch = branchView.promptBranchDetails(branch_code);

            if (branchModel.edit(branch)) {
            	branchView.displayMessage("Branch updated successfully!");
            } else {
                branchView.displayMessage("Update failed.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
        	branchView.displayMessage("Error: Either the there are contradicting Branch details or the Branch Code is already taken.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void remove (BranchView branchView, BranchManagement branchModel) {
        try {
            String branch_code = branchView.promptBranchCode("Enter the code of the branch to remove: ");

            if (branchModel.remove(branch_code)) {
                branchView.displayMessage("Branch removed successfully!");
            } else {
                branchView.displayMessage("No branch found with code: " + branch_code);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void view (BranchView branchView, BranchManagement branchModel) {
        while (true) {
            int other_choice = branchView.displayViewMenu();

            if (other_choice == 4) {
                return;
            }

            if (other_choice < 1 || other_choice > 3) {
            	branchView.displayInvalid();
                continue;
            }

            try {
            	branchView.displayBranches(branchModel.view(other_choice));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}