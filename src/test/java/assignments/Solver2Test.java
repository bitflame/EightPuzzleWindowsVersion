package assignments;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;

public class Solver2Test {
    final static File folder = new File("C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\ModifiedTests");

    @Test
    public void inidividualFileTest() {
        StdOut.println("Running Solver2 Individual test");
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
        Solver2 solver2 = new Solver2(b);
        TestCase.assertEquals(4, solver2.GetNumberOfMoves());
    }
}
