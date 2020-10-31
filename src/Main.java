import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private final int menuHeight = 40;
    private final int gameWindowSize = 800;

    private GraphicsContext gc;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Snake");
            primaryStage.setResizable(false);
            Group root = new Group();
            Canvas canvas = new Canvas(gameWindowSize, gameWindowSize);
            root.getChildren().add(canvas);
            Scene scene = new Scene(root);
            MenuBar menuBar = new MenuBar();
            Menu menuFile = new Menu("File");
            Menu menuEdit = new Menu("Edit");
            Menu menuView = new Menu("View");
            menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
            root.getChildren().addAll(menuBar);
            primaryStage.setScene(scene);
            primaryStage.show();
            gc = canvas.getGraphicsContext2D();
            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void run() {
        Tail tail = new Tail(400,80,40,40);
        Tail tail2 = new Tail(440,80,40,40);
        Tail tail3 = new Tail(480,80,40,40);
        Tail tail4 = new Tail(520,80,40,40);
        Tail tail5 = new Tail(560,80,40,40);
        Tail tail6 = new Tail(600,80,40,40);
        Tail tail7 = new Tail(640,80,40,40);
        Tail tail8 = new Tail(680,80,40,40);
        Tail tail9 = new Tail(720,80,40,40);
        Snake snek = new Snake(tail);
        snek.addTail(tail2);
        snek.addTail(tail3);
        snek.addTail(tail4);
        snek.addTail(tail5);
        snek.addTail(tail6);
        snek.addTail(tail7);
        snek.addTail(tail8);
        snek.addTail(tail9);

        drawBackground(gc);
        snek.drawSnake(gc);
    }

    private void drawBackground(GraphicsContext gc) {
        int cellCount = 20;
        for (int i = 0; i < cellCount; i++) {
            for (int j = 0; j < cellCount; j++) {
                if ((i + j) % 2 == 0)
                    gc.setFill(Color.web("AAD751"));
                else {
                    gc.setFill(Color.web("A2D149"));
                }
                int cellSize = gameWindowSize / cellCount;
                gc.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }
}