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
    private ArrayList<Board> solutionBoardList = new ArrayList<>();

    public Solver(Board initialBoard) {
        ArrayList<SearchNode> solutionList = new ArrayList<>();
        List<SearchNode> originalList = new ArrayList<>();
        List<SearchNode> twinsList = new ArrayList<>();
        SearchNode[] goals = new SearchNode[1];
        int goalsIndex = 0;
        if (initialBoard == null) {
            throw new IllegalArgumentException("The Board object is empty.");
        }
        // initialize Searchnodes, twins, and associated boards
        SearchNode initialSearchNode = new SearchNode(initialBoard, 0,
                (initialBoard.manhattan()), null);
        Board currentTwinBoard = initialBoard.twin();
        if (currentTwinBoard.isGoal()) {
            solvable = false;
            return;
        }
        SearchNode initialTwinSearchNode = new SearchNode(currentTwinBoard, 0,
                (initialBoard.manhattan()), null);
        // Create priority queues
//        MinPQ<SearchNode> currentPriorityQueue = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
//            @Override
//            public int compare(SearchNode o1, SearchNode o2) {
//                if (o1.prevSearchNode.GetManhattanPriority() + o1.GetManhattan()
//                        > o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan()) return 1;
//                else if (o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan() >
//                        o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan()) return -1;
//                else return 0;
//            }
//        });
//        MinPQ<SearchNode> currentPriorityQueueTwin = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
//            @Override
//            public int compare(SearchNode o1, SearchNode o2) {
//                if (o1.prevSearchNode.GetManhattanPriority() + o1.GetManhattan()
//                        > o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan()) return 1;
//                else if (o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan() >
//                        o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan()) return -1;
//                else return 0;
//            }
//        });
        MinPQ<SearchNode> currentPriorityQueue = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.prevSearchNode.numOfMoves + o1.GetManhattan()
                        > o2.prevSearchNode.numOfMoves + o2.GetManhattan()) return 1;
                else if (o2.prevSearchNode.numOfMoves + o2.GetManhattan() >
                        o1.prevSearchNode.numOfMoves + o1.GetManhattan()) return -1;
                else return 0;
            }
        });
        MinPQ<SearchNode> currentPriorityQueueTwin = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.prevSearchNode.numOfMoves + o1.GetManhattan()
                        > o2.prevSearchNode.numOfMoves + o2.GetManhattan()) return 1;
                else if (o2.prevSearchNode.numOfMoves + o2.GetManhattan() >
                        o1.prevSearchNode.numOfMoves + o1.GetManhattan()) return -1;
                else return 0;
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
        /* Twin of the goal board is the goal of the twin of the initial board and it can lead to the goal board */
        Board gBoard = new Board(goal);
        Board gBoardTwin = gBoard.twin();
        // Take the first nodes out of Priority Queues, calculate the neighbors, and put them in the nodes arrays
        SearchNode minSearchNode = currentPriorityQueue.delMin();
        SearchNode minTwinNode = currentPriorityQueueTwin.delMin();
        for (Board tb : minTwinNode.GetCurrentBoard().neighbors()) {
            SearchNode temp1Twin = new SearchNode(tb, minTwinNode.numOfMoves + 1, tb.manhattan(),
                    minTwinNode);
            currentPriorityQueueTwin.insert(temp1Twin);
            twinsList.add(temp1Twin);
        }
        for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
            SearchNode temp1 = new SearchNode(b, minSearchNode.numOfMoves + 1, b.manhattan(),
                    minSearchNode);
            currentPriorityQueue.insert(temp1);
            originalList.add(temp1);
        }

        while ((!currentPriorityQueue.isEmpty())) {
// check to see if minSearchNode is the answer
            if (minSearchNode.GetCurrentBoard().isGoal()) {
//                goals[goalsIndex] = minSearchNode;
//                goalsIndex++;
//                SearchNode[] temp = new SearchNode[goalsIndex + 1];
//                for (int i = 0; i < goalsIndex; i++) {
//                    temp[i] = goals[i];
//                }
//                goals = temp;
//                minSearchNode = currentPriorityQueue.delMin();
                break;
            } else {
                solvable = false;
            }
            for (SearchNode s : originalList) {
                /* Check to see if there is a less costly path to the goal from minSearchNode i.e. is there a node that
                 * is a neighbor of minSearchNode that has less number of moves b/c its manhattan can not change. You
                 * also want to make sure that you would not select it using the currentPriorityQueue. Also check for
                 * nodes that have the same board, but one's priority is higher i.e. one's number of moves is better
                 * than the other */
                for (SearchNode priorityNode : currentPriorityQueue) {

                }
                if (!currentPriorityQueue.isEmpty()) {
                    if (s.GetCurrentBoard().equals(currentPriorityQueue.min().GetCurrentBoard()) &&
                            s.numOfMoves < currentPriorityQueue.min().numOfMoves
                        /*s.GetPriority() <= currentPriorityQueue.min().GetPriority()*/) {
                        // Just delete it; do not need to visit it
                        currentPriorityQueue.delMin();
                    }
                }
            }

            /* If it was not visited or it might provide a better path expand it */
            minSearchNode = currentPriorityQueue.delMin();


            for (SearchNode tS : twinsList) {
                if (!currentPriorityQueueTwin.isEmpty()) {
                    if (tS.GetCurrentBoard().equals(currentPriorityQueueTwin.min().GetCurrentBoard()) && tS.GetPriority()
                            < currentPriorityQueueTwin.min().GetPriority()) {
                        currentPriorityQueueTwin.delMin();
                    } else {
                        minTwinNode = currentPriorityQueueTwin.delMin();
                    }
                }
            }
            // populate the priority queues with more nodes
            for (Board tb : minTwinNode.GetCurrentBoard().neighbors()) {

                SearchNode temp1Twin = new SearchNode(tb, minTwinNode.numOfMoves + 1, tb.manhattan(), minTwinNode);
                if (!tb.equals(minTwinNode.prevSearchNode.currentBoard)) { // make sure this line works
                    currentPriorityQueueTwin.insert(temp1Twin);
                    twinsList.add(temp1Twin);
                }
            }
            /* add more nodes to priority queue and original list array */
            for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
                SearchNode temp1 = new SearchNode(b, minSearchNode.numOfMoves + 1, b.manhattan(),
                        minSearchNode);
                // why not check to see if one of the children is the goal?
//                if (temp1.GetCurrentBoard().isGoal()) {
//                    solvable = true;
//                    minSearchNode = temp1;
//                    goals[goalsIndex] = minSearchNode;
//                    goalsIndex++;
//                    SearchNode[] temp = new SearchNode[goalsIndex++];
//                    for (int i = 0; i < goalsIndex; i++) {
//                        temp[i] = goals[i];
//                    }
//                    goals = temp;
                //break;
                //}
                if (minSearchNode.prevSearchNode == null || !b.equals(minSearchNode.prevSearchNode.GetCurrentBoard())) {
                    currentPriorityQueue.insert(temp1);
                    originalList.add(temp1);
                }
            }
        }// very first loop -
        /* Create a jagged array for all possible solutions, find the paths, then take the shortest path */
//        Board[][] solutions = new Board[goalsIndex - 1][];
//        for (SearchNode s : goals) {
//            minSearchNode = s;
//            moves = 0;
//            goalsIndex = 0;// use it to point to each solution
//            while (!minSearchNode.GetCurrentBoard().equals(initialBoard)) {
//                moves++;
//                solutions[goalsIndex][moves] = minSearchNode.GetCurrentBoard();
//                //solutionList.add(minSearchNode);
//                //solutionBoardList.add(minSearchNode.GetCurrentBoard());
//                minSearchNode = minSearchNode.GetPrevSearchNode();
//            }
//            //solutionList.add(initialSearchNode);
//            //solutionBoardList.add(initialBoard);
//            solutions[goalsIndex][moves + 1] = initialBoard;
//            goalsIndex++;
//        }
//        for (int i = 0; i < goalsIndex; i++) {
//            if (solutions[i].length < solutions[i + 1].length) {
//                solutionBoardList = new ArrayList<Board>(Arrays.asList(solutions[i]));
//            } else {
//                solutionBoardList = new ArrayList<Board>(Arrays.asList(solutions[i + 1]));
//            }
//        }
        if (minSearchNode.GetCurrentBoard().isGoal()) {
            solvable = true;
            //moves = minSearchNode.numOfMoves;
            moves = 0;
            while (!minSearchNode.GetCurrentBoard().equals(initialBoard)) {
                moves++;
                solutionList.add(minSearchNode);
                solutionBoardList.add(minSearchNode.GetCurrentBoard());
                minSearchNode = minSearchNode.GetPrevSearchNode();
            }
            solutionList.add(initialSearchNode);
            solutionBoardList.add(initialBoard);
        } else {
            solvable = false;
        }
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


        public SearchNode GetPrevSearchNode() {
            return prevSearchNode;
        }

        public int GetPriority() {
            return (this.GetCurrentBoard().manhattan() + (numOfMoves));
        }

        public int GetManhattan() {
            return this.manhattan;
        }

//        private int GetManhattanPriority() {
//            return (this.manhattan + this.numOfMoves);
//        }

        public boolean equals(SearchNode o) {
            if (this == o) return true;
            if (o == null) return false;
            if (this.getClass() != o.getClass()) return false;
            Board that = o.GetCurrentBoard();
            return this.GetCurrentBoard() == that;
        }

        @Override
        public int compareTo(SearchNode o) {
            if (this.GetPriority() > o.GetPriority()) return 1;
            if (this.GetPriority() < o.GetPriority()) return -1;
            return 0;
        }

    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] tiles = new int[][]{{1, 2, 3, 4, 5, 7, 14}, {8, 9, 10, 11, 12, 13, 6}, {15, 16, 17, 18, 19, 20, 21},
                {22, 23, 24, 25, 26, 27, 28}, {29, 30, 31, 32, 0, 33, 34}, {36, 37, 38, 39, 40, 41, 35},
                {43, 44, 45, 46, 47, 48, 42}};
        //int[][] testTiles1 = {{5, 2, 3}, {4, 7, 0}, {8, 6, 1}};// puzzle 21 - actual = 29 moves
        //int[][] testTiles1 = {{8, 6, 7}, {2, 0, 4}, {3, 5, 1}};
        //Board testTilesBoard = new Board(testTiles1);
        int[][] tilesVIII = new int[][]{{8, 3, 7}, {1, 5, 6}, {4, 2, 0}};
        Board tilesVIIIBoard = new Board(tilesVIII);
        Board tilesVIIIBoardTwin = tilesVIIIBoard.twin();

        Board testTiles2Board = new Board(tiles);
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
