package assignments;

import edu.princeton.cs.algs4.In;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

@RunWith(Parameterized.class)
public class BoardTest {
    public Solver board;
    private int[][] fInput;
    private boolean fExpected;
    final static File folder = new File("C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\ModifiedTests");
    final static String destFolder = "C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\board_test_results";
    final static ArrayList<Object[]> filesList = new ArrayList<>();
    private static Object[] testInst;

    @Parameterized.Parameters(name = "{index}: Number of moves for [{0}]={2}")
    public static Iterable<Object[]> data() {
        String path = "";
        int counter = 0;
        for (final File fileEntry : folder.listFiles()) {
            //System.out.println("processing file: " + fileEntry.getName())
            counter++;
            if (counter == 100) break;
            path = destFolder + fileEntry;
            In in = new In(fileEntry.getAbsolutePath());
            String fileName = fileEntry.getName();
            int n = in.readInt();
            int moves = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = in.readInt();

            testInst = new Object[]{fileName, tiles};
            filesList.add(testInst);
        }
        return filesList;
    }


    public BoardTest(String fileName, int[][] tiles) throws IOException {
        fInput = tiles;
        fExpected = true;
        File myObj = new File(destFolder, fileName);
        FileWriter myWriter = new FileWriter(myObj);
        for (Solver.SearchNode s : board.solutionList) {
            myWriter.write("Here is the board: " + s.GetCurrentBoard().toString() + " Here is the number of moves: " +
                    s.GetMovesCount() + " Here is the manhattan distance: " + s.GetCurrentBoard().manhattan() +
                    " Here is the hamming distance: " + s.GetCurrentBoard().hamming());
        }
        myWriter.close();
    }

    @Test
    public void test() {

        Board a = new Board(fInput);
        Board b = new Board(fInput);
        boolean value = a.equals(b);
        boolean expected = true;
//        assert equals(value, expected);
//        assert true (value);
    }
}
