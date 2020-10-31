import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;

public class Snake {
    private final ArrayList<Tail> tails;
    private final Tail head;

    public Snake(Tail head) {
        tails = new ArrayList<>();
        tails.add(head);
        this.head = head;
    }

    public void addTail(Tail t) {
        tails.add(t);
    }

    public void drawSnake(GraphicsContext gc) {
        for (int i = 0; i < tails.size(); i++) {
            // r: 150, g: 0, b: (255, 130); 255-130=125
            gc.setFill(Color.rgb(150, 0, (130 + 124*i/tails.size())));
            gc.fillRect(tails.get(i).getPosX(), tails.get(i).getPosY(), tails.get(i).getSizeX(), tails.get(i).getSizeY());
        }
    }

    public ArrayList<Tail> getTails() {
        return tails;
    }

    public Tail getHead() {
        return head;
    }

}
