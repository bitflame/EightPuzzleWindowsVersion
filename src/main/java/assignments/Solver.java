package assignments;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Solver {
    private boolean solvable;
    public int moves = 0;
    public final ArrayList<Board> solutionBoardList = new ArrayList<>();
    public final ArrayList<SearchNode> solutionList = new ArrayList<>();
    private boolean isConsecitiveNeighbor;
    private int blankCol;
    private int blankRow;

    public Solver(Board initialBoard) {
        if (initialBoard == null) {
            throw new IllegalArgumentException("The Board object is empty.");
        }
        GameTree<SearchNode, Integer> gameTree = new GameTree<>();
        SearchNode[] NeighborsArray = new SearchNode[10000];
        int NeighborsCount = 0;
        int NeighIndex = 0;
        int NeiArrayItems = 0;
        int loopCounter = 0;
        solvable = true;
        if (solvable == false) return;
        SearchNode initialSearchNode = new SearchNode(initialBoard, 0, initialBoard.manhattan(), initialBoard.hamming(), null);
        Board currentTwinBoard = initialBoard.twin();
        SearchNode initialTwinSearchNode = new SearchNode(currentTwinBoard, 0, initialBoard.manhattan(),
                initialBoard.hamming(), null);
        MinPQ<SearchNode> currentPriorityQueue = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode o1, SearchNode o2) {
                if (o1.GetManhattanPriority() > o2.GetManhattanPriority()) return 1;
                if (o2.GetManhattanPriority() > o1.GetManhattanPriority()) return -1;
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
                if (o1.GetManhattanPriority() > o2.GetManhattanPriority()) return 1;
                if (o2.GetManhattanPriority() > o1.GetManhattanPriority()) return -1;
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
        boolean loopCond = true;
        outerloop:
        while (loopCond) {
            int[][] dbEntry = {{1, 6, 4}, {7, 0, 8}, {2, 3, 5}};//  puzzle 20 number of nodes in the tree = 1
            Board dbBoard = new Board(dbEntry);
            //(Board b, int m, int manhattan, int hamming, SearchNode prev)
            SearchNode dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 2, 7}, {0, 4, 3}, {6, 5, 8}};// puzzle 19
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{5, 6, 2}, {1, 8, 4}, {7, 0, 3}};// puzzle 18
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{5, 1, 8}, {2, 7, 3}, {4, 0, 6}};// puzzle 17
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 0, 2}, {7, 5, 4}, {8, 6, 3}};// puzzle 11
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 6, 4}, {7, 0, 8}, {2, 3, 5}};// puzzle 20
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{2, 3, 5}, {1, 0, 4}, {7, 8, 6}};// puzzle 8
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};// puzzle 7
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{4, 1, 3}, {0, 2, 6}, {7, 5, 8}};// puzzle 6
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{8, 6, 7}, {2, 5, 4}, {3, 0, 1}};// puzzle3x3-31.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{8, 6, 7}, {2, 0, 4}, {3, 5, 1}};// puzzle3x3-30.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 8, 5}, {0, 2, 4}, {3, 6, 7}};// puzzle3x3-29.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{6, 3, 8}, {5, 4, 1}, {7, 2, 0}};// puzzle3x3-28.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{4, 8, 7}, {5, 3, 1}, {0, 6, 2}};// puzzle3x3-26.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{8, 3, 5}, {6, 4, 2}, {1, 0, 7}};// puzzle3x3-25.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{6, 5, 3}, {4, 1, 7}, {0, 2, 8}};// puzzle3x3-24.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{6, 0, 8}, {4, 3, 5}, {1, 2, 7}};// puzzle3x3-23.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{5, 3, 6}, {4, 0, 7}, {1, 8, 2}};// puzzle3x3-22.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{8, 7, 2}, {1, 5, 0}, {4, 6, 3}};// puzzle3x3-21.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{7, 4, 3}, {2, 8, 6}, {0, 5, 1}};// puzzle3x3-20.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{7, 0, 4}, {8, 5, 1}, {6, 3, 2}};// puzzle3x3-19.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 4, 3}, {7, 0, 8}, {6, 5, 2}};// puzzle3x3-18.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{4, 3, 1}, {0, 2, 6}, {7, 8, 5}};// puzzle3x3-17.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{5, 2, 1}, {4, 8, 3}, {7, 6, 0}};// puzzle3x3-16.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{2, 0, 8}, {1, 3, 5}, {4, 6, 7}};// puzzle3x3-15.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{3, 4, 6}, {2, 0, 8}, {1, 7, 5}};// puzzle3x3-14.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{4, 3, 1}, {0, 7, 2}, {8, 5, 6}};// puzzle3x3-13.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{4, 1, 2}, {3, 0, 6}, {5, 7, 8}};// puzzle3x3-12.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 3, 5}, {7, 2, 6}, {8, 0, 4}};// puzzle3x3-11.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{0, 4, 1}, {5, 3, 2}, {7, 8, 6}};// puzzle3x3-10.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 3, 6}, {5, 2, 8}, {4, 0, 7}};// puzzle3x3-9.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{0, 4, 3}, {2, 1, 6}, {7, 5, 8}};// puzzle3x3-8.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 2, 3}, {0, 4, 8}, {7, 6, 5}};// puzzle3x3-7.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 2, 3}, {4, 8, 5}, {7, 6, 0}};// puzzle3x3-6.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 0, 2}, {4, 6, 3}, {7, 5, 8}};// puzzle3x3-5.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-4.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 2, 3}, {0, 4, 5}, {7, 8, 6}};// puzzle3x3-3.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle3x3-2.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-1.txt
            dbBoard = new Board(dbEntry);
            dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
            gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
            // should have 39 nodes in the game tree

            for (Object o : gameTree.keys()) {
                SearchNode temp = (SearchNode) o;
                for (Board NeigBoard : temp.GetCurrentBoard().neighbors()) {
                    SearchNode temp1 = new SearchNode(NeigBoard, temp.numOfMoves + 1, NeigBoard.manhattan(),
                            NeigBoard.hamming(), temp);
                    gameTree.put(temp1, temp1.GetPriority());
                }
            }
            // Add the neighbors of minSearchNode to an array
            for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
                if (minSearchNode.GetPrevSearchNode() == null ||
                        minSearchNode.GetPrevSearchNode().GetCurrentBoard() != b) {
                    //(Board b, int m, int manhattan, int hamming, SearchNode prev)
                    if (minSearchNode.numOfMoves < 25) {
                        NeighborsArray[NeighborsCount] = new SearchNode(b, minSearchNode.numOfMoves + 1, b.manhattan(),
                                b.hamming(), minSearchNode);
                        gameTree.put(NeighborsArray[NeighborsCount], NeighborsArray[NeighborsCount].GetPriority());
                        NeighborsCount = NeighborsCount + 1;
                    }
                }
            }

// Find the neighbors of GameTree nodes and put them in the tree if Manhattan distance is below what you need - in this
// case, 25
            loopCounter++;
            if (loopCounter % 2000 == 0) {
                StdOut.println("Game Tree Size: " + gameTree.size() + " " +
                        "Manhattan for the GameTree Min: " + gameTree.min().GetManhattanPriority());
            }
            for (Object o : gameTree.keys()) {
                SearchNode temp = (SearchNode) o;
                for (Board NeigBoard : temp.GetCurrentBoard().neighbors()) {
                    if (temp.prevSearchNode == null && temp.GetManhattan() < 20) {
                        SearchNode temp1 = new SearchNode(NeigBoard, temp.numOfMoves + 1, NeigBoard.manhattan(),
                                NeigBoard.hamming(), temp);
                        gameTree.put(temp1, temp1.GetPriority());
                    } else if (temp.prevSearchNode != null && !NeigBoard.equals(temp.prevSearchNode.GetCurrentBoard())
                            && temp.manhattan < 20) {
                        SearchNode temp1 = new SearchNode(NeigBoard, temp.numOfMoves + 1, NeigBoard.manhattan(),
                                NeigBoard.hamming(), temp);
                        gameTree.put(temp1, temp1.GetPriority());
                    }
                }
                //StdOut.println("Added " + genCounter + " more neighbors to the database.");
            }
            NeiArrayItems = NeighborsCount;
            for (int i = NeighIndex; i < NeiArrayItems; i++) {
                NeighIndex = NeighIndex + 1;
                for (Board b : NeighborsArray[i].GetCurrentBoard().neighbors()) {
                    if (!b.equals(NeighborsArray[i].GetPrevSearchNode().GetCurrentBoard()) &&
                            NeighborsArray[i].GetPriority() < 50) {
                        NeighborsArray[NeighborsCount] = new SearchNode(b, NeighborsArray[i].numOfMoves + 1,
                                b.manhattan(), b.hamming(), NeighborsArray[i]);
                        NeighborsCount = NeighborsCount + 1;
                    }
                }
            }
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
                    //gameTreeTwin.put(temp1Twin, temp1Twin.numOfMoves);
                } else if (minTwinNode.GetPrevSearchNode() != null && !tb.equals(minTwinNode.GetPrevSearchNode().GetCurrentBoard())) {
                    if (currentPriorityQueueTwin.size() > 800) {
                        //StdOut.println("resetting the Twin priority queue.");
                        MinPQ<SearchNode> copyTwinPQ = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
                            @Override
                            public int compare(SearchNode o1, SearchNode o2) {
                                if (o1.GetManhattanPriority() > o2.GetManhattanPriority()) return 1;
                                if (o2.GetManhattanPriority() > o1.GetManhattanPriority()) return -1;
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
                }
            }
            for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
                SearchNode temp1 = new SearchNode(b, minSearchNode.GetMovesCount() + 1, b.manhattan(), b.hamming(),
                        minSearchNode);
                if (minSearchNode.GetPrevSearchNode() == null && !b.equals(initialBoard)) {
                    currentPriorityQueue.insert(temp1);
                } else if (minSearchNode.GetPrevSearchNode() != null && !b.equals(minSearchNode.GetPrevSearchNode().GetCurrentBoard())) {
                    if (currentPriorityQueue.size() > 800) {
                        MinPQ<SearchNode> copyPQ = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
                            @Override
                            public int compare(SearchNode o1, SearchNode o2) {
                                if (o1.GetManhattanPriority() > o2.GetManhattanPriority()) return 1;
                                if (o2.GetManhattanPriority() > o1.GetManhattanPriority()) return -1;
                                if (o1.GetHammingPriority() > o2.GetHammingPriority()) return 1;
                                if (o2.GetHammingPriority() > o1.GetHammingPriority()) return -1;
                                if (o1.numOfMoves > o2.numOfMoves) return 1;
                                if (o1.numOfMoves > o2.numOfMoves) return -1;
                                return 0;
                            }
                        });
                        for (int i = 0; i < 100; i++) {
                            copyPQ.insert(currentPriorityQueue.delMin());
                        }
                        currentPriorityQueue = copyPQ;
                    }
                    currentPriorityQueue.insert(temp1);
                    if (minSearchNode.GetCurrentBoard().equals(gBoard)) {
                        break outerloop;
                    }
                }
            }
            minTwinNode = currentPriorityQueueTwin.delMin();
            if (!currentPriorityQueue.isEmpty()) {
//                if (gameTree.select(gameTree.rank(minSearchNode) - 1) != null) {
//                    StdOut.println("There is a node closer to the goal, you may want to check out.");
//                    StdOut.println("Here is minSearchNode: " + minSearchNode.GetCurrentBoard() + " with rank of: " +
//                            gameTree.rank(minSearchNode) +
//                            " Here is the node: " + (gameTree.select(gameTree.rank(minSearchNode) - 1)).GetCurrentBoard());
//                }
                minSearchNode = currentPriorityQueue.delMin();
                // See what is the rank of minSearchNode in the tree at this point? If it is less than the number of
                // moves in it, then there might be a shorter path to the goal
            } else {
                StdOut.println("Why is PQ empty? ");
            }
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
            if (this.GetManhattan() > o.GetManhattan()) return 1;
            if (this.GetManhattan() < o.GetManhattan()) return -1;
            if (this.GetHamming() > o.GetHamming()) return 1;
            if (o.GetHamming() > this.GetHamming()) return -1;
            if (this.numOfMoves > o.numOfMoves) return 1;
            if (o.numOfMoves > this.numOfMoves) return -1;
            if (this.GetCurrentBoard().equals(o.GetCurrentBoard())) return 0;
            return 0;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        int[][] testTiles1 = {{5, 2, 3}, {4, 7, 0}, {8, 6, 1}};// puzzle 21 - actual = 29 moves
        //int[][] testTiles1 = {{8, 6, 7}, {2, 0, 4}, {3, 5, 1}};
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
