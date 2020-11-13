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
        File file = new File("leaderboard.ser");
        if (file.exists()) {
            try {
                Score s;
                FileInputStream fileIn = new FileInputStream("leaderboard.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                s = (Score) in.readObject();
                this.score = s;
                fileIn.close();
                in.close();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        else {
            resetLeaderboard();
            saveLeaderboard(false, 0);
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
        VBox leaderboardGroup = new VBox();
        Text text;
        Text leaderboardText = new Text("LEADERBOARD\n");
        leaderboardText.setId("menu-text");
        leaderboardGroup.getChildren().add(leaderboardText);
        for (int i = 0; i < 10; i++) {
            text = new Text((float) windowsSize / 2, (float) 0, "#" + (i + 1) + ".\t\t" + score.getPoints().get(i) + "\n");
            text.setId("leaderboard-text");
            leaderboardGroup.getChildren().add(text);
        }
        Button menuButton = new Button("Menu");
        menuButton.setId("button");
        menuButton.setOnMousePressed(mouseEvent -> gameStatus(Main.GameStatus.MENU));
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

