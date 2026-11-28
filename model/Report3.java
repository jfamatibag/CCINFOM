package model;

public class Report3 {
    private final String genre;
    private final double totalRevenue;
    private final int unitsSold;

    public Report3(String genre, double totalRevenue, int unitsSold) {
        this.genre = genre;
        this.totalRevenue = totalRevenue;
        this.unitsSold = unitsSold;
    }

    public String getGenre() {
    	return genre;
    }
    public double getTotalRevenue() {
    	return totalRevenue;
    }
    public int getUnitsSold() {
    	return unitsSold;
    }
}