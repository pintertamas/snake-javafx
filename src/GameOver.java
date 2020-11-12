import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class GameOver {

    private final ArrayList<GameStatusListener> gameStatusListeners;

    public GameOver() {
        gameStatusListeners = new ArrayList<>();
    }

    public void drawGameOverScreen(Group root, int gameWindowSize) {
        String gameOverStyle = "-fx-font-weight: bold;\n" + "-fx-font-size: 4em;";
        Text gameOver = new Text((float) gameWindowSize / 3, (float) gameWindowSize / 2, "GAME OVER!");
        String buttonStyle = "-fx-padding: 15;\n" +
                "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                "    -fx-background-radius: 10;\n" +
                "    -fx-background-color: \n" +
                "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\n" +
                "        #9d4024,\n" +
                "        #d86e3a,\n" +
                "        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 2em;";

        Button menu = new Button("Menu");
        menu.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameStatus(Main.GameStatus.MENU);
            }
        });
        menu.setStyle(buttonStyle);
        gameOver.setStyle(gameOverStyle);
        VBox gameOverGroup = new VBox(100);
        gameOverGroup.getChildren().addAll(gameOver, menu);
        gameOverGroup.setMinWidth((float)gameWindowSize/3);
        gameOverGroup.setLayoutX((float)gameWindowSize/2 - gameOverGroup.getMinWidth()/2);
        gameOverGroup.setLayoutY((float)gameWindowSize/7);
        gameOverGroup.setAlignment(Pos.CENTER);
        root.getChildren().add(gameOverGroup);
    }

    public void registerGameStatusListener(GameStatusListener gsl) {
        gameStatusListeners.add(gsl);
    }

    private void gameStatus(Main.GameStatus gs) {
        for (GameStatusListener gol : gameStatusListeners)
            gol.gameStatusHandler(gs);
    }
}
