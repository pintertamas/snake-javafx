import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;

public class Leaderboard {

    private Score score;
    private final ArrayList<GameStatusListener> gameStatusListeners;

    public Leaderboard() {
        score = new Score();
        gameStatusListeners = new ArrayList<>();
        resetLeaderboard();
        loadLeaderboard();
    }

    public void loadLeaderboard() {
        try {
            Score s;
            FileInputStream fileIn = new FileInputStream("leaderboard.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            s = (Score) in.readObject();
            this.score = s;
            fileIn.close();
            in.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Score class not found");
            c.printStackTrace();
        }
    }

    public void saveLeaderboard(boolean saved, int newScore) {
        if (!saved) {
            updateScores(newScore);
            try {
                FileOutputStream fileOut =
                        new FileOutputStream("leaderboard.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(score);
                fileOut.close();
                out.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    public void resetLeaderboard() {
        for (int i = 0; i < 10; i++)
            score.addPoint(0);
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
            text = new Text((float) windowsSize / 2, (float) 0, "#" + (i + 1) + ".\t\t" + score.getPoints().get(i) + "\n");
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

    private void updateScores(int newScore) {
        score.getPoints().sort(new ScoreComparator());
        if (score.getPoints().get(score.getPoints().size() - 1) < newScore)
            score.getPoints().set(score.getPoints().size() - 1, newScore);
        score.getPoints().sort(new ScoreComparator());
    }

    public void registerGameOverListener(GameStatusListener gsl) {
        gameStatusListeners.add(gsl);
    }

    private void gameStatus(Main.GameStatus gs) {
        for (GameStatusListener gsl : gameStatusListeners)
            gsl.gameStatusHandler(gs);
    }
}

