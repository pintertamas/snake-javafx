import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class Menu extends Thread {

    private final ArrayList<GameStatusListener> gameOverListeners;
    private final ArrayList<DifficultyListener> difficultyListeners;

    public Menu(Group root) {
        this.gameOverListeners = new ArrayList<>();
        this.difficultyListeners = new ArrayList<>();
    }

    public void createMenu(Group root) {
        MenuBar menuBar = new MenuBar();
        javafx.scene.control.Menu menu = new javafx.scene.control.Menu("Menu");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameStatus(Main.GameStatus.NEWGAME);
            }
        });
        MenuItem leaderboard = new MenuItem("Leaderboard");
        leaderboard.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameStatus(Main.GameStatus.LEADERBOARD);
            }
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        menu.getItems().addAll(newGame, leaderboard, exit);

        javafx.scene.control.Menu menuDiff = new javafx.scene.control.Menu("Difficulty");
        MenuItem easy = new MenuItem("Easy");
        easy.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                difficulty(Main.Difficulty.EASY);
                gameStatus(Main.GameStatus.NEWGAME);
            }
        });
        MenuItem medium = new MenuItem("Medium");
        medium.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                difficulty(Main.Difficulty.MEDIUM);
                gameStatus(Main.GameStatus.NEWGAME);
            }
        });
        MenuItem hard = new MenuItem("Hard");
        hard.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                difficulty(Main.Difficulty.HARD);
                gameStatus(Main.GameStatus.NEWGAME);
            }
        });
        menuDiff.getItems().addAll(easy, medium, hard);

        menuBar.getMenus().addAll(menu, menuDiff);
        root.getChildren().add(menuBar);
    }

    public void registerGameOverListener(GameStatusListener gol) {
        gameOverListeners.add(gol);
    }

    private void gameStatus(Main.GameStatus gs) {
        for (GameStatusListener gol : gameOverListeners)
            gol.gameStatusHandler(gs);
    }

    public void registerDifficultyListener(DifficultyListener dl) {
        difficultyListeners.add(dl);
    }

    private void difficulty(Main.Difficulty difficulty) {
        for (DifficultyListener dl : difficultyListeners)
            dl.difficultyHandler(difficulty);
    }
}
