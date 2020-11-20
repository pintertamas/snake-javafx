import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScreenTest {
    Scene scene = new Scene(new Group());
    GraphicsContext gc = new Canvas().getGraphicsContext2D();
    Screen screen = new Screen(800, scene, gc);
    Food food1, food2;

    /**
     * Tests the collision between the snake and any food
     */
    @Test
    void isFoodCollision() {
        Snake snake = new Snake(new DrawableUnit(400,400), 800, scene, gc);
        snake.addDrawableUnit(new DrawableUnit(360, 400));
        screen.setSnake(snake);

        food1 = new Food(screen);
        food2 = new Food(screen);
        food1.setPosX(10);
        food1.setPosY(10);
        food2.setPosX(400);
        food2.setPosY(400);

        assertFalse(screen.isFoodCollision(food1));
        assertTrue(screen.isFoodCollision(food2));
    }

    /**
     * Test case for the wall collision function
     */
    @Test
    void isWallCollision() {
        Snake snake = new Snake(new DrawableUnit(-1,-1), 800, scene, gc);
        snake.addDrawableUnit(new DrawableUnit(1040, 1040));
        screen.setSnake(snake);

        assertTrue(screen.isWallCollision());
    }

    /**
     * Tests whether the snake collided with itself or not
     */
    @Test
    void isSelfCollision() {
        Snake snake = new Snake(new DrawableUnit(300,300), 800, scene, gc);
        snake.addDrawableUnit(new DrawableUnit(300,300));
        screen.setSnake(snake);

        assertTrue(screen.isSelfCollision(0, 1));
    }
}