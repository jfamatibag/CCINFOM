package model;

import java.time.LocalDate;

public class Sales {
    private int sale_id;
    private int customer_number;
    private int game_code;
    private String branch_code;
    private LocalDate purchase_date;
    private double amount_paid;
    private String genre;
    private int quantity;

    public Sales (int sale_id, int customer_number, int game_code, String branch_code, LocalDate purchase_date, double amount_paid, String genre, int quantity) {
        this.sale_id = sale_id;
        this.customer_number = customer_number;
        this.game_code = game_code;
        this.branch_code = branch_code;
        this.purchase_date = purchase_date;
        this.amount_paid = amount_paid;
        this.genre = genre;
        this.quantity = quantity;
    }

    public int getSaleId () {
        return sale_id;
    }

    public int getCustomerNumber () {
        return customer_number;
    }

    public int getGameCode () {
        return game_code;
    }

    public String getBranchCode () {
        return branch_code;
    }

    public LocalDate getPurchaseDate () {
        return purchase_date;
    }

    public double getAmountPaid () {
        return amount_paid;
    }

    public String getGenre () {
        return genre;
    }

    public int getQuantity () {
        return quantity;
    }
}