import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Food {
    private static int posX;
    private static int posY;

    public void spawnFood(GraphicsContext gc) {
        gc.setFill(Color.rgb(100,20,20));
        gc.fillOval(posX, posY, 20,20);
    }

    public static void setPosX(int posX) {
        Food.posX = posX;
    }

    public static void setPosY(int posY) {
        Food.posY = posY;
    }

    public static int getPosX() {
        return posX;
    }

    public static int getPosY() {
        return posY;
    }
}
