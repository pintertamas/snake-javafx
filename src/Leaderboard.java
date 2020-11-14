import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;

public class Leaderboard {

    private Scores scores;
    private final ArrayList<GameStatusListener> gameStatusListeners;

    public Leaderboard() {
        scores = new Scores();
        gameStatusListeners = new ArrayList<>();
        resetLeaderboard();
        loadLeaderboard();
    }

    public void loadLeaderboard() {
        File file = new File("leaderboard.ser");
        if (file.exists()) {
            try {
                Scores s;
                FileInputStream fileIn = new FileInputStream("leaderboard.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                s = (Scores) in.readObject();
                this.scores = s;
                fileIn.close();
                in.close();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            resetLeaderboard();
            saveLeaderboard(false, 0, "-");
        }
    }

    public void saveLeaderboard(boolean saved, int newScore, String difficulty) {
        if (!saved) {
            updateScores(newScore, difficulty);
            try {
                FileOutputStream fileOut =
                        new FileOutputStream("leaderboard.ser");
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(scores);
                fileOut.close();
                out.close();
            } catch (IOException i) {
                i.printStackTrace();
            }
        }
    }

    public void resetLeaderboard() {
        for (int i = 0; i < 10; i++)
            scores.addScore(0, "X");
    }

    public void draw(Group root, int windowsSize) {
        VBox leaderboardGroup = new VBox(10);
        Text scoreText;
        Text difficultyText;
        Text leaderboardText = new Text("LEADERBOARD\n");
        leaderboardText.setId("menu-text");
        leaderboardGroup.getChildren().add(leaderboardText);

        VBox pointBox = new VBox(20);
        VBox difficultyBox = new VBox(20);
        for (int i = 0; i < 10; i++) {
            scoreText = new Text((float) windowsSize / 2, (float) 0, "#" + (i + 1) + ".\t" + scores.getScores().get(i).getPoint());
            difficultyText = new Text((float) windowsSize / 2, (float) 0, " (" + scores.getScores().get(i).getDifficulty() + ")");
            scoreText.setId("leaderboard-text");
            difficultyText.setId("leaderboard-text");
            pointBox.getChildren().add(scoreText);
            difficultyBox.getChildren().add(difficultyText);
        }
        pointBox.setAlignment(Pos.CENTER_LEFT);
        difficultyBox.setAlignment(Pos.CENTER_RIGHT);

        HBox scoresBox = new HBox(200);
        scoresBox.getChildren().addAll(pointBox, difficultyBox);
        scoresBox.setAlignment(Pos.CENTER);

        leaderboardGroup.getChildren().add(scoresBox);

        Button menuButton = new Button("Menu");
        menuButton.setId("button");
        menuButton.setOnMousePressed(mouseEvent -> gameStatus());

        leaderboardGroup.getChildren().add(menuButton);
        leaderboardGroup.setMinWidth((float) windowsSize / 2);
        leaderboardGroup.setLayoutX((float) windowsSize / 2 - leaderboardGroup.getMinWidth() / 2);
        leaderboardGroup.setLayoutY(40.0f);
        leaderboardGroup.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(leaderboardGroup);
    }

    private void updateScores(int newScore, String difficulty) {
        scores.getScores().sort(new ScoreComparator());
        if (scores.getScores().get(9).getPoint() < newScore) {
            scores.getScores().set(9, new SingleScore(newScore, difficulty));
        }
        scores.getScores().sort(new ScoreComparator());
    }

    public void registerGameOverListener(GameStatusListener gsl) {
        gameStatusListeners.add(gsl);
    }

    private void gameStatus() {
        for (GameStatusListener gsl : gameStatusListeners)
            gsl.gameStatusHandler(Main.GameStatus.MENU);
    }
}
