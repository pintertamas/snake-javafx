import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<DrawableUnit> DrawableUnits;
    private final DrawableUnit head;
    private final int windowSize;
    private final Scene scene;
    private final ArrayList<GameStatusListener> gameOverListeners;
    private final GraphicsContext gc;

    private enum dir {
        UP, DOWN, LEFT, RIGHT
    }

    private dir direction;

    public Snake(DrawableUnit head, int windowSize, Scene scene, GraphicsContext gc) {
        DrawableUnits = new ArrayList<>();
        DrawableUnits.add(head);
        this.head = head;
        this.direction = dir.RIGHT;
        this.windowSize = windowSize;
        this.scene = scene;
        gameOverListeners = new ArrayList<>();
        this.gc = gc;
    }

    public void addDrawableUnit(DrawableUnit t) {
        DrawableUnits.add(t);
    }

    public void drawSnake(GraphicsContext gc) {
        for (int i = 0; i < DrawableUnits.size(); i++) {
            // r: 150, g: 0, b: (255, 130); 255-130=125
            gc.setFill(Color.rgb(150, 0, (130 + 125 * i / DrawableUnits.size())));
            gc.fillRect(DrawableUnits.get(i).getPosX(), DrawableUnits.get(i).getPosY(), DrawableUnits.get(i).getSize(), DrawableUnits.get(i).getSize());
        }
    }

    public void changeDirection() {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
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
            }
        });
    }

    public void moveSnake(Main.GameStatus gameStatus) {
        if (gameStatus != Main.GameStatus.GAMEOVER) {
            for (int i = DrawableUnits.size() - 1; i > 0; i--) {
                DrawableUnits.get(i).setPosX(DrawableUnits.get(i - 1).getPosX());
                DrawableUnits.get(i).setPosY(DrawableUnits.get(i - 1).getPosY());
            }
            switch (direction) {
                case RIGHT -> {
                    if (!(DrawableUnits.get(0).getPosX() + 40 < windowSize))
                        gameStatus(Main.GameStatus.GAMEOVER);
                    else DrawableUnits.get(0).setPosX(DrawableUnits.get(0).getPosX() + 40);
                }
                case LEFT -> {
                    if (!(DrawableUnits.get(0).getPosX() - 40 >= 0))
                        gameStatus(Main.GameStatus.GAMEOVER);
                    else DrawableUnits.get(0).setPosX(DrawableUnits.get(0).getPosX() - 40);
                }
                case UP -> {
                    if (!(DrawableUnits.get(0).getPosY() - 40 >= 0))
                        gameStatus(Main.GameStatus.GAMEOVER);
                    else DrawableUnits.get(0).setPosY(DrawableUnits.get(0).getPosY() - 40);

                }
                case DOWN -> {
                    if (!(DrawableUnits.get(0).getPosY() + 40 < windowSize))
                        gameStatus(Main.GameStatus.GAMEOVER);
                    else DrawableUnits.get(0).setPosY(DrawableUnits.get(0).getPosY() + 40);
                }
            }
            drawSnake(gc);
        }
    }

    public ArrayList<DrawableUnit> getDrawableUnits() {
        return DrawableUnits;
    }

    public DrawableUnit head() {
        return head;
    }

    public int getScore() {
        return DrawableUnits.size() - 1;
    }

    public void registerGameOverListener(GameStatusListener gol) {
        gameOverListeners.add(gol);
    }

    private void gameStatus(Main.GameStatus gs) {
        for (GameStatusListener gol : gameOverListeners)
            gol.gameStatusHandler(gs);
    }
}
