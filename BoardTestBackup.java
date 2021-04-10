package assignments;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class BoardTest extends TestCase {
    File resourcesDirectory = new File("src/input/puzzle00.txt");
    int[][] testTiles = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
    int[][] left = {{8, 1, 3}, {0, 4, 2}, {7, 6, 5}};
    int[][] down = {{8, 1, 3}, {4, 6, 2}, {7, 0, 5}};
    int[][] right = {{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};
    int[][] up = {{8, 0, 3}, {4, 1, 2}, {7, 6, 5}};
    ArrayList<int[][]> neighbors = new ArrayList<>();
    int[][] testTilesCopy = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
    int[][] goalTiles = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    int[][] goalTilesCopy = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    int[][] twinTest = {{0, 1}, {2, 3}};
    int[][] twinResponse = {{0, 3}, {2, 1}};
    Board testBoard = new Board(testTiles);
    Board testBoardCopy = new Board(testTilesCopy);
    Board goalBoard = new Board(goalTiles);
    private Board b;
    private int[][] input;
    private int[][] expected;

    @Test
    public void testTwin(int[][] input, int[][] expected) {
        this.input = input;
        this.expected = expected;
        Assert.assertTrue(input == expected);
    }

    public void testIterator() {
    }

    //   public void setUp() throws Exception {
//        In in = new In(resourcesDirectory);
//        int n = in.readInt();
//        int[][] myTiles = new int[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                myTiles[i][j] = in.readInt(); //tiles[i][j];
//            }
//        }
//        Board initial = new Board(myTiles);
//        StdOut.println("The original board is: " + initial);
//        // solve the puzzle
//        Solver solver = new Solver(initial);
//        // print solution to standard output
//        if (!solver.isSolvable())
//            StdOut.println("No solution possible");
//        else {
//            StdOut.println("Here is the list of moves that make up the solution: ");
//            for (Board board : solver.solution())
//                StdOut.println("The board: " + board + " It's Manhattan value: " + board.manhattan() + " Its hamming value: " + board.hamming());
//            StdOut.println("The board is solvable, and the ");
//            StdOut.println("Minimum number of moves = " + solver.moves());
//        }
//        super.setUp();
//}

    public void tearDown() throws Exception {
    }

    public void testForEach() {
    }

    public void testSpliterator() {

    }

    public void testTestToString() {
    }

    public void testDimension() {
    }

    public void testHamming() {
        assertEquals(5, testBoard.hamming());
    }

    public void testManhattan() {
        assertEquals(10, testBoard.manhattan());
    }

    public void testIsGoal() {
        //assertFalse(testBoard.isGoal());
        assertTrue(goalBoard.isGoal());
    }

    public void testTestEquals() {
        assertEquals(testBoard, testBoardCopy);
    }

    public void testNeighbors() {
        neighbors.add(left);
        neighbors.add(down);
        neighbors.add(right);
        neighbors.add(up);
        Assertions.assertIterableEquals(neighbors, testBoard.neighbors());
    }


    @Test
    @Parameterized.Parameters
    public static Collection data() {
        return Arrays.asList(new Object[][]{
                {new Board(new int[][]{{8, 1, 3}, {4, 7, 2}, {0, 6, 5}}), new Board(new int[][]{{8, 1, 3}, {6, 7, 2}, {0, 4, 5}})},
                {new Board(new int[][]{{8, 1, 3}, {4, 5, 2}, {7, 6, 0}}), new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 5, 0}})},
                {new Board(new int[][]{{8, 1, 3}, {4, 5, 2}, {7, 6, 0}}), new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 5, 0}})},
                {new Board(new int[][]{{8, 1, 3}, {0, 5, 2}, {7, 6, 4}}), new Board(new int[][]{{8, 1, 3}, {4, 6, 2}, {7, 5, 4}})},
                {new Board(new int[][]{{8, 9, 3}, {1, 5, 2}, {7, 6, 4}}), new Board(new int[][]{{8, 9, 3}, {1, 5, 2}, {7, 6, 4}})}
        });
    }
}
