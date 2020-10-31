import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private final int menuHeight = 100;
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
            primaryStage.setScene(scene);
            primaryStage.show();
            gc = canvas.getGraphicsContext2D();
            run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void run() {
        drawBackground(gc);
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