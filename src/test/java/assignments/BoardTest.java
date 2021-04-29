package assignments;

import edu.princeton.cs.algs4.In;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Parameterized.class)
public class BoardTest {
    public Solver board;
    private int[][] fInput;
    private boolean fExpected;
    private boolean fExpected2;
    private boolean fActual;
    private boolean fResult;
    final static File folder = new File("C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\ModifiedTests");
    final static String destFolder = "C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\board_test_results\\";
    final static ArrayList<Object[]> filesList = new ArrayList<>();
    private static Object[] testInst;
    // test 2
    int[][] tiles1 = {{5, 0, 2}, {4, 1, 3}, {8, 7, 6}};
    int[][] tiles2 = {{5, 2, 0}, {4, 1, 3}, {8, 7, 6}};
    Board board1 = new Board(tiles1);
    Board board2 = new Board(tiles2);

    @Parameterized.Parameters(name = "{index}: Number of moves for [{0}]={2}")
    public static Iterable<Object[]> data() {
        String path = "";
        int counter = 0;
        for (final File fileEntry : folder.listFiles()) {
            //System.out.println("processing file: " + fileEntry.getName())
            counter++;
            if (counter == 144) break;
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
        fExpected2 = false;
        Board a = new Board(fInput);
        Board b = new Board(fInput);
        FileWriter myWriter = new FileWriter(destFolder + fileName);
        fResult = a.equals(b);
        myWriter.write("For " + fileName + " the result is: " + fResult);
        myWriter.close();
        File myObj = new File(destFolder, fileName);
        fActual = board1.equals(board2);
    }

    @Test
    public void test() throws IOException {
        //assertTrue(value);
        assertEquals(fExpected, fResult);
        assertEquals(fExpected2, fActual);
    }
}
