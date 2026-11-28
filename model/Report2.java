package model;

public class Report2 {
	private final String branchCode;
	private final String branchName;
	private final int gameCode;
	private final String gameName;
	private final int remainingInventory;
	private final int unitsSold;

	public Report2(String branchCode, String branchName, int gameCode,
				   String gameName, int remainingInventory, int unitsSold) {
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.gameCode = gameCode;
		this.gameName = gameName;
		this.remainingInventory = remainingInventory;
		this.unitsSold = unitsSold;
    }

    public String getBranchCode() {
    	return branchCode;
    }
    public String getBranchName() {
    	return branchName;
    }
    public int getGameCode() {
    	return gameCode;
    }
    public String getGameName() {
    	return gameName;
    }
    public int getRemainingInventory() {
    	return remainingInventory;
    }
    public int getUnitsSold() {
    	return unitsSold;
    }
}
