package assignments;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    char[][] testTiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
    char[][] testTilesCopy = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
    char[][] goalTiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    char[][] secGoalTiles = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
