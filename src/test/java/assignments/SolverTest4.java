package assignments;

import edu.princeton.cs.algs4.In;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.util.ArrayList;

@RunWith(Parameterized.class)
public class SolverTest4 {
    private boolean fExpected;
    final static File folder = new File("C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\ModifiedTests");
    final static String destFolder = "C:\\Users\\Azizam\\IdeaProjects\\EightPuzzle\\src\\testresults";
    final static ArrayList<Object[]> filesList = new ArrayList<>();
    private static Object[] testInst;
    private static String fileName = "";
    private static Solver.SearchNode firstSameNode;
    private static Solver.SearchNode secondSameNode;

    @Parameterized.Parameters(name = "{index}: Two different search nodes of  {0} board are equal!")
    public static Iterable<Object[]> data() {
        String path = "";
        int counter = 0;
        for (final File fileEntry : folder.listFiles()) {
            //System.out.println("processing file: " + fileEntry.getName())
            counter++;
            if (counter == 3) break;
            path = destFolder + fileEntry;
            In in = new In(fileEntry.getAbsolutePath());
            fileName = fileEntry.getName();
            int n = in.readInt();
            int moves = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = in.readInt();
            Board b = new Board(tiles);
            Board c = new Board(tiles);
            firstSameNode = new Solver.SearchNode(b, null, 3, 5, 4);
            secondSameNode = new Solver.SearchNode(c, null, 3, 5, 4);
            testInst = new Object[]{fileName, b, moves, firstSameNode, secondSameNode};
            filesList.add(testInst);
        }
        return filesList;
//        return Arrays.asList(new Object[][]{{0, 0}, {1, 1}, {2, 1},
//                {3, 2}, {4, 3}, {5, 5}, {6, 8}});
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void equalsMethodShouldWork() {
        Assert.assertEquals(true, firstSameNode.equals(secondSameNode));
    }
}
