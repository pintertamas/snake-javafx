import java.io.Serializable;

public class SingleScore implements Serializable, Comparable<SingleScore>{
    private final int point;
    private final String difficulty;

    SingleScore(int point, String difficulty) {
        this.point = point;
        this.difficulty = difficulty;
    }

    public int getPoint() {
        return point;
    }

    public String getDifficulty() {
        return difficulty;
    }


    @Override
    public int compareTo(SingleScore s) {
        return Integer.compare(this.point, s.point);
    }
}
