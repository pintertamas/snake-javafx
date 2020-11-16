import java.io.Serializable;

/**
 * Represents a single score by a point and a difficulty.
 */
public class SingleScore implements Serializable, Comparable<SingleScore> {
    private final int point;
    private final String difficulty;

    /**
     * Constructor
     *
     * @param point      the value we want to set the point to
     * @param difficulty the String that we want to see in the leaderboard next to the point
     */
    SingleScore(int point, String difficulty) {
        this.point = point;
        this.difficulty = difficulty;
    }

    /**
     * Point getter
     *
     * @return point
     */
    public int getPoint() {
        return point;
    }

    /**
     * Difficulty getter
     *
     * @return difficulty
     */
    public String getDifficulty() {
        return difficulty;
    }


    /**
     * This function is used by the score comparator.
     *
     * @param s a SingleScore that we want to compare with this object.
     * @return the higher one of the objects' points
     */
    @Override
    public int compareTo(SingleScore s) {
        return Integer.compare(this.point, s.point);
    }
}
