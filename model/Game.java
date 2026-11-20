package model;

public class Game {
    private int game_code;
    private String branch_code;
    private String game_name;
    private String genre;
    private int quantity_in_stock;
    private boolean discontinued;

    public Game (int game_code, String branch_code, String game_name, String genre, int quantity_in_stock, boolean discontinued) {
        this.game_code = game_code;
        this.branch_code = branch_code;
        this.game_name = game_name;
        this.genre = genre;
        this.quantity_in_stock = quantity_in_stock;
        this.discontinued = discontinued;
    }

    public int getGameCode () {
        return game_code;
    }

    public String getBranchCode () {
        return branch_code;
    }

    public String getGameName () {
        return game_name;
    }

    public String getGenre () {
        return genre;
    }

    public int getQuantityInStock () {
        return quantity_in_stock;
    }

    public boolean isDiscontinued () {
        return discontinued;
    }
}