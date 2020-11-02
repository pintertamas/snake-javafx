import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Snake {
    private final ArrayList<Tail> tails;
    private final Tail head;
    private final int windowSize;
    private final Scene scene;
    private final GraphicsContext gc;

    private enum dir {
        UP, DOWN, LEFT, RIGHT
    }

    private dir direction;

    public Snake(Tail head, int windowSize, Scene scene, GraphicsContext gc) {
        tails = new ArrayList<>();
        tails.add(head);
        this.head = head;
        this.direction = dir.RIGHT;
        this.windowSize = windowSize;
        this.scene = scene;
        this.gc = gc;
    }

    public void addTail(Tail t) {
        tails.add(t);
    }

    public void drawSnake(GraphicsContext gc) {
        for (int i = 0; i < tails.size(); i++) {
            // r: 150, g: 0, b: (255, 130); 255-130=125
            gc.setFill(Color.rgb(150, 0, (130 + 125 * i / tails.size())));
            gc.fillRect(tails.get(i).getPosX(), tails.get(i).getPosY(), tails.get(i).getSizeX(), tails.get(i).getSizeY());
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
        for (int i = tails.size() - 1; i >= 1; i--) {
            tails.get(i).setPosX(tails.get(i - 1).getPosX());
            tails.get(i).setPosY(tails.get(i - 1).getPosY());
        }
        switch (direction) {
            case RIGHT -> {
                if (tails.get(0).getPosX() + 40 <= windowSize - 40)
                    tails.get(0).setPosX(tails.get(0).getPosX() + 40);
                else gameStatus = Main.GameStatus.GAMEOVER;
                System.out.println(gameStatus);
            }
            case LEFT -> {
                if (tails.get(0).getPosX() - 40 >= 0)
                    tails.get(0).setPosX(tails.get(0).getPosX() - 40);
                else gameStatus = Main.GameStatus.GAMEOVER;
                System.out.println(gameStatus);
            }
            case UP -> {
                if (tails.get(0).getPosY() - 40 >= 0)
                    tails.get(0).setPosY(tails.get(0).getPosY() - 40);
                else gameStatus = Main.GameStatus.GAMEOVER;
                System.out.println(gameStatus);
            }
            case DOWN -> {
                if (tails.get(0).getPosY() + 40 <= windowSize - 40)
                    tails.get(0).setPosY(tails.get(0).getPosY() + 40);
                else gameStatus = Main.GameStatus.GAMEOVER;
                System.out.println(gameStatus);
            }
        }
        drawSnake(gc);

    }

    public ArrayList<Tail> getTails() {
        return tails;
    }

    public Tail head() {
        return head;
    }

    public void setDirection(dir direction) {
        this.direction = direction;
    }

}
