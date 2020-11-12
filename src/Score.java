import java.io.Serializable;
import java.util.ArrayList;

public class Score implements Serializable{

    ArrayList<Integer> points;

    Score() {
        points = new ArrayList<>();
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public void addPoint(int point) {
        points.add(point);
    }



}
