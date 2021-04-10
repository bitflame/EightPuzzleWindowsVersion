package assignments;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver2 {
    private boolean solvable;
    private int moves = 0;
    ArrayList<Board> solutionBoardList = new ArrayList<>();

    public Solver2(Board initialBoard) {
        List<ArrayList<Board>> solutionAlternatives = new ArrayList<ArrayList<Board>>();
        if (initialBoard == null) {
            throw new IllegalArgumentException("The Board object is empty.");
        }
        solvable = true; // for now
        if (solvable == false) return;
        SearchNode initialSearchNode = new SearchNode(initialBoard, 0, null);
        MinPQ<SearchNode> currentPriorityQueue = new MinPQ<SearchNode>(3000000, new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.GetPriority() > o2.GetPriority()) return 1;
                else if (o2.GetPriority() > o1.GetPriority()) return -1;
                return 0;
            }
        });
        currentPriorityQueue.insert(initialSearchNode);
        GameTree<SearchNode, Integer> gameTree = new GameTree<SearchNode, Integer>();
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
        //SearchNode gNode = new SearchNode(gBoard, gBoard.manhattan(), null);
        int gMTreeIndex = 1;
        gameTree.put(initialSearchNode, gMTreeIndex);
        gMTreeIndex++;
        SearchNode minimumSearchNode = currentPriorityQueue.delMin();
        boolean keepProcessing = true;
        StdOut.println("Running loop for " + factorial((initialBoard.dimension() * initialBoard.dimension()) - 1));
        do {
            int neighborCount = 0;
            int matchingBoard = 0;
            for (Board b : minimumSearchNode.currentBoard.neighbors()) {
                SearchNode temp = new SearchNode(b, minimumSearchNode.numOfMoves + 1, minimumSearchNode);
                neighborCount++;
                for (SearchNode s : gameTree.keys()) {
                    if (gameTree.get(temp) != null) {
                        matchingBoard++;
                        //Once I get here I need to go to the next neighbor or get another minimumSearchNode I have
                        // tried break and continue. Neither do what I want.
                    } else {
                        if (minimumSearchNode.GetPrevSearchNode() == null && !b.equals(initialBoard)) {
                            currentPriorityQueue.insert(temp);
                            gameTree.put(temp, gMTreeIndex);
                            gMTreeIndex++;
                        } else if (minimumSearchNode.GetPrevSearchNode() != null &&
                                !b.equals(minimumSearchNode.GetPrevSearchNode().GetCurrentBoard())) {
                            currentPriorityQueue.insert(temp);
                            gameTree.put(temp, gMTreeIndex);
                            gMTreeIndex++;
                        }
                    }
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
                }
            }
            if (neighborCount == matchingBoard) {
                keepProcessing = false;
            }
            minimumSearchNode = currentPriorityQueue.delMin();
            // The first path is the minimum if you generate the neighbors for all the nodes. Maybe use number of moves
            // to keep track of which level you are at
        } while (gameTree.size() < factorial((initialBoard.dimension() * initialBoard.dimension()) - 1));
        ;// stop when all the neighbors of a board are already in the tree
        for (ArrayList solution : solutionAlternatives) {
            int pathLength = solution.size();
            if (solutionBoardList.size() > pathLength) {
                solutionBoardList = solution;
                moves = solution.size();
            }
        }
    }

    private static long factorial(int number) {
        long result = 1;

        for (int factor = 2; factor <= number; factor++) {
            result *= factor;
        }

        return result;
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
            else x.N = size(x.left) + size(x.right) + 1;
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

    private static class SearchNode implements Comparable<SearchNode> {
        private final Board currentBoard;
        //private final int manhattan;
        //private final int hamming;
        private final int numOfMoves;
        private final SearchNode prevSearchNode;

        public SearchNode(Board b, int m, SearchNode prev) {
            currentBoard = b;
            numOfMoves = m;
            prevSearchNode = prev;
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

        public int GetPriority() {
            return ((3 * this.GetCurrentBoard().manhattan()) + (2 * numOfMoves));
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
            return this.moves;
        } else return -1;
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
            //StdOut.println("The original board is: " + initial);
            // solve the puzzle
            Solver2 solver = new Solver2(initial);
            // print solution to standard output
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Here is the list of moves that make up the solution: ");
                for (Board board : solver.solution())
                    StdOut.println("The board: " + board + " It's Manhattan value: " + board.manhattan() + " Its hamming value: " + board.hamming());
                StdOut.println("The board is solvable, and the ");
                StdOut.println("Minimum number of moves = " + solver.moves());
            }
        }
    }
}
