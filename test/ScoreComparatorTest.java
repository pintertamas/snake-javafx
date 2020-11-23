import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreComparatorTest {
    Scores scores = new Scores();

    @Test
    public void testSortWithoutChange() {
        SingleScore ss1 = new SingleScore(10, "EASY");
        SingleScore ss2 = new SingleScore(20, "MEDIUM");
        scores.addScore(ss1.getPoint(), ss1.getDifficulty());
        scores.addScore(ss2.getPoint(), ss2.getDifficulty());
        scores.getScores().sort(new ScoreComparator());

        assertEquals(scores.getScores().get(0).getPoint(), ss2.getPoint());
        assertEquals(scores.getScores().get(0).getDifficulty(), ss2.getDifficulty());
    }

    @Test
    public void testSort() {
        SingleScore ss1 = new SingleScore(20, "EASY");
        SingleScore ss2 = new SingleScore(10, "MEDIUM");
        scores.addScore(ss1.getPoint(), ss1.getDifficulty());
        scores.addScore(ss2.getPoint(), ss2.getDifficulty());
        scores.getScores().sort(new ScoreComparator());

        assertEquals(scores.getScores().get(0).getPoint(), ss1.getPoint());
        assertEquals(scores.getScores().get(0).getDifficulty(), ss1.getDifficulty());
    }

    @Test
    public void testLessThan() {
        SingleScore ss2 = new SingleScore(20, "MEDIUM");
        SingleScore ss3 = new SingleScore(20, "HARD");
        scores.addScore(ss2.getPoint(), ss2.getDifficulty());
        scores.addScore(ss3.getPoint(), ss3.getDifficulty());
        scores.getScores().sort(new ScoreComparator());

        assertEquals(scores.getScores().get(0).getPoint(), scores.getScores().get(1).getPoint());
    }
}