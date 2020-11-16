import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class represents the food that the snake can eat. It extends the DrawableUnit class.
 * If a Food object is eaten, it increases the length of the snake and disappears from the screen.
 */
public class Food extends DrawableUnit {
    private int red;
    private int green;
    private int blue;
    protected final Screen screen;

    /**
     * Constructor for the class
     * Creates a food outside of the screen, with black color.
     *
     * @param screen it will be the food's screen attribute
     */
    public Food(Screen screen) {
        super(-40, -40);
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.screen = screen;
    }

    /**
     * Draws this object on the screen using the GraphicsContext
     *
     * @param gc this is the GraphicsContext that we use to draw something on the screen.
     */
    public void drawFood(GraphicsContext gc) {
        gc.setFill(Color.rgb(red, green, blue));
        gc.fillOval(getPosX(), getPosY(), this.getSize(), this.getSize());
    }

    /**
     * Sets this Food's colors to the given parameters
     *
     * @param r red
     * @param g green
     * @param b blue
     */
    public void setColor(int r, int g, int b) {
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    /**
     * If the object's position is out of sight, it calls the relocate food function on the screen
     *
     * @param difficulty this needs to be passed because of the relocateFood() function
     */
    public void update(Main.Difficulty difficulty) {
        if (this.getPosX() < 0)
            screen.relocateFood(this, difficulty);
    }
}
