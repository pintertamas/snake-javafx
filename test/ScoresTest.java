import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoresTest {
    final Scores score = new Scores();

    /**
     * Test case for checking the addScore() method
     */
    @Test
    void addScore() {
        score.addScore(10, "HARD");
        score.addScore(100, "MEDIUM");
        assertEquals(score.getScores().get(0).getPoint(), 10);
        assertEquals(score.getScores().get(1).getDifficulty(), "MEDIUM");
    }

    /**
     * Tests the getScores() method by comparing the size of the returned ArrayList before and after adding a score to the list
     */
    @Test
    void getScores() {
        assertEquals(score.getScores().size(), 0);
        score.addScore(10, "HARD");
        assertEquals(score.getScores().size(), 1);
    }
}