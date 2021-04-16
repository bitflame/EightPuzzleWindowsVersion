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
    private boolean isConsecitiveNeighbor;
    private int blankCol;
    private int blankRow;
    private int dbMoves;
    private List<SearchNode> dB = new ArrayList<>();

    //    private List<SearchNode> GeneratePermutations(int boardDimensions, Integer[] currentCycle, SearchNode goalNode) {
//        char[][] permutedTiles = new char[boardDimensions][boardDimensions];
//        char[][] parentTiles = new char[boardDimensions][boardDimensions];
//        int[] boardArray = new int[boardDimensions * boardDimensions];
//        int[] boardArrayCopy = new int[boardDimensions * boardDimensions];
//        List<SearchNode> currentPermutations = new ArrayList<>();
    // Copy the contents of the board into a one dimensional array
    // Change this method so you start with a board with 0 at i and moving it to j. Doing this for all the cycles
    // creates all the permutations that get you to the goal
//        for (int i = 0; i < boardArray.length; i++) {
//            boardArray[i] = i + 1;
//            boardArrayCopy[i] = i + 1;
//        }
//        boardArray[currentCycle[0]] = 0;
//        boardArrayCopy[currentCycle[0]] = 0;
//        permutedTiles = ConvertOneDemensionalArrayToTwoDemensional(boardArray, boardDimensions);
//        Board tempBoard = new Board(permutedTiles);
//        // Create permutations using the current cycle currentCycle.length number of times. You should get n! permutations
//        // of the length of the cycle. Test this later; perhaps by converting the larger cycle to transpositions
//        SearchNode parentS = new SearchNode(tempBoard, 0, null);
//        currentPermutations.add(parentS);
//        for (int i = 0; i < currentCycle.length - 1; i++) {
//            int temp = boardArray[currentCycle[currentCycle.length - 1]];
//            for (int j = currentCycle.length - 1; j > 0; j--) { // shift the contents of board array at cycle address one
//                // slot over
//                boardArray[currentCycle[j]] = boardArray[currentCycle[j - 1]];
//            }
//            boardArray[currentCycle[0]] = temp;
//            // now move all the boardArray to a two dimensional board
//            int currentCounter = 0;
//            for (int g = 0; g < boardDimensions; g++) {
//                for (int h = 0; h < boardDimensions; h++) {
//                    permutedTiles[g][h] = (char) boardArray[currentCounter];
//                    parentTiles[g][h] = (char) boardArrayCopy[currentCounter];
//                    currentCounter++;
//                }
//            }
//            tempBoard = new Board(permutedTiles);
//            Board tempBoardParent = new Board(parentTiles);
//            parentS = new SearchNode(tempBoardParent, parentS.numOfMoves + 1, parentS.prevSearchNode);
//            SearchNode s = new SearchNode(tempBoard, parentS.numOfMoves + 1, parentS);
//            currentPermutations.add(s);
//            // now move all the boardArray to a two dimensional board
//            for (int g = 0; g < (boardDimensions * boardDimensions); g++) {
//                boardArrayCopy[g] = boardArray[g];
//            }
//        }
//        return currentPermutations;
//    }
//
//    private char[][] ConvertOneDemensionalArrayToTwoDemensional(int[] oneD, int twoDemLeng) {
//        int currentCounter = 0;
//        char[][] twoDemArr = new char[twoDemLeng][twoDemLeng];
//        for (int g = 0; g < twoDemLeng - 1; g++) {
//            for (int h = 0; h < twoDemLeng - 1; h++) {
//                twoDemArr[g][h] = (char) oneD[currentCounter];
//                twoDemArr[g][h] = (char) oneD[currentCounter];
//                currentCounter++;
//            }
//        }
//        return twoDemArr;
//    }
    public Solver(Board initialBoard) {
        if (initialBoard == null) {
            throw new IllegalArgumentException("The Board object is empty.");
        }

        //if (this.isSolvable()) { // How to figure out if the board is solvable or not?
        solvable = true; // for now
        if (solvable == false) return;
        SearchNode initialSearchNode = new SearchNode(initialBoard, 0, initialBoard.manhattan(), initialBoard.hamming(), null);
//        StdOut.print("Here is the first node: " + initialSearchNode.GetCurrentBoard() + " With Priority of: " +
//                initialSearchNode.GetPriority() + " Manhattan value of: " + initialSearchNode.manhattan +
//                " Hamming distance of: " + initialSearchNode.hamming + " Number of moves: " + initialSearchNode.numOfMoves);
        Board currentTwinBoard = initialBoard.twin();
        SearchNode initialTwinSearchNode = new SearchNode(currentTwinBoard, 0, initialBoard.manhattan(), initialBoard.hamming(), null);
        //StdOut.println("This table is in solver: " + initialBoard);
//FIXME: Work on the following tomorrow: 1) My understanding is that we are to implement two priority queues side by side.
// The first queue is to attempt to solve the puzzle with the input board. The other queue is to attempt to solve the
// puzzle with a twin of the input board. Run through the steps of the A* algorithm on each of the two priority queues
// in step until one of them results in a goal board. If the "twin" queue finds a solution, then the original puzzle is
// unsolvable.
// here is someone else's ccomment: 2) Agree with the answers by LJ. Just want to add that you need to implement some sort
// of pointer to track the parent of each search node. When you find a solution, you can follow the parents all the way
// back to the beginning. The parent pointers form sort of a reversed tree structure (no child connections, but a parent
// connection), and are independent of the priority queue structure.
// One more; 3) I am using a counter right now for the number of moves needed to solve the puzzle, while I need to walk bakc
// once I find a solution. I may run into an issue with how I am doing it right now. Here is how others have done it: In
// my solution, I didn't create a stack until the puzzle was solved. At that point, you can take the current search node
// and walk backwards through each node's "previous" pointer.
// 4) Also make sure to only use the approved apis even in the main()
// 5) Also this one:  i am only using Manhattan distance, not priority which should include the number of moves up to here
// also.6) From the faqs: You will compute the priority function in Solver by calling hamming() or manhattan() and adding to
// it the number of moves.
//        MinPQ<SearchNode> currentPriorityQueueTwin = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
//            @Override
//            public int compare(SearchNode o1, SearchNode o2) {
//                if (o1.GetPriority() > o2.GetPriority()) return 1;
//                else if (o2.GetPriority() > o1.GetPriority()) return -1;
//                else if (o1.GetPriority() == o2.GetPriority()) {
//                    if (o1.hamming > o2.hamming) return 1;
//                    else if (o2.hamming > o1.hamming) return -1;
//                }
//                return 0;
//            }
//        });
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

//        twinMoves = 0;
//        StdOut.println();
//        StdOut.println(initialBoard.toString() + "Adding the first Board with hamming distance of: " + initialBoard.hamming() +
//                " and manhattan distance of: " + initialBoard.manhattan() + " To Priority Queue");
        currentPriorityQueue.insert(initialSearchNode);

        currentPriorityQueueTwin.insert(initialTwinSearchNode);
//        StdOut.println("Adding the first Twin Board with hamming distance of: " + currentTwinBoard.hamming() +
//                " and manhattan distance of: " + currentTwinBoard.manhattan() + " To Twin Priority Queue");

//        GameTree<SearchNode, Integer> gameTree = new GameTree<SearchNode, Integer>();
//        GameTree<SearchNode, Integer> gameTreeTwin = new GameTree<SearchNode, Integer>();
//        Board tempBoard;
//        SearchNode tempSearchNode;
//        int[][] t1 = {{1, 0}, {3, 2}};
//        int t1_Moves = 1;
//        tempBoard = new Board(t1);
//        tempSearchNode = new SearchNode(tempBoard, t1_Moves, null);
//        gameTree.put(tempSearchNode, tempSearchNode.numOfMoves);
//        int[][] t2 = {{1, 2}, {0, 3}};
//        int t2_Moves = 1;
//        tempBoard = new Board(t2);
//        tempSearchNode = new SearchNode(tempBoard, t2_Moves, null);
//        gameTree.put(tempSearchNode, tempSearchNode.numOfMoves);
//        int[][] t3 = {{0, 1}, {3, 2}};
//        int t3_Moves = 2;
//        tempBoard = new Board(t3);
//        tempSearchNode = new SearchNode(tempBoard, t3_Moves, null);
//        gameTree.put(tempSearchNode, tempSearchNode.numOfMoves);
//        int[][] t4 = {{3, 1}, {0, 2}};
//        int t4_Moves = 3;
//        tempBoard = new Board(t4);
//        tempSearchNode = new SearchNode(tempBoard, t4_Moves, null);
//        gameTree.put(tempSearchNode, tempSearchNode.numOfMoves);
//        int[][] t5 = {{2, 3}, {1, 0}};
//        int t5_Moves = 4;
//        tempBoard = new Board(t5);
//        tempSearchNode = new SearchNode(tempBoard, t5_Moves, null);
//        gameTree.put(tempSearchNode, tempSearchNode.numOfMoves);
//        int[][] t6 = {{2, 3}, {0, 1}};
//        int t6_Moves = 5;
//        tempBoard = new Board(t6);
//        tempSearchNode = new SearchNode(tempBoard, t6_Moves, null);
//        gameTree.put(tempSearchNode, tempSearchNode.numOfMoves);
//        int[][] t7 = {{0, 3}, {2, 1}};
//        int t7_Moves = 6;
//        tempBoard = new Board(t7);
//        tempSearchNode = new SearchNode(tempBoard, t7_Moves, null);
//        gameTree.put(tempSearchNode, tempSearchNode.numOfMoves);
// Create permutations for nine cycles
        // convert the address of each cycle to conventional two dimensional array addressing
        int index = 1;
        int[][] goal = new int[initialBoard.dimension()][initialBoard.dimension()];
        for (int i = 0; i <= initialBoard.dimension() - 1; i++) {
            for (int j = 0; j <= initialBoard.dimension() - 1; j++) {
                goal[i][j] = (char) index;
                index++;
            }
        }
        // I may have to remove the goal board from the BST, I do not see why I should keep it. In fact, it may cause
        // problems for me.
        goal[initialBoard.dimension() - 1][initialBoard.dimension() - 1] = 0;
        Board gBoard = new Board(goal);
        SearchNode gNode = new SearchNode(gBoard, 0, 0, 0, null);
        // adding puzzle 20 to the search node
        //int[][] dbEntry1 = {{1, 6, 4}, {7, 0, 8}, {2, 3, 5}};
        int[][] dbEntry1 = {{1, 6, 4}, {7, 0, 8}, {2, 3, 5}};//puzzle 20
        Board dbBoard1 = new Board(dbEntry1);
        dbMoves = 0;
        GameTree<Board, Integer> gameTree = new GameTree<>();
        gameTree.put(dbBoard1, dbMoves);
        dbMoves++;
        for (Board b : dbBoard1.neighbors()) {
            gameTree.put(b, dbMoves);
        }
        //gameTree.put(gNode, gBoard.manhattan());
//        List<Integer[]> cycles = new ArrayList<>();
//        Integer[] cycleOne = {0, 1, 2, 3, 7, 6, 5};// {1, 2, 3, 4, 5, 6, 7}
        //Integer[] cycleOne = {0, 1, 2, 3, 4, 5, 6};
//        cycles.add(cycleOne);
        //Integer[] cycleTwo = {1, 2, 3, 7, 6};// {2, 3, 4, 5, 6}
        //Integer[] cycleTwo = {1, 2, 3, 7, 6};
//        cycles.add(cycleTwo);
//        Integer[] cycleThree = {2, 3, 7};// {3, 4, 5}
        //Integer[] cycleThree = {2, 3, 4};
//        cycles.add(cycleThree);
//        Integer[] cycleFour = {7, 6, 5, 4, 8, 9, 10};// {5, 6, 7, 8, 9, 10, 11}
        //Integer[] cycleFour = {4, 5, 6, 7, 8, 9, 10};
//        cycles.add(cycleFour);
//        Integer[] cycleFive = {6, 5, 4, 8, 9}; // {6, 7, 8, 9, 10}
//        cycles.add(cycleFive);
//        Integer[] cycleSix = {5, 6, 8}; // {7, 8, 9}
//        cycles.add(cycleSix);
//        Integer[] cycleSeven = {8, 9, 10, 11, 15, 14, 13};//{9, 10, 11, 12, 13, 14, 15 }
//        cycles.add(cycleSeven);
//        Integer[] cycleEight = {9, 10, 11, 15, 14};//{10, 11 ,12 ,13 ,14}
//        cycles.add(cycleEight);
//        Integer[] cycleNine = {10, 11, 15};//{11, 12, 13}
//        cycles.add(cycleNine);

//        for (Integer[] currentCycle : cycles) {
//            for (SearchNode s : GeneratePermutations(initialBoard.dimension(), currentCycle, gNode)) {
//                gameTree.put(s, s.GetPriority());
//                for (Object o : gameTree.keys()) {
//                    SearchNode ss = o;
//                    StdOut.println(ss.GetCurrentBoard());
//                }
//            }
//        }
// create a new search node - still need to figure out what the previous node would be and number of moves
        //SearchNode newSearchNode = new SearchNode(initialBoard, 1, null);
        // add the node to the GameTree
        //gameTree.put(initialSearchNode, initialSearchNode.GetPriority());
// Test to see if testBoard is in GameTree
//        char[][] expected = {{5, 4, 0, 1}, {6, 7, 3, 2}, {8, 9, 10, 11}, {12, 13, 14, 15}};
//        Board expectedB = new Board(expected);
//        for (Object s : gameTree.keys()) {
//            SearchNode temp =  s;
//            if (temp.GetCurrentBoard().equals(expectedB)) {
//                StdOut.println("Found the test key in Game Tree Binary Search Tree.");
//            }
//        }
//        SearchNode expectedSearchNode = new SearchNode(expectedB, expectedB.manhattan(), null);
//        if (gameTree.get(expectedSearchNode) != null) {
//            StdOut.println("Found it with get also.");
//        }
        //gameTree.put(initialSearchNode, initialSearchNode.GetPriority());
        //gameTreeTwin.put(initialTwinSearchNode, initialTwinSearchNode.numOfMoves);
        //gameTreeTwin.put(initialTwinSearchNode, ++twinValue);
//        StdOut.println("Adding " + initialSearchNode.GetCurrentBoard().toString() + " with hamming: " + initialSearchNode.GetCurrentBoard().hamming() +
//                " with manhattan: " + initialSearchNode.GetCurrentBoard().manhattan());
        SearchNode minSearchNode = currentPriorityQueue.delMin();
        SearchNode minTwinNode = currentPriorityQueueTwin.delMin();
        //SearchNode minSearchNodeTwin = currentPriorityQueueTwin.delMin();
//        StdOut.println("Adding " + initialTwinSearchNode.GetCurrentBoard().toString() + " with hamming: " + initialSearchNode.GetCurrentBoard().hamming() +
//                " with manhattan: " + initialSearchNode.GetCurrentBoard().manhattan() + " To Twin Priority Queue");

        //int counter = 0;
        //while (gameTree.get(gNode) == null) { // I need to find a way to keep adding nodes until all the paths lead to
        // gNode i.e. until the floor of every child node is gNode, then take the shortest path

        // I need to figure out a way to run the following code while game tree has nodes with neighbors other than the
        // goal node
        //while (!(minSearchNode.GetCurrentBoard().isGoal()) || gameTree.get(gNode) != null) {

        boolean loopCond = true;
        outerloop:
        while (loopCond) {
//            if (minSearchNodeTwin.GetCurrentBoard().isGoal()) {
//                solvable = false;
//                break;
//            }
            // I tested to see if I can check hamming and manhattan distance to stop adding boards and improve performance
//            if ((minSearchNode.GetCurrentBoard().hamming() % 2) == 1) {
//                StdOut.println("Manhattan distance of board is odd");
//                break;
//            }
//            for (Board bt : minSearchNodeTwin.GetCurrentBoard().neighbors()) {
//                // add a search node to the twin priority queue and twin GameTree only if it is not already in the GameTree
//                // or the priority queue
//                // I tested to see if checking manhattan or hamming distance improves the performance b/c goal has
//                // manhattan and hamming of zero i.e. even
//
//                SearchNode temp = new SearchNode(bt, minSearchNodeTwin.GetMovesCount() + 1, minSearchNodeTwin);
//                if (minSearchNodeTwin.GetPrevSearchNode() == null) {
//                    currentPriorityQueueTwin.insert(temp);
//                    gameTreeTwin.put(temp, twinValue);
//                } else if (!temp.GetCurrentBoard().equals(minSearchNodeTwin.GetPrevSearchNode().GetCurrentBoard())) {
//                    //StdOut.println("Adding to Twin Priority Queue.");
////                    StdOut.println("Adding neighbor Board with " + bt.toString() + " and hamming distance of: " + bt.hamming() +
////                            " and manhattan distance of: " + bt.manhattan() + " To Twin Priority Queue");
////                    StdOut.println("Adding neighbor Board with hamming distance of: " + s.GetCurrentBoard().hamming() +
//                            " and manhattan distance of: " + s.GetCurrentBoard().manhattan() + " Current moves count: "
//                            + twinMoves + " To Twin Priority Queue");
//                    // if you remove the item, you can use the same index next time to get another item
//
//                    currentPriorityQueueTwin.insert(temp);
//                    // I do not think I need to know how many moves it takes for the twin to solve so just using value
//                    gameTreeTwin.put(temp, twinValue);
//                } // else StdOut.println("Twin game tree already has this node.");
//            }
            //currentPriorityQueueTwin.insert( gameTreeTwin.min());
            //Create goal neighbors
//            for (Object o : gameTree.keys()) {
//                SearchNode s =  o;
//                Board tBoard = s.GetCurrentBoard();
//                if (minSearchNode.GetCurrentBoard().equals(tBoard)) {//found a match of minSearchNode in the Game Tree
//                    SearchNode temp = new SearchNode(s.GetCurrentBoard(), minSearchNode.GetMovesCount() + 1, minSearchNode);
//                    minSearchNode = temp;
//                    while (!minSearchNode.GetCurrentBoard().isGoal()) {
//                        temp = new SearchNode(minSearchNode.prevSearchNode.GetCurrentBoard(), minSearchNode.GetMovesCount() + 1,
//                                minSearchNode);
//                        minSearchNode = temp;
//                    }
//                    break;
//                }
//            SearchNode temp;
//            if (gameTree.rank(s) == 0) {
//                for (Board nei : s.GetCurrentBoard().neighbors()) {
//                    if (s.GetPrevSearchNode() == null) {
//                        temp = new SearchNode(nei, s.GetMovesCount() + 1, s);
//                        gameTree.put(temp, value);
//                        value++;
//                    } else if (!s.GetPrevSearchNode().equals(nei)) {
//                        gameTree.put(new SearchNode(nei, s.GetMovesCount() + 1, s), value);
//                        value++;
//                    }
//                }
//            }
//        }
            // Find the neighbors of all the leaves in GameTree and add them to the GameTree
//            counter++;
//            if (counter > 500) {
//                StdOut.println("Reseting Priority Queue.");
//                currentPriorityQueue = new MinPQ<>();
//                currentPriorityQueue.insert(minSearchNode);
//            }
            // Generate 3 - cycles from the goal
//            for (int z = 0; z <= counter; z++) {
//                int d = initialBoard.dimension();
//                Integer[] currentCycle = {d, d - 1, d - 2};
//                for (SearchNode currentPerm : GeneratePermutations(gBoard.dimension(), currentCycle)) {
//                    gameTree.put(currentPerm, currentPerm.GetPriority());
//                }
//            }
            // If you find that minSearchNode is already on the tree, then set minSearchnode equal to tree node's parent
//            if (gameTree.get(minSearchNode) != null) {
//                SearchNode s =  gameTree.floor(minSearchNode);
//                Board t = s.GetCurrentBoard();
//                StdOut.println("There was a match in the gametree.");
//                s = new SearchNode(t, minSearchNode.GetMovesCount() + 1, minSearchNode);
//                minSearchNode = s;
//            }


//                    StdOut.println("Successful Match. !!!!! " +
//                        "Minimum Search Node: " + minSearchNode.GetCurrentBoard() + "Game Tree: " + s.GetCurrentBoard());
//            if (minSearchNode.GetPriority() > (minSearchNode.manhattan * 4)) {
//                StdOut.println("Working ");
//                Object o = gameTree.get(gameTree.select(0));
//                SearchNode s =  o;
//                minSearchNode = s;
//                currentPriorityQueue = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
//                    @Override
//                    public int compare(SearchNode o1, SearchNode o2) {
//                        if (o1.GetPriority() > o2.GetPriority()) return 1;
//                        else if (o2.GetPriority() > o1.GetPriority()) return -1;
//                        else if (o1.GetPriority() == o2.GetPriority()) {
//                            if (o1.hamming > o2.hamming) return 1;
//                            else if (o2.hamming > o1.hamming) return -1;
//                        }
//                        return 0;
//                    }
//                });
//                currentPriorityQueue.insert(minSearchNode);
//
//            }
//            currentPriorityQueue = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
//                @Override
//                public int compare(SearchNode o1, SearchNode o2) {
//                    if (o1.GetPriority() > o2.GetPriority()) return 1;
//                    else if (o2.GetPriority() > o1.GetPriority()) return -1;
//                    return 0;
//                }
//            });
//            currentPriorityQueueTwin = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
//                @Override
//                public int compare(SearchNode o1, SearchNode o2) {
//                    if (o1.GetPriority() > o2.GetPriority()) return 1;
//                    else if (o2.GetPriority() > o1.GetPriority()) return -1;
//                    return 0;
//                }
//            });
//            if (!minSearchNode.equals(initialSearchNode)) {
//                for (SearchNode s : gameTree.keys()) {
//                    if (s.GetPriority() < minSearchNode.GetPriority())
//                        currentPriorityQueue.insert(s); // only add the leaf nodes to the priority queue
//                }
//                currentPriorityQueue.insert(gameTree.floor(minSearchNode));
//            }
//            if (!minTwinNode.equals(initialTwinSearchNode)) {
//                for (SearchNode st : gameTreeTwin.keys()) {
//                    if (st.GetPriority() < minTwinNode.GetPriority())
//                        currentPriorityQueueTwin.insert(st);
//                }
//                currentPriorityQueueTwin.insert(gameTreeTwin.floor(minTwinNode));
//            }
            // Added the following for loop 4/13/21 2:47
            for (Board b : gameTree.keys()) {
                for (Board bNei : b.neighbors()) {
                    if (bNei != initialBoard && bNei != minSearchNode.GetCurrentBoard()) {
                        gameTree.put(bNei, dbMoves);
                    }
                    dbMoves++;
                    if (gameTree.get(minSearchNode.GetCurrentBoard()) != null) {
                        StdOut.println("Found a match of Minimum Search Tree in the Game Tree.");
                    }
                }
            }
            int[][] targetTiles = {{5, 2, 0}, {4, 1, 3}, {8, 7, 6}};//
            Board targetBoard = new Board(targetTiles);

            if (minTwinNode.GetCurrentBoard().isGoal()) {
                //StdOut.println("Matched the goal in the twin priority queue.");
                solvable = false;
                break;
            } else if (minSearchNode.GetCurrentBoard().isGoal()) {
                solvable = true;
                break;
            }
            // Check to see if game tree has a match

            //if (gameTreeTwin.get(minSearchNode) != null) {
            // You have calculated this already
            //break;
            //}
            for (Board tb : minTwinNode.GetCurrentBoard().neighbors()) {
                SearchNode temp1Twin = new SearchNode(tb, minTwinNode.GetMovesCount() + 1, tb.manhattan(), tb.hamming(), minTwinNode);

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
//                        if (s.hamming < minTwinNode.hamming) {
//                            copyPQ.insert(s);
//                        }
                            copyTwinPQ.insert(currentPriorityQueueTwin.delMin());
//                        if (s.GetPriority() < minSearchNode.GetPriority()) {
//                            copyPQ.insert(s);
//                        }
                        }
                        currentPriorityQueueTwin = copyTwinPQ;
                    }
                    currentPriorityQueueTwin.insert(temp1Twin);
                    //gameTreeTwin.put(temp1Twin, temp1Twin.GetPriority());
                }
            }
            for (Board b : minSearchNode.GetCurrentBoard().neighbors()) {
                // If manhattan of all the neighbors are more than the minSearchNode, and there is another node at the same
                // level minSearchNode or higher that I have not tried with a lower manhattan, I guess I should try it.
                // are there any nodes in the tree, which have a lower manhattan than minSearchNode, and have not been
                // used yet? i.e. it is not the parent of any nodes? And they are not the immediate neighbor of the
                // current minSearchNode
                //StdOut.println(gameTree.rank(minSearchNode));
                //if (gameTree.rank(minSearchNode) > 1) {
                //minSearchNode = gameTree.select(gameTree.rank())
                //for (Object o : gameTree.keys(gameTree.min(), minSearchNode)) {
                // If any of these nodes are used before, they should be the floor or ceiling of another node
                // in the same subtree, but that is the case even if they are not used
                //}
                //}
//                StdOut.println("Current Minimum Search Node is:  " + minSearchNode.GetCurrentBoard().toString() + " its hamming distance is: " +
//                        minSearchNode.GetCurrentBoard().hamming() + " its manhattan distance is: " + minSearchNode.GetCurrentBoard().manhattan());
//                StdOut.println();
//                StdOut.println("The current neighbor being considered is : " + b.toString() +
//                        " its manhattan value is: " + b.manhattan());
                SearchNode temp1 = new SearchNode(b, minSearchNode.GetMovesCount() + 1, b.manhattan(), b.hamming(), minSearchNode);

                if (gameTree.get(b) != null) {
                    StdOut.println("Found a match in a GameTree of size: " + gameTree.size());
                    if (!gameTree.floor(b).equals(b))
                        StdOut.println("Found a match with a lower floor.");
                }


                // Added the following while loop 4/13/21 2:47
//                while (gameTree.get(temp1) != null) {
//                    Object o = gameTree.get(temp1);
//                    currentPriorityQueue.insert((SearchNode) o);
//                    break;
//                }
                if (minSearchNode.GetPrevSearchNode() == null && !b.equals(initialBoard)) {

//                    StdOut.println();
//                    StdOut.println("Adding neighbor Board : " + b.toString() + " with hamming distance of :  " + b.hamming() +
//                            " and manhattan distance of:  " + b.manhattan() + " Number of moves: " + temp1.numOfMoves +
//                            " To priority queue.");
                    //SearchNode result =  gameTree.get(temp1);
                    currentPriorityQueue.insert(temp1);
//                    gameTree.put(temp1, gMTreeIndex);
//                    gMTreeIndex++;
                } else if (minSearchNode.GetPrevSearchNode() != null && !b.equals(minSearchNode.GetPrevSearchNode().GetCurrentBoard())) {
                    // Add: if b's manhattan is more than minSearchNode's manhattan, check for a smaller manhattan in the tree

                    if (currentPriorityQueue.size() > 800) {
                        //StdOut.println("resetting the priority queue.");
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
                        //GameTree<SearchNode, Integer> gameTreeCopy = new GameTree<SearchNode, Integer>();
                        //int treeValue = 0;
                        //StdOut.println("Size of game tree before pruning: " + gameTree.size());
                        for (int i = 0; i < 100; i++) {
                            copyPQ.insert(currentPriorityQueue.delMin());
                            //treeValue++;
                            //gameTreeCopy.put(s, treeValue);
                        }
                        //StdOut.println("Size of copyPQ: " + copyPQ.size());
                        //StdOut.println("Size of game tree after prunning: " + gameTree.size());
                        currentPriorityQueue = copyPQ;
                        //gameTree = gameTreeCopy;
                    }
//                    if (gameTree.size() > 500000) {
//                        StdOut.println("Reseting the tree");
//                        GameTree<SearchNode, Integer> copyTree = new GameTree<SearchNode, Integer>();
//                        for (SearchNode s : gameTree.keys()) {
//                            if (s.GetCurrentBoard().manhattan() < initialBoard.manhattan()) {
//                                copyTree.put(s, s.GetPriority());
//                            }
//                        }
//                        gameTree = copyTree;
//                    }
//                    if (minSearchNode.GetCurrentBoard().manhattan() > minSearchNode.prevSearchNode.GetCurrentBoard().manhattan()) {
//                        StdOut.println("Parent of Minimum Search Node is:  " + minSearchNode.prevSearchNode.GetCurrentBoard().toString() +
//                                " its hamming distance is: " + minSearchNode.prevSearchNode.GetCurrentBoard().hamming() +
//                                " its manhattan distance is: " + minSearchNode.prevSearchNode.GetCurrentBoard().manhattan());
//                        StdOut.println();
//                        StdOut.println("Current Minimum Search Node is:  " + minSearchNode.GetCurrentBoard().toString() +
//                                " its hamming distance is: " + minSearchNode.GetCurrentBoard().hamming() +
//                                " its manhattan distance is: " + minSearchNode.GetCurrentBoard().manhattan());
//                        StdOut.println("Here are all neighbors from which minimumSearchNode was choosen: ");
//                        for (Board bo : minSearchNode.prevSearchNode.GetCurrentBoard().neighbors()) {
//                            StdOut.println(bo.toString() + "Manhattan: " + bo.manhattan() + "Hamming: " + bo.hamming() +
//                                    "Number of moves: ");
//                        }
//                    }
                    //StdOut.println("<<<<<<<<<<<<<<<<<<<<<<<<<<Here are all the boards in the GameTree.>>>>>>>>>>>>>>>>");
//                    for (Object o : gameTree.keys()) {
//                        SearchNode s =  o;
//                StdOut.println(s.GetCurrentBoard());
//  Now I have to match a node close to the answer and make sure I can get to the answer. Does minSearchNode match any
//  of nodes already in the tree? If so follow it to the goal. This is where I should pick up tomorrow. Some how iteratively
// search the tree and cut off branches that are too far. And if they are closed, to add them to my path and get to solution
//                        if (s.manhattan > (initialBoard.manhattan() * 2)) gameTree.delete(s);
//                        if (s.GetCurrentBoard().equals(temp1.GetCurrentBoard()) && !s.equals(gNode)) {
//                            minSearchNode = new SearchNode(temp1.GetCurrentBoard(), minSearchNode.numOfMoves + 1, minSearchNode);
//                            StdOut.println(" The Node that matched the neighbor: " + s.GetCurrentBoard() + " Manhatan Distance: " +
//                                    s.manhattan + " The Rank of the Node " +
//                                    "that matched the neighbor: " + gameTree.rank(s));
//                            StdOut.println(" The parent of the Node that matched the neighbor: " + s.prevSearchNode.GetCurrentBoard() + " Manhatan Distance: "
//                                    + s.prevSearchNode.GetCurrentBoard().manhattan() + " The Rank of the Node that matched the neighbor: " + gameTree.rank(s));
//                            SearchNode f =  gameTree.floor(s);
//                            SearchNode c =  gameTree.ceiling(s);
//                            StdOut.println(" Floor Node: " + f.GetCurrentBoard() + " Manhattan Distance: " + f.manhattan + " Rank of floor of s: " + gameTree.rank(f));
//                            StdOut.println(" Ceiling Node: " + c.GetCurrentBoard() + " Manhattan Distance: " + c.manhattan + " Rank of celing of s: " + gameTree.rank(c));
//                            while (!minSearchNode.GetCurrentBoard().equals(gBoard)) {
// Note: Some nodes in the GameTree do not have a previous node, so you may have to use the floor or ceiling to get
// to the goal node or you may have to look at the neighbors again
//                            while (s.prevSearchNode != null) {
//                                minSearchNode = new SearchNode(s.prevSearchNode.GetCurrentBoard(), minSearchNode.numOfMoves + 1,
//                                        minSearchNode);
//                                if (minSearchNode.GetCurrentBoard().isGoal()) {
//                                    break outerloop;
//                                }
//                            }
//                            if (minSearchNode.GetCurrentBoard().isGoal()) {
//                                break outerloop;
//                            }
//                        }
//                    }
//                    if (minSearchNode.GetCurrentBoard().isGoal()) {
//                        break outerloop;
//                    }
//                    StdOut.println("Adding to minimum priority queue.");
//                    StdOut.println("Adding neighbor Board : " + b.toString() + " with hamming distance of :  " + b.hamming() + " and manhattan distance of:  " + b.manhattan() + " Number of moves: " + temp1.numOfMoves + " To priority queue and the Game Tree.");
//                    StdOut.println("Adding neighbor Board with hamming distance of :  " + b.hamming() + " and manhattan distance of:  " + b.manhattan() + " Current moves count: " + moves + " To priority queue");
//                    StdOut.println("Adding neighbor Board : " + b.toString() + " with hamming distance of :  " + b.hamming() +
//                            " and manhattan distance of:  " + b.manhattan() + " Number of moves: " + temp1.numOfMoves +
//                            " To priority queue.");
                    currentPriorityQueue.insert(temp1);
//                    gameTree.put(temp1, gMTreeIndex);
//                    gMTreeIndex++;
                    if (minSearchNode.GetCurrentBoard().equals(gBoard)) {
                        //StdOut.println("minSearchNode is the goal. Coming out of the loop.");
                        break outerloop;
                    }
//                    if (b.equals(gBoard)) {
//                        minSearchNode = temp1;
//                        StdOut.println("One of the neighbors is the goal. Added the goal to the tree. Coming out of the loop.");
//                        break outerloop;
//                    }
                }
//                // calculate and add all the neighbors of nodes in the gameTree
//                for (SearchNode s : gameTree.keys()) {
//
//                    for (Board bG : s.GetCurrentBoard().neighbors()) {
//                        SearchNode sG;
//                        if (!bG.equals(s.GetPrevSearchNode().GetCurrentBoard())) {
//                            sG = new SearchNode(bG, s.numOfMoves + 1, s);
//                            gameTree.put(sG, gMTreeIndex);
//                            gMTreeIndex++;
//                        }
//                    }
//                }
            }
            //currentPriorityQueue.insert( gameTree.min());
//            StdOut.println("Here are all the search nodes in the Priority Queue.");
//            for (SearchNode s : currentPriorityQueue) {
//                StdOut.print(s.GetCurrentBoard() + " Priority: " + s.GetPriority() + " Manhattan: " + s.manhattan + " Hamming " + s.hamming + " Number of moves: " + s.numOfMoves);
//            }
            //Object o = gameTree.min();
            //minSearchNode =  o;
//            if (!currentPriorityQueueTwin.isEmpty()) {
//                if (currentPriorityQueueTwin.size() > 9000) {
//                    StdOut.println(" reseting minTwinPriorityQueue ");
//                    MinPQ<SearchNode> nodeTwinPriority = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
//                        @Override
//                        public int compare(SearchNode o1, SearchNode o2) {
//                            if (o1.GetPriority() > o2.GetPriority()) return 1;
//                            else if (o2.GetPriority() > o1.GetPriority()) return -1;
//                            return 0;
//                        }
//                    });
//                    //currentPriorityQueue.insert( gameTree.min());
////                    for (Object o : gameTree.keys()) {
////                        SearchNode s =  o;
////                        if (gameTree.rank(s) <= 2)
////                            currentPriorityQueue.insert(s);
////                    }
////                    gameTree = new GameTree();
////                    MinPQ<SearchNode> hammingSoloPriority = new MinPQ<>(new Comparator<SearchNode>() {
////                        @Override
////                        public int compare(SearchNode o1, SearchNode o2) {
////                            if (o1.hamming > o2.hamming) return 1;
////                            else if (o2.hamming > o1.hamming) return -1;
////                            return 0;
////                        }
////                    });
////                    MinPQ<SearchNode> naturalOrder = new MinPQ<>();
////                    MinPQ<SearchNode> numberOfMovesSolo = new MinPQ<>(new Comparator<SearchNode>() {
////                        @Override
////                        public int compare(SearchNode o1, SearchNode o2) {
////                            if (o1.numOfMoves > o2.numOfMoves) return 1;
////                            else if (o2.numOfMoves > o1.numOfMoves) return -1;
////                            else return 0;
////                        }
////                    });
//                    for (int i = 0; i < 5000; i++) {
//                        nodeTwinPriority.insert(currentPriorityQueueTwin.delMin());
//                        //hammingSoloPriority.insert(currentPriorityQueue.delMin());
//                        //naturalOrder.insert(currentPriorityQueue.delMin());
//                        //numberOfMovesSolo.insert(currentPriorityQueue.delMin());
//                    }
//                    for (int i = 0; i < 2000; i++) {
//                        hammingSoloPriority.insert(manhattanSoloPriority.delMin());
//                    }
//                    for (int i = 0; i < 1000; i++) {
//                        numberOfMovesSolo.insert(hammingSoloPriority.delMin());
//                    }
            //currentPriorityQueue = manhattanSoloPriority;
            //currentPriorityQueue = hammingSoloPriority;
            //currentPriorityQueue = naturalOrder;
//                    currentPriorityQueueTwin = nodeTwinPriority;
//                }
            minTwinNode = currentPriorityQueueTwin.delMin();
            //}

            if (!currentPriorityQueue.isEmpty()) {
//                if (currentPriorityQueue.size() > 9000) {
//                    //StdOut.println(" reseting minPriorityQueue ");
//                    MinPQ<SearchNode> nodePriority = new MinPQ<SearchNode>(new Comparator<SearchNode>() {
//                        @Override
//                        public int compare(SearchNode o1, SearchNode o2) {
//                            if (o1.GetPriority() > o2.GetPriority()) return 1;
//                            else if (o2.GetPriority() > o1.GetPriority()) return -1;
//                            return 0;
//                        }
//                    });
                //currentPriorityQueue.insert( gameTree.min());
//                    for (Object o : gameTree.keys()) {
//                        SearchNode s =  o;
//                        if (gameTree.rank(s) <= 2)
//                            currentPriorityQueue.insert(s);
//                    }
//                    gameTree = new GameTree();
//                    MinPQ<SearchNode> hammingSoloPriority = new MinPQ<>(new Comparator<SearchNode>() {
//                        @Override
//                        public int compare(SearchNode o1, SearchNode o2) {
//                            if (o1.hamming > o2.hamming) return 1;
//                            else if (o2.hamming > o1.hamming) return -1;
//                            return 0;
//                        }
//                    });
//                    MinPQ<SearchNode> naturalOrder = new MinPQ<>();
//                    MinPQ<SearchNode> numberOfMovesSolo = new MinPQ<>(new Comparator<SearchNode>() {
//                        @Override
//                        public int compare(SearchNode o1, SearchNode o2) {
//                            if (o1.numOfMoves > o2.numOfMoves) return 1;
//                            else if (o2.numOfMoves > o1.numOfMoves) return -1;
//                            else return 0;
//                        }
//                    });
//                    for (int i = 0; i < 5000; i++) {
//                        nodePriority.insert(currentPriorityQueue.delMin());
//                        //hammingSoloPriority.insert(currentPriorityQueue.delMin());
//                        //naturalOrder.insert(currentPriorityQueue.delMin());
//                        //numberOfMovesSolo.insert(currentPriorityQueue.delMin());
//                    }
//                    for (int i = 0; i < 2000; i++) {
//                        hammingSoloPriority.insert(manhattanSoloPriority.delMin());
//                    }
//                    for (int i = 0; i < 1000; i++) {
//                        numberOfMovesSolo.insert(hammingSoloPriority.delMin());
//                    }
                //currentPriorityQueue = manhattanSoloPriority;
                //currentPriorityQueue = hammingSoloPriority;
                //currentPriorityQueue = naturalOrder;
//                    currentPriorityQueue = nodePriority;
//
                minSearchNode = currentPriorityQueue.delMin();
                //gameTree.delete(minSearchNode);
                // When the manhattan of minimum search node increases, see if game tree has another node with the same
                // Manhattan and prefrably lower number of moves to switch to.
                // reset the game tree to nodes with initial node's manhattan priority ( i.e. Manhattan + number of moves )
                //StdOut.println("Here is minimum searchnode's rank: " + gameTree.rank(minSearchNode));
//                if (minSearchNode.manhattan > minSearchNode.GetPrevSearchNode().manhattan) {
//                    StdOut.println("Backtracking.");
//                    for (SearchNode s : gameTree.keys()) {
                //StdOut.println("Node:" + s.GetCurrentBoard() + "Node's ceiling: " + gameTree.ceiling(s).GetCurrentBoard() + "Node's floor: ");
//                        if (s.GetPriority() < minSearchNode.GetPriority()) {
//                            minSearchNode = s;
//                        }
//                    }
//                }


                // Also reset the minimum search node if it gets larger than 2000
//                StdOut.println();
//                StdOut.println(" Here is the current size of Priority Queue: " + currentPriorityQueue.size() + " And here is the size of the Binary Search Tree: " + gameTree.size());


//                StdOut.println("Here is the node I am removing from the Priority Queue.");
//                StdOut.print(minSearchNode.GetCurrentBoard() + " Priority: " + minSearchNode.GetPriority() + " Manhattan: " + minSearchNode.manhattan + " Hamming " + minSearchNode.hamming + " Number of moves: " + minSearchNode.numOfMoves);
                //} //else StdOut.println("Priority queue is empty.");
//            StdOut.println("Here are all the search nodes in the Twin Priority Queue.");
//            for (SearchNode s : currentPriorityQueueTwin) {
//                StdOut.println(s.GetCurrentBoard());
//            }
//            if (!currentPriorityQueueTwin.isEmpty()) {
//                //prevTwinSearchNode = minSearchNode;
//                minSearchNodeTwin = currentPriorityQueueTwin.delMin();
//                StdOut.println("Taking " + minSearchNodeTwin.GetCurrentBoard().toString() + " twin minimum search node with hamming: " +
//                        minSearchNode.GetCurrentBoard().hamming() + "with manhattan: " + minSearchNode.GetCurrentBoard().manhattan() +
//                        "out of Twin Priority Queue");
//            } else StdOut.println("Twin priority queue is empty.");
            }
        }
        //moves = gameTree.rank(minSearchNode);
        if (minSearchNode.GetCurrentBoard().isGoal()) {
            solvable = true;
            moves = minSearchNode.numOfMoves;
            //solutionList.add(minSearchNode);
            //solutionBoardList.add(minSearchNode.GetCurrentBoard());
            while (!minSearchNode.GetCurrentBoard().equals(initialBoard)) {
                //gameTree.put(minSearchNode, minSearchNode.numOfMoves);
                solutionList.add(minSearchNode);
                solutionBoardList.add(minSearchNode.GetCurrentBoard());
                minSearchNode = minSearchNode.GetPrevSearchNode();
            }
            solutionList.add(initialSearchNode);
            solutionBoardList.add(initialBoard);
            // This is the loop that counts backwards to get the number of moves to source and it works

//            while (!minSearchNode.GetCurrentBoard().equals(initialBoard)) {
//
//                solutionBoardList.add(minSearchNode.GetCurrentBoard());
//                moves++;
//            }
//            StdOut.println("Here is what was in the Search Tree. ");
//            for (Object o : gameTree.keys()) {
//                SearchNode s =  o;
//                StdOut.println("Node: " + s.currentBoard);
//            }
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
