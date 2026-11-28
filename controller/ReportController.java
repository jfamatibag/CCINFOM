package controller;

import java.sql.*;
import model.ReportManagement;
import view.ReportView;

public class ReportController {

    public static void main (String [] args){
        ReportView reportView = new ReportView();
        ReportManagement reportModel = new ReportManagement();

        while (true) {
        	switch (reportView.displayReportMenu()) {
	            case 1:
	                generateReport1(reportView, reportModel);
	                break;
	            case 2:
	                generateReport2(reportView, reportModel);
	                break;
	            case 3:
	                generateReport3(reportView, reportModel);
	                break;
	            case 4:
	                generateReport4(reportView, reportModel);
	                break;
	            case 5:
	            	reportView.displayExit();
                    return;
                default:
                	reportView.displayInvalid();
        	}
        }
    }

    public static void generateReport1 (ReportView reportView, ReportManagement reportModel) {
    	while (true) {
    		int month = reportModel.getMonthNumber(reportView.promptMonth("Month of: "));

    		if (month == -1) {
            	reportView.displayMessage("Provided an invalid month.");
                return;
            }

    		try {
    			reportView.displayListReport1(reportModel.getMonthlyRevenuePerBranch(month), month);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
    }

    public static void generateReport2 (ReportView reportView, ReportManagement reportModel) {
    	while (true) {
    		int month = reportModel.getMonthNumber(reportView.promptMonth("Month of: "));

    		if (month == -1) {
            	reportView.displayMessage("Provided an invalid month.");
                return;
            }

    		try {
    			reportView.displayListReport2(reportModel.getDiscontinuedGamesStockPerBranch(month), month);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
    }
    
    public static void generateReport3 (ReportView reportView, ReportManagement reportModel) {
    	while (true) {
    		int month = reportModel.getMonthNumber(reportView.promptMonth("Month of: "));

    		if (month == -1) {
            	reportView.displayMessage("Provided an invalid month.");
                return;
            }

    		try {
    			reportView.displayListReport3(reportModel.getMonthlySalesByGenre(month), month);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
    }
    
    public static void generateReport4 (ReportView reportView, ReportManagement reportModel) {
    	while (true) {
    		int month = reportModel.getMonthNumber(reportView.promptMonth("Month of: "));

    		if (month == -1) {
            	reportView.displayMessage("Provided an invalid month.");
                return;
            }

    		try {
    			reportView.displayListReport4(reportModel.getMembershipPointsPerBranch(month));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}