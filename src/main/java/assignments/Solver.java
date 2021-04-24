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
    public List<Board> solutionBoardList = new ArrayList<>();
    public final List<SearchNode> solutionNodes = new ArrayList<>();
    List<List<Board>> solutions = new ArrayList<>();

    public Solver(Board initialBoard) {
        if (initialBoard == null) {
            throw new IllegalArgumentException("The Board object is empty.");
        }
        GameTree<SearchNode, Integer> gameTree = new GameTree<>();
        GameTree<SearchNode, Integer> gameTreeTwin = new GameTree<>();
        solvable = true;
        if (solvable == false) return;
        SearchNode initialSearchNode = new SearchNode(initialBoard, 0, initialBoard.manhattan(), initialBoard.hamming(), null);
        Board currentTwinBoard = initialBoard.twin();
        SearchNode initialTwinSearchNode = new SearchNode(currentTwinBoard, 0, initialBoard.manhattan(),
                initialBoard.hamming(), null);
        MinPQ<SearchNode> currentPriorityQueue = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.GetPriority() > o2.GetPriority()) return 1;
                if (o2.GetPriority() > o1.GetPriority()) return -1;
                if (o1.GetHammingPriority() > o2.GetHammingPriority()) return 1;
                if (o2.GetHammingPriority() > o1.GetHammingPriority()) return -1;
                if (o1.numOfMoves > o2.numOfMoves) return 1;
                if (o1.numOfMoves > o2.numOfMoves) return -1;
                return 0;
            }
        });
        MinPQ<SearchNode> currentPriorityQueueTwin = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.GetPriority() > o2.GetPriority()) return 1;
                if (o2.GetPriority() > o1.GetPriority()) return -1;
                if (o1.GetHammingPriority() > o2.GetHammingPriority()) return 1;
                if (o2.GetHammingPriority() > o1.GetHammingPriority()) return -1;
                if (o1.numOfMoves > o2.numOfMoves) return 1;
                if (o1.numOfMoves > o2.numOfMoves) return -1;
                return 0;
            }
        });

        currentPriorityQueue.insert(initialSearchNode);

        currentPriorityQueueTwin.insert(initialTwinSearchNode);
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
        SearchNode gNode = new SearchNode(gBoard, 0, 0, 0, null);
        SearchNode minSearchNode = currentPriorityQueue.delMin();
        SearchNode minTwinNode = currentPriorityQueueTwin.delMin();
        /*Write a loop to run while there are neighbors to be visited */
        boolean loopCond = true;
        outerloop:
        while (loopCond) {
            if (minTwinNode.GetCurrentBoard().isGoal()) {
                //StdOut.println("Matched the goal in the twin priority queue.");
                solvable = false;
                break;
            } else if (minSearchNode.GetCurrentBoard().isGoal()) {
                solvable = true;
                break;
            }
            for (Board tb : minTwinNode.GetCurrentBoard().neighbors()) {
                SearchNode temp1Twin = new SearchNode(tb, minTwinNode.GetMovesCount() + 1, tb.manhattan(),
                        tb.hamming(), minTwinNode);
                if (minTwinNode.GetPrevSearchNode() == null && !tb.equals(initialTwinSearchNode.GetCurrentBoard())) {
                    currentPriorityQueueTwin.insert(temp1Twin);
                    gameTreeTwin.put(temp1Twin, temp1Twin.manhattan);
                } else if (minTwinNode.GetPrevSearchNode() != null &&
                        !tb.equals(minTwinNode.GetPrevSearchNode().GetCurrentBoard())) {
                    if (currentPriorityQueueTwin.size() > 800) {
                        //StdOut.println("resetting the Twin priority queue.");
                        MinPQ<SearchNode> copyTwinPQ = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
                            @Override
                            public int compare(SearchNode o1, SearchNode o2) {
                                if (o1.GetPriority() > o2.GetPriority()) return 1;
                                if (o2.GetPriority() > o1.GetPriority()) return -1;
                                if (o1.GetHammingPriority() > o2.GetHammingPriority()) return 1;
                                if (o2.GetHammingPriority() > o1.GetHammingPriority()) return -1;
                                if (o1.numOfMoves > o2.numOfMoves) return 1;
                                if (o1.numOfMoves > o2.numOfMoves) return -1;
                                return 0;
                            }
                        });
                        for (int i = 0; i < 100; i++) {
                            copyTwinPQ.insert(currentPriorityQueueTwin.delMin());

                        }
                        currentPriorityQueueTwin = copyTwinPQ;
                    }
                    currentPriorityQueueTwin.insert(temp1Twin);
                    gameTreeTwin.put(temp1Twin, temp1Twin.manhattan);
                }
                if (minTwinNode.GetCurrentBoard().isGoal()) {
                    solvable = false;
                    break;
                }
            }
            // put the children of minSearchNode in the tree with the number of moves


            // Take one of the children and process its children for 2 x the manhattan value of minimum search node
            int currentNodeCounter = minSearchNode.manhattan * 2;
            SearchNode originalNode = minSearchNode;
            do {
                for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
                    SearchNode temp1 = new SearchNode(b, minSearchNode.GetMovesCount() + 1, b.manhattan(), b.hamming(),
                            minSearchNode);
                    ;
                    if (minSearchNode.GetPrevSearchNode() == null && !b.equals(initialBoard)) {
                        currentPriorityQueue.insert(temp1);
                        gameTree.put(temp1, temp1.GetPriority());
                    } else if (minSearchNode.GetPrevSearchNode() != null &&
                            !b.equals(minSearchNode.GetPrevSearchNode().GetCurrentBoard())) {
                        gameTree.put(temp1, temp1.GetPriority());
                        currentPriorityQueue.insert(temp1);
                        if (minSearchNode.GetCurrentBoard().equals(gBoard)) {
                            solvable = true;
                            moves = minSearchNode.numOfMoves;
                            //StdOut.println("Here is the minimum:  " + gameTree.min().GetCurrentBoard());
                            while (!minSearchNode.GetCurrentBoard().equals(initialBoard)) {
                                solutionNodes.add(minSearchNode);
                                solutionBoardList.add(minSearchNode.GetCurrentBoard());
                                minSearchNode = minSearchNode.GetPrevSearchNode();
                            }
                            solutionNodes.add(initialSearchNode);
                            solutionBoardList.add(initialBoard);
                            solutions.add(solutionBoardList); // add the solution you found to the list
                        }
                    }
                }
                currentNodeCounter--;
            } while (currentNodeCounter > 0);
            // Get the next node
            if (gameTree.floor(originalNode) != null) minSearchNode = gameTree.floor(originalNode);
            if (gameTree.ceiling(originalNode) != null) minSearchNode = gameTree.ceiling(minSearchNode);
            else {
                // Get the shortest solution
                List<Board> optimalSolution = solutions.get(0);
                for (int i = 1; i < solutions.size(); i++) {
                    if (optimalSolution.size() > solutions.get(i).size()) optimalSolution = solutions.get(i);
                }
                return;
            }
            // Once you get the next search node you can reset the priority queue
            currentPriorityQueue = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
                @Override
                public int compare(SearchNode o1, SearchNode o2) {
                    if (o1.GetPriority() > o2.GetPriority()) return 1;
                    if (o2.GetPriority() > o1.GetPriority()) return -1;
                    if (o1.GetHammingPriority() > o2.GetHammingPriority()) return 1;
                    if (o2.GetHammingPriority() > o1.GetHammingPriority()) return -1;
                    if (o1.numOfMoves > o2.numOfMoves) return 1;
                    if (o1.numOfMoves > o2.numOfMoves) return -1;
                    return 0;
                }
            });
        }
    }


    private boolean isNeighbor(Board a, Board b) {
        for (Board bNeig : b.neighbors()) {
            if (a.equals(bNeig)) return true;
        }
        return false;
    }


    private Solver() {
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
            ArrayList<Board> tempArray = new ArrayList<>(solutionBoardList);
            return tempArray;
        } else
            return null;
    }

    // region GameTree
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
            else {
                //StdOut.println("massage from put() - " + key + " and " + x.key + " are equal" + " setting " + x.val + " = " + val);
                x.val = val;
            }
            x.N = size(x.left) + size(x.right) + 1;
            return x;
        }


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

    // endregion
    // region SearchNode
    public static class SearchNode implements Comparable<SearchNode> {
        private final Board currentBoard;
        private final int numOfMoves;
        private final SearchNode prevSearchNode;
        private int hamming;
        private int manhattan;

        //(Board b, int m, int manhattan, int hamming, SearchNode prev)
        public SearchNode(Board b, int m, int manhattan, int hamming, SearchNode prev) {
            currentBoard = b;
            numOfMoves = m;
            prevSearchNode = prev;
            this.hamming = hamming;
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
            //return ((this.GetCurrentBoard().manhattan()) + (numOfMoves));
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

            if (this.GetManhattan() > o.GetManhattan()) return 1;
            if (this.GetManhattan() < o.GetManhattan()) return -1;
            if (this.GetHamming() > o.GetHamming()) return 1;
            if (o.GetHamming() > this.GetHamming()) return -1;
            if (this.numOfMoves > o.numOfMoves) return 1;
            if (o.numOfMoves > this.numOfMoves) return -1;
            if (this.GetCurrentBoard().equals(o.GetCurrentBoard())) return 0;
            else return 1;
        }
    }

    // endregion
    // test client (see below)
    public static void main(String[] args) {

        int[][] testTiles1 = {{5, 2, 3}, {4, 7, 0}, {8, 6, 1}}; // puzzle 21 - actual = 29 moves

        Board testTilesBoard = new Board(testTiles1);
        Solver s = new Solver(testTilesBoard);
        if (!s.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves= " + s.moves);
            for (Board board : s.solutionBoardList) {
                StdOut.println(" board: " + board + " Hamming Distance of : " + board.hamming() + " Manhattan Distance of : " + board.manhattan());
            }
        }
    }
}
