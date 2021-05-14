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
    boolean twinGoalHit = false;

    public Solver(Board initialBoard) {
        ArrayList<SearchNode> solutionList = new ArrayList<>();
        ArrayList<SearchNode> twinSolutionList = new ArrayList<>();
        List<SearchNode> originalList = new ArrayList<>();
        List<SearchNode> twinsList = new ArrayList<>();

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
        MinPQ<SearchNode> currentPriorityQueue = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.prevSearchNode.GetManhattanPriority() + o1.GetManhattan()
                        > o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan()) return 1;
                else if (o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan() >
                        o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan()) return -1;
                else return 0;
            }
        });
        MinPQ<SearchNode> currentPriorityQueueTwin = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.prevSearchNode.GetManhattanPriority() + o1.GetManhattan()
                        > o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan()) return 1;
                else if (o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan() >
                        o2.prevSearchNode.GetManhattanPriority() + o2.GetManhattan()) return -1;
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
//            if (tb.equals(gBoardTwin)) {
//                twinGoalHit = true;
//                SearchNode temp = temp1Twin;
//                while (!temp.equals(null)) {
//                    twinSolutionList.add(temp);
//                    temp = temp.prevSearchNode;
//                }
//            }
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
            /* Create a visited nodes array and keep track of actual cost of them ***********************************/
            if (minSearchNode.GetCurrentBoard().isGoal()) {
                break;
            } else {
                solvable = false;
            }
            if (minTwinNode.equals(gBoard.twin())) {
                /* Go back all the way to the initial twin node, while you add the twin of each previous node to
                the solution list until you get to the initial node
                 * search node
                 * solvable = true;
            moves = minSearchNode.numOfMoves;
            while (!minSearchNode.GetCurrentBoard().equals(initialBoard)) {
                solutionList.add(minSearchNode);
                solutionBoardList.add(minSearchNode.GetCurrentBoard());
                minSearchNode = minSearchNode.GetPrevSearchNode();
            }
            solutionList.add(initialSearchNode);
            solutionBoardList.add(initialBoard);
            return; */
                while (!minTwinNode.equals(initialTwinSearchNode)) {
                    twinSolutionList.add(minTwinNode);
                    minTwinNode = minTwinNode.prevSearchNode;
                }
                break;
            }
            //           int[][] t1 = new int[][]{{3, 1, 6, 4}, {5, 0, 9, 7}, {10, 2, 11, 8}, {13, 15, 14, 12}};
//            int[][] t2 = new int[][]{{3, 1, 6, 4}, {5, 2, 9, 7}, {10, 0, 11, 8}, {13, 15, 14, 12}};
//            int[][] t3 = new int[][]{{3, 1, 6, 4}, {5, 2, 9, 7}, {0, 10, 11, 8}, {13, 15, 14, 12}};
//            int[][] t4 = new int[][]{{3, 1, 6, 4}, {0, 2, 9, 7}, {5, 10, 11, 8}, {13, 15, 14, 12}};
//            int[][] t5 = new int[][]{{3, 1, 6, 4}, {2, 0, 9, 7}, {5, 10, 11, 8}, {13, 15, 14, 12}};
//            int[][] t6 = new int[][]{{3, 1, 6, 4}, {2, 9, 0, 7}, {5, 10, 11, 8}, {13, 15, 14, 12}};
//            int[][] t7 = new int[][]{{3, 1, 6, 4}, {2, 9, 11, 7}, {5, 10, 0, 8}, {13, 15, 14, 12}};
//            int[][] t8 = new int[][]{{3, 1, 6, 4}, {2, 9, 11, 7}, {5, 10, 14, 8}, {13, 15, 0, 12}};
//            int[][] t9 = new int[][]{{3, 1, 6, 4}, {2, 9, 11, 7}, {5, 10, 14, 8}, {13, 0, 15, 12}};
//            int[][] t10 = new int[][]{{3, 1, 6, 4}, {2, 9, 11, 7}, {5, 0, 14, 8}, {13, 10, 15, 12}};
            int[][] t11 = new int[][]{{3, 1, 6, 4}, {2, 0, 11, 7}, {5, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t12 = new int[][]{{3, 0, 6, 4}, {2, 1, 11, 7}, {5, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t13 = new int[][]{{0, 3, 6, 4}, {2, 1, 11, 7}, {5, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t14 = new int[][]{{2, 3, 6, 4}, {0, 1, 11, 7}, {5, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t15 = new int[][]{{2, 3, 6, 4}, {1, 0, 11, 7}, {5, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t16 = new int[][]{{2, 3, 6, 4}, {1, 11, 0, 7}, {5, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t17 = new int[][]{{2, 3, 0, 4}, {1, 11, 6, 7}, {5, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t18 = new int[][]{{2, 0, 3, 4}, {1, 11, 6, 7}, {5, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t19 = new int[][]{{0, 2, 3, 4}, {1, 11, 6, 7}, {5, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t20 = new int[][]{{1, 2, 3, 4}, {0, 11, 6, 7}, {5, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t21 = new int[][]{{1, 2, 3, 4}, {5, 11, 6, 7}, {0, 9, 14, 8}, {13, 10, 15, 12}};
//            int[][] t22 = new int[][]{{1, 2, 3, 4}, {5, 11, 6, 7}, {9, 0, 14, 8}, {13, 10, 15, 12}};
//            int[][] t23 = new int[][]{{1, 2, 3, 4}, {5, 0, 6, 7}, {9, 11, 14, 8}, {13, 10, 15, 12}};
//            int[][] t24 = new int[][]{{1, 2, 3, 4}, {5, 6, 0, 7}, {9, 11, 14, 8}, {13, 10, 15, 12}};
//            int[][] t25 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 0}, {9, 11, 14, 8}, {13, 10, 15, 12}};
//            int[][] t26 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 11, 14, 0}, {13, 10, 15, 12}};
//            int[][] t27 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 11, 14, 12}, {13, 10, 15, 0}};
//            int[][] t28 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 11, 14, 12}, {13, 10, 0, 15}};
//            int[][] t29 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 11, 0, 12}, {13, 10, 14, 15}};
//            int[][] t30 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 0, 11, 12}, {13, 10, 14, 15}};
//            int[][] t31 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 0, 14, 15}};
//            int[][] t32 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 0, 15}};
//            int[][] t33 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
//            Board board = new Board(t7);
            Board board1 = new Board(t11);
            // Get the next candidate if it is not in visited or it has a lower A* value
            //minSearchNode = currentPriorityQueue.delMin();
            //minTwinNode = currentPriorityQueueTwin.delMin();
            /* Modify the check of original loop here so you find the node with least priority */
            /* Is there a node in the original list that is a neighbor of the minimum search node and perhaps closer to
            the goal? If so, set minimum search node equal to the node. I need to see what I need to do below to get
            puzzle 11 working. */
            //for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
            //SearchNode temp = minSearchNode;
            //SearchNode neiSNode = new SearchNode(b, minSearchNode.numOfMoves + 1, b.manhattan(), minSearchNode);
            for (SearchNode s : originalList) {
                /* Check to see if there is a less costly path to the goal from minSearchNode i.e. is there a node that
                 * is a neighbor of minSearchNode that has less number of moves b/c its manhattan can not change. You
                 * also want to make sure that you would not select it using the currecntPriorityQueue */
                    /* o1.prevSearchNode.GetManhattanPriority() + o1.GetManhattan() - the comparison below might actually
                    have to be between minSearchNode's neighbors generated above and any other node. Not minSearchNode
                    itself.
                    **********************************Do Tomorrow****************************************
                    Try adding the node that costs less to the priority queue with the new cost instead of
                    setting the minSearchNode to it. That way you may not skip any search nodes. */
//                    if (s.GetCurrentBoard().equals(b) && s.prevSearchNode.GetManhattanPriority() + s.GetManhattan() <
//                            neiSNode.prevSearchNode.GetManhattanPriority() + neiSNode.GetManhattan() &&
//                            (!s.GetCurrentBoard().equals(minSearchNode.prevSearchNode.GetCurrentBoard())) &&
//                            (!s.GetCurrentBoard().equals(minSearchNode.GetCurrentBoard()))) {
//                        minSearchNode = s;
//                    }
                if (!currentPriorityQueue.isEmpty()) {
                    if (s.GetCurrentBoard().equals(currentPriorityQueue.min().GetCurrentBoard()) && s.GetPriority() <
                            currentPriorityQueue.min().GetPriority()) {
                        currentPriorityQueue.delMin();// Just delete it; do not need to visit it
                    }
                }
            }
            minSearchNode = currentPriorityQueue.delMin(); /* If it was not visited or it might provide a better path
            expand it */

            for (SearchNode tS : twinsList) {
                if (!currentPriorityQueueTwin.isEmpty()) {
                    if (tS.GetCurrentBoard().equals(currentPriorityQueueTwin.min().GetCurrentBoard()) && tS.GetPriority() <
                            currentPriorityQueueTwin.min().GetPriority()) {
                        currentPriorityQueueTwin.delMin();
                    } else {
                        minTwinNode = currentPriorityQueueTwin.delMin();
                    }
                }
            }
            //}

//            if (minSearchNode.GetCurrentBoard().equals(board1)) {
//                StdOut.println(" ......saw node 10 ..");
//            }
            // populate the priority queues with more nodes
            for (Board tb : minTwinNode.GetCurrentBoard().neighbors()) {
                if (tb.twin().isGoal()) twinGoalHit = true;
                SearchNode temp1Twin = new SearchNode(tb, minTwinNode.numOfMoves + 1, tb.manhattan(), minTwinNode);
                if (!tb.equals(minTwinNode.prevSearchNode.currentBoard)) { // make sure this line works
                    currentPriorityQueueTwin.insert(temp1Twin);
                    twinsList.add(temp1Twin);
                    if (tb.equals(gBoardTwin)) {
                        twinGoalHit = true;
                        SearchNode temp = temp1Twin;
                        while (!temp.equals(initialTwinSearchNode)) {
                            twinSolutionList.add(temp);
                            temp = temp.prevSearchNode;
                        }
                        twinSolutionList.add(temp);
                    }
                }
            }
            /* Check the twin list for nodes smaller than the minTwinNode and if there is a match with twin
             * of the goal */
//            if (twinGoalHit) {
//                SearchNode temp = minSearchNode;
//                for (SearchNode s : twinSolutionList) {
//                    /* Check to see if original list has the twin, if not, get the previous twin
//                     * node and check that one. Also check for neighbors  */
//                    for (SearchNode sol : originalList) {
//                        if (sol.GetCurrentBoard().equals(s.GetCurrentBoard().twin())) {
//                            if (sol.GetPriority() + sol.manhattan <
//                                    minSearchNode.GetPriority() + minSearchNode.GetManhattan()) {
//                                minSearchNode = sol;
//                                if (minSearchNode.GetCurrentBoard().isGoal()) {
//                                    solvable = true;
//                                    moves = minSearchNode.numOfMoves;
//                                    while (!minSearchNode.GetCurrentBoard().equals(initialBoard)) {
//                                        solutionList.add(minSearchNode);
//                                        solutionBoardList.add(minSearchNode.GetCurrentBoard());
//                                        minSearchNode = minSearchNode.GetPrevSearchNode();
//                                    }
//                                    solutionList.add(initialSearchNode);
//                                    solutionBoardList.add(initialBoard);
//                                    return;
//                                }
//                            }
//                        }
//                    }
//                }
//                if (!minSearchNode.GetCurrentBoard().equals(temp.GetCurrentBoard())) {
//                    currentPriorityQueue.insert(temp);
//                }
//            }

            /* add more nodes to priority queue and original list array */
            for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
                SearchNode temp1 = new SearchNode(b, minSearchNode.numOfMoves + 1, b.manhattan(),
                        minSearchNode);
                // why not check to see if one of the children is the goal?
                if (temp1.GetCurrentBoard().isGoal()) {
                    solvable = true;
                    minSearchNode = temp1;
                    break;
                }
                if (minSearchNode.prevSearchNode == null || !b.equals(minSearchNode.prevSearchNode.GetCurrentBoard())) {
                    currentPriorityQueue.insert(temp1);
                    originalList.add(temp1);
//                    if (b.equals(board1))
//                        StdOut.println(" put 10 in current priority queue and original list. ");
                }
            }

            /* Maybe a loop that goes through all the nodes in minimum priority queue, and only save the node with the
             * shortest path into a new priority queue, then save the results */
            //StdOut.println("Here is minimum search node after original list modification: " + minSearchNode.GetCurrentBoard());
        }// very first loop -
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
            /* does original list have a shorter path? Given, minSearchNode is the current path, comparing its priority
             * with previous nodes of any other path in the original list should tell me if there is a shorter path */
//            for (SearchNode s : originalList) {
//                if (minSearchNode.prevSearchNode != null) {
//                    if (s.GetCurrentBoard().equals(gBoard) && s.prevSearchNode.GetPriority() <
//                            minSearchNode.prevSearchNode.GetPriority()) {
//                        moves = s.numOfMoves;
//                        solutionList = new ArrayList<SearchNode>();
//                        solutionBoardList = new ArrayList<Board>();
//                        while (!s.GetCurrentBoard().equals(initialBoard)) {
//                            solutionList.add(s);
//                            solutionBoardList.add(minSearchNode.GetCurrentBoard());
//                            minSearchNode = minSearchNode.GetPrevSearchNode();
//                        }
//                        solutionList.add(initialSearchNode);
//                        solutionBoardList.add(initialBoard);
//                    }
//                }
//            }
        } else if (twinSolutionList.size() > 0) {
            moves = twinSolutionList.size();
            for (SearchNode ts : twinSolutionList) {
                Board tb = ts.GetCurrentBoard().twin();
                // add them all to the solution board list
                solutionBoardList.add(tb);
                solvable = true;
            }
            Board b = (Board) solutionBoardList.get(0);
            if (b.isGoal()) {
                solvable = true;
                moves = 0;
                SearchNode s = new SearchNode(b, moves, b.manhattan(), null);
                solutionList.add(s);
                for (int i = 1; i < solutionBoardList.size(); i++) {
                    moves++;
                    b = (Board) solutionBoardList.get(i);
                    SearchNode newS = new SearchNode(b, moves, b.manhattan(), s);
                    solutionList.add(newS);
                }
            } else {
                StdOut.println(" Twins produced a likely solution, but you need to look into why it does" +
                        "not lead to Goal. ");
            }
        } else {
            solvable = false;
        }
    }

    /* What if I set a counter for 500 or something and loop until it hits zero, then go through the array; not
     * the priority queue but the array, and see if the answer is in there. If not, continue for another 500 loops
     * or something? */
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
            return (this.GetCurrentBoard().manhattan() + (numOfMoves));
        }

        public int GetManhattan() {
            return this.manhattan;
        }

        private int GetManhattanPriority() {
            return (this.manhattan + this.numOfMoves);
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
