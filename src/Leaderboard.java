import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;

/**
 * This class represents the leaderboard.
 * It defines what's on the leaderboard and how the leaderboard looks like.
 */
public class Leaderboard {

    private Scores scores;
    private final ArrayList<GameStatusListener> gameMenuListeners;

    /**
     * Constructor that creates the scores, the listeners, and calls the essential functions.
     */
    public Leaderboard() {
        scores = new Scores();
        gameMenuListeners = new ArrayList<>();
        resetLeaderboard();
        loadLeaderboard();
    }

    /**
     * Loads the leaderboard from a .ser file.
     */
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

    /**
     * Updates the scores, then saves the data to a .ser file.
     *
     * @param saved      we don't want to save more than once.
     * @param newScore   this is the current score that might appear on the leaderboard.
     * @param difficulty the current difficulty that might appear on the leaderboard.
     */
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

    /**
     * It resets the scores on the leaderboard to 0,"X".
     */
    public void resetLeaderboard() {
        for (int i = 0; i < 10; i++)
            scores.addScore(0, "X");
    }

    /**
     * Draws the leaderboard on the screen using the root and the windowSize
     *
     * @param root        the group where we'll add the leaderboard
     * @param windowsSize the current size of the window
     */
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
        menuButton.setOnMousePressed(mouseEvent -> gameMenu());

        leaderboardGroup.getChildren().add(menuButton);
        leaderboardGroup.setMinWidth((float) windowsSize / 2);
        leaderboardGroup.setLayoutX((float) windowsSize / 2 - leaderboardGroup.getMinWidth() / 2);
        leaderboardGroup.setLayoutY(40.0f);
        leaderboardGroup.setAlignment(Pos.TOP_CENTER);

        root.getChildren().add(leaderboardGroup);
    }

    /**
     * Sorts the scores with the ScoreComparator
     * Replaces the worst score with newScore if it's higher than that.
     *
     * @param newScore   the score that might appear on the leaderboard
     * @param difficulty the difficulty that might appear on the leaderboard
     */
    private void updateScores(int newScore, String difficulty) {
        scores.getScores().sort(new ScoreComparator());
        if (scores.getScores().get(9).getPoint() < newScore) {
            scores.getScores().set(9, new SingleScore(newScore, difficulty));
        }
        scores.getScores().sort(new ScoreComparator());
    }

    /**
     * Adds the parameter to the gameMenuListeners
     *
     * @param gsl this is the GameStatusListener that we want to add to the listeners
     */
    public void registerGameMenuListener(GameStatusListener gsl) {
        gameMenuListeners.add(gsl);
    }

    /**
     * Calls the gameStatusHandler function on the listeners with the MENU parameter.
     */
    private void gameMenu() {
        for (GameStatusListener gsl : gameMenuListeners)
            gsl.gameStatusHandler(Main.GameStatus.MENU);
    }
}
