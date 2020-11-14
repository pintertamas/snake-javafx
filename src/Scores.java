import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class Scores implements Serializable {

    private final ArrayList<SingleScore> scores;

    public Scores() {
        this.scores = new ArrayList<>();
    }

    public void addScore(int point, String difficulty) {
        scores.add(new SingleScore(point, difficulty));
    }

    public ArrayList<SingleScore> getScores() {
        return scores;
    }
}
