import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Snake {
    private ArrayList<Tail> tails;
    private int speed;
    private Tail head;

    public Snake(int speed, Tail head) {
        tails = new ArrayList<Tail>();
        tails.add(head);
        this.speed = speed;
        this.head = head;
    }

    public void addTail(Tail t) {
        tails.add(t);
    }

    public void drawSnake() {
        for (Tail tail : tails) {
            Rectangle currentTail = new Rectangle(tail.getPosX(), tail.getPosY(), tail.getSizeX(), tail.getSizeY());

        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public ArrayList<Tail> getTails() {
        return tails;
    }

    public int getSpeed() {
        return speed;
    }

    public Tail getHead() {
        return head;
    }

}
