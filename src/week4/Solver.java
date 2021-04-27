import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Deque;
import java.util.LinkedList;

public class Solver {

    private boolean isSolvable;
    private final MinPQ<SearchNode> PQ; // Priority Queue
    private SearchNode solution;

    // Create Search Node Class to insert into priority Queue
    private class SearchNode implements Comparable<SearchNode> {

        private final SearchNode previous;
        private final Board board;
        private final int steps;

        SearchNode(Board board, int steps, SearchNode previous) {
            this.board = board;
            this.steps = steps;
            this.previous = previous;
        }

        @Override
        public int compareTo(SearchNode that) {
            return this.priority() - that.priority();
        }

        public int priority() {
            return board.manhattan() + steps;
        }

        public Board getBoard() {
            return board;
        }

        public int getsteps() {
            return steps;
        }

        public SearchNode previous() {
            return previous;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // Null Check
        if (initial == null) throw new IllegalArgumentException();

        // Insert Initial Node
        solution = null;
        PQ = new MinPQ<>();
        PQ.insert(new SearchNode(initial, 0, null));

        // A* algorithm
        
        while (true) { 
            SearchNode currentNode = PQ.delMin();
            Board currentBoard = currentNode.getBoard();

            if (currentNode.board.isGoal()) {
                isSolvable = true; 
                solution = currentNode;
                break;
            }

            if (currentBoard.twin().isGoal() && currentBoard.hamming() == 2) {
                isSolvable = false;
                break;
            }
            
            int steps = currentNode.getsteps();
            Board previousBoard = steps > 0? currentNode.previous().getBoard() : null;

            for (Board b: currentBoard.neighbors()) {
                if (previousBoard != null && b.equals(previousBoard)) {
                    continue;
                }
                SearchNode newNode = new SearchNode(b, steps + 1, currentNode);
                PQ.insert(newNode);
            }
        }
        
    }   




    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return solution.getsteps();
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Deque<Board> solutionIterable = new LinkedList<>();
        SearchNode node = solution;
        while (node != null) {
            solutionIterable.addFirst(node.getBoard());
            node = node.previous();
        }
        return solutionIterable;
    }


    // test client (see below) 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
    
        // solve the puzzle
        Solver solver = new Solver(initial);
    
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
