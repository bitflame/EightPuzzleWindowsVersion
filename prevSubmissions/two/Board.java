

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;


public class Board {
    private final int[][] tiles;
    private final int n;
    private Integer blankRow;
    private Integer blankCol;
    private final int hamming;
    private final int manhattan;
    private boolean solvable;

    private int Inversions() {
        int current = 0;
        int inversionCount = 0;
        int[] temp = new int[n * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                temp[k] = tiles[i][j];
                current = tiles[i][j];
                for (int l = 0; l <= k; l++) {
                    if (current == 0) continue;
                    if (current < temp[l]) inversionCount++;
                }
                k++;
            }
        }
        return inversionCount;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("The board you are submitting is empty.");
        }
        this.n = tiles[0].length;
        if (this.n < 2 || this.n > 128) {
            throw new IllegalArgumentException("The value of n is more than expected.");
        }
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (this.tiles[i][j] == 0) {
                    this.blankRow = i;
                    this.blankCol = j;
                }
            }
        }
        int distanceHamming = 0;
        for (char i = 0; i < n; i++) {
            for (char j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != (((i * tiles.length) + 1) + j)) {
                    distanceHamming++;
                }
            }
        }
        int distanceManhattan = 0;
        for (char i = 0; i < n; i++) {
            for (char j = 0; j < n; j++) {
                if (tiles[i][j] != 0) { //
                    int targetX = (tiles[i][j] - 1) / n;
                    int targetY = (tiles[i][j] - 1) % n;
                    int dx = i - targetX;
                    int dy = j - targetY;
                    distanceManhattan += Math.abs(dx) + Math.abs(dy);
                }
            }
        }
        this.manhattan = distanceManhattan;
        this.hamming = distanceHamming;
        if (((n * n) % 2 != 0) && (Inversions() & 1) == 1) solvable = false;
        else if (((n * n) % 2 != 0) && (Inversions() & 1) == 0) solvable = true;
        else if (((Inversions() + blankRow) % 2) == 0) solvable = false;
        else if (((Inversions() + blankRow) % 2) == 1) solvable = true;
    }


    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        return this.hamming;
    }

    // sum of Manhattan distances between tiles and goal
    // Here is where I got this from:
    //  https://www.coursera.org/learn/algorithms-part1/programming/iqOQi/8-puzzle/discussions/threads/2Fon3sA7EeevSwpBtQ053g
    public int manhattan() {
        return this.manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == 0) continue;
                if (this.tiles[i][j] != (n * i) + (j + 1)) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        for (char i = 0; i < n; i++) {
            for (char j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        // Commented this out initially b/c of autograder not expecting this error message and later b/c spotbug
        // for useless if statement.
        // if (blankCol == null || blankRow == null) {
        // throw new InvalidParameterException("There is something wrong with the Board data. You may be using numbers " +
        // "outside of what is allowed and should be used. ");
        // }
        ArrayList<Board> neighbors = new ArrayList<>();
        int[][] neighbor = copyBoard(this.tiles);
        int index = n - 1;
        if (blankRow < index) {// zero is less than n
            neighbor[blankRow + 1][blankCol] = 0;
            neighbor[blankRow][blankCol] = this.tiles[blankRow + 1][blankCol];
            neighbors.add(new Board(neighbor));
        }
        if (blankRow > 0) {
            neighbor = copyBoard(this.tiles);
            neighbor[blankRow - 1][blankCol] = 0;
            neighbor[blankRow][blankCol] = this.tiles[blankRow - 1][blankCol];
            neighbors.add(new Board(neighbor));
        }
        if (blankCol < index) {
            neighbor = copyBoard(this.tiles);
            neighbor[blankRow][blankCol + 1] = 0;
            neighbor[blankRow][blankCol] = this.tiles[blankRow][blankCol + 1];
            neighbors.add(new Board(neighbor));
        }
        if (blankCol > 0) {
            neighbor = copyBoard(this.tiles);
            neighbor[blankRow][blankCol - 1] = 0;
            neighbor[blankRow][blankCol] = this.tiles[blankRow][blankCol - 1];
            neighbors.add(new Board(neighbor));
        }
        ArrayList<Board> neiCopy = new ArrayList<Board>(neighbors);
        return neiCopy;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        // Commented this out initially b/c of autograder not expecting this error message and later b/c spotbug
        // for useless if statement.
        // if (blankCol == null || blankRow == null) {
        // throw new InvalidParameterException("There is something wrong with the Board data. You may be using numbers " +
        // "outside of what is allowed and should be used. ");
        // }
        int[][] tempTiles = copyBoard(this.tiles);
        if (blankRow < n - 1 && blankCol < n - 1) {
            int temp = this.tiles[blankRow + 1][blankCol + 1];
            int temp2 = this.tiles[blankRow][blankCol + 1];
            tempTiles[blankRow + 1][blankCol + 1] = temp2;
            tempTiles[blankRow][blankCol + 1] = temp;
        } else if (blankRow > 0 && blankCol > 0) {
            int temp = tiles[blankRow - 1][blankCol - 1];
            int temp2 = tiles[blankRow][blankCol - 1];
            tempTiles[blankRow - 1][blankCol - 1] = temp2;
            tempTiles[blankRow][blankCol - 1] = temp;
        } else if (blankRow > 0 && blankRow < n - 1) {
            int temp = tiles[blankRow - 1][blankCol];
            int temp2 = tiles[blankRow + 1][blankCol];
            tempTiles[blankRow + 1][blankCol] = temp;
            tempTiles[blankRow - 1][blankCol] = temp2;
        } else if (blankRow == 0 && blankCol == n - 1) {
            int temp = tiles[blankRow + 1][blankCol];
            int temp2 = tiles[blankRow][blankCol - 1];
            tempTiles[blankRow + 1][blankCol] = temp2;
            tempTiles[blankRow][blankCol - 1] = temp;
        } else if (blankRow > 0 && blankCol < n - 1) {
            int temp = tiles[blankRow - 1][blankCol];
            int temp2 = tiles[blankRow][blankCol + 1];
            tempTiles[blankRow - 1][blankCol] = temp2;
            tempTiles[blankRow][blankCol + 1] = temp;
        } else {
            // throw new InvalidParameterException("The board you submit did not match any of the rules.");
        }
        Board retBoard = new Board(tempTiles);
        return retBoard;
    }

    // Do not really need the following method. Delete it tomorrow
//    private int[][] TilesConvert(char[][] tiles) {
//        int[][] temp = new int[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < n; j++) {
//                temp[i][j] = (int) tiles[i][j];
//            }
//        }
//        return temp;
//    }

    private static int[][] copyBoard(int[][] b) {
        int[][] temp = new int[b.length][b.length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                temp[i][j] = b[i][j];
            }
        }
        return temp;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] testTiles = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}}; // 1
        int[][] testTiles0 = {{1, 2, 3}, {4, 5, 6}, {8, 0, 7}}; // 1
        int[][] testTiles1 = {{1, 2, 3}, {4, 0, 6}, {8, 5, 7}}; // 3
        int[][] testTiles2 = {{1, 2, 3}, {0, 4, 6}, {8, 5, 7}}; // 3
        int[][] testTiles3 = {{1, 2, 3}, {4, 6, 7}, {8, 5, 0}}; // 3
        int[][] testTiles4 = {{0, 1, 3}, {4, 2, 5}, {7, 8, 6}}; // 4
        int[][] testTiles5 = {{1, 0, 3}, {4, 2, 5}, {7, 8, 6}}; // 4
        int[][] testTiles6 = {{1, 2, 3}, {4, 0, 5}, {7, 8, 6}}; // 2
        int[][] testTiles7 = {{1, 2, 3}, {4, 5, 0}, {7, 8, 6}}; // 2
        int[][] testTiles8 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}; // 0 inversions
        Board tb = new Board(testTiles);
        Board tb0 = new Board(testTiles0);
        Board tb1 = new Board(testTiles1);
        Board tb2 = new Board(testTiles2);
        Board tb3 = new Board(testTiles3);
        Board tb4 = new Board(testTiles4);
        Board tb5 = new Board(testTiles5);
        Board tb6 = new Board(testTiles6);
        Board tb7 = new Board(testTiles7);
        Board tb8 = new Board(testTiles8);
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(tb);
        boards.add(tb0);
        boards.add(tb1);
        boards.add(tb2);
        boards.add(tb3);
        boards.add(tb4);
        boards.add(tb5);
        boards.add(tb6);
        boards.add(tb7);
        boards.add(tb8);
        for (Board b : boards) {
            StdOut.println("Board: " + b + "Board's Twin");
            StdOut.println("Board's neighbors and their twins : ");
            for (Board bn : b.neighbors()) {
                StdOut.println(bn + "" + bn.twin());
            }
        }
        // char[][] goalTiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        // StdOut.println("Original table: ");
        // StdOut.println(tb);
        // StdOut.println("Here are the neighbors: ");
//        for (Board b : tb.neighbors()) {
//            StdOut.println(b);
//            StdOut.println(b.compareTo(tb));
//        }
        // StdOut.println("The dimension is: " + tb.dimension());
        StdOut.println("The number of inversions are: ");
    }
}
