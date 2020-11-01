import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Map {
    private static int windowSize;
    private int cellCount = 20;
    private final Snake snake;
    private Food food;
    private Scene scene;

    public Map(int windowSize, Scene scene, GraphicsContext gc) {
        Map.windowSize = windowSize;
        this.snake = new Snake(new Tail(360, 400, 40, 40), windowSize, scene, gc);
        food = new Food();
    }

    /*public void setupMap() {
        Tail tail2 = new Tail(400, 400, 40, 40);
        Tail tail3 = new Tail(440, 400, 40, 40);
        Tail tail4 = new Tail(480, 400, 40, 40);
        Tail tail5 = new Tail(520, 400, 40, 40);
        Tail tail6 = new Tail(560, 400, 40, 40);
        Tail tail7 = new Tail(600, 400, 40, 40);
        Tail tail8 = new Tail(640, 400, 40, 40);
        Tail tail9 = new Tail(680, 400, 40, 40);
        snake.addTail(tail2);
        snake.addTail(tail3);
        snake.addTail(tail4);
        snake.addTail(tail5);
        snake.addTail(tail6);
        snake.addTail(tail7);
        snake.addTail(tail8);
        snake.addTail(tail9);
    }*/

    public void updateMap(GraphicsContext gc) {
        drawBackground(gc);
        snake.drawSnake(gc);
        generateFood(gc);
        snake.moveSnake();
    }

    private void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < cellCount; i++) {
            for (int j = 0; j < cellCount; j++) {
                if ((i + j) % 2 == 0)
                    gc.setFill(Color.web("AAD751"));
                else {
                    gc.setFill(Color.web("A2D149"));
                }
                int cellSize = windowSize / cellCount;
                gc.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    public void generateFood(GraphicsContext gc) {
        Random r = new Random();
        int low = 0;
        int high = cellCount;
        int posX;
        int posY;
        while (true) {
            boolean correctFoodPosition = true;
            posX = (windowSize / cellCount) * r.nextInt(high - low) + low;
            posY = (windowSize / cellCount) * r.nextInt(high - low) + low;
            for (Tail tail : snake.getTails()) {
                if (tail.getPosX() == posX && tail.getPosY() == posY) {
                    correctFoodPosition = false;
                    break;
                }
            }
            if (correctFoodPosition)
                break;
        }
        food.spawnFood(posX, posY, gc);
    }

}
