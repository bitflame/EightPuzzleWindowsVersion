package assignments;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class Solver {
    private boolean solvable;
    public int moves = 0;
    public final ArrayList<Board> solutionBoardList = new ArrayList<>();
    public final ArrayList<SearchNode> solutionList = new ArrayList<>();
    private int blankCol;
    private int blankRow;
    private SearchNode[] nodes = new SearchNode[100];
    private SearchNode[] tempNodes = new SearchNode[100];
    private SearchNode[] twinNodes = new SearchNode[100];
    private SearchNode[] tempTwinNodes = new SearchNode[100];
    private int nodeIndex;
    private int twinNodeIndex;

    public Solver(Board initialBoard) {
        if (initialBoard == null) {
            throw new IllegalArgumentException("The Board object is empty.");
        }
        solvable = true; // for now
        if (initialBoard.isGoal()) {
            return;
        }
        SearchNode initialSearchNode = new SearchNode(initialBoard, 0, initialBoard.manhattan(), initialBoard.hamming(), null);
        Board currentTwinBoard = initialBoard.twin();
        if (currentTwinBoard.isGoal()) {
            solvable = false;
            return;
        }
        SearchNode initialTwinSearchNode = new SearchNode(currentTwinBoard, 0, initialBoard.manhattan(),
                initialBoard.hamming(), null);
        MinPQ<SearchNode> currentPriorityQueue = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.GetManhattanPriority() > o2.GetManhattanPriority()) return 1;
                if (o2.GetManhattanPriority() > o1.GetManhattanPriority()) return -1;
                return 0;
            }
        });
        MinPQ<SearchNode> currentPriorityQueueTwin = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.GetManhattanPriority() > o2.GetManhattanPriority()) return 1;
                if (o2.GetManhattanPriority() > o1.GetManhattanPriority()) return -1;
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
            SearchNode temp1Twin = new SearchNode(tb, moves, tb.manhattan(), tb.hamming(), minTwinNode);
            if (!tb.equals(initialTwinSearchNode.GetCurrentBoard())) {
                currentPriorityQueueTwin.insert(temp1Twin);
                twinNodes[twinNodeIndex] = temp1Twin;
                twinNodeIndex++;
            }
        }
        for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
            SearchNode temp1 = new SearchNode(b, moves, b.manhattan(), b.hamming(),
                    minSearchNode);
            if (!b.equals(initialBoard)) {
                currentPriorityQueue.insert(temp1);
                nodes[nodeIndex] = temp1;
                nodeIndex++;
            }
        }
        minTwinNode = currentPriorityQueueTwin.delMin();
        minSearchNode = currentPriorityQueue.delMin();
        boolean loopCond = true;
        outerloop:
        while (loopCond) {
            moves++;
            if (minTwinNode.GetCurrentBoard().isGoal()) {
                //StdOut.println("Matched the goal in the twin priority queue.");
                solvable = false;
                break;
            } else if (minSearchNode.GetCurrentBoard().isGoal()) {
                solvable = true;
                break;
            }
            int tempTwinNodeIndex = 0;
            for (SearchNode s : twinNodes) {
                for (Board tb : s.GetCurrentBoard().neighbors()) {
                    SearchNode temp1Twin = new SearchNode(tb, moves, tb.manhattan(), tb.hamming(), s);
                    if (!tb.equals(minTwinNode.GetPrevSearchNode().GetCurrentBoard())) {
                        currentPriorityQueueTwin.insert(temp1Twin);
                        tempTwinNodes[tempTwinNodeIndex] = temp1Twin;
                        tempTwinNodeIndex++;
                    }
                }
            }
            int tempNodeIndex = 0;
            for (SearchNode s : nodes) {
                for (Board b : s.GetCurrentBoard().neighbors()) {
                    SearchNode temp1 = new SearchNode(b, moves, b.manhattan(), b.hamming(),
                            s);
                    if (!b.equals(minSearchNode.GetPrevSearchNode().GetCurrentBoard())) {
                        currentPriorityQueue.insert(temp1);
                        tempNodes[tempNodeIndex] = temp1;
                        tempNodeIndex++;
                    }
                }
            }
            // Take the next node out of priority queue
            minTwinNode = currentPriorityQueueTwin.delMin();
            minSearchNode = currentPriorityQueue.delMin();
            /* reset nodes and twinNodes arrays, then copy the both temp SearchNode arrays into the main ones, then
             * reset the priority queues also */
            nodes = new SearchNode[tempNodeIndex];
            twinNodes = new SearchNode[tempTwinNodeIndex];
            nodeIndex = 0;
            for (int i = 0; i < tempNodeIndex; i++) {
                nodes[i] = tempNodes[i];
                nodeIndex++;
            }
            // reset the tempNodes array
            tempNodes = new SearchNode[100];
            twinNodeIndex = 0;
            for (int i = 0; i < tempTwinNodeIndex; i++) {
                twinNodes[i] = tempTwinNodes[i];
                twinNodeIndex++;
            }
            // reset twinTempNodes array
            tempTwinNodes = new SearchNode[100];
            // reset the priority queues
            currentPriorityQueue = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
                @Override
                public int compare(SearchNode o1, SearchNode o2) {
                    if (o1.GetManhattanPriority() > o2.GetManhattanPriority()) return 1;
                    if (o2.GetManhattanPriority() > o1.GetManhattanPriority()) return -1;
                    return 0;
                }
            });
            currentPriorityQueueTwin = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
                @Override
                public int compare(SearchNode o1, SearchNode o2) {
                    if (o1.GetManhattanPriority() > o2.GetManhattanPriority()) return 1;
                    if (o2.GetManhattanPriority() > o1.GetManhattanPriority()) return -1;
                    return 0;
                }
            });
        }
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
        } else {
            solvable = false;
        }
    }

    private void staticDb() {
        ArrayList<Board> boards = new ArrayList<>();
        int[][] t1 = {{1, 0}, {3, 2}};
        int t1_Moves = 1;
        int[][] t2 = {{1, 2}, {0, 3}};
        int t2_Moves = 1;
        int[][] t3 = {{0, 1}, {3, 2}};
        int t3_Moves = 2;
        int[][] t4 = {{3, 1}, {0, 2}};
        int t4_Moves = 3;
        int[][] t5 = {{2, 3}, {1, 0}};
        int t5_Moves = 4;
        int[][] t6 = {{2, 3}, {0, 1}};
        int t6_Moves = 5;
        int[][] t7 = {{0, 3}, {2, 1}};
        int t7_Moves = 6;
    }

    private void createShortCutDB(int x, int y, int z) {
        //int[][] testTiles = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};
        int[][] t = {{y, 0}, {x, z}};
        Board b = new Board(t);
    }

    private void arrangeImmediateNeighbors(int[][] t) {
        List<Integer> Positions = new ArrayList<Integer>();
        List<Integer> Values = new ArrayList<Integer>();
        // check blank row +/- 1 and blank col +/- 1
        int n = t.length;
        if (blankCol == 0) {
            if (blankRow == 0) {
                // first column first row
                // check col 2
                Values.add(t[blankRow][blankCol + 1]);
                Values.add(t[blankRow + 1][blankCol]);
                Values.add(t[blankRow + 1][blankCol + 1]);
                Positions.add(1);
                Positions.add(2);
                Positions.add(n + 1);
                for (int p : Positions) {
                    for (int v : Values) {
                        if (v == p) {
                            Positions.remove(p);
                            Values.remove(v);
                        }
                    }
                }
                if (Positions.isEmpty() && Values.isEmpty()) {
                    // check the database and swap values
                }
                // check row 2
            } else if (blankRow == n) {
                // last row first column
                Values.add(t[blankRow - 1][blankCol]);
                Values.add(t[blankRow - 1][blankCol + 1]);
                Values.add(t[blankRow][blankCol + 1]);
                Positions.add((blankRow - 1) * n);
                Positions.add((blankRow - 1) * n + 1);
                Positions.add(blankRow + 1);
                for (int p : Positions) {
                    for (int v : Values) {
                        if (v == p) {
                            Positions.remove(p);
                            Values.remove(v);
                        }
                    }
                }
                if (Positions.isEmpty() && Values.isEmpty()) {
                    // check the database and swap values
                }
            } else {
                // any other row. Looks like you have to check all the corners
                // create another method for evaluating the neighboring cells for possible database match
            }
        } else if (blankCol == n) {
            if (blankRow == 0) {
                // first row last column
                Values.add(t[blankRow][blankCol - 1]);
                Values.add(t[blankRow + 1][blankCol - 1]);
                Values.add(t[blankRow + 1][blankCol]);
                Positions.add(blankCol - 1);
                Positions.add(((blankCol) * 2) - 1);
                Positions.add((blankCol) * 2);
            } else if (blankRow == n) {
                // last row last column
            } else {
                // any other row
            }
        } else {
            // we are somewhere in the middle
        }

    }

    private void locateBlank(int[][] t) {
        int n = t.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (t[i][j] == 0) {
                    this.blankRow = i;
                    this.blankCol = j;
                    // check to see if t[balnkRow][blankCol]+/-1= (blanRow+/-1)(blankCol+/-1) and if so then check the
                    // database for a match
                    arrangeImmediateNeighbors(t);
                }
            }
        }
    }

    private Solver() {
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    private int[] applyCycle(int[] initialTiles, int[] cycle) {
        //int temp;
        for (int i = 0; i < cycle.length - 1; i++) {
//            temp = initialTiles[cycle[i] - 1];
//            initialTiles[cycle[i] + 1] = initialTiles[cycle[i] - 1];
//            initialTiles[cycle[i] - 1] = temp;
            for (int j = 0; j < initialTiles.length - 1; j++) {
                if (i == cycle.length - 1) initialTiles[cycle[0] - 1] = initialTiles[cycle[cycle.length - 1]];
                else initialTiles[cycle[i + 1]] = initialTiles[j];
            }
        }
        return initialTiles;
    }

    // If you have more than one cycle use the method below to apply each cycle. Cycles is a jagged array; two dimensional
    // array of different length
    private int[] applyCycles(int[] initialTiles, int[][] cycles) {
        for (int[] c : cycles) {
            applyCycle(initialTiles, c);
        }
        return initialTiles;
    }

    private int[] changeToOneDemArray(int[][] initialTiles) {
        int N = initialTiles[0].length;
        int[] result = new int[N * N];
        int counter = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result[counter] = initialTiles[i][j];
                counter++;
            }
        }
        return result;
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
            ArrayList<Board> tempArray = new ArrayList<>(solutionBoardList);
            return tempArray;
        } else
            return null;
    }

    private static class GameTree<Key extends Comparable<Key>, Value> {
        private node root;


        private class node {
            private final Key key;
            private Value val;
            private node left, right;
            private int N;


            public node(Key key, Value val, int N) {
                this.key = key;
                this.val = val;
                this.N = N;
            }
        }

        public Value get(Key key) {
            return get(root, key);
        }

        public Iterable<Key> keys() {
            return keys(min(), max());
        }

        public Iterable<Key> keys(Key lo, Key hi) {
            Queue<Key> queue = new Queue<Key>();
            keys(root, queue, lo, hi);
            return queue;
        }

        public void keys(node x, Queue<Key> queue, Key lo, Key hi) {
            if (x == null) return;
            int cmplo = lo.compareTo(x.key);
            int cmphi = hi.compareTo(x.key);
            if (cmplo < 0) keys(x.left, queue, lo, hi);
            if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
            if (cmphi > 0) keys(x.right, queue, lo, hi);
        }

        private void inorder(node x, Queue<Key> q) {
            if (x == null) return;
            inorder(x.left, q);
            q.enqueue(x.key);
            inorder(x.right, q);
        }

        private Value get(node x, Key key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) return get(x.left, key);
            else if (cmp > 0) return get(x.right, key);
            else return x.val;
        }

        public int size() {
            return size(root);
        }

        private int size(node x) {
            if (x == null) return 0;
            else return x.N;
        }

        public void put(Key key, Value val) {
            root = put(root, key, val);
        }

        private node put(node x, Key key, Value val) {
            if (x == null) return new node(key, val, 1);
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x.left = put(x.left, key, val);
            else if (cmp > 0) x.right = put(x.right, key, val);
            else x.val = val;
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }

        // M P TR_TAIL_RECURSION TR: Method Solver$GameTree.min(Solver$GameTree$node) employs tail recursion
        // At Solver.java:[line 857]  <- spotbugs error
        //M P TR_TAIL_RECURSION TR: Method Solver$GameTree.max(Solver$GameTree$node) employs tail recursion
        // At Solver.java:[line 882] - It causes stackoverflow according to the following. Most of the content is
        // about when it is used. https://stackoverflow.com/questions/19854007/what-is-the-advantage-of-using-tail-recursion-here#19854062
        // https://en.wikipedia.org/wiki/Tail_call
        // https://stackoverflow.com/questions/33923/what-is-tail-recursion
        // http://www.lua.org/pil/6.3.html
        // https://weblogs.asp.net/podwysocki/recursing-into-linear-tail-and-binary-recursion
        // https://mitpress.mit.edu/sites/default/files/sicp/index.html
        // https://stackoverflow.com/questions/491376/why-doesnt-net-c-optimize-for-tail-call-recursion
        // It causes stackoverflow, which is what I experienced too. But I am not using it at the moment
        public Key min() {
            return min(root).key;
        }

        private node min(node x) {
            if (x.left == null) return x;
            return min(x.left);
        }

        public Key floor(Key key) {
            node x = floor(root, key);
            if (x == null) return null;
            return x.key;
        }

        private node floor(node x, Key key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x;
            if (cmp < 0) return floor(x.left, key);
            node t = floor(x.right, key);
            if (t != null) return t;
            else return x;
        }

        public Key max() {
            return max(root).key;
        }

        private node max(node x) {
            if (x.right == null) return x;
            return max(x.right);
        }

        public Key ceiling(Key key) {
            node x = ceiling(root, key);
            if (x == null) return null;
            return x.key;
        }


        private node ceiling(node x, Key key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp == 0) return x;
            if (cmp > 0) return ceiling(x.right, key);
            node t = ceiling(x.left, key);
            if (t != null) return t;
            else return x;
        }

        // Find the subtree of a certain size. i.e. there are at least k nodes below the node you get back if the tree
        // has it
        public Key select(int k) {
            return select(root, k).key;
        }

        private node select(node x, int k) {
            //Return Node containing key of rank k.
            if (x == null) return null;
            int t = size(x.left);
            if (t > k) return select(x.left, k);
            else if (t < k) return select(x.right, k - t - 1);
            else return x;
        }

        // Find the subtree of a certain rank i.e. all keys below it are less than the key we pass to the method
        public int rank(Key key) {
            return rank(key, root);
        }

        private int rank(Key key, node x) {
            // Return number of keys less than x.key in the subtree rooted at x
            if (x == null) return 0;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) return rank(key, x.left);
            else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
            else return size(x.left);
        }

        //        M D CFS_CONFUSING_FUNCTION_SEMANTICS CFS: Method Solver$GameTree.deleteMin(Solver$GameTree$node) returns
        //        modified parameter  At Solver.java:[line 942]
        public void deleteMin() {
            root = deleteMin(root);
        }

        private node deleteMin(node x) {
            if (x.left == null) return x.right;
            x.left = deleteMin(x.left);
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }

        public void delete(Key key) {
            root = delete(root, key);
        }

        private node delete(node x, Key key) {
            if (x == null) return null;
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x.left = delete(x.left, key);
            else if (cmp > 0) x.right = delete(x.right, key);
            else {
                if (x.right == null) return x.left;
                if (x.left == null) return x.right;
                node t = x;
                x = min(t.right);
                x.right = deleteMin(t.right);
                x.left = t.left;
            }
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }

        public void print() {
            print(root);
        }

        public void print(Key key) {
            print(root);
        }

        private void print(node x) {
            if (x == null) return;
            print(x.left);
            StdOut.println(x.key);
            print(x.right);
        }
    }

    public static class SearchNode implements Comparable<SearchNode> {
        private final Board currentBoard;
        //private final int manhattan;
        //private final int hamming;
        private final int numOfMoves;
        private final SearchNode prevSearchNode;
        private int hamming;
        private int manhattan;


        public SearchNode(Board b, int m, int manhattan, int hamming, SearchNode prev) {
            currentBoard = b;
            numOfMoves = m;
            prevSearchNode = prev;
            this.hamming = hamming;
            this.manhattan = manhattan;
            //this.manhattan = currentBoard.manhattan();
            //this.hamming = currentBoard.hamming();
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

        //        public int GetPriority() {
//            return ((this.GetCurrentBoard().manhattan()) + (numOfMoves));
//        }
        public int GetPriority() {
            return ((this.GetCurrentBoard().manhattan()) + (numOfMoves));
        }

        public int GetManhattan() {
            return this.manhattan;
        }

        public int GetHamming() {
            return this.hamming;
        }

        private int GetHammingPriority() {
            return hamming + numOfMoves;
        }

        private int GetManhattanPriority() {
            return manhattan + numOfMoves;
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
            //TODO check for each case separately i.e. manhattan, if they are equal, then hamming, if they are still
            // equal then number of moves

//            else if (this.hamming > o.hamming) return 1;
//            else if (o.hamming > this.hamming) return -1;
//            else if (this.numOfMoves > o.numOfMoves) return 1;
//            else if (o.numOfMoves > this.numOfMoves) return -1;
            if (this.GetManhattanPriority() > o.GetHammingPriority()) return 1;
            if (this.GetManhattanPriority() < o.GetHammingPriority()) return -1;
            if (this.GetHammingPriority() > o.GetHammingPriority()) return 1;
            if (o.GetHammingPriority() > this.GetHammingPriority()) return -1;
            if (this.numOfMoves > o.numOfMoves) return 1;
            if (o.numOfMoves > this.numOfMoves) return -1;
            return 0;
        }
//    @Override
//    public int compare(SearchNode o1, SearchNode o2) {
//        if ((o1.GetCurrentBoard().manhattan() + o1.GetMovesCount()) > (o2.GetCurrentBoard().manhattan() + o2.GetMovesCount()))
//            return 1;
//        if ((o1.GetCurrentBoard().manhattan() + o1.GetMovesCount()) < (this.GetCurrentBoard().manhattan() + this.GetMovesCount()))
//            return -1;
//        //if (o.GetCurrentBoard().manhattan() > this.GetCurrentBoard().manhattan()) return -1;
//        return 0;
//    }
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
            for (Board board : s2.solutionBoardList) {
                StdOut.println(" board: " + board + " Hamming Distance of : " + board.hamming() + " Manhattan Distance of : " + board.manhattan());
            }
        }
    }
}
