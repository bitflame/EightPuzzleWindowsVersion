package assignments;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameTreeTest {
    GameTree<Solver.SearchNode, Integer> testGm = new GameTree<>();
    int[][] testTiles1 = new int[3][3];
    int[][] testTiles2 = new int[3][3];
    Board testBoard1 = new Board(testTiles1);
    Board testBoard2 = new Board(testTiles2);
    //(Board b, int m, int manhattan, int hamming, SearchNode prev)
    Solver.SearchNode testSm1 = new Solver.SearchNode(testBoard1, 1, testBoard1.manhattan(), testBoard1.hamming(), null);
    Solver.SearchNode testSm2 = new Solver.SearchNode(testBoard2, 1, testBoard2.manhattan(), testBoard2.hamming(), null);

    @BeforeEach
    void setUp() {
        testTiles1 = new int[][]{{5, 2, 3}, {4, 7, 0}, {8, 6, 1}};
        testTiles2 = new int[][]{{1, 4, 3}, {2, 5, 6}, {3, 7, 8}};
    }

    //region InactiveMethods
    @AfterEach
    void tearDown() {
    }

    @Test
    void get() {
    }

    @Test
    void keys() {
    }

    @Test
    void size() {
    }

    @Test
    void min() {
    }

    @Test
    void floor() {
    }

    @Test
    void max() {
    }

    @Test
    void ceiling() {
    }

    @Test
    void select() {
    }

    @Test
    void rank() {
    }

    @Test
    void deleteMin() {
    }

    @Test
    void delete() {
    }

    @Test
    void print() {
    }

    @Test
    void testPrint() {
    }

    //endregion
    @Test
    void put() {
        int expected = 1;
        testGm.put(testSm1, testSm1.GetPriority());
        testGm.put(testSm2, testSm2.GetPriority());
        Assert.assertEquals(testGm.size(), expected);
    }


}
