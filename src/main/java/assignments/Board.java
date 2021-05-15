package assignments;

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
        int index = 1;

        int[][] goal = new int[n][n];
        for (int i = 0; i <= n - 1; i++) {
            for (int j = 0; j <= n - 1; j++) {
                goal[i][j] = index;
                index++;
            }
        }
        goal[n - 1][n - 1] = 0;
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
                if (tiles[i][j] != goal[i][j] && tiles[i][j] != 0) { //
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
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        //Board temp = (Board) y;
        int length = ((Board) y).n;
        if (this.n != length) return false;
        if (this == y) return true;
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
        ArrayList<Board> neighbors = new ArrayList<>();
        int[][] neighbor = copyBoard(this.tiles);
        int index = n - 1;
        if (blankCol < index) { // Right move
            neighbor[blankRow][blankCol + 1] = 0;
            neighbor[blankRow][blankCol] = this.tiles[blankRow][blankCol + 1];
            neighbors.add(new Board(neighbor));
        }
        if (blankRow < index) { // zero is less than n - Up move
            neighbor = copyBoard(this.tiles);
            neighbor[blankRow + 1][blankCol] = 0;
            neighbor[blankRow][blankCol] = this.tiles[blankRow + 1][blankCol];
            neighbors.add(new Board(neighbor));
        }
        if (blankRow > 0) {  // Down move
            neighbor = copyBoard(this.tiles);
            neighbor[blankRow - 1][blankCol] = 0;
            neighbor[blankRow][blankCol] = this.tiles[blankRow - 1][blankCol];
            neighbors.add(new Board(neighbor));
        }
        if (blankCol > 0) { // Left move
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
        }
        Board retBoard = new Board(tempTiles);
        return retBoard;
    }

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

    }
}
