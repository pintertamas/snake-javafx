import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnakeTest {
    final Scene scene = new Scene(new Group());
    final GraphicsContext gc = new Canvas().getGraphicsContext2D();
    final Snake snake = new Snake(new DrawableUnit(80,120), 800, scene, gc);

    /**
     * If we add a new tail to the snake, the size of it must increase by 1
     * (also the score is the snake's size - 1)
     */
    @Test
    void addDrawableUnit() {
        assertEquals(0, snake.getScore());
        snake.addDrawableUnit(new DrawableUnit(800,160));
        assertEquals(1, snake.getScore());
    }

    /**
     * If the moveSnake() function gets called, the snake must be one block (40 pixels) away to the right
     * This test case tests the start and end location of the snake's head
     */
    @Test
    void moveSnake() {
        assertEquals(snake.head().getPosX(), 80);
        assertEquals(snake.head().getPosY(), 120);
        snake.moveSnake(); // the default direction is RIGHT
        assertEquals(snake.head().getPosX(), 120);
        assertEquals(snake.head().getPosY(), 120);
    }

    /**
     * This test case tests whether the getDrawableUnits() function returns the correct tails, and the correct snake length
     */
    @Test
    void getDrawableUnits() {
        assertEquals(snake.getDrawableUnits().get(0), snake.head());
        assertEquals(snake.getDrawableUnits().size(), 1);
    }

    /**
     * Test case for checking the head() function of the snake
     */
    @Test
    void head() {
        assertEquals(80, snake.head().getPosX());
        assertEquals(120, snake.head().getPosY());
    }
}