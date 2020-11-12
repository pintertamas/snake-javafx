import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Leaderboard {

    private final ArrayList<Integer> points;
    private final ArrayList<GameStatusListener> gameStatusListeners;

    public Leaderboard() {
        points = new ArrayList<>();
        gameStatusListeners = new ArrayList<>();
    }

    public void loadLeaderboard() {
        int point;
        try {
            FileInputStream in = new FileInputStream("leaderboard.ser");
            ObjectInputStream os = new ObjectInputStream(in);
            for (int i = 0; i < 10; i++) {
                point = os.read();
                System.out.println(point);
                addPoint(point);
            }
            in.close();
            os.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveLeaderboard(boolean saved, int score) {
        if (!saved) {
            updateScores(points, score);
            try {
                FileOutputStream fileOut =
                        new FileOutputStream("leaderboard.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                for (Integer point : points)
                    out.writeObject(point);
                out.close();
                fileOut.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    public void resetLeaderboard() {
        for (int i = 0; i < 10; i++)
            points.add(0);
    }

    public void draw(Group root, int windowsSize) {
        String leaderboardStyle = "-fx-font-weight: bold;\n" + "-fx-font-size: 4em;";
        String textStyle = "-fx-font-weight: bold;" + "-fx-font-size: 1.5em;";
        String menuButtonStyle = "-fx-padding: 15;\n" +
                "    -fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;\n" +
                "    -fx-background-radius: 10;\n" +
                "    -fx-background-color: \n" +
                "        linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%),\n" +
                "        #9d4024,\n" +
                "        #d86e3a,\n" +
                "        radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-font-size: 2em;";
        VBox leaderboardGroup = new VBox();
        Text text;
        Text leaderboardText = new Text("LEADERBOARD\n");
        leaderboardText.setStyle(leaderboardStyle);
        leaderboardGroup.getChildren().add(leaderboardText);
        for (int i = 0; i < 10; i++) {
            text = new Text((float) windowsSize / 2, (float) 0, "#" + (i + 1) + ".\t\t" + points.get(i) + "\n");
            text.setStyle(textStyle);
            leaderboardGroup.getChildren().add(text);
        }
        Button menuButton = new Button("Menu");
        menuButton.setStyle(menuButtonStyle);
        menuButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                gameStatus(Main.GameStatus.MENU);
            }
        });
        leaderboardGroup.getChildren().add(menuButton);
        leaderboardGroup.setMinWidth((float) windowsSize / 2);
        leaderboardGroup.setLayoutX((float) windowsSize / 2 - leaderboardGroup.getMinWidth() / 2);
        leaderboardGroup.setLayoutY(40.0f);
        leaderboardGroup.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(leaderboardGroup);
    }

    private void updateScores(ArrayList<Integer> topScores, int score) {
        topScores.sort(new ScoreComparator());
        if (topScores.get(topScores.size() - 1) < score)
            topScores.set(topScores.size() - 1, score);
        topScores.sort(new ScoreComparator());
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }

    private void addPoint(int point) {
        points.add(point);
    }

    public void registerGameOverListener(GameStatusListener gsl) {
        gameStatusListeners.add(gsl);
    }

    private void gameStatus(Main.GameStatus gs) {
        for (GameStatusListener gsl : gameStatusListeners)
            gsl.gameStatusHandler(gs);
    }
}

