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
    public enum GameStatus {GAME, NEWGAME, LEADERBOARD, GAMEOVER}
    private Difficulty difficulty = Difficulty.EASY;
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
        Thread[] threads = new Thread[2];
        Screen screen = new Screen(gameWindowSize, scene, gc);
        threads[0] = new Thread(screen);
        Menu menu = new Menu(root);
        threads[1] = new Thread(menu);
        threads[0].start();
        threads[1].start();
        Leaderboard leaderboard = new Leaderboard(gc);
        leaderboard.initLeaderboard();
        menu.registerGameOverListener(this);
        menu.registerDifficultyListener(this);
        screen.registerGameOverListener(this);
        screen.getSnake().registerGameOverListener(this);

        new AnimationTimer() {
            public void handle(long currTime) {
                switch (gameStatus) {
                    case GAME -> {
                        Text score = screen.drawScore(gc);
                        try {
                            screen.updateScreen(root, canvas, difficulty, gameStatus, gc);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        menu.createMenu(root);
                        root.getChildren().add(score);
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