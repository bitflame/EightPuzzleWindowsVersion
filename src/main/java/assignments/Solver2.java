package assignments;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver2 {
    private boolean solvable;
    private int moves = 0;
    private ArrayList<Board> solutionBoardList = new ArrayList<>();
    private int step = 0;

    Solver2(Board initialBoard) {
        List<ArrayList<Board>> solutionAlternatives = new ArrayList<ArrayList<Board>>();
        if (initialBoard == null) {
            throw new IllegalArgumentException("The Board object is empty.");
        }
        solvable = true; // for now
        if (solvable == false) return;
        Solver2.SearchNode initialSearchNode = new Solver2.SearchNode(initialBoard, 0, null);
        MinPQ<SearchNode> currentPriorityQueue = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
            @Override
            public int compare(Solver2.SearchNode o1, Solver2.SearchNode o2) {
                if (o1.GetPriority() > o2.GetPriority()) return 1;
                else if (o2.GetPriority() > o1.GetPriority()) return -1;
                return 0;
            }
        });
        currentPriorityQueue.insert(initialSearchNode);
        int index = 1;
        int[][] goal = new int[initialBoard.dimension()][initialBoard.dimension()];
        for (int i = 0; i <= initialBoard.dimension() - 1; i++) {
            for (int j = 0; j <= initialBoard.dimension() - 1; j++) {
                goal[i][j] = (char) index;
                index++;
            }
        }
        goal[initialBoard.dimension() - 1][initialBoard.dimension() - 1] = 0;
        Board gBoard = new Board(goal);
        StdOut.println("Step: " + step + " priority: " + initialSearchNode.GetPriority() +
                " moves = " + initialSearchNode.numOfMoves + " manhattan: " + initialSearchNode.manhattanDist +
                "\n" + initialSearchNode.GetCurrentBoard());
        SearchNode minimumSearchNode = currentPriorityQueue.delMin();
        boolean keepProcessing = true;
        do {
            int neighborCount = 0;
            int matchingBoard = 0;
            for (Board b : minimumSearchNode.currentBoard.neighbors()) {
                SearchNode temp = new SearchNode(b, minimumSearchNode.numOfMoves + 1, minimumSearchNode);
                neighborCount++;
                if (minimumSearchNode.GetPrevSearchNode() == null && !b.equals(initialBoard)) {
                    currentPriorityQueue.insert(temp);

                } else if (minimumSearchNode.GetPrevSearchNode() != null &&
                        !b.equals(minimumSearchNode.GetPrevSearchNode().GetCurrentBoard())) {
                    currentPriorityQueue.insert(temp);
                }
                if (b.equals(gBoard)) {
                    solutionBoardList = new ArrayList<>();
                    moves = 0;
                    while (!temp.GetCurrentBoard().equals(initialBoard)) {
                        moves++;
                        temp = temp.prevSearchNode;
                        solutionBoardList.add(temp.currentBoard);
                    }
                    solutionAlternatives.add(solutionBoardList);
                    keepProcessing = false;
                }
            }
            step++;
            printPriorityQueueContent(currentPriorityQueue, step);
            minimumSearchNode = currentPriorityQueue.delMin();
        } while (keepProcessing);
        for (ArrayList solution : solutionAlternatives) {
            int pathLength = solution.size();
            if (solutionBoardList.size() > pathLength) {
                solutionBoardList = solution;
                moves = solution.size();
            }
        }

    }

    public void printPriorityQueueContent(MinPQ<SearchNode> m, int step) {
        for (Object o : m) {
            SearchNode s = (SearchNode) o;
            StdOut.println("Step: " + step + " priority: " + s.GetPriority() +
                    " moves = " + s.numOfMoves + " manhattan: " + s.manhattanDist +
                    "\n" + s.GetCurrentBoard());
        }
    }

    public int GetNumberOfMoves() {
        return this.moves;
    }

    class SearchNode implements Comparable<SearchNode> {
        private final Board currentBoard;
        //private final int manhattan;
        //private final int hamming;
        private final int numOfMoves;
        private final SearchNode prevSearchNode;
        private final int manhattanDist;
        private final int hammingDist;

        public SearchNode(Board b, int m, SearchNode prev) {
            currentBoard = b;
            numOfMoves = m;
            prevSearchNode = prev;
            this.manhattanDist = b.manhattan();
            this.hammingDist = b.hamming();
        }

        public int GetManhattanDist() {
            return this.manhattanDist;
        }

        public int GetHammingDist() {
            return this.hammingDist;
        }

        public Board GetCurrentBoard() {
            return currentBoard;
        }

        public int GetMovesCount() {
            return numOfMoves;
        }

        public SearchNode GetPrevSearchNode() {
            return prevSearchNode;
        }

        public int GetPriority() {
            return ((this.GetCurrentBoard().manhattan()) + (this.numOfMoves));
        }


        public boolean equals(SearchNode o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            Board that = (Board) o.GetCurrentBoard();
            return this.GetCurrentBoard() == that;
        }

        @Override
        public int compareTo(SearchNode o) {
            if (this.equals(o)) return 0;
            if (this.GetCurrentBoard().manhattan() > o.GetCurrentBoard().manhattan()) return 1;
            if (o.GetCurrentBoard().manhattan() > this.GetCurrentBoard().manhattan()) return -1;
            if (this.GetCurrentBoard().hamming() > o.GetCurrentBoard().hamming()) return 1;
            if (o.GetCurrentBoard().hamming() > this.GetCurrentBoard().hamming()) return -1;
            if (this.numOfMoves > o.numOfMoves) return 1;
            if (o.numOfMoves > this.numOfMoves) return -1;
            return -1;
        }


        public boolean isSolvable() {
            return solvable;
        }

        public Iterable<Board> solution() {
            if (solvable) {
                Collections.reverse(solutionBoardList);
                ArrayList<Board> tempArray = new ArrayList<>(solutionBoardList);
                return tempArray;
            } else
                return null;
        }

        public int moves() {
            if (solvable) {
                return this.numOfMoves;
            } else return -1;
        }
    }

    public static void main(String[] args) {
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    tiles[i][j] = in.readInt();
            Board initial = new Board(tiles);
            Solver2 solver = new Solver2(initial);
            // print solution to standard output
            if (!solver.solvable)
                StdOut.println("No solution possible");
            else {
                StdOut.println("Here is the list of moves that make up the solution: ");
                for (Board board : solver.solutionBoardList)
                    StdOut.println("The board: " + board + " It's Manhattan value: " + board.manhattan() + " Its hamming value: " + board.hamming());
                StdOut.println("The board is solvable, and the ");
                StdOut.println("Minimum number of moves = " + solver.moves);
            }
        }
    }
}

