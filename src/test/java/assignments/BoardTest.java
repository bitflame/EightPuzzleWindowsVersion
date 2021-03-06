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
    private boolean fExpected1;
    private boolean fExpected2;
    private boolean fExpected3;
    private boolean fExpected4;
    private boolean fExpected5;
    private boolean fExpected6;
    private boolean fActual;
    private boolean fActual1;
    private boolean fActual2;
    private boolean fActual3;
    private boolean fActual4;
    private boolean fActual5;
    private boolean fActual6;
    final static File folder = new File("C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\ModifiedTests");
    final static String destFolder = "C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\board_test_results\\";
    final static ArrayList<Object[]> filesList = new ArrayList<>();
    private static Object[] testInst;
    // test 2
    int[][] tiles1 = {{5, 0, 2}, {4, 1, 3}, {8, 7, 6}};
    int[][] tiles2 = {{5, 2, 0}, {4, 1, 3}, {8, 7, 6}};
    int[][] tiles3 = {{5, 0, 2, 15}, {4, 1, 3, 14}, {8, 7, 6, 13}, {9, 10, 11, 12}};
    int[][] tiles4 = {{5, 0, 2}, {4, 1, 3}, {8, 7, 6}};
    Board board1 = new Board(tiles1);
    Board board2 = new Board(tiles2);
    Board board3 = new Board(tiles3);
    Board board4 = new Board(tiles4);

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
        fExpected1 = false;
        fExpected2 = false;
        fExpected3 = true;
        fExpected4 = true;
        fExpected5 = true;
        fExpected6 = false;
// Reflexive Test for equals
        Board a = new Board(fInput);
        Board b = new Board(fInput);
        FileWriter myWriter = new FileWriter(destFolder + fileName);
        fActual = a.equals(b);
        myWriter.write("For " + fileName + " the result is: " + fActual);
        myWriter.close();
        File myObj = new File(destFolder, fileName);
        fActual1 = board1.equals(board2);
        fActual2 = board3.equals(board2);
        // Transitive test for equals
        fActual3 = board4.equals(board1);
        fActual4 = board1.equals(board4);
        fActual5 = board1.equals(board1);
        fActual6 = board1.equals(null);
    }

    @Test
    public void bulk_test() {
        assertEquals(fExpected, fActual);
        assertEquals(fExpected1, fActual1);
        // Testing arrays of different sizes
        assertEquals(fExpected2, fActual2);
        assertEquals(fExpected3, fActual3);
        assertEquals(fExpected4, fActual4);
        assertEquals(fExpected5, fActual5);
        assertEquals(fExpected6, fActual6);
    }
}
