import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * This is the class that represents the game menu.
 */
public class Menu {

    private final ArrayList<GameStatusListener> gameStatusListeners;
    private final ArrayList<DifficultyListener> difficultyListeners;

    /**
     * The constructor creates the listeners.
     */
    public Menu() {
        this.gameStatusListeners = new ArrayList<>();
        this.difficultyListeners = new ArrayList<>();
    }

    /**
     * This function creates the menu and its buttons, and sets their properties/designs them.
     *
     * @param root        the group that will contain the menu.
     * @param windowsSize the current size of the window.
     * @param difficulty  the current difficulty. buttons will appear differently based on this value.
     */
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

    /**
     * When the gameStatus changes in the menu, it adds the parameter to the listeners.
     *
     * @param gsl this is the GameStatusListener that we want to add to the listeners
     */
    public void registerGameOverListener(GameStatusListener gsl) {
        gameStatusListeners.add(gsl);
    }

    /**
     * Calls the gameStatusHandler function on the listeners.
     *
     * @param gs we want to change the game state to this
     */
    private void gameStatus(Main.GameStatus gs) {
        for (GameStatusListener gsl : gameStatusListeners)
            gsl.gameStatusHandler(gs);
    }

    /**
     * When the difficulty changes in the menu, it adds the parameter to the listeners.
     *
     * @param dl this is the DifficultyListener that we want to add to the listeners
     */
    public void registerDifficultyListener(DifficultyListener dl) {
        difficultyListeners.add(dl);
    }

    /**
     * Calls the difficultyHandler function on the listeners.
     *
     * @param difficulty we want to change the difficulty to this
     */
    private void difficulty(Main.Difficulty difficulty) {
        for (DifficultyListener dl : difficultyListeners)
            dl.difficultyHandler(difficulty);
    }
}
