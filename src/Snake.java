import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Tail> tails;
    private int speed;
    private Tail head;

    public void addTail(Tail t) {
        tails.add(t);
    }

    public void drawSnake() {
        for (Tail tail : tails) {
            Rectangle currentTail = new Rectangle(tail.getPosX(), tail.getPosY(), tail.getSizeX(), tail.getSizeY());

        }

    }
}
