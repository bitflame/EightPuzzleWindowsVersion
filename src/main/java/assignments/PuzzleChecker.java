/******************************************************************************
 *  Compilation:  javac-algs4 PuzzleChecker.java
 *  Execution:    java-algs4 PuzzleChecker filename1.txt filename2.txt ...
 *  Dependencies: Board.java Solver.java
 *
 *  This program creates an initial board from each filename specified
 *  on the command line and finds the minimum number of moves to
 *  reach the goal state.
 *
 *  % java-algs4 PuzzleChecker puzzle*.txt
 *  puzzle00.txt: 0
 *  puzzle01.txt: 1
 *  puzzle02.txt: 2
 *  puzzle03.txt: 3
 *  puzzle04.txt: 4
 *  puzzle05.txt: 5
 *  puzzle06.txt: 6
 *  ...
 *  puzzle3x3-impossible: -1
 *  ...
 *  puzzle42.txt: 42
 *  puzzle43.txt: 43
 *  puzzle44.txt: 44
 *  puzzle45.txt: 45
 *
 ******************************************************************************/
package assignments;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class PuzzleChecker {

    public static void main(String[] args) {

        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = in.readInt();
            Board initial = new Board(tiles);
            //StdOut.println("The original board is: " + initial);
            // solve the puzzle
            Solver solver = new Solver(initial);
            // print solution to standard output
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Here is the list of moves that make up the solution: ");
                /*Had to comment out the following in order to submit to autograder */
                for (Object o : solver.solution()) {
                    Board b = (Board) o;
                    StdOut.println("The board: " + b + " It's Manhattan value: " + b.manhattan() +
                            " Its hamming value: " + b.hamming());
                }

                StdOut.println("The board is solvable, and the ");
                StdOut.println("Minimum number of moves = " + solver.moves());
            }
        }
    }

    private static void ConvertNumber(int number) {
        Stack<Character> s = new Stack<>();
        while (number > 0) {
            s.push(Character.forDigit((number % 10), 10));
            number = number / 10;
        }
        char[] numChar = new char[10];
        int i = 0;
        while (!s.isEmpty()) {
            numChar[i] = s.pop();
            i++;
        }
    }

    private static void BuildCharArrray(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        //String s = in.readAll();
        String[] ss = in.readAllStrings();
        for (String s : ss) {
            s.toCharArray();
        }
    }
}
