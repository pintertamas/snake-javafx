import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleScoreTest {
    SingleScore ss1;
    SingleScore ss2;
    SingleScore ss3;

    /**
     * Test case for the comparator auxiliary function
     */
    @Test
    void compareTo() {
        ss1 = new SingleScore(10, "EASY");
        ss2 = new SingleScore(20, "MEDIUM");
        ss3 = new SingleScore(20, "HARD");

        assertEquals(-1, ss1.compareTo(ss2));
        assertEquals(1, ss2.compareTo(ss1));
        assertEquals(0, ss2.compareTo(ss3));
    }
}