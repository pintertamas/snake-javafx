import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Contains some getter and setter tests for the DrawableUnit class
 */
class DrawableUnitTest {
    final DrawableUnit testDrawableUnit = new DrawableUnit(400, 400);

    @Test
    void getPosX() {
        assertEquals(400, testDrawableUnit.getPosX(), "Error at: DrawableUnit posX getter");
    }

    @Test
    void getPosY() {
        assertEquals(400, testDrawableUnit.getPosY(), "Error at: DrawableUnit posY getter");
    }

    @Test
    void getSize() {
        assertEquals(40, testDrawableUnit.getSize(), "Error at: DrawableUnit size getter");
    }

    @Test
    void setPosX() {
        testDrawableUnit.setPosX(500);
        assertEquals(500, testDrawableUnit.getPosX(), "Error at: DrawableUnit posX setter");

    }

    @Test
    void setPosY() {
        testDrawableUnit.setPosY(500);
        assertEquals(500, testDrawableUnit.getPosY(), "Error at: DrawableUnit posY setter");
    }
}