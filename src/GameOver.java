import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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
        String buttonStyle = "-fx-padding: 8 15 15 15;\n" +
                "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                "    -fx-background-radius: 8;\n" +
                "    -fx-background-color: \n" +
                "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\n" +
                "        #9d4024,\n" +
                "        #d86e3a,\n" +
                "        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 1.1em;";

        Button menu = new Button("Menu");
        menu.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameStatus(Main.GameStatus.MENU);
            }
        });
        menu.setStyle(buttonStyle);
        menu.setMinSize(100, 100);

        gameOver.setStyle(gameOverStyle);
        root.getChildren().addAll(gameOver, menu);
    }

    public void registerGameStatusListener(GameStatusListener gsl) {
        gameStatusListeners.add(gsl);
    }

    private void gameStatus(Main.GameStatus gs) {
        for (GameStatusListener gol : gameStatusListeners)
            gol.gameStatusHandler(gs);
    }
}
