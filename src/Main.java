import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class Main extends Application implements GameOverListener {

    private final int gameWindowSize = 800;

    private GraphicsContext gc;

    public enum dif {EASY, MEDIUM, HARD}

    private dif difficulty = dif.EASY;

    public enum GameStatus {
        GAME, NEWGAME, LEADERBOARD, GAMEOVER
    }

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
            createMenu(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            gc = canvas.getGraphicsContext2D();
            run(scene);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void run(Scene scene) {
        Screen screen = new Screen(gameWindowSize, scene, gc);
        Leaderboard leaderboard = new Leaderboard(gc);
        leaderboard.initLeaderboard();
        screen.registerGameOverListener(this);
        new AnimationTimer() {
            public void handle(long currTime) {
                switch (gameStatus) {
                    case GAME -> {
                        savedToFile = false;
                        screen.getSnake().setGameOver(false);
                        screen.updateScreen(difficulty, gameStatus, gc);
                        if (screen.getSnake().getGameOver())
                            setGameStatus(GameStatus.GAMEOVER);
                        try {
                            Thread.sleep(75);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    case NEWGAME -> {
                        screen.setSnake(new Snake(new Tail(360, 400, 40, 40), gameWindowSize, scene, gc));
                        screen.initFood(difficulty);
                        gameStatus = GameStatus.GAME;
                    }
                    case LEADERBOARD -> {

                        leaderboard.show();
                    }
                    case GAMEOVER -> {
                        leaderboard.updateScores(leaderboard.getPoints(), screen.getSnake().getScore());
                        leaderboard.saveLeaderboard(savedToFile, leaderboard.getPoints());
                        savedToFile = true;
                    }
                }
            }
        }.start();
    }

    private void createMenu(Group root) {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        MenuItem newGame = new MenuItem("New Game");
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameStatus = GameStatus.NEWGAME;
            }
        });
        MenuItem leaderboard = new MenuItem("Leaderboard");
        leaderboard.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                gameStatus = GameStatus.LEADERBOARD;
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
                difficulty = dif.EASY;
                gameStatus = GameStatus.NEWGAME;
            }
        });
        MenuItem medium = new MenuItem("Medium");
        medium.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                difficulty = dif.MEDIUM;
                gameStatus = GameStatus.NEWGAME;
            }
        });
        MenuItem hard = new MenuItem("Hard");
        hard.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                difficulty = dif.HARD;
                gameStatus = GameStatus.NEWGAME;
            }
        });
        menuDiff.getItems().addAll(easy, medium, hard);

        menuBar.getMenus().addAll(menu, menuDiff);
        root.getChildren().addAll(menuBar);
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public void gameOverHandler() {
        setGameStatus(GameStatus.GAMEOVER);
    }
}