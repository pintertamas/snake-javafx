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

    private Difficulty difficulty = Difficulty.EASY;

    public enum GameStatus {GAME, NEWGAME, LEADERBOARD, GAMEOVER}

    private GameStatus gameStatus = GameStatus.GAME;

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
            run(root, canvas, scene);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void run(Group root, Canvas canvas, Scene scene) {
        Screen screen = new Screen(gameWindowSize, scene, gc);
        Menu menu = new Menu();
        Leaderboard leaderboard = new Leaderboard(gc);
        leaderboard.initLeaderboard();
        menu.registerGameOverListener(this);
        menu.registerDifficultyListener(this);
        screen.registerGameOverListener(this);
        screen.registerDifficultyListener(this);
        screen.getSnake().registerGameOverListener(this);

        Thread[] threads = new Thread[2];

        new AnimationTimer() {
            public void handle(long currTime) {
                switch (gameStatus) {
                    case GAME -> {
                        try {
                            Text score = screen.drawScore(gc);
                            screen.updateScreen(root, canvas, difficulty, gameStatus, gc);
                            menu.createMenu(root);
                            root.getChildren().add(score);
                            Thread.sleep(75);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    case NEWGAME -> {
                        screen.setSnake(new Snake(new Tail(360, 400, 40, 40), gameWindowSize, scene, gc));
                        screen.initFood(difficulty);
                        savedToFile = false;
                        gameStatus = GameStatus.GAME;
                    }
                    case LEADERBOARD -> {
                        leaderboard.show();
                    }
                    case GAMEOVER -> {
                        Text gameOver = new Text((float) gameWindowSize / 3, (float) gameWindowSize / 2, "GAME OVER!");
                        //leaderboard.updateScores(leaderboard.getPoints(), screen.getSnake().getScore());
                        //leaderboard.saveLeaderboard(savedToFile, leaderboard.getPoints());
                        savedToFile = true;
                    }
                }
            }
        }.start();
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void gameStatusHandler(GameStatus gs) {
        setGameStatus(gs);
    }

    @Override
    public void difficultyHandler(Difficulty difficulty) {
        setDifficulty(difficulty);
    }
}