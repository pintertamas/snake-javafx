import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Food {

    public void spawnFood(int posX, int posY, GraphicsContext gc) {
        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);

        gc.setFill(Color.rgb(r, g, b));
        gc.fillOval(posX, posY, 40, 40);
    }
}
