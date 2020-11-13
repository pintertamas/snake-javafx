import java.util.Comparator;

class ScoreComparator implements Comparator<Score> {
    public int compare(Score s1,Score s2){
        return s2.compareTo(s1);
    }
}