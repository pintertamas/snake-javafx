import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

/**
 * A class that represents the game over screen.
 */
public class GameOver {

    private final ArrayList<GameStatusListener> gameStatusListeners;

    /**
     * Constructor that sets up the gameStatusListeners
     */
    public GameOver() {
        gameStatusListeners = new ArrayList<>();
    }

    /**
     * It displays a Text that says "GAME OVER!", and a button that will navigate the user back to the menu.
     *
     * @param root           the Group that will contain the game over screen
     * @param gameWindowSize the current window size
     */
    public void drawGameOverScreen(Group root, int gameWindowSize) {
        Text gameOver = new Text((float) gameWindowSize / 3, (float) gameWindowSize / 2, "GAME OVER!");

        Button menu = new Button("Menu");
        menu.setOnMousePressed(mouseEvent -> gameMenu());
        menu.setId("button");
        gameOver.setId("menu-text");
        VBox gameOverGroup = new VBox(100);
        gameOverGroup.getChildren().addAll(gameOver, menu);
        gameOverGroup.setMinWidth((float) gameWindowSize / 3);
        gameOverGroup.setLayoutX((float) gameWindowSize / 2 - gameOverGroup.getMinWidth() / 2);
        gameOverGroup.setLayoutY((float) gameWindowSize / 7);
        gameOverGroup.setAlignment(Pos.CENTER);
        root.getChildren().add(gameOverGroup);
    }

    /**
     * Adds the parameter to the gameStatusListeners
     *
     * @param gsl this is the GameStatusListener that we want to add to the listeners
     */
    public void registerGameStatusListener(GameStatusListener gsl) {
        gameStatusListeners.add(gsl);
    }

    /**
     * Calls the gameStatusHandler function on the listeners with the MENU parameter.
     */
    private void gameMenu() {
        for (GameStatusListener gol : gameStatusListeners)
            gol.gameStatusHandler(Main.GameStatus.MENU);
    }
}
