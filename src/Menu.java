import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
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
        String menuButtonStyle = "-fx-padding: 15;\n" +
                "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                "    -fx-background-radius: 10;\n" +
                "    -fx-background-color: \n" +
                "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\n" +
                "        #9d4024,\n" +
                "        #d86e3a,\n" +
                "        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 2em;";

        String pressedButton = "-fx-padding: 15;\n" +
                "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                "    -fx-background-radius: 10;\n" +
                "    -fx-background-color: \n" +
                "        linear-gradient(from 0% 93% to 0% 100%, #aaaaaa 0%, #903b12 100%),\n" +
                "        #9d4024,\n" +
                "        #d86e3a,\n" +
                "        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 2em;";

        String snakeTextStyle = "-fx-font-weight: bold;\n" + "-fx-font-size: 6em;";
        String spaceTextStyle = "-fx-font-size: 1em;";
        Text snakeText = new Text((float) windowsSize / 3, (float) windowsSize / 2, "SNAKE");
        Text space = new Text(" ");
        snakeText.setStyle(snakeTextStyle);
        space.setStyle(spaceTextStyle);

        Button newGame = new Button("New Game");
        Button leaderboard = new Button("Leaderboard");
        Button exit = new Button("Exit");

        ToggleButton easy = new ToggleButton("Easy");
        ToggleButton medium = new ToggleButton("Medium");
        ToggleButton hard = new ToggleButton("Hard");

        newGame.setMinSize(200, 200);

        newGame.setStyle(menuButtonStyle);
        leaderboard.setStyle(menuButtonStyle);
        exit.setStyle(menuButtonStyle);

        easy.setStyle(menuButtonStyle);
        medium.setStyle(menuButtonStyle);
        hard.setStyle(menuButtonStyle);

        newGame.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameStatus(Main.GameStatus.NEWGAME);
            }
        });

        leaderboard.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameStatus(Main.GameStatus.LEADERBOARD);
            }
        });

        exit.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                System.exit(0);
            }
        });

        easy.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                easy.setSelected(true);
                medium.setSelected(false);
                hard.setSelected(false);
                difficulty(Main.Difficulty.EASY);
            }
        });

        medium.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                easy.setSelected(false);
                medium.setSelected(true);
                hard.setSelected(false);
                difficulty(Main.Difficulty.MEDIUM);
            }
        });

        hard.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                easy.setSelected(false);
                medium.setSelected(false);
                hard.setSelected(true);
                difficulty(Main.Difficulty.HARD);
            }
        });

        ToggleGroup tg = new ToggleGroup();
        easy.setToggleGroup(tg);
        medium.setToggleGroup(tg);
        hard.setToggleGroup(tg);

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
