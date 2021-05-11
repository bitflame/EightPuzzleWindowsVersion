package assignments;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest3 {
    int[][] tiles = new int[][]{{1, 2, 3, 4, 5, 7, 14}, {8, 9, 10, 11, 12, 13, 6}, {15, 16, 17, 18, 19, 20, 21},
            {22, 23, 24, 25, 26, 27, 28}, {29, 30, 31, 32, 0, 33, 34}, {36, 37, 38, 39, 40, 41, 35},
            {43, 44, 45, 46, 47, 48, 42}};
    int[][] tilesII = new int[][]{{1, 2, 3, 4, 5, 7, 14}, {8, 9, 10, 11, 12, 13, 6}, {15, 16, 17, 18, 19, 20, 21},
            {22, 23, 24, 25, 26, 27, 28}, {29, 30, 31, 32, 33, 0, 34}, {36, 37, 38, 39, 40, 41, 35},
            {43, 44, 45, 46, 47, 48, 42}};
    int[][] tilesIII = new int[][]{{1, 2, 3, 4, 5, 7, 14}, {8, 9, 10, 11, 12, 13, 6}, {15, 16, 17, 18, 19, 0, 21},
            {22, 23, 24, 25, 26, 0, 28}, {29, 30, 31, 32, 33, 27, 34}, {36, 37, 38, 39, 40, 41, 35},
            {43, 44, 45, 46, 47, 48, 42}};
    int[][] tilesIV = new int[][]{{1, 2, 3, 4, 5, 7, 14}, {8, 9, 10, 11, 12, 13, 6}, {15, 16, 17, 18, 19, 20, 21},
            {22, 23, 24, 25, 26, 20, 28}, {29, 30, 31, 32, 33, 27, 34}, {36, 37, 38, 39, 40, 41, 35},
            {43, 44, 45, 46, 47, 48, 42}};
    int[][] tilesV = new int[][]{{1, 2, 3, 4, 5, 7, 14}, {8, 9, 10, 11, 12, 0, 6}, {15, 16, 17, 18, 19, 13, 21},
            {22, 23, 24, 25, 26, 20, 28}, {29, 30, 31, 32, 33, 27, 34}, {36, 37, 38, 39, 40, 41, 35},
            {43, 44, 45, 46, 47, 48, 42}};
    int[][] tilesVI = new int[][]{{1, 2, 3, 4, 5, 7, 14}, {8, 9, 10, 11, 12, 6, 0}, {15, 16, 17, 18, 19, 13, 21},
            {22, 23, 24, 25, 26, 20, 28}, {29, 30, 31, 32, 33, 27, 34}, {36, 37, 38, 39, 40, 41, 35},
            {43, 44, 45, 46, 47, 48, 42}};
    int[][] tilesVII = new int[][]{{1, 2, 3, 4, 5, 7, 0}, {8, 9, 10, 11, 12, 6, 14}, {15, 16, 17, 18, 19, 13, 21},
            {22, 23, 24, 25, 26, 20, 28}, {29, 30, 31, 32, 33, 27, 34}, {36, 37, 38, 39, 40, 41, 35},
            {43, 44, 45, 46, 47, 48, 42}};
    int[][] tilesVIII = new int[][]{{8, 3, 7}, {1, 5, 6}, {4, 2, 0}};
    int[][] tilesVIIITwin = new int[][]{{8, 3, 7}, {1, 2, 6}, {4, 5, 0}};
    Board b;
    Board bII;
    Board bIII;
    Board bIV;
    Board bV;
    Board bVI;
    Board bVII;
    Board bVIII;
    Board bVIIITwin;

    @Before
    public void setUp() throws Exception {
        b = new Board(tiles);
        bII = new Board(tilesII);
        bIII = new Board(tilesIII);
        bIV = new Board(tilesIV);
        bV = new Board(tilesV);
        bVI = new Board(tilesVI);
        bVII = new Board(tilesVII);
        bVIII = new Board(tilesVIII);
        bVIIITwin = new Board(tilesVIIITwin);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void manhattan() {
        assertEquals(8, b.manhattan());
        assertEquals(7, bII.manhattan());
        assertEquals(8, bIII.manhattan());
        assertEquals(9, bIV.manhattan());
        assertEquals(10, bV.manhattan());
        assertEquals(9, bVI.manhattan());
        assertEquals(8, bVII.manhattan());
    }

    @Test
    public void twinOfATwinShouldBeTheOriginal() {
        assertEquals(bVIII, bVIIITwin.twin());
    }
}
