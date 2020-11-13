import java.io.Serializable;
import java.util.ArrayList;

public class Score implements Serializable, Comparable<Score> {

    final ArrayList<Integer> points;
    final ArrayList<String> difficulty;

    Score() {
        points = new ArrayList<>();
        difficulty = new ArrayList<>();
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public ArrayList<String> getDifficulty() {
        return difficulty;
    }

    public void addPoint(int point, String difficulty) {
        points.add(point);
        this.difficulty.add(difficulty);
    }

    @Override
    public int compareTo(Score score) {
        return this.compareTo(score);
    }
}
