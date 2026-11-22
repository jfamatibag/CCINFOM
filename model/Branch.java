package model;

public class Branch {
    private String branch_code;
    private String branch_name;
    private String street_address;
    private String city;

    public Branch (String branch_code, String branch_name, String street_address, String city) {
        this.branch_code = branch_code;
        this.branch_name = branch_name;
        this.street_address = street_address;
        this.city = city;
    }

	public String getBranchCode() {
		return branch_code;
	}

	public String getBranchName() {
		return branch_name;
	}

	public String getStreetAddress() {
		return street_address;
	}

	public String getCity() {
		return city;
	}    
}