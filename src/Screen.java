import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the screen. It controls the game.
 */
public class Screen {
    private static int windowSize;
    private final int cellCount = 20;
    private Snake snake;
    private ArrayList<Food> foods;
    private final ArrayList<GameStatusListener> gameOverListeners;

    /**
     * Constructor that sets up the screen.
     *
     * @param windowSize the current size of the window
     * @param scene      we want to add objects to this screen
     * @param gc         the GraphicsContext that helps us draw objects on the screen
     */
    public Screen(int windowSize, Scene scene, GraphicsContext gc) {
        Screen.windowSize = windowSize;
        this.snake = new Snake(new DrawableUnit(360, 400), windowSize, scene, gc);
        this.foods = new ArrayList<>();
        this.gameOverListeners = new ArrayList<>();
    }

    /**
     * Initializes the foods based on the difficulty. (the number of them)
     *
     * @param difficulty the current difficulty
     */
    public void initFood(Main.Difficulty difficulty) {
        foods.clear();
        foods = new ArrayList<>();
        for (int i = 0; i < getFoodCount(difficulty); i++) {
            if (difficulty == Main.Difficulty.HARD)
                foods.add(new DisappearingFood(getScreen()));
            else foods.add(new Food(getScreen()));
        }
    }

    /**
     * Updates the content on the screen.
     * Calls the functions and waits for a tenth of a second
     *
     * @param root       the group that will contain the screen.
     * @param canvas     we'll draw objects on this canvas using the GraphicsContext
     * @param difficulty the current difficulty
     * @param gc         the GraphicsContext
     */
    public void updateScreen(Group root, Canvas canvas, Main.Difficulty difficulty, GraphicsContext gc) throws InterruptedException {
        resetScreen(root, canvas);
        drawBackground(gc);
        generateFood(difficulty, gc);
        snake.changeDirection();
        snake.moveSnake();
        snake.drawSnake(gc);
        drawScore(gc);
        checkFoodCollision();
        checkSelfCollision();
        checkWallCollision();
        Thread.sleep(100);
    }

    /**
     * Resets the screen by clearing everything from it.
     *
     * @param root   the group that we want to clear
     * @param canvas the canvas that we want to put back into the group
     */
    public void resetScreen(Group root, Canvas canvas) {
        root.getChildren().clear();
        root.getChildren().add(canvas);
    }

    /**
     * Checks whether the snake ate a food
     * @param food the food that might got eaten by the snake
     * @return whether the snake ate a food or not
     */
    public boolean isFoodCollision(Food food) {
        return (snake.head().getPosX() == food.getPosX() && snake.head().getPosY() == food.getPosY());
    }

    /**
     * Handles snake-food collisions
     */
    public void checkFoodCollision() {
        for (Food food : foods)
            if (isFoodCollision(food)) {
                food.setPosX(-1);
                snake.addDrawableUnit(new DrawableUnit(0, 0));
            }
    }

    /**
     * Checks wall-snake collision
     * @return whether the snake bumped into the wall
     */
    public boolean isWallCollision() {
        return (snake.head().getPosX() < 0 ||
                snake.head().getPosX() > windowSize ||
                snake.head().getPosY() < 0 ||
                snake.head().getPosY() > windowSize);
    }

    /**
     * If the snake hits a wall, the gameOver() function gets called
     */
    public void checkWallCollision() {
        if (isWallCollision())
            gameOver();
    }

    /**
     * Checks the collision between two of the snake's tails
     * @param i first tail
     * @param j second tail
     * @return whether the tails collide
     */
    public boolean isSelfCollision(int i, int j) {
        return (snake.getDrawableUnits().get(i).getPosX() == snake.getDrawableUnits().get(j).getPosX() &&
                snake.getDrawableUnits().get(i).getPosY() == snake.getDrawableUnits().get(j).getPosY());
    }

    /**
     * If the snake eats it's tail, the gameOver() function gets called
     */
    public void checkSelfCollision() {
        if (snake.getDrawableUnits().size() > 1)
            for (int i = 0; i < snake.getDrawableUnits().size(); i++)
                for (int j = i + 1; j < snake.getDrawableUnits().size() - i; j++)
                    if (isSelfCollision(i, j)) {
                        gameOver();
                        break;
                    }
    }

    /**
     * Draws the background of the screen
     *
     * @param gc the GraphicsContext
     */
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

    /**
     * Draws the score on the screen
     *
     * @param gc the GraphicsContext
     * @return a Text which contains the score that we want to display
     */
    public Text drawScore(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0));
        Text scoreText = new Text("Score: " + getSnake().getScore());
        scoreText.setX(windowSize - 120);
        scoreText.setY(30);
        scoreText.setScaleX(3);
        scoreText.setScaleY(3);
        return scoreText;
    }

    /**
     * Generates a food based on the difficulty
     * Updates the foods' position
     * Draws the foods
     *
     * @param difficulty the current difficulty
     * @param gc         the GraphicsContext
     */
    public void generateFood(Main.Difficulty difficulty, GraphicsContext gc) {
        if (foods.size() != getFoodCount(difficulty))
            initFood(difficulty);
        for (Food food : foods) {
            food.update(difficulty);
        }
        for (Food food : foods)
            food.drawFood(gc);
    }

    /**
     * Relocates the food on the screen based on the difficulty.
     *
     * @param food       this food will be relocated
     * @param difficulty this is the current difficulty
     */
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

    /**
     * Snake getter
     *
     * @return snake
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Snake setter
     *
     * @param snake this will be the snake
     */
    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    /**
     * Screen getter
     *
     * @return screen
     */
    public Screen getScreen() {
        return this;
    }

    /**
     * Food count getter based on the difficulty
     *
     * @param difficulty the current difficulty
     * @return the amount of food we want to display on the screen
     */
    public int getFoodCount(Main.Difficulty difficulty) {
        return difficulty.ordinal() + 1;
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
