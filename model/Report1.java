package model;

public class Report1 {
	private final String branchCode;
	private final String branchName;
	private final double totalPaid;

	public Report1(String branchCode, String branchName, double totalPaid) {
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.totalPaid = totalPaid;
    }

	public String getBranchCode() {
		return branchCode;
	}
	public String getBranchName() {
		return branchName;
	}
	public double getTotalPaid() {
		return totalPaid;
	}
}