import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            primaryStage.setTitle("Snake");
            primaryStage.setResizable(false);

            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("game.css").toExternalForm());

            Tail tail = new Tail(10,10,20,20);
            Snake snek = new Snake(5, tail);

            primaryStage.setScene(scene);
            primaryStage.show();

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

    }
}