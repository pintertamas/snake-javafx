import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
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

    public void createMenu(Group root, int windowsSize) {
        Text snakeText = new Text((float) windowsSize / 3, (float) windowsSize / 2, "SNAKE");
        Text space = new Text(" ");
        snakeText.setId("snake-text-style");
        space.setId("space");

        ToggleGroup tg = new ToggleGroup();

        Button newGame = new Button("New Game");
        Button leaderboard = new Button("Leaderboard");
        Button exit = new Button("Exit");

        ToggleButton easy = new ToggleButton("Easy");
        ToggleButton medium = new ToggleButton("Medium");
        ToggleButton hard = new ToggleButton("Hard");

        easy.setSelected(true);

        easy.setToggleGroup(tg);
        medium.setToggleGroup(tg);
        hard.setToggleGroup(tg);

        tg.selectedToggleProperty().addListener((ov, toggle, new_toggle) -> {
            System.out.println(tg.getSelectedToggle());
            tg.getSelectedToggle().setSelected(true);
            toggle.setSelected(true);
            new_toggle.setSelected(true);
            System.out.println(tg.getSelectedToggle());

        });

        newGame.setMinSize(200, 200);

        newGame.setOnMousePressed(mouseEvent -> gameStatus(Main.GameStatus.NEWGAME));

        leaderboard.setOnMousePressed(mouseEvent -> gameStatus(Main.GameStatus.LEADERBOARD));

        exit.setOnMousePressed(mouseEvent -> System.exit(0));

        easy.setOnMousePressed(mouseEvent -> difficulty(Main.Difficulty.EASY));

        medium.setOnMousePressed(mouseEvent -> difficulty(Main.Difficulty.MEDIUM));

        hard.setOnMousePressed(mouseEvent -> difficulty(Main.Difficulty.HARD));

        newGame.setId("button");
        leaderboard.setId("button");
        exit.setId("button");
        /*easy.setId("button");
        medium.setId("button");
        hard.setId("button");*/

        HBox difficultyBP = new HBox(25);
        difficultyBP.setAlignment(Pos.CENTER);
        difficultyBP.getChildren().addAll(easy, medium, hard);

        VBox menuGroup = new VBox(25);
        menuGroup.getChildren().addAll(snakeText, space, newGame, difficultyBP, leaderboard, exit);
        menuGroup.setMinWidth((float) windowsSize / 2);
        menuGroup.setLayoutX((float) windowsSize / 2 - menuGroup.getMinWidth() / 2);
        menuGroup.setLayoutY((float) windowsSize / 7);
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
