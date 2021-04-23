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
    public final ArrayList<SearchNode> floorsList = new ArrayList<>();
    public final ArrayList<int[][]> solutionTileList = new ArrayList<>();
    private boolean isConsecitiveNeighbor;
    private int blankCol;
    private int blankRow;
    SearchNode[] NeighborsArray = new SearchNode[10000];

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
// region db
        int[][] dbEntry = {{1, 0, 2}, {7, 5, 4}, {8, 6, 3}};// puzzle 11
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 3}, {8, 6, 0}};// puzzle 11
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 3}, {8, 0, 6}};// puzzle 11
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 3}, {0, 8, 6}};// puzzle 11
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {0, 4, 3}, {7, 8, 6}};// puzzle 11
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {4, 0, 3}, {7, 8, 6}};// puzzle 11
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle 11
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle 11
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle 11
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle 11
        solutionTileList.add(dbEntry);

        //Board dbBoard = new Board(dbEntry);
        //(Board b, int m, int manhattan, int hamming, SearchNode prev)
        //SearchNode dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 2, 7}, {0, 4, 3}, {6, 5, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 7}, {6, 4, 3}, {0, 5, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 7}, {6, 4, 3}, {5, 0, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 7}, {6, 0, 3}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 7}, {0, 6, 3}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 2, 7}, {1, 6, 3}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 0, 7}, {1, 6, 3}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 7, 0}, {1, 6, 3}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 7, 3}, {1, 6, 0}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 7, 3}, {1, 0, 6}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 0, 3}, {1, 7, 6}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 2, 3}, {1, 7, 6}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {7, 0, 6}, {5, 4, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {7, 4, 6}, {5, 0, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {7, 4, 6}, {0, 5, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 4, 6}, {7, 5, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle 19
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle 19
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{5, 6, 2}, {1, 8, 4}, {7, 3, 0}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 6, 2}, {1, 8, 4}, {7, 0, 3}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 6, 2}, {1, 0, 4}, {7, 8, 3}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 6, 2}, {1, 0, 4}, {7, 8, 3}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 0, 2}, {1, 6, 4}, {7, 8, 3}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 5, 2}, {1, 6, 4}, {7, 8, 3}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {0, 6, 4}, {7, 8, 3}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 6, 4}, {0, 8, 3}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 6, 4}, {8, 0, 3}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 0, 4}, {8, 6, 3}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 0}, {8, 6, 3}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 3}, {8, 6, 0}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 3}, {8, 0, 6}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 3}, {0, 8, 6}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {0, 4, 3}, {7, 8, 6}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {4, 0, 3}, {7, 8, 6}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle 18
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle 18
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{5, 1, 8}, {2, 7, 3}, {4, 0, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 8}, {2, 0, 3}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 0, 8}, {2, 1, 3}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 5, 8}, {2, 1, 3}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 8}, {0, 1, 3}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 8}, {1, 0, 3}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 0, 8}, {1, 5, 3}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 8, 0}, {1, 5, 3}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 8, 3}, {1, 5, 0}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 8, 3}, {1, 0, 5}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 0, 3}, {1, 8, 5}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 2, 3}, {1, 8, 5}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 8, 5}, {4, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 5}, {0, 7, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 5}, {7, 0, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle 17
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle 17
        solutionTileList.add(dbEntry);

        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());

        dbEntry = new int[][]{{1, 6, 4}, {7, 0, 8}, {2, 3, 5}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 6, 4}, {7, 3, 8}, {2, 0, 5}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 6, 4}, {7, 3, 8}, {2, 5, 0}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 6, 4}, {7, 3, 0}, {2, 5, 8}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 6, 0}, {7, 3, 4}, {2, 5, 8}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 6}, {7, 3, 4}, {2, 5, 8}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {7, 0, 4}, {2, 5, 8}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {7, 4, 0}, {2, 5, 8}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {7, 4, 8}, {2, 5, 0}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {7, 4, 8}, {2, 0, 5}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {7, 4, 8}, {0, 2, 5}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {0, 4, 8}, {7, 2, 5}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {4, 0, 8}, {7, 2, 5}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {4, 2, 8}, {7, 0, 5}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {4, 2, 8}, {7, 5, 0}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {4, 2, 0}, {7, 5, 8}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 0}, {4, 2, 6}, {7, 5, 8}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {4, 2, 6}, {7, 5, 8}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle 20
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle 20
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
//        dbEntry = new int[][]{{2, 3, 5}, {1, 0, 4}, {7, 8, 6}};// puzzle 8
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{2, 3, 5}, {1, 4, 0}, {7, 8, 6}};// puzzle 8
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{2, 3, 0}, {1, 4, 5}, {7, 8, 6}};// puzzle 8
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{2, 0, 3}, {1, 4, 5}, {7, 8, 6}};// puzzle 8
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{0, 2, 3}, {1, 4, 5}, {7, 8, 6}};// puzzle 8
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {0, 4, 5}, {7, 8, 6}};// puzzle 8
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle 8
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle 8
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle 8
//        solutionTileList.add(dbEntry);

        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
//        dbEntry = new int[][]{{1, 2, 3}, {0, 7, 6}, {5, 4, 8}};// puzzle 7
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {5, 7, 6}, {0, 4, 8}};// puzzle 7
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {5, 7, 6}, {4, 0, 8}};// puzzle 7
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {5, 0, 6}, {4, 7, 8}};// puzzle 7
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {0, 5, 6}, {4, 7, 8}};// puzzle 7
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {0, 7, 8}};// puzzle 7
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle 7
//        solutionTileList.add(dbEntry);

        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
//        dbEntry = new int[][]{{4, 1, 3}, {0, 2, 6}, {7, 5, 8}};// puzzle 6
//        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
//        dbEntry = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};// puzzle 4
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};// puzzle 4
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle 4
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle 4
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle 4
//        solutionTileList.add(dbEntry);
//
//        dbEntry = new int[][]{{4, 1, 3}, {0, 2, 6}, {7, 5, 8}};// puzzle 5
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{0, 1, 3}, {4, 2, 6}, {7, 5, 8}};// puzzle 5
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 0, 3}, {4, 2, 6}, {7, 5, 8}};// puzzle 5
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};// puzzle 5
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle 5
//        solutionTileList.add(dbEntry);
//        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle 5
//        solutionTileList.add(dbEntry);

        dbEntry = new int[][]{{8, 6, 7}, {2, 5, 4}, {3, 0, 1}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 6, 7}, {2, 5, 4}, {3, 1, 0}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 6, 7}, {2, 5, 0}, {3, 1, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 6, 0}, {2, 5, 7}, {3, 1, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 6, 0}, {2, 5, 7}, {3, 1, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 0, 6}, {2, 5, 7}, {3, 1, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 5, 6}, {2, 0, 7}, {3, 1, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 5, 6}, {0, 2, 7}, {3, 1, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 5, 6}, {8, 2, 7}, {3, 1, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 0, 6}, {8, 2, 7}, {3, 1, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 2, 6}, {8, 0, 7}, {3, 1, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 2, 6}, {8, 1, 7}, {3, 0, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 2, 6}, {8, 1, 7}, {3, 0, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 2, 6}, {8, 1, 7}, {0, 3, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 2, 6}, {0, 1, 7}, {8, 3, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 2, 6}, {1, 0, 7}, {8, 3, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 2, 6}, {1, 7, 0}, {8, 3, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 2, 0}, {1, 7, 6}, {8, 3, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 0, 2}, {1, 7, 6}, {8, 3, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 5, 2}, {1, 7, 6}, {8, 3, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {0, 7, 6}, {8, 3, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 0, 6}, {8, 3, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 3, 6}, {8, 0, 4}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 3, 6}, {8, 4, 0}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 3, 0}, {8, 4, 6}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 0, 3}, {8, 4, 6}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 3}, {8, 0, 6}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 3}, {0, 8, 6}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {0, 4, 3}, {7, 8, 6}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {4, 0, 3}, {7, 8, 6}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-31.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{8, 6, 7}, {2, 0, 4}, {3, 5, 1}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 0, 7}, {2, 6, 4}, {3, 5, 1}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 0}, {2, 6, 4}, {3, 5, 1}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 4}, {2, 6, 0}, {3, 5, 1}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 4}, {2, 0, 6}, {3, 5, 1}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 4}, {0, 2, 6}, {3, 5, 1}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 4}, {3, 2, 6}, {0, 5, 1}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 4}, {3, 2, 6}, {5, 0, 1}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 4}, {3, 2, 6}, {5, 1, 0}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 4}, {3, 2, 0}, {5, 1, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 4}, {3, 0, 2}, {5, 1, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 4}, {0, 3, 2}, {5, 1, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 7, 4}, {8, 3, 2}, {5, 1, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 0, 4}, {8, 3, 2}, {5, 1, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 0}, {8, 3, 2}, {5, 1, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 2}, {8, 3, 0}, {5, 1, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 2}, {8, 0, 3}, {5, 1, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 2}, {8, 1, 3}, {5, 0, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 2}, {8, 1, 3}, {0, 5, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 2}, {0, 1, 3}, {8, 5, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 4, 2}, {7, 1, 3}, {8, 5, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 0, 2}, {7, 1, 3}, {8, 5, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 0, 3}, {8, 5, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 3}, {8, 0, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 3}, {0, 8, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {0, 5, 3}, {7, 8, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-30.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 8, 5}, {0, 2, 4}, {3, 6, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 8, 5}, {2, 0, 4}, {3, 6, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 8, 5}, {2, 4, 0}, {3, 6, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 8, 0}, {2, 4, 5}, {3, 6, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 8}, {2, 4, 5}, {3, 6, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 8}, {2, 4, 5}, {3, 6, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 1, 8}, {0, 4, 5}, {3, 6, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 1, 8}, {3, 4, 5}, {0, 6, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 1, 8}, {3, 4, 5}, {6, 0, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 1, 8}, {3, 0, 5}, {6, 4, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 1, 8}, {0, 3, 5}, {6, 4, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 8}, {2, 3, 5}, {6, 4, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 8}, {2, 3, 5}, {6, 4, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {0, 2, 5}, {6, 4, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {6, 2, 5}, {4, 0, 7}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {6, 2, 5}, {4, 7, 0}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {6, 2, 0}, {4, 7, 5}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 0}, {6, 2, 8}, {4, 7, 5}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {6, 2, 8}, {4, 7, 5}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {6, 0, 8}, {4, 7, 5}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 6, 8}, {4, 7, 5}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 8}, {0, 7, 5}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 8}, {7, 0, 5}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 8}, {7, 5, 0}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 0}, {7, 5, 8}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-29.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{6, 3, 8}, {5, 4, 1}, {7, 2, 0}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {5, 4, 0}, {7, 2, 1}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {5, 0, 4}, {7, 2, 1}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {5, 2, 4}, {7, 0, 1}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {5, 2, 4}, {7, 1, 0}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {5, 2, 0}, {7, 1, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 0}, {5, 2, 8}, {7, 1, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 0, 3}, {5, 2, 8}, {7, 1, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 6, 3}, {5, 2, 8}, {7, 1, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 6, 3}, {0, 2, 8}, {7, 1, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 6, 3}, {2, 0, 8}, {7, 1, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 0, 3}, {2, 6, 8}, {7, 1, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 5, 3}, {2, 6, 8}, {7, 1, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 3}, {0, 6, 8}, {7, 1, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 3}, {7, 6, 8}, {0, 1, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 3}, {7, 6, 8}, {1, 0, 4}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 3}, {7, 6, 8}, {1, 4, 0}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 3}, {7, 6, 0}, {1, 4, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 3}, {7, 0, 6}, {1, 4, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 3}, {0, 7, 6}, {1, 4, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 3}, {1, 7, 6}, {0, 4, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 3}, {1, 7, 6}, {4, 0, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 5, 3}, {1, 0, 6}, {4, 7, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 0, 3}, {1, 5, 6}, {4, 7, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 2, 3}, {1, 5, 6}, {4, 7, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 5, 6}, {4, 7, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {0, 7, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-28.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{4, 8, 7}, {5, 3, 1}, {0, 6, 2}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 8, 7}, {5, 3, 1}, {6, 0, 2}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 8, 7}, {5, 0, 1}, {6, 3, 2}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 0, 7}, {5, 8, 1}, {6, 3, 2}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 7, 0}, {5, 8, 1}, {6, 3, 2}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 7, 0}, {5, 8, 1}, {6, 3, 2}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 7, 1}, {5, 8, 0}, {6, 3, 2}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 7, 1}, {5, 8, 2}, {6, 3, 0}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 7, 1}, {5, 8, 2}, {6, 0, 3}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 7, 1}, {5, 8, 2}, {0, 6, 3}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 7, 1}, {0, 8, 2}, {5, 6, 3}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 7, 1}, {8, 0, 2}, {5, 6, 3}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 0, 1}, {8, 7, 2}, {5, 6, 3}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 0}, {8, 7, 2}, {5, 6, 3}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {8, 7, 0}, {5, 6, 3}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {8, 7, 3}, {5, 6, 0}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {8, 7, 3}, {5, 0, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {8, 7, 3}, {0, 5, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {0, 7, 3}, {8, 5, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 0, 3}, {8, 5, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 3}, {8, 0, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 3}, {0, 8, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {0, 5, 3}, {7, 8, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-26.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{8, 3, 5}, {6, 4, 2}, {1, 0, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 3, 5}, {6, 0, 2}, {1, 4, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 3, 5}, {0, 6, 2}, {1, 4, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 3, 5}, {1, 6, 2}, {0, 4, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 3, 5}, {1, 6, 2}, {4, 0, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 3, 5}, {1, 0, 2}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 0, 5}, {1, 3, 2}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 5, 0}, {1, 3, 2}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 5, 2}, {1, 3, 0}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 5, 2}, {1, 0, 3}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 0, 2}, {1, 5, 3}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 8, 2}, {1, 5, 3}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 8, 2}, {0, 5, 3}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 8, 2}, {5, 0, 3}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {5, 8, 3}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {5, 8, 3}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 8, 0}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 0, 8}, {4, 6, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 6, 8}, {4, 0, 7}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 6, 8}, {4, 7, 0}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 6, 0}, {4, 7, 8}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 0, 6}, {4, 7, 8}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 5, 6}, {4, 7, 8}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {0, 7, 8}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-25.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{6, 5, 3}, {4, 1, 7}, {0, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 5, 3}, {0, 1, 7}, {4, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 5, 3}, {6, 1, 7}, {4, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 0, 3}, {6, 1, 7}, {4, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 3}, {6, 0, 7}, {4, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 3}, {0, 6, 7}, {4, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 3}, {5, 6, 7}, {4, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {5, 6, 7}, {4, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 0}, {5, 6, 7}, {4, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 7}, {5, 6, 0}, {4, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 7}, {5, 0, 6}, {4, 2, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 7}, {5, 2, 6}, {4, 0, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 7}, {5, 2, 6}, {4, 8, 0}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 7}, {5, 2, 0}, {4, 8, 6}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 0}, {5, 2, 7}, {4, 8, 6}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {5, 2, 7}, {4, 8, 6}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 0, 7}, {4, 8, 6}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 7, 0}, {4, 8, 6}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 7, 6}, {4, 8, 0}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 7, 6}, {4, 0, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 0, 6}, {4, 7, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 5, 6}, {4, 7, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {0, 7, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-24.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{6, 0, 8}, {4, 3, 5}, {1, 2, 7}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {4, 0, 5}, {1, 2, 7}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {4, 2, 5}, {1, 0, 7}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {4, 2, 5}, {0, 1, 7}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {0, 2, 5}, {4, 1, 7}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {2, 0, 5}, {4, 1, 7}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {2, 1, 5}, {4, 0, 7}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {2, 1, 5}, {4, 7, 0}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 8}, {2, 1, 0}, {4, 7, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 3, 0}, {2, 1, 8}, {4, 7, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{6, 0, 3}, {2, 1, 8}, {4, 7, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 6, 3}, {2, 1, 8}, {4, 7, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 6, 3}, {0, 1, 8}, {4, 7, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 6, 3}, {1, 0, 8}, {4, 7, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 0, 3}, {1, 6, 8}, {4, 7, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 2, 3}, {1, 6, 8}, {4, 7, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 6, 8}, {4, 7, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 8}, {0, 7, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 8}, {7, 0, 5}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 8}, {7, 5, 0}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 0}, {7, 5, 8}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-23.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{5, 3, 6}, {4, 0, 7}, {1, 8, 2}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 6}, {4, 8, 7}, {1, 0, 2}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 6}, {4, 8, 7}, {1, 2, 0}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 6}, {4, 8, 0}, {1, 2, 7}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 6}, {4, 0, 8}, {1, 2, 7}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 6}, {4, 2, 8}, {1, 0, 7}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 6}, {4, 2, 8}, {0, 1, 7}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 6}, {0, 2, 8}, {4, 1, 7}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 6}, {2, 0, 8}, {4, 1, 7}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 6}, {2, 1, 8}, {4, 0, 7}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 6}, {2, 1, 0}, {4, 7, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 3, 0}, {2, 1, 6}, {4, 7, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 0, 3}, {2, 1, 6}, {4, 7, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 3}, {2, 0, 6}, {4, 7, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 3}, {0, 2, 6}, {4, 7, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 3}, {5, 2, 6}, {4, 7, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {5, 2, 6}, {4, 7, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 0, 6}, {4, 7, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 5, 6}, {4, 7, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {0, 7, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);

        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{8, 7, 2}, {1, 5, 0}, {4, 6, 3}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 2}, {1, 5, 3}, {4, 6, 0}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 2}, {1, 5, 3}, {4, 0, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 2}, {1, 0, 3}, {4, 5, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{8, 7, 2}, {0, 1, 3}, {4, 5, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 7, 2}, {8, 1, 3}, {4, 5, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 0, 2}, {8, 1, 3}, {4, 5, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 1, 2}, {8, 0, 3}, {4, 5, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 1, 2}, {8, 5, 3}, {0, 4, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 1, 2}, {0, 5, 3}, {8, 4, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 2}, {7, 5, 3}, {8, 4, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {7, 5, 3}, {8, 4, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 0, 3}, {8, 4, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 3}, {8, 0, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {7, 4, 3}, {0, 8, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {0, 4, 3}, {7, 8, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 5, 2}, {4, 0, 3}, {7, 8, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-21.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{7, 4, 3}, {2, 8, 6}, {0, 5, 1}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 3}, {0, 8, 6}, {2, 5, 1}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 3}, {8, 0, 6}, {2, 5, 1}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 3}, {8, 5, 6}, {2, 0, 1}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 3}, {8, 5, 6}, {2, 1, 0}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 3}, {8, 5, 0}, {2, 1, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 3}, {8, 0, 5}, {2, 1, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 3}, {8, 1, 5}, {2, 0, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 3}, {8, 1, 5}, {0, 2, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 3}, {0, 1, 5}, {8, 2, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 4, 3}, {7, 1, 5}, {8, 2, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 0, 3}, {7, 1, 5}, {8, 2, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 3}, {7, 0, 5}, {8, 2, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 3}, {7, 2, 5}, {8, 0, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 3}, {7, 2, 5}, {0, 8, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 3}, {0, 2, 5}, {7, 8, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 3}, {4, 2, 5}, {7, 8, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {4, 2, 5}, {7, 8, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-20.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-22.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{7, 0, 4}, {8, 5, 1}, {6, 3, 2}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 0}, {8, 5, 1}, {6, 3, 2}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 1}, {8, 5, 0}, {6, 3, 2}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 1}, {8, 5, 2}, {6, 3, 0}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 1}, {8, 5, 2}, {6, 0, 3}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 1}, {8, 5, 2}, {0, 6, 3}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{7, 4, 1}, {0, 5, 2}, {8, 6, 3}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 4, 1}, {7, 5, 2}, {8, 6, 3}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 0, 1}, {7, 5, 2}, {8, 6, 3}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 0}, {7, 5, 2}, {8, 6, 3}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 0}, {8, 6, 3}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 3}, {8, 6, 0}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 3}, {8, 0, 6}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 3}, {0, 8, 6}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {0, 5, 3}, {7, 8, 6}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 0}};// puzzle3x3-19.txt
        solutionTileList.add(dbEntry);

        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 4, 3}, {7, 0, 8}, {6, 5, 2}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {7, 4, 8}, {6, 5, 2}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 0}, {7, 4, 8}, {6, 5, 2}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {7, 4, 0}, {6, 5, 2}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {7, 4, 2}, {6, 5, 0}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {7, 4, 2}, {6, 0, 5}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {7, 4, 2}, {0, 6, 5}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {0, 4, 2}, {7, 6, 5}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {4, 0, 2}, {7, 6, 5}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 8}, {4, 2, 0}, {7, 6, 5}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 0}, {4, 2, 8}, {7, 6, 5}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {4, 2, 8}, {7, 6, 5}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 0}, {7, 6, 5}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 5}, {7, 6, 0}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 5}, {7, 0, 6}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 0}};// puzzle3x3-18.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{4, 3, 1}, {0, 2, 6}, {7, 8, 5}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 3, 1}, {7, 2, 6}, {0, 8, 5}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 3, 1}, {7, 2, 6}, {8, 0, 5}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 3, 1}, {7, 2, 6}, {8, 5, 0}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 3, 1}, {7, 2, 0}, {8, 5, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 3, 1}, {7, 0, 2}, {8, 5, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 0, 1}, {7, 3, 2}, {8, 5, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 0}, {7, 3, 2}, {8, 5, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 3, 0}, {8, 5, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 0, 3}, {8, 5, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 3}, {8, 0, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 3}, {0, 8, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {0, 5, 3}, {7, 8, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 0}};// puzzle3x3-17.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{5, 2, 1}, {4, 8, 3}, {7, 6, 0}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 2, 1}, {4, 8, 3}, {7, 0, 6}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 2, 1}, {4, 0, 3}, {7, 8, 6}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 0, 1}, {4, 2, 3}, {7, 8, 6}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 0}, {4, 2, 3}, {7, 8, 6}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 3}, {4, 2, 0}, {7, 8, 6}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 3}, {4, 2, 6}, {7, 8, 0}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 3}, {4, 2, 6}, {7, 0, 8}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 3}, {4, 2, 6}, {0, 7, 8}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{5, 1, 3}, {0, 2, 6}, {4, 7, 8}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 3}, {5, 2, 6}, {4, 7, 8}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {5, 2, 6}, {4, 7, 8}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 0, 6}, {4, 7, 8}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 5, 6}, {4, 7, 8}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {0, 7, 8}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-16.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{2, 0, 8}, {1, 3, 5}, {4, 6, 7}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 8}, {1, 0, 5}, {4, 6, 7}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 8}, {1, 6, 5}, {4, 0, 7}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 8}, {1, 6, 5}, {4, 7, 0}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 8}, {1, 6, 0}, {4, 7, 5}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 0}, {1, 6, 8}, {4, 7, 5}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 0, 3}, {1, 6, 8}, {4, 7, 5}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 2, 3}, {1, 6, 8}, {4, 7, 5}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 6, 8}, {4, 7, 5}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 8}, {0, 7, 5}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 8}, {7, 0, 5}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 8}, {7, 5, 0}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 0}, {7, 5, 8}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-15.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{3, 4, 6}, {2, 0, 8}, {1, 7, 5}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{3, 0, 6}, {2, 4, 8}, {1, 7, 5}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 3, 6}, {2, 4, 8}, {1, 7, 5}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 6}, {0, 4, 8}, {1, 7, 5}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 6}, {1, 4, 8}, {0, 7, 5}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 6}, {1, 4, 8}, {7, 0, 5}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 6}, {1, 4, 8}, {7, 5, 0}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 6}, {1, 4, 0}, {7, 5, 8}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 3, 0}, {1, 4, 6}, {7, 5, 8}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 0, 3}, {1, 4, 6}, {7, 5, 8}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 2, 3}, {1, 4, 6}, {7, 5, 8}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 4, 6}, {7, 5, 8}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-14.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{4, 3, 1}, {0, 7, 2}, {8, 5, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 3, 1}, {7, 0, 2}, {8, 5, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 0, 1}, {7, 3, 2}, {8, 5, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 0}, {7, 3, 2}, {8, 5, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 3, 0}, {8, 5, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 0, 3}, {8, 5, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {7, 5, 3}, {8, 0, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {0, 5, 3}, {7, 8, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-13.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{4, 1, 2}, {3, 0, 6}, {5, 7, 8}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {0, 3, 6}, {5, 7, 8}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {5, 3, 6}, {0, 7, 8}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {5, 3, 6}, {7, 0, 8}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {5, 3, 6}, {7, 8, 0}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {5, 3, 0}, {7, 8, 6}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {5, 0, 3}, {7, 8, 6}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {0, 5, 3}, {7, 8, 6}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-12.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 3, 5}, {7, 2, 6}, {8, 0, 4}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 5}, {7, 2, 6}, {8, 4, 0}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 5}, {7, 2, 0}, {8, 4, 6}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 0}, {7, 2, 5}, {8, 4, 6}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {7, 2, 5}, {8, 4, 6}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {7, 0, 5}, {8, 4, 6}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {7, 4, 5}, {8, 0, 6}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {7, 4, 5}, {0, 8, 6}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 4, 5}, {7, 8, 6}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-11.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{0, 4, 1}, {5, 3, 2}, {7, 8, 6}};// puzzle3x3-10.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 0, 1}, {5, 3, 2}, {7, 8, 6}};// puzzle3x3-10.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 0}, {5, 3, 2}, {7, 8, 6}};// puzzle3x3-10.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {5, 3, 0}, {7, 8, 6}};// puzzle3x3-10.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {5, 0, 3}, {7, 8, 6}};// puzzle3x3-10.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{4, 1, 2}, {0, 5, 3}, {7, 8, 6}};// puzzle3x3-10.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-10.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-10.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-10.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-10.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 3, 6}, {5, 2, 8}, {4, 0, 7}};// puzzle3x3-9.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {5, 2, 8}, {4, 7, 0}};// puzzle3x3-9.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 6}, {5, 2, 0}, {4, 7, 8}};// puzzle3x3-9.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 3, 0}, {5, 2, 6}, {4, 7, 8}};// puzzle3x3-9.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 3}, {5, 2, 6}, {4, 7, 8}};// puzzle3x3-9.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {5, 0, 6}, {4, 7, 8}};// puzzle3x3-9.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 5, 6}, {4, 7, 8}};// puzzle3x3-9.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {0, 7, 8}};// puzzle3x3-9.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-9.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-9.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{0, 4, 3}, {2, 1, 6}, {7, 5, 8}};// puzzle3x3-8.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 4, 3}, {0, 1, 6}, {7, 5, 8}};// puzzle3x3-8.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 4, 3}, {1, 0, 6}, {7, 5, 8}};// puzzle3x3-8.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{2, 0, 3}, {1, 4, 6}, {7, 5, 8}};// puzzle3x3-8.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{0, 2, 3}, {1, 4, 6}, {7, 5, 8}};// puzzle3x3-8.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {0, 4, 6}, {7, 5, 8}};// puzzle3x3-8.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};// puzzle3x3-8.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};// puzzle3x3-8.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-8.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 2, 3}, {0, 4, 8}, {7, 6, 5}};// puzzle3x3-7.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 8}, {7, 6, 5}};// puzzle3x3-7.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 0}, {7, 6, 5}};// puzzle3x3-7.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 5}, {7, 6, 0}};// puzzle3x3-7.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 5}, {7, 0, 6}};// puzzle3x3-7.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle3x3-7.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-7.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-7.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 2, 0}, {4, 8, 3}, {7, 6, 5}};// puzzle3x3-6.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 0}, {7, 6, 5}};// puzzle3x3-6.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 5}, {7, 6, 0}};// puzzle3x3-6.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 8, 5}, {7, 0, 6}};// puzzle3x3-6.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle3x3-6.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-6.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-6.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 0, 2}, {4, 6, 3}, {7, 5, 8}};// puzzle3x3-5.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 6, 3}, {7, 5, 8}};
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 6, 0}, {7, 5, 8}};
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 6}, {7, 5, 8}};
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{0, 1, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-4.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-4.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};// puzzle3x3-4.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-4.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-4.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 2, 3}, {0, 4, 5}, {7, 8, 6}};// puzzle3x3-3.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle3x3-3.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-3.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-3.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 2, 3}, {4, 0, 5}, {7, 8, 6}};// puzzle3x3-2.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-2.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-2.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 0}, {7, 8, 6}};// puzzle3x3-1.txt
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-1.txt
        solutionTileList.add(dbEntry);
        //dbBoard = new Board(dbEntry);
        //dbSearchNode = new SearchNode(dbBoard, 0, dbBoard.manhattan(), dbBoard.hamming(), null);
        //gameTree.put(dbSearchNode, dbSearchNode.GetPriority());
        dbEntry = new int[][]{{1, 0, 2}, {4, 5, 3}, {7, 8, 6}};
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 0}, {4, 5, 3}, {7, 8, 6}};
        solutionTileList.add(dbEntry);
        dbEntry = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};// puzzle3x3-0.txt
        solutionTileList.add(dbEntry);
// endregion
        /*Solutions -- write a loop for the solutionTileList that creates search nodes and adds them to the GameTree
         * then put minSearchNode in the MinPQ, and see if minSearchNode or its neighbors are in the tree. If so, put them
         * in the minPriority Queue with a higher priority*/
        for (int[][] t : solutionTileList) {
            Board b = new Board(t);
            //(Board b, int m, int manhattan, int hamming, SearchNode prev)
            SearchNode s = new SearchNode(b, 0, b.manhattan(), b.hamming(), null);
            gameTree.put(s, s.GetPriority());
        }
        /*Going to comment the following out for the time being. I want to see if just the nodes will give me the
        answer.*/
//        for (Object o : gameTree.keys()) {
//            SearchNode temp = (SearchNode) o;
//            for (Board NeigBoard : temp.GetCurrentBoard().neighbors()) {
//                SearchNode temp1 = new SearchNode(NeigBoard, temp.numOfMoves + 1, NeigBoard.manhattan(),
//                        NeigBoard.hamming(), temp);
//                gameTree.put(temp1, temp1.GetPriority());
//            }
//        }
//        for (int i = 0; i < 1000; i++) {
//            // Find the neighbors of GameTree nodes and put them in the tree if Manhattan distance is below what you need
//            for (Object o : gameTree.keys()) {
//                SearchNode temp = (SearchNode) o;
//                for (Board NeigBoard : temp.GetCurrentBoard().neighbors()) {
//                    if (temp.prevSearchNode == null && NeigBoard.manhattan() < 15) {
//                        SearchNode temp1 = new SearchNode(NeigBoard, temp.numOfMoves + 1, NeigBoard.manhattan(),
//                                NeigBoard.hamming(), temp);
//                        gameTree.put(temp1, temp1.GetPriority());
//
//                    } else if (temp.prevSearchNode != null && !NeigBoard.equals(temp.prevSearchNode.GetCurrentBoard())
//                            && NeigBoard.manhattan() < 15) {
//                        SearchNode temp1 = new SearchNode(NeigBoard, temp.numOfMoves + 1, NeigBoard.manhattan(),
//                                NeigBoard.hamming(), temp);
//                        gameTree.put(temp1, temp1.GetPriority());
//                    }
//                }
//            }
//        }
        StdOut.println("There are  " + gameTree.size() + " nodes in the Game Tree.");
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
            }
            for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
                SearchNode temp1 = new SearchNode(b, minSearchNode.GetMovesCount() + 1, b.manhattan(), b.hamming(),
                        minSearchNode);
                ;
                /*If the node is in the tree already, put it in the min search node with more priority.*/
                if (gameTree.get(temp1) != null) {
                    temp1 = new SearchNode(b, 0, b.manhattan(), b.hamming(), minSearchNode);
                    StdOut.println("*********Found a node in the tree, and put it in priority queue.");
                }

                if (minSearchNode.GetPrevSearchNode() == null && !b.equals(initialBoard)) {
                    currentPriorityQueue.insert(temp1);
                } else if (minSearchNode.GetPrevSearchNode() != null &&
                        !b.equals(minSearchNode.GetPrevSearchNode().GetCurrentBoard())) {
                    if (currentPriorityQueue.size() > 800) {
                        MinPQ<SearchNode> copyPQ = new MinPQ<SearchNode>(1000, new Comparator<SearchNode>() {
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
//                if (gameTree.floor(minSearchNode) != minSearchNode) {
//                    StdOut.println("There is a node closer to the goal, you may want to check out.");
//                    StdOut.println("Here is minSearchNode: " + minSearchNode.GetCurrentBoard() + " with rank of: " +
//                            gameTree.rank(minSearchNode) +
//                            " Here is the node: " + (gameTree.select(gameTree.rank(minSearchNode) - 1)).GetCurrentBoard());
//                }
                minSearchNode = currentPriorityQueue.delMin();
//                StdOut.println("This is the rank of the minimum search node. " + gameTree.rank(minSearchNode));
//                StdOut.println("This is the size of game tree. " + gameTree.size());
//                StdOut.println("This is the size of minimum priority queue. " + currentPriorityQueue.size());
                // See what is the rank of minSearchNode in the tree at this point? If it is less than the number of
                // moves in it, then there might be a shorter path to the goal
            } else {
                StdOut.println("Why is PQ empty? ");
            }
        }
        if (minSearchNode.GetCurrentBoard().isGoal()) {
            solvable = true;
            moves = minSearchNode.numOfMoves;
            //StdOut.println("Here is the minimum:  " + gameTree.min().GetCurrentBoard());
            while (!minSearchNode.GetCurrentBoard().equals(initialBoard)) {
                //StdOut.println("This is the rank of minSearchNode :  " + gameTree.rank(minSearchNode));
//                StdOut.println("Here is floor of minSearchNode: " + gameTree.floor(minSearchNode).GetCurrentBoard());

                floorsList.add(gameTree.floor(minSearchNode));
                solutionList.add(minSearchNode);
                solutionBoardList.add(minSearchNode.GetCurrentBoard());
                minSearchNode = minSearchNode.GetPrevSearchNode();
            }
            solutionList.add(initialSearchNode);
            solutionBoardList.add(initialBoard);
        } else {
            solvable = false;
        }
        for (Board b : solutionBoardList) {
            for (SearchNode floorNode : floorsList) {
                if (b.equals(floorNode.GetCurrentBoard()))
                    //StdOut.println("This node in solution exists in the floors: " + b);
                    for (Board bNei : b.neighbors()) {
                        if (bNei.equals(floorNode.GetCurrentBoard()))
                            StdOut.println(bNei + "is a neighbor of " + b + " in " +
                                    "the solution and exists in the floors list.");
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

        private void changePriority() {
            this.hamming = this.hamming * 4;
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
            else this.changePriority();
            return 1;
        }
    }

    // endregion
    // test client (see below)
    public static void main(String[] args) {

        int[][] testTiles1 = {{5, 2, 3}, {4, 7, 0}, {8, 6, 1}}; // puzzle 21 - actual = 29 moves
        //int[][] testTiles1 = {{6, 3, 0}, {5, 4, 8}, {7, 2, 1}};
        //int[][] testTiles1 = {{5, 4, 1}, {7, 3, 2}, {0, 8, 6}};  // This gets to goal in 12 moves
        //int[][] testTiles1 = {{5, 0, 2}, {4, 7, 3}, {8, 6, 1}};  // This gets to goal in 21 moves
        //int[][] testTiles1 = {{5, 4, 1}, {3, 0, 2}, {7, 8, 6}};  //  This gets to goal in 12 moves
        // int[][] testTiles1 = {{1, 2, 7}, {6, 4, 3}, {5, 0, 8}};  //  This gets to goal in 12 moves
        // int[][] testTiles1 = {{4, 1, 2}, {3, 7, 6}, {5, 8, 0}};  // This gets to goal in 18 moves
        // int[][] testTiles1 = {{1, 5, 2}, {0, 7, 4}, {8, 6, 3}};  // 11 moves
        //int[][] testTiles1 = {{2, 3, 5}, {1, 8, 4}, {7, 6, 0}};  // 10 moves
        //int[][] testTiles1 = {{4, 3, 1}, {7, 2, 6}, {8, 0, 5}};  // 15 moves
        //int[][] testTiles1 = {{2, 3, 5}, {1, 8, 4}, {7, 6, 0}};  // 10 moves
        //int[][] testTiles1 = {{4, 0, 1}, {7, 3, 2}, {8, 5, 6}};  // 11 moves
        // int[][] testTiles1 = {{4, 1, 2}, {3, 6, 8}, {5, 7, 0}};  //14 moves
        //int[][] testTiles1 = {{2, 0, 7}, {1, 4, 3}, {6, 5, 8}};  // 19 moves
        //int[][] testTiles1 = {{4, 1, 2}, {3, 6, 8}, {5, 7, 0}};  // 14 moves
        //int[][] testTiles1 = {{4, 0, 1}, {7, 3, 2}, {8, 5, 6}};  // 11
        //int[][] testTiles1 = {{2, 3, 5}, {1, 8, 4}, {7, 6, 0}}; //10
        //int[][] testTiles1 = {{2, 0, 3}, {1, 7, 6}, {5, 4, 8}};  // 9 moves
        // int[][] testTiles1 = {{0, 2, 5}, {1, 3, 4}, {7, 8, 6}};  // 10 moves
        // int[][] testTiles1 = {{1, 2, 3}, {0, 7, 6}, {5, 4, 8}}; // 7 moves
        //int[][] testTiles1 = {{1, 2, 0}, {4, 8, 3}, {7, 6, 5}};// 6 moves
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
