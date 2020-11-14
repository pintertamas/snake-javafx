import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
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
    private final ArrayList<GameStatusListener> gameStatusListeners;

    public Screen(int windowSize, Scene scene, GraphicsContext gc) {
        Screen.windowSize = windowSize;
        this.snake = new Snake(new DrawableUnit(360, 400), windowSize, scene, gc);
        this.foods = new ArrayList<>();
        this.gameStatusListeners = new ArrayList<>();
    }

    public void initFood(Main.Difficulty difficulty) {
        foods.clear();
        foods = new ArrayList<>();
        for (int i = 0; i < getFoodCount(difficulty); i++) {
            if (difficulty == Main.Difficulty.HARD)
                foods.add(new DisappearingFood(getScreen()));
            else foods.add(new Food(getScreen()));
        }
    }

    public void updateScreen(Group root, Canvas canvas, Main.Difficulty difficulty, Main.GameStatus gameStatus, GraphicsContext gc) {
        resetScreen(root, canvas);
        drawBackground(gc);
        generateFood(difficulty, gc);
        snake.changeDirection();
        snake.moveSnake(gameStatus);
        snake.drawSnake(gc);
        drawScore(gc);
        checkFoodCollision();
        checkSelfCollision();
        checkWallCollision();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void resetScreen(Group root, Canvas canvas) {
        root.getChildren().clear();
        root.getChildren().add(canvas);
    }

    public void checkFoodCollision() {
        for (Food food : foods)
            if (snake.head().getPosX() == food.getPosX() && snake.head().getPosY() == food.getPosY()) {
                food.setPosX(-1);
                snake.addDrawableUnit(new DrawableUnit(0, 0));
            }
    }

    public void checkWallCollision() {
        if (snake.head().getPosX() < 0 ||
                snake.head().getPosX() > windowSize ||
                snake.head().getPosY() < 0 ||
                snake.head().getPosY() > windowSize)
            gameStatus();
    }

    public void checkSelfCollision() {
        if (snake.getDrawableUnits().size() > 1)
            for (int i = 0; i < snake.getDrawableUnits().size(); i++)
                for (int j = i + 1; j < snake.getDrawableUnits().size() - i; j++)
                    if (snake.getDrawableUnits().get(i).getPosX() == snake.getDrawableUnits().get(j).getPosX() &&
                            snake.getDrawableUnits().get(i).getPosY() == snake.getDrawableUnits().get(j).getPosY()) {
                        gameStatus();
                        break;
                    }
    }

    public void drawBackground(GraphicsContext gc) {
        for (int i = 0; i < cellCount; i++) {
            for (int j = 0; j < cellCount; j++) {
                if ((i + j) % 2 == 0)
                    gc.setFill(Color.rgb(170, 215, 81));
                else {
                    gc.setFill(Color.rgb(162, 209, 73));
                }
                int cellSize = windowSize / cellCount;
                gc.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }

    public Text drawScore(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0));
        Text scoreText = new Text("Score: " + getSnake().getScore());
        scoreText.setX(windowSize - 120);
        scoreText.setY(30);
        scoreText.setScaleX(3);
        scoreText.setScaleY(3);
        return scoreText;
    }

    public void generateFood(Main.Difficulty difficulty, GraphicsContext gc) {
        if (foods.size() != getFoodCount(difficulty))
            initFood(difficulty);
        for (Food food : foods) {
            food.update(difficulty);
        }
        for (Food food : foods)
            food.drawFood(food, gc);
    }

    public void relocateFood(Food food, Main.Difficulty difficulty) {
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
            for (DrawableUnit DrawableUnit : snake.getDrawableUnits()) {
                if (DrawableUnit.getPosX() == posX && DrawableUnit.getPosY() == posY) {
                    correctFoodPosition = false;
                    break;
                }
            }
            if (correctFoodPosition) {
                Food newFood;
                if (difficulty == Main.Difficulty.MEDIUM) {
                    int foodType = rand.nextInt(2);
                    if (foodType == 1) {
                        newFood = new Food(getScreen());
                    } else {
                        newFood = new DisappearingFood(getScreen());
                    }
                } else if (difficulty == Main.Difficulty.HARD) {
                    newFood = new DisappearingFood(getScreen());
                } else {
                    newFood = new Food(getScreen());
                }
                newFood.setPosX(posX);
                newFood.setPosY(posY);
                newFood.setColor(r, g, b);
                foods.set(foods.indexOf(food), newFood);
                break;
            }
        }
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Screen getScreen() {
        return this;
    }

    public int getFoodCount(Main.Difficulty difficulty) {
        return difficulty.ordinal() + 1;
    }

    public Snake getSnake() {
        return snake;
    }

    public void registerGameStatusListener(GameStatusListener gsl) {
        gameStatusListeners.add(gsl);
    }

    private void gameStatus() {
        for (GameStatusListener gol : gameStatusListeners)
            gol.gameStatusHandler(Main.GameStatus.GAMEOVER);
    }
}
