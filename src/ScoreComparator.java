import java.util.Comparator;

public class ScoreComparator implements Comparator<Integer> {

    @Override
    public int compare(Integer i1, Integer i2) {
        return i1 <= i2 ? i1 : i2;
    }
}
