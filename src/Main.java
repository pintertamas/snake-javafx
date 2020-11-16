import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * <h1>This is a Snake GUI application.</h1>
 * *
 * * @author Pintér Tamás
 * * @since 2020.11.01
 * * The github repository for this project is here: <a href="#{@link}">{@link "https://github.com/pintertamas/snake-javafx"}</a>
 */
public class Main extends Application implements GameStatusListener, DifficultyListener {

    /**
     * Initialization of the variables that needs to be set up before the game starts.
     */
    private final int gameWindowSize = 800;
    private GraphicsContext gc;

    /**
     * The game has 3 different modes.
     * EASY mode spawns single non-disappearing food.
     * MEDIUM will spawn 2 food and they might disappear after a while.
     * HARD mode only spawns disappearing food.
     */
    public enum Difficulty {EASY, MEDIUM, HARD}

    public enum GameStatus {MENU, GAME, NEWGAME, LEADERBOARD, GAMEOVER}

    private Difficulty difficulty = Difficulty.EASY;
    private GameStatus gameStatus = GameStatus.MENU;
    private boolean savedToFile = false;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This function creates the windows for the user, sets up its properties, and calls run() which is the game loop function.
     *
     * @param primaryStage it is the primary window that will be shown to the user.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Awesome Snake Game");
            primaryStage.setResizable(false);
            Group root = new Group();
            Canvas canvas = new Canvas(gameWindowSize, gameWindowSize);
            root.getChildren().add(canvas);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            gc = canvas.getGraphicsContext2D();
            String style = getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().addAll(style);
            root.getStylesheets().addAll(style);
            run(root, canvas, scene);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This function creates the classes that will be used during the game, then creates a loop that calls the functions of these classes (and animates them).
     *
     * @param root   It's a group of objects.
     * @param canvas Canvas is an image that can be drawn on using a set of graphics commands provided by a GraphicsContext
     * @param scene  The contents of the scene graph will be clipped by the scene's width and height and changes to the scene's size will not alter the layout of the scene graph.
     */
    private void run(Group root, Canvas canvas, Scene scene) {
        Screen screen = new Screen(gameWindowSize, scene, gc);
        Menu menu = new Menu();
        Leaderboard leaderboard = new Leaderboard();
        GameOver gameOver = new GameOver();
        menu.registerGameOverListener(this);
        menu.registerDifficultyListener(this);
        screen.registerGameOverListener(this);
        screen.getSnake().registerGameOverListener(this);
        leaderboard.registerGameMenuListener(this);
        gameOver.registerGameStatusListener(this);

        new AnimationTimer() {
            /**
             * The displayed content will depend on what is the current value of the gameStatus attribute.
             * @param currTime I don't use it, but the AnimationTimer handle function needs it there.
             */
            public void handle(long currTime) {
                switch (gameStatus) {
                    case MENU -> {
                        screen.resetScreen(root, canvas);
                        screen.drawBackground(gc);
                        menu.createMenu(root, gameWindowSize, difficulty);
                    }
                    case GAME -> {
                        Text score = screen.drawScore(gc);
                        screen.updateScreen(root, canvas, difficulty, gc);
                        root.getChildren().add(score);
                    }
                    case NEWGAME -> {
                        screen.setSnake(new Snake(new DrawableUnit(360, 360), gameWindowSize, scene, gc));
                        screen.initFood(difficulty);
                        savedToFile = false;
                        gameStatus = GameStatus.GAME;
                    }
                    case LEADERBOARD -> {
                        screen.resetScreen(root, canvas);
                        leaderboard.draw(root, gameWindowSize);
                    }
                    case GAMEOVER -> {
                        leaderboard.saveLeaderboard(savedToFile, screen.getSnake().getScore(), difficulty.name());
                        savedToFile = true;
                        gameOver.drawGameOverScreen(root, gameWindowSize);
                    }
                }
            }
        }.start();
    }

    /**
     * This function handles the gameStatus changes. When a listener wants to change the state of the game, this function handles it here.
     *
     * @param gs gameState will be set to this.
     */
    @Override
    public void gameStatusHandler(GameStatus gs) {
        this.gameStatus = gs;
    }

    /**
     * This function handles the difficulty changes. When a listener wants to change the difficulty, this function handles it here.
     *
     * @param difficulty difficulty will be set to this.
     */
    @Override
    public void difficultyHandler(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}