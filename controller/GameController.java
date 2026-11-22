package controller;

import java.sql.*;
import model.Game;
import model.GameManagement;
import view.GameView;

public class GameController {

    public static void main (String [] args){
        GameView gameView = new GameView();
        GameManagement gameModel = new GameManagement();

        while (true) {
            switch(gameView.displayGameMenu()) {
                case 1:
                    add(gameView, gameModel);
                    break;
                case 2:
                    edit(gameView, gameModel);
                    break;
                case 3:
                    remove(gameView, gameModel);
                    break;
                case 4:
                    view(gameView, gameModel);
                    break;
                case 5:
                    gameView.displayExit();
                    return;
                default:
                    gameView.displayInvalid();
            }
        }
    }

    public static void add (GameView gameView, GameManagement gameModel) {
        try {
            int game_code = gameView.promptGameCode("Enter game code: ");
            String branch_code = gameView.promptBranchCode("Enter branch code: ");

            if (gameModel.isGameCodeTaken(game_code, branch_code)) {
                gameView.displayMessage("Error: Game code " + game_code + " already exists in branch " + branch_code + "!");
                return;
            }

            Game game = gameView.promptGameDetails(game_code, branch_code);

            if (gameModel.add(game)) {
                gameView.displayMessage("Game added successfully!");
            } else {
                gameView.displayMessage("Add failed.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            gameView.displayMessage("Error: Either the Branch Code does not exist or the Game Code is already taken.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void edit (GameView gameView, GameManagement gameModel) {
        try {
            int game_code = gameView.promptGameCode("Enter the game code to edit: ");
            String branch_code = gameView.promptBranchCode("Enter the branch code of the game to edit: ");

            if (!gameModel.isGameCodeTaken(game_code, branch_code)) {
                gameView.displayMessage("No game found with code " + game_code + " in branch " + branch_code);
                return;
            }

            Game game = gameView.promptGameDetails(game_code, branch_code);

            if (gameModel.edit(game)) {
                gameView.displayMessage("Game updated successfully!");
            } else {
                gameView.displayMessage("Update failed.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            gameView.displayMessage("Error: Either the Branch Code does not exist or the Game Code is already taken.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void remove (GameView gameView, GameManagement gameModel) {
        try {
            int game_code = gameView.promptGameCode("Enter the code of the game to remove: ");
            String branch_code = gameView.promptBranchCode("Enter the branch code to remove it from: ");

            if (gameModel.remove(game_code, branch_code)) {
                gameView.displayMessage("Game removed successfully!");
            } else {
                gameView.displayMessage("No game found with code " + game_code + " in branch " + branch_code);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            gameView.displayMessage("Error: Cannot remove this game because it has sales records.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void view (GameView gameView, GameManagement gameModel) {
        while (true) {
            int other_choice = gameView.displayViewMenu();

            if (other_choice == 4) {
                return;
            }

            if (other_choice < 1 || other_choice > 3) {
                gameView.displayInvalid();
                continue;
            }

            try {
                gameView.displayGames(gameModel.view(other_choice));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }
    }
}