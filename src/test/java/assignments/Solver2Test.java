package assignments;

import edu.princeton.cs.algs4.In;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class Solver2Test {
    private Solver2 solver2;
    private Solver2.SearchNode searchNode;

    @Before
    public void setUp() throws Exception {
        final File folder = new File("C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\ModifiedTests");
        String filePath = "C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\ModifiedTests\\puzzle04.txt";
        In in = new In(filePath);
        String fileName = "puzzle04.txt";
        int n = in.readInt();
        int moves = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board b = new Board(tiles);
        solver2 = new Solver2(b);
        individualFileTest();
    }

    @Test
    public void individualFileTest() {
        //StdOut.println("Running Solver2 Individual test");
        TestCase.assertEquals(4, solver2.GetNumberOfMoves());
    }

    @Test
    public void searchNodeEqualsShouldWork() {

    }

}
