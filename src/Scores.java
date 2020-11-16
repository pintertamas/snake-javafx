import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class that represents the scores and implements Serializable.
 * It contains an ArrayList of SingleScores.
 */
public class Scores implements Serializable {

    private final ArrayList<SingleScore> scores;

    /**
     * Constructor that creates the scores.
     */
    public Scores() {
        this.scores = new ArrayList<>();
    }

    /**
     * Adds a SingleScore to the scores.
     *
     * @param point      the SingleScore's point
     * @param difficulty the SingleScore's difficulty
     */
    public void addScore(int point, String difficulty) {
        scores.add(new SingleScore(point, difficulty));
    }

    /**
     * Scores getter
     *
     * @return scores
     */
    public ArrayList<SingleScore> getScores() {
        return scores;
    }
}
