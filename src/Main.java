import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements GameStatusListener, DifficultyListener {

    private final int gameWindowSize = 800;
    private GraphicsContext gc;
    public enum Difficulty {EASY, MEDIUM, HARD}
    public enum GameStatus {MENU, GAME, NEWGAME, LEADERBOARD, GAMEOVER}
    private Difficulty difficulty = Difficulty.EASY;
    private GameStatus gameStatus = GameStatus.MENU;
    private boolean savedToFile = false;

    public static void main(String[] args) {
        launch(args);
    }

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

    private void run(Group root, Canvas canvas, Scene scene) {
        Screen screen = new Screen(gameWindowSize, scene, gc);
        Menu menu = new Menu();
        Leaderboard leaderboard = new Leaderboard();
        GameOver gameOver = new GameOver();
        menu.registerGameOverListener(this);
        menu.registerDifficultyListener(this);
        screen.registerGameStatusListener(this);
        screen.getSnake().registerGameOverListener(this);
        leaderboard.registerGameOverListener(this);
        gameOver.registerGameStatusListener(this);

        new AnimationTimer() {
            public void handle(long currTime) {
                switch (gameStatus) {
                    case MENU -> {
                        screen.resetScreen(root, canvas);
                        screen.drawBackground(gc);
                        menu.createMenu(root, gameWindowSize);
                    }
                    case GAME -> {
                        Text score = screen.drawScore(gc);
                        screen.updateScreen(root, canvas, difficulty, gameStatus, gc);
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
                        leaderboard.saveLeaderboard(savedToFile, screen.getSnake().getScore());
                        savedToFile = true;
                        gameOver.drawGameOverScreen(root, gameWindowSize);
                    }
                }
            }
        }.start();
    }

    @Override
    public void gameStatusHandler(GameStatus gs) {
        this.gameStatus = gs;
    }

    @Override
    public void difficultyHandler(Difficulty difficulty) {
        this.difficulty = difficulty;
    }
}