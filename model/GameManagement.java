package model;

import java.sql.*;
import java.util.*;
import static connection.DatabaseConnection.getConnection;

public class GameManagement {

	public boolean isGameCodeTaken (int game_code, String branch_code) throws SQLException {
        String sql = "SELECT 1 FROM game WHERE game_code = ? AND branch_code = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, game_code);
            stmt.setString(2, branch_code);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean add (Game game) throws SQLException {
        String sql = "INSERT INTO game (game_code, branch_code, game_name, genre, quantity_in_stock, discontinued) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, game.getGameCode());
            stmt.setString(2, game.getBranchCode());
            stmt.setString(3, game.getGameName());
            stmt.setString(4, game.getGenre());
            stmt.setInt(5, game.getQuantityInStock());
            stmt.setBoolean(6, game.isDiscontinued());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean edit (Game game) throws SQLException {
        String sql = "UPDATE game SET game_name = ?, genre = ?, quantity_in_stock = ?, discontinued = ? WHERE game_code = ? AND branch_code = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, game.getGameName());
            stmt.setString(2, game.getGenre());
            stmt.setInt(3, game.getQuantityInStock());
            stmt.setBoolean(4, game.isDiscontinued());
            stmt.setInt(5, game.getGameCode());
            stmt.setString(6, game.getBranchCode());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean remove (int game_code, String branch_code) throws SQLException {
        String sql = "DELETE FROM game WHERE game_code = ? AND branch_code = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, game_code);
            stmt.setString(2, branch_code);

            return stmt.executeUpdate() > 0;
        }
    }

    public List<Game> view (int other_choice) throws SQLException {
        String sql = "";

        switch (other_choice) {
            case 1:
                sql = "SELECT * FROM game";
                break;
            case 2:
                sql = "SELECT * FROM game ORDER BY genre";
                break;
            case 3:
                sql = "SELECT * FROM game ORDER BY game_name";
                break;
        }

        List<Game> games = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int game_code = rs.getInt("game_code");
                String branch_code = rs.getString("branch_code");
                String game_name = rs.getString("game_name");
                String genre = rs.getString("genre");
                int quantity_in_stock = rs.getInt("quantity_in_stock");
                boolean discontinued = rs.getBoolean("discontinued");

                games.add(new Game(game_code, branch_code, game_name, genre, quantity_in_stock, discontinued));
            }
        }

        return games;
    }
}