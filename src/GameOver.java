import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class GameOver {

    private final ArrayList<GameStatusListener> gameStatusListeners;

    public GameOver() {
        gameStatusListeners = new ArrayList<>();
    }

    public void drawGameOverScreen(Group root, int gameWindowSize) {
        Text gameOver = new Text((float) gameWindowSize / 3, (float) gameWindowSize / 2, "GAME OVER!");

        Button menu = new Button("Menu");
        menu.setOnMousePressed(mouseEvent -> gameStatus());
        menu.setId("button");
        gameOver.setId("menu-text");
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

    private void gameStatus() {
        for (GameStatusListener gol : gameStatusListeners)
            gol.gameStatusHandler(Main.GameStatus.MENU);
    }
}
