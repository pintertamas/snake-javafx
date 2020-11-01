import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Food extends Point{
    private int posX;
    private int posY;
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
        gc.fillOval(food.getPosX(), food.getPosY(), 40, 40);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setColor(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }
}
