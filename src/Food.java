import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Food extends Tail {
    private int red;
    private int green;
    private int blue;

    public Food(int posX, int posY, int r, int g, int b) {
        super(posX, posY);
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public void drawFood(Food food, GraphicsContext gc) {
        gc.setFill(Color.rgb(food.red, food.green, food.blue));
        gc.fillOval(food.getPosX(), food.getPosY(), this.getSize(), this.getSize());
    }

    public void setColor(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }
}
