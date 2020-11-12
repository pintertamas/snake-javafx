import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Food extends DrawableUnit {
    private int red;
    private int green;
    private int blue;
    protected Screen screen;

    public Food(Screen screen, int posX, int posY, int r, int g, int b) {
        super(posX, posY);
        this.red = r;
        this.green = g;
        this.blue = b;
        this.screen = screen;
    }

    public Food(Screen screen) {
        super(-40, -40);
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.screen = screen;
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

    public void update(Main.Difficulty difficulty) {
        if (this.getPosX() < 0)
            screen.relocateFood(this, difficulty);
    }
}
