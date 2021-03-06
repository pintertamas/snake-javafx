import java.util.Comparator;

/**
 * A comparator that compares two SingleScore objects.
 */
class ScoreComparator implements Comparator<SingleScore> {
    public int compare(SingleScore s1, SingleScore s2) {
        return s2.compareTo(s1);
    }
}
