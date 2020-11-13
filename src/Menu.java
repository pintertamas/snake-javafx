import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Menu {

    private final ArrayList<GameStatusListener> gameStatusListeners;
    private final ArrayList<DifficultyListener> difficultyListeners;

    public Menu() {
        this.gameStatusListeners = new ArrayList<>();
        this.difficultyListeners = new ArrayList<>();
    }

    public void createMenu(Group root, int windowsSize, Main.Difficulty difficulty) {
        Text snakeText = new Text((float) windowsSize / 3, (float) windowsSize / 2, "SNAKE");
        Text space = new Text(" ");
        snakeText.setId("snake-text");
        space.setId("space");

        Button newGame = new Button("New Game");
        Button leaderboard = new Button("Leaderboard");
        Button exit = new Button("Exit");

        Button easy = new Button("Easy");
        Button medium = new Button("Medium");
        Button hard = new Button("Hard");

        newGame.setId("button");
        leaderboard.setId("button");
        exit.setId("button");

        easy.setId("button");
        medium.setId("button");
        hard.setId("button");

        newGame.setMinSize(200, 200);

        newGame.setOnMousePressed(mouseEvent -> gameStatus(Main.GameStatus.NEWGAME));

        leaderboard.setOnMousePressed(mouseEvent -> gameStatus(Main.GameStatus.LEADERBOARD));

        exit.setOnMousePressed(mouseEvent -> System.exit(0));

        easy.setOnMousePressed(mouseEvent -> difficulty(Main.Difficulty.EASY));

        medium.setOnMousePressed(mouseEvent -> difficulty(Main.Difficulty.MEDIUM));

        hard.setOnMousePressed(mouseEvent -> difficulty(Main.Difficulty.HARD));

        easy.setId(difficulty == Main.Difficulty.EASY ? "button-selected" : "button");
        medium.setId(difficulty == Main.Difficulty.MEDIUM ? "button-selected" : "button");
        hard.setId(difficulty == Main.Difficulty.HARD ? "button-selected" : "button");

        HBox difficultyBP = new HBox(25);
        difficultyBP.setAlignment(Pos.CENTER);
        difficultyBP.getChildren().addAll(easy, medium, hard);

        VBox menuGroup = new VBox(25);
        menuGroup.getChildren().addAll(snakeText, space, newGame, difficultyBP, leaderboard, exit);
        menuGroup.setMinWidth((float) windowsSize / 2);
        menuGroup.setLayoutX((float) windowsSize / 2 - menuGroup.getMinWidth() / 2);
        menuGroup.setLayoutY((float) windowsSize / 10);
        menuGroup.setAlignment(Pos.CENTER);
        root.getChildren().addAll(menuGroup);
    }

    public void registerGameOverListener(GameStatusListener gsl) {
        gameStatusListeners.add(gsl);
    }

    private void gameStatus(Main.GameStatus gs) {
        for (GameStatusListener gsl : gameStatusListeners)
            gsl.gameStatusHandler(gs);
    }

    public void registerDifficultyListener(DifficultyListener dl) {
        difficultyListeners.add(dl);
    }

    private void difficulty(Main.Difficulty difficulty) {
        for (DifficultyListener dl : difficultyListeners)
            dl.difficultyHandler(difficulty);
    }
}
