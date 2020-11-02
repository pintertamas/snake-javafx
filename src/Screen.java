import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Random;

public class Screen {
    private static int windowSize;
    private final int cellCount = 20;
    private Snake snake;
    private ArrayList<Food> foods;
    private final ArrayList<GameStatusListener> gameOverListeners;
    private final ArrayList<DifficultyListener> difficultyListeners;

    public Screen(int windowSize, Scene scene, GraphicsContext gc) {
        Screen.windowSize = windowSize;
        this.snake = new Snake(new Tail(360, 400, 40, 40), windowSize, scene, gc);
        this.foods = new ArrayList<>();
        this.gameOverListeners = new ArrayList<>();
        this.difficultyListeners = new ArrayList<>();
    }

    public void initFood(Main.Difficulty difficulty) {
        foods.clear();
        foods = new ArrayList<>();
        for (int i = 0; i < getFoodCount(difficulty); i++)
            foods.add(new Food(-40, -40, 0, 0, 0));
    }

    public void updateScreen(Group root, Canvas canvas, Main.Difficulty difficulty, Main.GameStatus gameStatus, GraphicsContext gc) {
        resetScreen(root, canvas);
        createMenu(root);
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

    private void resetScreen(Group root, Canvas canvas) {
        root.getChildren().clear();
        root.getChildren().add(canvas);
    }

    private void createMenu(Group root) {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameStatus(Main.GameStatus.NEWGAME);
            }
        });
        MenuItem leaderboard = new MenuItem("Leaderboard");
        leaderboard.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameStatus(Main.GameStatus.LEADERBOARD);
            }
        });
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.exit(0);
            }
        });
        menu.getItems().addAll(newGame, leaderboard, exit);

        Menu menuDiff = new Menu("Difficulty");
        MenuItem easy = new MenuItem("Easy");
        easy.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                difficulty(Main.Difficulty.EASY);
                gameStatus(Main.GameStatus.NEWGAME);
            }
        });
        MenuItem medium = new MenuItem("Medium");
        medium.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                difficulty(Main.Difficulty.MEDIUM);
                gameStatus(Main.GameStatus.NEWGAME);
            }
        });
        MenuItem hard = new MenuItem("Hard");
        hard.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                difficulty(Main.Difficulty.HARD);
                gameStatus(Main.GameStatus.NEWGAME);
            }
        });
        menuDiff.getItems().addAll(easy, medium, hard);

        menuBar.getMenus().addAll(menu, menuDiff);
        root.getChildren().addAll(menuBar);
    }

    public void checkFoodCollision() {
        for (Food food : foods)
            if (snake.head().getPosX() == food.getPosX() && snake.head().getPosY() == food.getPosY()) {
                food.setPosX(-1);
                snake.addTail(new Tail(0, 0, 40, 40));
            }
    }

    public void checkWallCollision() {
        if (snake.head().getPosX() < 0 ||
                snake.head().getPosX() > windowSize ||
                snake.head().getPosY() < 0 ||
                snake.head().getPosY() > windowSize)
            gameStatus(Main.GameStatus.GAMEOVER);
    }

    public void checkSelfCollision() {
        if (snake.getTails().size() > 1)
            for (int i = 0; i < snake.getTails().size(); i++)
                for (int j = i + 1; j < snake.getTails().size() - i; j++)
                    if (snake.getTails().get(i).getPosX() == snake.getTails().get(j).getPosX() &&
                            snake.getTails().get(i).getPosY() == snake.getTails().get(j).getPosY()) {
                        gameStatus(Main.GameStatus.GAMEOVER);
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

    public Text drawScore(GraphicsContext gc) {
        gc.setFill(Color.rgb(0, 0, 0));
        Text scoreText = new Text(Integer.toString(snake.getTails().size()));
        scoreText.setX(windowSize - 20);
        scoreText.setY(30);
        return scoreText;
    }

    public void generateFood(Main.Difficulty difficulty, GraphicsContext gc) {
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

    public int getFoodCount(Main.Difficulty difficulty) {
        return difficulty.ordinal() + 1;
    }

    public Snake getSnake() {
        return snake;
    }

    public void registerGameOverListener(GameStatusListener gol) {
        gameOverListeners.add(gol);
    }

    private void gameStatus(Main.GameStatus gs) {
        for (GameStatusListener gol : gameOverListeners)
            gol.gameStatusHandler(gs);
    }

    public void registerDifficultyListener(DifficultyListener dl) {
        difficultyListeners.add(dl);
    }

    private void difficulty(Main.Difficulty difficulty) {
        for (DifficultyListener dl : difficultyListeners)
            dl.difficultyHandler(difficulty);
    }

}