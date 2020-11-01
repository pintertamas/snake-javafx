import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class Main extends Application {

    public final int gameWindowSize = 800;

    private GraphicsContext gc;

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
            MenuBar menuBar = new MenuBar();
            Menu menuFile = new Menu("File");
            Menu menuEdit = new Menu("Edit");
            Menu menuView = new Menu("View");
            menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
            root.getChildren().addAll(menuBar);
            primaryStage.setScene(scene);
            primaryStage.show();
            gc = canvas.getGraphicsContext2D();
            run(scene);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void run(Scene scene) {
        Map map = new Map(gameWindowSize, scene, gc);
        //map.setupMap(); // ki kell majd torolni, most csak egy ideiglenes kigyot rajzol fel.
        map.updateMap(gc);
    }
}