import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * This class represents the snake.
 * We can move it with the arrows, or the WASD keys.
 * The snake has a head and tails which are represented by DrawableUnits.
 */
public class Snake {
    private final ArrayList<DrawableUnit> drawableUnits;
    private final DrawableUnit head;
    private final int windowSize;
    private final Scene scene;
    private final ArrayList<GameStatusListener> gameOverListeners;
    private final GraphicsContext gc;

    private enum dir {
        UP, DOWN, LEFT, RIGHT
    }

    private dir direction;

    /**
     * Constructor of the snake
     *
     * @param head       it's the head of the snake. We can move this, and the tails will follow it
     * @param windowSize the current size of the window
     * @param scene      we want to add object to this scene
     * @param gc         the GraphicsContext that helps us draw objects on the screen
     */
    public Snake(DrawableUnit head, int windowSize, Scene scene, GraphicsContext gc) {
        drawableUnits = new ArrayList<>();
        drawableUnits.add(head);
        this.head = head;
        this.direction = dir.RIGHT;
        this.windowSize = windowSize;
        this.scene = scene;
        gameOverListeners = new ArrayList<>();
        this.gc = gc;
    }

    /**
     * Increases the length of the snake
     *
     * @param t the tail we want to add to the snake
     */
    public void addDrawableUnit(DrawableUnit t) {
        drawableUnits.add(t);
    }

    /**
     * Draws the snake on the screen
     *
     * @param gc the GraphicsContext
     */
    public void drawSnake(GraphicsContext gc) {
        for (int i = 0; i < drawableUnits.size(); i++) {
            // r: 150, g: 0, b: (255, 130); 255-130=125
            gc.setFill(Color.rgb(150, 0, (130 + 125 * i / drawableUnits.size())));
            gc.fillRect(drawableUnits.get(i).getPosX(), drawableUnits.get(i).getPosY(), drawableUnits.get(i).getSize(), drawableUnits.get(i).getSize());
        }
    }

    /**
     * Changes the direction of the snake when a key is pressed
     */
    public void changeDirection() {
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.RIGHT || code == KeyCode.D) {
                if (direction != dir.LEFT)
                    direction = dir.RIGHT;
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                if (direction != dir.RIGHT)
                    direction = dir.LEFT;
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                if (direction != dir.DOWN)
                    direction = dir.UP;
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                if (direction != dir.UP)
                    direction = dir.DOWN;
            }
        });
    }

    /**
     * Moves the snake to the specified direction
     */
    public void moveSnake() {

        for (int i = drawableUnits.size() - 1; i > 0; i--) {
            drawableUnits.get(i).setPosX(drawableUnits.get(i - 1).getPosX());
            drawableUnits.get(i).setPosY(drawableUnits.get(i - 1).getPosY());
        }
        switch (direction) {
            case RIGHT -> {
                if (!(drawableUnits.get(0).getPosX() + 40 < windowSize))
                    gameOver();
                else drawableUnits.get(0).setPosX(drawableUnits.get(0).getPosX() + 40);
            }
            case LEFT -> {
                if (!(drawableUnits.get(0).getPosX() - 40 >= 0))
                    gameOver();
                else drawableUnits.get(0).setPosX(drawableUnits.get(0).getPosX() - 40);
            }
            case UP -> {
                if (!(drawableUnits.get(0).getPosY() - 40 >= 0))
                    gameOver();
                else drawableUnits.get(0).setPosY(drawableUnits.get(0).getPosY() - 40);

            }
            case DOWN -> {
                if (!(drawableUnits.get(0).getPosY() + 40 < windowSize))
                    gameOver();
                else drawableUnits.get(0).setPosY(drawableUnits.get(0).getPosY() + 40);
            }
        }
        drawSnake(gc);

    }

    /**
     * Returns the snake's tails
     *
     * @return drawableUnits
     */
    public ArrayList<DrawableUnit> getDrawableUnits() {
        return drawableUnits;
    }

    /**
     * Returns the head of the snake
     *
     * @return head
     */
    public DrawableUnit head() {
        return head;
    }

    /**
     * Score getter
     *
     * @return score
     */
    public int getScore() {
        return drawableUnits.size() - 1;
    }

    /**
     * Adds the parameter to the gameOverListeners
     *
     * @param gol this is the GameStatusListener that we want to add to the listeners
     */
    public void registerGameOverListener(GameStatusListener gol) {
        gameOverListeners.add(gol);
    }

    /**
     * Calls the gameStatusHandler function on the listeners with the GAMEOVER parameter.
     */
    private void gameOver() {
        for (GameStatusListener gol : gameOverListeners)
            gol.gameStatusHandler(Main.GameStatus.GAMEOVER);
    }
}
