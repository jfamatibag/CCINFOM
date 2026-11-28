package model;

public class Report4 {
    private final String branchCode;
    private final String branchName;
    private final int rank;
    private final int customerNumber;
    private final String firstName;
    private final String lastName;
    private final int membershipPoints;

    public Report4(String branchCode, String branchName, int rank, int customerNumber,
                   String firstName, String lastName, int membershipPoints) {
        this.branchCode = branchCode;
        this.branchName = branchName;
        this.rank = rank;
        this.customerNumber = customerNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.membershipPoints = membershipPoints;
    }

    public String getBranchCode() {
    	return branchCode;
    }
    public String getBranchName() {
    	return branchName;
    }
    public int getRank() {
    	return rank;
    }
    public int getCustomerNumber() {
    	return customerNumber;
    }
    public String getFirstName() {
    	return firstName;
    }
    public String getLastName() {
    	return lastName;
    }
    public int getMembershipPoints() {
    	return membershipPoints;
    }
}

