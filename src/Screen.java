import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;

public class Screen {
    private static int windowSize;
    private final int cellCount = 20;
    private Snake snake;
    private ArrayList<Food> foods;
    private boolean gameOver = false;

    public Screen(int windowSize, Scene scene, GraphicsContext gc) {
        Screen.windowSize = windowSize;
        this.snake = new Snake(new Tail(360, 400, 40, 40), windowSize, scene, gc);
        this.foods = new ArrayList<>();
    }

    public void initFood(Main.dif difficulty) {
        foods.clear();
        foods = new ArrayList<>();
        for (int i = 0; i < getFoodCount(difficulty); i++)
            foods.add(new Food(-40, -40, 0, 0, 0));
    }

    public void updateScreen(Main.dif difficulty, Main.GameStatus gameStatus, GraphicsContext gc) {
        drawBackground(gc);
        generateFood(difficulty, gc);
        snake.changeDirection();
        snake.moveSnake(gameStatus);
        snake.drawSnake(gc);
        drawScore(gc);
        checkFoodCollision();
        checkSelfCollision();
        checkWallCollision();
    }

    public void checkFoodCollision() {
        for (Food food : foods)
            if (snake.head().getPosX() == food.getPosX() && snake.head().getPosY() == food.getPosY()) { // collision detected
                food.setPosX(-1);
                snake.addTail(new Tail(0, 0, 40, 40));
            }
    }

    public void checkWallCollision() {
        if (snake.head().getPosX() < 0 ||
                snake.head().getPosX() > windowSize ||
                snake.head().getPosY() < 0 ||
                snake.head().getPosY() > windowSize)
            gameOver = true;
    }

    public void checkSelfCollision() {
        if (snake.getTails().size() > 1)
            for (int i = 0; i < snake.getTails().size(); i++)
                for (int j = i + 1; j < snake.getTails().size() - i; j++)
                    if (snake.getTails().get(i).getPosX() == snake.getTails().get(j).getPosX() &&
                            snake.getTails().get(i).getPosY() == snake.getTails().get(j).getPosY()) {
                        gameOver = true;
                        break;
                    }
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

    private void drawScore(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0));
        Text scoreText = new Text(Integer.toString(snake.getTails().size()));
        scoreText.setX(windowSize - 20);
        scoreText.setY(30);
    }

    public void generateFood(Main.dif difficulty, GraphicsContext gc) {
        if (foods.size() != getFoodCount(difficulty))
            initFood(difficulty);
        for (Food food : foods) {
            if (food.getPosX() < 0) {
                Random rand = new Random();
                int low = 0;
                int high = cellCount;
                int posX;
                int posY;
                int r = rand.nextInt(255);
                int g = rand.nextInt(100);
                int b = rand.nextInt(255);
                while (true) {
                    boolean correctFoodPosition = true;
                    posX = (windowSize / cellCount) * rand.nextInt(high - low) + low;
                    posY = (windowSize / cellCount) * rand.nextInt(high - low) + low;
                    for (Tail tail : snake.getTails()) {
                        if (tail.getPosX() == posX && tail.getPosY() == posY) {
                            correctFoodPosition = false;
                            break;
                        }
                    }
                    if (correctFoodPosition) {
                        food.setPosX(posX);
                        food.setPosY(posY);
                        food.setColor(r, g, b);
                        break;
                    }
                }

            }
        }
        for (Food food : foods)
            food.drawFood(food, gc);
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public int getFoodCount(Main.dif difficulty) {
        if (difficulty == Main.dif.EASY)
            return 1;
        else if (difficulty == Main.dif.MEDIUM)
            return 2;
        else if (difficulty == Main.dif.HARD)
            return 3;
        else return 0;
    }

    public Snake getSnake() {
        return snake;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}