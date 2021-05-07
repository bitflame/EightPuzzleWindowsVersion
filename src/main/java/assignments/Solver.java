package assignments;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private boolean solvable;
    private int moves = 0;
    private final ArrayList<Object> solutionBoardList = new ArrayList<>();
    private final ArrayList<SearchNode> solutionList = new ArrayList<>();
    private final ArrayList<SearchNode> previousPath = new ArrayList<>();
    private List<SearchNode> originalList = new ArrayList<>();
    private List<SearchNode> twinsList = new ArrayList<>();
    int movesLimit = 0;
    int manhattanWeight = 10;

    public Solver(Board initialBoard) {
        movesLimit = ((initialBoard.manhattan() + 1) * 4);
        if (initialBoard == null) {
            throw new IllegalArgumentException("The Board object is empty.");
        }
        solvable = true; // for now
        if (initialBoard.isGoal()) {
            return;
        }
        SearchNode initialSearchNode = new SearchNode(initialBoard, 0,
                (initialBoard.manhattan() * manhattanWeight), null);
        Board currentTwinBoard = initialBoard.twin();
        if (currentTwinBoard.isGoal()) {
            solvable = false;
            return;
        }
        SearchNode initialTwinSearchNode = new SearchNode(currentTwinBoard, 0,
                (initialBoard.manhattan() * manhattanWeight), null);
        MinPQ<SearchNode> currentPriorityQueue = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.numOfMoves > o2.numOfMoves) return 1;
                if (o2.numOfMoves > o1.numOfMoves) return -1;
                if (o1.GetManhattan() > o2.GetManhattan()) return 10 * (o1.GetManhattan());
                if (o2.GetManhattan() > o1.GetManhattan()) return -10 * (o2.GetManhattan());
//                if (o1.GetManhattanPriority() > o2.GetManhattanPriority()) return 5;
//                if (o2.GetManhattanPriority() > o1.GetManhattanPriority()) return -5;

//                if (o1.GetManhattan() < o2.GetManhattan()) return 1;
//                if (o2.GetManhattan() > o1.GetManhattan()) return -1;
//                if (o1.GetPriority() > o2.GetPriority()) return 5;
//                if (o2.GetPriority() > o2.GetPriority()) return -5;
                // try returning 5 or something in above two lines next time if this does not work
                return 0;
            }
        });
        MinPQ<SearchNode> currentPriorityQueueTwin = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.numOfMoves > o2.numOfMoves) return 1;
                if (o2.numOfMoves > o1.numOfMoves) return -1;
                if (o1.GetManhattan() > o2.GetManhattan()) return 10 * (o1.GetManhattan());
                if (o2.GetManhattan() > o1.GetManhattan()) return -10 * (o2.GetManhattan());
//                if (o1.GetManhattanPriority() > o2.GetManhattanPriority()) return 5;
//                if (o2.GetManhattanPriority() > o1.GetManhattanPriority()) return -5;

//                if (o1.GetManhattan() < o2.GetManhattan()) return 1;
//                if (o2.GetManhattan() > o1.GetManhattan()) return -1;
//                if (o1.GetPriority() > o2.GetPriority()) return 5;
//                if (o2.GetPriority() > o2.GetPriority()) return -5;
                return 0;
            }
        });
        // put the first node in priority queues
        currentPriorityQueue.insert(initialSearchNode);

        currentPriorityQueueTwin.insert(initialTwinSearchNode);
        // Create a board for Goal
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
        // Take the first nodes out of Priority Queues, calculate the neighbors, and put them in the nodes arrays
        SearchNode minSearchNode = currentPriorityQueue.delMin();
        SearchNode minTwinNode = currentPriorityQueueTwin.delMin();
        moves++;
        for (Board tb : minTwinNode.GetCurrentBoard().neighbors()) {
            SearchNode temp1Twin = new SearchNode(tb, moves, (tb.manhattan() * manhattanWeight), minTwinNode);
            currentPriorityQueueTwin.insert(temp1Twin);
            //twinsList.add(temp1Twin);
        }
        for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
            SearchNode temp1 = new SearchNode(b, moves, (b.manhattan() * manhattanWeight),
                    minSearchNode);
            currentPriorityQueue.insert(temp1);
            //originalList.add(temp1);
        }

        while ((!minSearchNode.GetCurrentBoard().isGoal())) {
            /*While you have a neighbor of the minimum search node on the priority queue use it and populate
             * it, if and when you are out populate the priority queue with children of nodes that have the
             * same priority or lower. Look for a neighbor with equal Manhattan and Hamming values */
// check to see if it is the goal
            if (minSearchNode.GetCurrentBoard().isGoal()) {
                solvable = true;
                moves = minSearchNode.numOfMoves;
                while (!minSearchNode.GetCurrentBoard().equals(initialBoard)) {
                    solutionList.add(minSearchNode);
                    solutionBoardList.add(minSearchNode.GetCurrentBoard());
                    minSearchNode = minSearchNode.GetPrevSearchNode();
                }
                solutionList.add(initialSearchNode);
                solutionBoardList.add(initialBoard);
                return;
            } else {
                solvable = false;
            }
            // Get the next candidate
            minSearchNode = currentPriorityQueue.delMin();
            minTwinNode = currentPriorityQueueTwin.delMin();
            moves++;
            // populate the priority queues with more nodes
            for (Board tb : minTwinNode.GetCurrentBoard().neighbors()) {
                SearchNode temp1Twin = new SearchNode(tb, moves, (tb.manhattan() * manhattanWeight), minTwinNode);
                if (!tb.equals(minTwinNode.prevSearchNode.currentBoard)) { // make sure this line works
                    currentPriorityQueueTwin.insert(temp1Twin);
                    twinsList.add(temp1Twin);
                }
            }
            for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
                SearchNode temp1 = new SearchNode(b, moves, (b.manhattan() * manhattanWeight),
                        minSearchNode);
                // why not check to see if one of the children is the goal?
                if (temp1.GetCurrentBoard().isGoal()) {
                    solvable = true;
                    minSearchNode = temp1;
                    moves = minSearchNode.numOfMoves;
                    while (!minSearchNode.GetCurrentBoard().equals(initialBoard)) {
                        solutionList.add(minSearchNode);
                        solutionBoardList.add(minSearchNode.GetCurrentBoard());
                        minSearchNode = minSearchNode.GetPrevSearchNode();
                    }
                    solutionList.add(initialSearchNode);
                    solutionBoardList.add(initialBoard);
                    return;
                }
                if (!b.equals(minSearchNode.prevSearchNode.currentBoard) && (
                        !previousPath.contains(temp1))) {
                    currentPriorityQueue.insert(temp1);
                    originalList.add(temp1);
                }
            }
        }// very first loop -
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board
    public int moves() {
        if (solvable) {
            return this.moves;
        } else return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (solvable) {
            Collections.reverse(solutionBoardList);
            ArrayList<Board> tempArray = new ArrayList<>();
            for (Object o : solutionBoardList) {
                tempArray.add((Board) o);
            }
            return tempArray;
        } else
            return null;
    }

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board currentBoard;
        private final int numOfMoves;
        private final SearchNode prevSearchNode;

        private int manhattan;


        public SearchNode(Board b, int m, int manhattan, SearchNode prev) {
            currentBoard = b;
            numOfMoves = m;
            prevSearchNode = prev;
            this.manhattan = manhattan;
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
            return ((this.GetCurrentBoard().manhattan() * 4) + (numOfMoves));
        }

        public int GetManhattan() {
            return this.manhattan;
        }

        private int GetManhattanPriority() {
            return ((this.manhattan * 4) + this.numOfMoves);
        }

        public boolean equals(SearchNode o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            Board that = o.GetCurrentBoard();
            return this.GetCurrentBoard() == that;
        }

        @Override
        public int compareTo(SearchNode o) {
            if (this.GetManhattanPriority() > o.GetManhattanPriority()) return 1;
            if (this.GetManhattanPriority() < o.GetManhattanPriority()) return -1;
            return 0;
        }

    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] testTiles2 = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        //int[][] testTiles1 = {{5, 2, 3}, {4, 7, 0}, {8, 6, 1}};// puzzle 21 - actual = 29 moves
        //int[][] testTiles1 = {{8, 6, 7}, {2, 0, 4}, {3, 5, 1}};
        //Board testTilesBoard = new Board(testTiles1);
        Board testTiles2Board = new Board(testTiles2);
        //Solver s = new Solver(testTilesBoard);
        Solver s2 = new Solver(testTiles2Board);
        if (!s2.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves= " + s2.moves);
            for (Object o : s2.solutionBoardList) {
                Board b = (Board) o;
                StdOut.println(" board: " + b + " Hamming Distance of : " + b.hamming() +
                        " Manhattan Distance of : " + b.manhattan());
            }
        }
    }
}
