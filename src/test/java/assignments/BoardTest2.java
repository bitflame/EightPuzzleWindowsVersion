package assignments;

import edu.princeton.cs.algs4.In;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class BoardTest2 {
    final static File neighborsTestsFolder = new
            File("C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\neighborstests\\");
    final static ArrayList<Object[]> filesList = new ArrayList<>();
    private static Object[] testInst;
    private static int expectedNeighborsCount = 0;
    private int[][] fInput;
    private boolean fExpected;
    private boolean fActual;
    final static String
            destFolder = "C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\board_test2_results\\";
    Board originalBoard;

    @Parameterized.Parameters(name = "{index}: Number of moves for [{0}]={2}")
    public static Iterable<Object[]> data() {
        List<Board> expectedNeighbors = new ArrayList<>();
        Board b;
        for (final File neighborFile : neighborsTestsFolder.listFiles()) {
            In in = new In(neighborFile.getAbsolutePath());
            String filename = neighborFile.getName();
            int n = in.readInt();
            while (!in.isEmpty()) {
                int[][] tiles = new int[n][n];
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        tiles[i][j] = in.readInt();
                int[][] neigh1 = new int[n][n];

                expectedNeighborsCount++;
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        neigh1[i][j] = in.readInt();
                b = new Board(neigh1);
                expectedNeighbors.add(b);
                expectedNeighborsCount++;
                int[][] neigh2 = new int[n][n];
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        neigh2[i][j] = in.readInt();
                b = new Board(neigh2);
                expectedNeighbors.add(b);
                try {
                    if (in.isEmpty()) throw new EOFException();
                    expectedNeighborsCount++;
                    int[][] neigh3 = new int[n][n];
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            neigh3[i][j] = in.readInt();
                    b = new Board(neigh3);
                    expectedNeighbors.add(b);
                } catch (EOFException e) {
                    //StdOut.println("reached the end of the input file for neighbors test for: " + neighborFile);
                }
                try {
                    if (in.isEmpty()) throw new EOFException();
                    expectedNeighborsCount++;
                    int[][] neigh4 = new int[n][n];
                    for (int i = 0; i < n; i++)
                        for (int j = 0; j < n; j++)
                            neigh4[i][j] = in.readInt();
                    b = new Board(neigh4);
                    expectedNeighbors.add(b);
                } catch (EOFException e) {
                    //StdOut.println("reached the end of the input file for neighbors test for: " + neighborFile);
                }
                testInst = new Object[]{filename, tiles, expectedNeighbors, expectedNeighborsCount};
                expectedNeighbors = new ArrayList<>();
                expectedNeighborsCount = 0;
                filesList.add(testInst);
            }
        }// added up to here for the neighbors test
        return filesList;
    }

    public BoardTest2(String filename, int[][] tiles, List<int[][]> neighbors, int expectedNeighborsCount) throws
            IOException {
        fInput = tiles;
        fExpected = true;
        Board a = new Board(fInput);
        // check to make sure all the expected neighbors are in the generated neighbors
        for (Board b : a.neighbors()) {
            if (neighbors.contains(b)) fActual = true;
            else fActual = false;
        }
        originalBoard = new Board(tiles);
        FileWriter myWriter = new FileWriter(destFolder + filename);
        myWriter.write(" Here is the original board: " + originalBoard +
                "Here is the expected neighbors: " + neighbors +
                " Here are the neighbors generated by the program: " +
                a.neighbors());
        myWriter.close();
    }

    @Test
    public void neighborsTest() throws IOException {
        assertEquals(fExpected, fActual);
    }
}
